/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.tokenbinding;

import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.serializer.TlsMessageSerializer;

public class TokenBindingMessageSerializer extends TlsMessageSerializer<TokenBindingMessage> {

    private final TokenBindingMessage message;

    public TokenBindingMessageSerializer(TokenBindingMessage message, ProtocolVersion version) {
        super(message, version);
        this.message = message;
    }

    @Override
    public byte[] serializeProtocolMessageContent() {
        appendInt(message.getTokenbindingsLength().getValue(), TokenBindingLength.TOKENBINDINGS);
        serializeBinding();
        return getAlreadySerialized();
    }

    public byte[] serializeKey() {
        if (message.getPoint() != null && message.getPoint().getValue() != null) {
            appendInt(message.getPointLength().getValue(), TokenBindingLength.POINT);
            appendBytes(message.getPoint().getValue());
        } else {
            appendInt(message.getModulusLength().getValue(), TokenBindingLength.MODULUS);
            appendBytes(message.getModulus().getValue());
            appendInt(message.getPublicExponentLength().getValue(), TokenBindingLength.PUBLIC_EXPONENT);
            appendBytes(message.getPublicExponent().getValue());
        }
        return getAlreadySerialized();
    }

    public byte[] serializeBinding() {
        appendByte(message.getTokenbindingType().getValue());
        appendByte(message.getKeyParameter().getValue());
        appendInt(message.getKeyLength().getValue(), TokenBindingLength.KEY);
        serializeKey();
        appendInt(message.getSignatureLength().getValue(), TokenBindingLength.SIGNATURE);
        appendBytes(message.getSignature().getValue());
        appendInt(message.getExtensionLength().getValue(), TokenBindingLength.EXTENSIONS);
        appendBytes(message.getExtensionBytes().getValue());
        return getAlreadySerialized();
    }

}
