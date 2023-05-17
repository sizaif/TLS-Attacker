/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.message;

import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.HeartbeatMessageType;
import de.rub.nds.tlsattacker.core.constants.ProtocolMessageType;
import de.rub.nds.tlsattacker.core.protocol.handler.HeartbeatMessageHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.TlsMessageHandler;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Heartbeat")
public class HeartbeatMessage extends TlsMessage {

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.TLS_CONSTANT)
    ModifiableByte heartbeatMessageType;

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.LENGTH)
    ModifiableInteger payloadLength;

    @ModifiableVariableProperty()
    ModifiableByteArray payload;

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.PADDING)
    ModifiableByteArray padding;

    public HeartbeatMessage() {
        super();
        this.protocolMessageType = ProtocolMessageType.HEARTBEAT;
    }

    public HeartbeatMessage(Config tlsConfig) {
        super();
        this.protocolMessageType = ProtocolMessageType.HEARTBEAT;
    }

    public ModifiableByte getHeartbeatMessageType() {
        return heartbeatMessageType;
    }

    public void setHeartbeatMessageType(ModifiableByte heartbeatMessageType) {
        this.heartbeatMessageType = heartbeatMessageType;
    }

    public void setHeartbeatMessageType(byte heartbeatMessageType) {
        this.heartbeatMessageType =
            ModifiableVariableFactory.safelySetValue(this.heartbeatMessageType, heartbeatMessageType);
    }

    public ModifiableInteger getPayloadLength() {
        return payloadLength;
    }

    public void setPayloadLength(ModifiableInteger payloadLength) {
        this.payloadLength = payloadLength;
    }

    public void setPayloadLength(int payloadLength) {
        this.payloadLength = ModifiableVariableFactory.safelySetValue(this.payloadLength, payloadLength);
    }

    public ModifiableByteArray getPayload() {
        return payload;
    }

    public void setPayload(ModifiableByteArray payload) {
        this.payload = payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = ModifiableVariableFactory.safelySetValue(this.payload, payload);
    }

    public ModifiableByteArray getPadding() {
        return padding;
    }

    public void setPadding(ModifiableByteArray padding) {
        this.padding = padding;
    }

    public void setPadding(byte[] padding) {
        this.padding = ModifiableVariableFactory.safelySetValue(this.padding, padding);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HeartbeatMessage:");
        sb.append("\n  Type: ");
        if (heartbeatMessageType != null && heartbeatMessageType.getValue() != null) {
            sb.append(HeartbeatMessageType.getHeartbeatMessageType(heartbeatMessageType.getValue()));
        } else {
            sb.append("null");
        }
        sb.append("\n  Payload Length: ");
        if (payloadLength != null && payloadLength.getValue() != null) {
            sb.append(payloadLength.getValue());
        } else {
            sb.append("null");
        }
        sb.append("\n  Payload: ");
        if (payload != null && payload.getValue() != null) {
            sb.append(ArrayConverter.bytesToHexString(payload.getValue()));
        } else {
            sb.append("null");
        }
        sb.append("\n  Padding: ");
        if (padding != null && padding.getValue() != null) {
            sb.append(ArrayConverter.bytesToHexString(padding.getValue()));
        } else {
            sb.append("null");
        }
        return sb.toString();
    }

    @Override
    public String toCompactString() {
        return "HEARTBEAT";
    }

    @Override
    public String toShortString() {
        return "HB";
    }

    @Override
    public TlsMessageHandler getHandler(TlsContext context) {
        return new HeartbeatMessageHandler(context);
    }
}
