package de.rub.nds.tlsattacker.core.protocol.message;

import de.rub.nds.modifiablevariable.HoldsModifiableVariable;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.crypto.keyexchange.TLSGostKeyTransportBlob;
import de.rub.nds.tlsattacker.core.protocol.ModifiableVariableHolder;
import de.rub.nds.tlsattacker.core.protocol.handler.GOSTClientKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.ProtocolMessageHandler;
import de.rub.nds.tlsattacker.core.protocol.message.computations.GOSTClientComputations;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GOSTClientKeyExchangeMessage extends ClientKeyExchangeMessage {

    @HoldsModifiableVariable
    @XmlElement
    protected GOSTClientComputations computations;

    @ModifiableVariableProperty(format = ModifiableVariableProperty.Format.ASN1, type = ModifiableVariableProperty.Type.KEY_MATERIAL)
    private TLSGostKeyTransportBlob keyTransportBlob;

    public void setKeyTransportBlob(TLSGostKeyTransportBlob keyTransportBlob) {
        this.keyTransportBlob = keyTransportBlob;
    }

    public TLSGostKeyTransportBlob getKeyTransportBlob() {
        return keyTransportBlob;
    }

    @Override
    public GOSTClientComputations getComputations() {
        return computations;
    }

    public GOSTClientKeyExchangeMessage() {
        super();
    }

    public GOSTClientKeyExchangeMessage(Config tlsConfig) {
        super(tlsConfig);
    }

    @Override
    public void prepareComputations() {
        if (computations != null) {
            computations = new GOSTClientComputations();
        }
    }

    @Override
    public String toCompactString() {
        return "GOST_CLIENT_KEY_EXCHANGE";
    }

    @Override
    public ProtocolMessageHandler getHandler(TlsContext context) {
        return new GOSTClientKeyExchangeHandler(context);
    }

    @Override
    public List<ModifiableVariableHolder> getAllModifiableVariableHolders() {
        List<ModifiableVariableHolder> holders = super.getAllModifiableVariableHolders();
        if (computations != null) {
            holders.add(computations);
        }
        return holders;
    }

}
