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
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.protocol.handler.DHEServerKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.PskDheServerKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PskDheServerKeyExchange")
public class PskDheServerKeyExchangeMessage extends DHEServerKeyExchangeMessage {

    private ModifiableByteArray identityHint;

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.LENGTH)
    private ModifiableInteger identityHintLength;

    public PskDheServerKeyExchangeMessage() {
        super();
    }

    public PskDheServerKeyExchangeMessage(Config tlsConfig) {
        super(tlsConfig);
    }

    public ModifiableByteArray getIdentityHint() {
        return identityHint;
    }

    public void setIdentityHint(ModifiableByteArray identityHint) {
        this.identityHint = identityHint;
    }

    public void setIdentityHint(byte[] identity) {
        this.identityHint = ModifiableVariableFactory.safelySetValue(this.identityHint, identity);
    }

    public ModifiableInteger getIdentityHintLength() {
        return identityHintLength;
    }

    public void setIdentityHintLength(ModifiableInteger identityHintLength) {
        this.identityHintLength = identityHintLength;
    }

    public void setIdentityHintLength(int identityHintLength) {
        this.identityHintLength = ModifiableVariableFactory.safelySetValue(this.identityHintLength, identityHintLength);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PskDheServerKeyExchangeMessage:");
        sb.append("\n  Modulus p: ");
        if (super.modulus != null && super.modulus.getValue() != null) {
            sb.append(ArrayConverter.bytesToHexString(modulus.getValue()));
        } else {
            sb.append("null");
        }
        sb.append("\n  Generator g: ");
        if (generator != null && generator.getValue() != null) {
            sb.append(ArrayConverter.bytesToHexString(generator.getValue()));
        } else {
            sb.append("null");
        }
        sb.append("\n  Public Key: ");
        if (getPublicKey() != null) {
            sb.append(ArrayConverter.bytesToHexString(getPublicKey().getValue(), false));
        } else {
            sb.append("null");
        }
        return sb.toString();
    }

    @Override
    public DHEServerKeyExchangeHandler<PskDheServerKeyExchangeMessage> getHandler(TlsContext context) {
        return new PskDheServerKeyExchangeHandler(context);
    }

    @Override
    public String toCompactString() {
        return "DHE_PSK_SERVER_KEY_EXCHANGE";
    }

    @Override
    public String toShortString() {
        return "PSK_DHE_CKE";
    }
}
