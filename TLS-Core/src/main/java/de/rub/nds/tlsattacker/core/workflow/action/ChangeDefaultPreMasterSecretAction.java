/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.workflow.action;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.UnformattedByteArrayAdapter;
import de.rub.nds.tlsattacker.core.exceptions.WorkflowExecutionException;
import de.rub.nds.tlsattacker.core.state.State;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 */
@XmlRootElement
public class ChangeDefaultPreMasterSecretAction extends ConnectionBoundAction {

    private static final Logger LOGGER = LogManager.getLogger();

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] newValue = null;
    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] oldValue = null;

    public ChangeDefaultPreMasterSecretAction(byte[] newValue) {
        super();
        this.newValue = newValue;
    }

    public ChangeDefaultPreMasterSecretAction() {
    }

    public void setNewValue(byte[] newValue) {
        this.newValue = newValue;
    }

    public byte[] getNewValue() {
        return newValue;
    }

    public byte[] getOldValue() {
        return oldValue;
    }

    @Override
    public void execute(State state) throws WorkflowExecutionException {
        TlsContext tlsContext = state.getTlsContext(getConnectionAlias());

        if (isExecuted()) {
            throw new WorkflowExecutionException("Action already executed!");
        }
        oldValue = tlsContext.getConfig().getDefaultPreMasterSecret();
        tlsContext.getConfig().setDefaultPreMasterSecret(newValue);
        LOGGER.info("Changed DefaultPreMasterSecret from " + ArrayConverter.bytesToHexString(oldValue)
            + " in config to " + ArrayConverter.bytesToHexString(newValue));
        setExecuted(true);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Arrays.hashCode(this.newValue);
        hash = 11 * hash + Arrays.hashCode(this.oldValue);
        return hash;
    }

    @Override
    public void reset() {
        oldValue = null;
        setExecuted(null);
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
        final ChangeDefaultPreMasterSecretAction other = (ChangeDefaultPreMasterSecretAction) obj;
        if (!Arrays.equals(this.newValue, other.newValue)) {
            return false;
        }
        return Arrays.equals(this.oldValue, other.oldValue);
    }

    @Override
    public boolean executedAsPlanned() {
        return isExecuted();
    }
}
