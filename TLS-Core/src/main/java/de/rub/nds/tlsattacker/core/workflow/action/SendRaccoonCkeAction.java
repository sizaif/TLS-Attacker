/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.workflow.action;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.Modifiable;
import de.rub.nds.tlsattacker.core.exceptions.WorkflowExecutionException;
import de.rub.nds.tlsattacker.core.protocol.ModifiableVariableHolder;
import de.rub.nds.tlsattacker.core.protocol.message.DHClientKeyExchangeMessage;
import de.rub.nds.tlsattacker.core.protocol.message.DtlsHandshakeMessageFragment;
import de.rub.nds.tlsattacker.core.protocol.ProtocolMessage;
import de.rub.nds.tlsattacker.core.record.AbstractRecord;
import de.rub.nds.tlsattacker.core.state.State;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import de.rub.nds.tlsattacker.core.workflow.action.executor.ActionOption;
import de.rub.nds.tlsattacker.core.workflow.action.executor.MessageActionResult;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@XmlRootElement
public class SendRaccoonCkeAction extends MessageAction implements SendingAction {

    private static final Logger LOGGER = LogManager.getLogger();

    private boolean withNullByte = true;

    private BigInteger initialSecret = new BigInteger("" + 5000);

    public SendRaccoonCkeAction() {
        super();
    }

    public SendRaccoonCkeAction(boolean withNullByte, BigInteger initialSecret) {
        super();
        this.withNullByte = withNullByte;
        this.initialSecret = initialSecret;
    }

    public SendRaccoonCkeAction(String connectionAlias) {
        super(connectionAlias);
    }

    public BigInteger getInitialSecret() {
        return initialSecret;
    }

    public void setInitialSecret(BigInteger initialSecret) {
        this.initialSecret = initialSecret;
    }

    public boolean isWithNullByte() {
        return withNullByte;
    }

    public void setWithNullByte(boolean withNullByte) {
        this.withNullByte = withNullByte;
    }

    @Override
    public void execute(State state) throws WorkflowExecutionException {
        TlsContext tlsContext = state.getTlsContext(connectionAlias);

        if (isExecuted()) {
            throw new WorkflowExecutionException("Action already executed!");
        }
        messages = new LinkedList<>();
        messages.add(generateRaccoonDhClientKeyExchangeMessage(state, withNullByte));
        String sending = getReadableString(messages);
        if (hasDefaultAlias()) {
            LOGGER.info("Sending Raccoon Cke message " + (withNullByte ? "(withNullByte)" : "(withoutNullByte)") + ": "
                + sending);
        } else {
            LOGGER.info("Sending Raccoon Cke message " + (withNullByte ? "(withNullByte)" : "(withoutNullByte)") + ": ("
                + connectionAlias + "): " + sending);
        }

        try {
            MessageActionResult result = sendMessageHelper.sendMessages(messages, fragments, records, tlsContext);
            messages = new ArrayList<>(result.getMessageList());
            records = new ArrayList<>(result.getRecordList());
            if (result.getMessageFragmentList() != null) {
                fragments = new ArrayList<>(result.getMessageFragmentList());
            }
            setExecuted(true);
        } catch (IOException e) {
            tlsContext.setReceivedTransportHandlerException(true);
            LOGGER.debug(e);
            setExecuted(getActionOptions().contains(ActionOption.MAY_FAIL));
        }
    }

    private DHClientKeyExchangeMessage generateRaccoonDhClientKeyExchangeMessage(State state, boolean withNullByte) {

        DHClientKeyExchangeMessage cke = new DHClientKeyExchangeMessage(state.getConfig());
        Chooser chooser = state.getTlsContext().getChooser();
        byte[] clientPublicKey = getClientPublicKey(chooser.getServerDhGenerator(), chooser.getServerDhModulus(),
            chooser.getServerDhPublicKey(), initialSecret, withNullByte);
        cke.setPublicKey(Modifiable.explicit(clientPublicKey));
        return cke;
    }

    private byte[] getClientPublicKey(BigInteger g, BigInteger m, BigInteger serverPublicKey,
        BigInteger initialClientDhSecret, boolean withNullByte) {
        int length = ArrayConverter.bigIntegerToByteArray(m).length;
        byte[] pms =
            ArrayConverter.bigIntegerToNullPaddedByteArray(serverPublicKey.modPow(initialClientDhSecret, m), length);

        if (((withNullByte && pms[0] == 0) && pms[1] != 0) || (!withNullByte && pms[0] != 0)) {
            BigInteger clientPublicKey = g.modPow(initialClientDhSecret, m);
            byte[] cke = ArrayConverter.bigIntegerToByteArray(clientPublicKey);
            if (cke.length == length) {
                return cke;
            }
        }
        initialClientDhSecret = initialClientDhSecret.add(new BigInteger("1"));
        return getClientPublicKey(g, m, serverPublicKey, initialClientDhSecret, withNullByte);
    }

    @Override
    public String toString() {
        StringBuilder sb;
        if (isExecuted()) {
            sb = new StringBuilder("Send Dynamic Client Key Exchange Action:\n");
        } else {
            sb = new StringBuilder("Send Dynamic Client Key Exchange Action: (not executed)\n");
        }
        sb.append("\tMessages:");
        if (messages != null) {
            for (ProtocolMessage message : messages) {
                sb.append(message.toCompactString());
                sb.append(", ");
            }
            sb.append("\n");
        } else {
            sb.append("null (no messages set)");
        }
        return sb.toString();
    }

    @Override
    public String toCompactString() {
        StringBuilder sb = new StringBuilder(super.toCompactString());
        if ((messages != null) && (!messages.isEmpty())) {
            sb.append(" (");
            for (ProtocolMessage message : messages) {
                sb.append(message.toCompactString());
                sb.append(",");
            }
            sb.deleteCharAt(sb.lastIndexOf(",")).append(")");
        } else {
            sb.append(" (no messages set)");
        }
        return sb.toString();
    }

    @Override
    public boolean executedAsPlanned() {
        return isExecuted();
    }

    @Override
    public void setRecords(List<AbstractRecord> records) {
        this.records = records;
    }

    @Override
    public void setFragments(List<DtlsHandshakeMessageFragment> fragments) {
        this.fragments = fragments;
    }

    @Override
    public void reset() {
        List<ModifiableVariableHolder> holders = new LinkedList<>();
        if (messages != null) {
            for (ProtocolMessage message : messages) {
                holders.addAll(message.getAllModifiableVariableHolders());
            }
        }
        if (getRecords() != null) {
            for (AbstractRecord record : getRecords()) {
                holders.addAll(record.getAllModifiableVariableHolders());
            }
        }
        if (getFragments() != null) {
            for (DtlsHandshakeMessageFragment fragment : getFragments()) {
                holders.addAll(fragment.getAllModifiableVariableHolders());
            }
        }
        for (ModifiableVariableHolder holder : holders) {
            List<Field> fields = holder.getAllModifiableVariableFields();
            for (Field f : fields) {
                f.setAccessible(true);

                ModifiableVariable mv = null;
                try {
                    mv = (ModifiableVariable) f.get(holder);
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    LOGGER.warn("Could not retrieve ModifiableVariables");
                    LOGGER.debug(ex);
                }
                if (mv != null) {
                    if (mv.getModification() != null || mv.isCreateRandomModification()) {
                        mv.setOriginalValue(null);
                    } else {
                        try {
                            f.set(holder, null);
                        } catch (IllegalArgumentException | IllegalAccessException ex) {
                            LOGGER.warn("Could not strip ModifiableVariable without Modification");
                        }
                    }
                }
            }
        }
        setExecuted(null);
    }

    @Override
    public List<ProtocolMessage> getSendMessages() {
        return messages;
    }

    @Override
    public List<AbstractRecord> getSendRecords() {
        return records;
    }

    @Override
    public List<DtlsHandshakeMessageFragment> getSendFragments() {
        return fragments;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SendDynamicClientKeyExchangeAction other = (SendDynamicClientKeyExchangeAction) obj;
        if (!Objects.equals(this.messages, other.messages)) {
            return false;
        }
        if (!Objects.equals(this.records, other.records)) {
            return false;
        }
        if (!Objects.equals(this.fragments, other.fragments)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 67 * hash + Objects.hashCode(this.messages);
        hash = 67 * hash + Objects.hashCode(this.records);
        hash = 67 * hash + Objects.hashCode(this.fragments);
        return hash;
    }

    @Override
    public MessageActionDirection getMessageDirection() {
        return MessageActionDirection.SENDING;
    }
}
