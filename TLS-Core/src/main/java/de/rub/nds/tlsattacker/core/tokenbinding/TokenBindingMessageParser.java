/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.tokenbinding;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.constants.TokenBindingKeyParameters;
import de.rub.nds.tlsattacker.core.protocol.parser.TlsMessageParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TokenBindingMessageParser extends TlsMessageParser<TokenBindingMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    public TokenBindingMessageParser(int pointer, byte[] array, ProtocolVersion version, Config config) {
        super(pointer, array, version, config);
    }

    @Override
    protected TokenBindingMessage parseMessageContent() {
        TokenBindingMessage message = new TokenBindingMessage();
        message.setTokenbindingsLength(parseIntField(TokenBindingLength.TOKENBINDINGS));
        LOGGER.debug("TokenbindingLength:" + message.getTokenbindingsLength().getValue());
        message.setTokenbindingType(parseByteField(TokenBindingLength.BINDING_TYPE));
        LOGGER.debug("TokenBindingType:" + message.getTokenbindingType().getValue());

        message.setKeyParameter(parseByteField(TokenBindingLength.KEY_PARAMETER));
        LOGGER.debug("KeyParameter:" + message.getKeyParameter().getValue());

        TokenBindingKeyParameters keyParameter =
            TokenBindingKeyParameters.getTokenBindingKeyParameter(message.getKeyParameter().getValue());
        message.setKeyLength(parseIntField(TokenBindingLength.KEY));
        LOGGER.debug("KeyLength:" + message.getKeyLength().getValue());

        if (keyParameter.equals(TokenBindingKeyParameters.ECDSAP256)) {
            message.setPointLength(parseIntField(TokenBindingLength.POINT));
            LOGGER.debug("PointLength:" + message.getPointLength().getValue());

            message.setPoint(parseByteArrayField(message.getPointLength().getValue()));
            LOGGER.debug("Point:" + ArrayConverter.bytesToHexString(message.getPoint().getValue()));

        } else {
            message.setModulusLength(parseIntField(TokenBindingLength.MODULUS));
            message.setModulus(parseByteArrayField(message.getModulusLength().getValue()));
            message.setPublicExponentLength(parseIntField(TokenBindingLength.PUBLIC_EXPONENT));
            message.setPublicExponent(parseByteArrayField(message.getPublicExponentLength().getValue()));
        }
        message.setSignatureLength(parseIntField(TokenBindingLength.SIGNATURE));
        LOGGER.debug("SignatureLength:" + message.getSignatureLength().getValue());

        message.setSignature(parseByteArrayField(message.getSignatureLength().getValue()));
        LOGGER.debug("Signature:" + ArrayConverter.bytesToHexString(message.getSignature().getValue()));

        message.setExtensionLength(parseIntField(TokenBindingLength.EXTENSIONS));
        LOGGER.debug("ExtensionLength:" + message.getExtensionLength().getValue());

        message.setExtensionBytes(parseByteArrayField(message.getExtensionLength().getValue()));
        LOGGER.debug("Extensions:" + ArrayConverter.bytesToHexString(message.getExtensionBytes().getValue()));

        return message;
    }

}
