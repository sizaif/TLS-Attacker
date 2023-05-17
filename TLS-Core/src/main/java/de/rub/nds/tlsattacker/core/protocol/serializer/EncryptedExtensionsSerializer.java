/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer;

import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.EncryptedExtensionsMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EncryptedExtensionsSerializer extends HandshakeMessageSerializer<EncryptedExtensionsMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final EncryptedExtensionsMessage msg;

    public EncryptedExtensionsSerializer(EncryptedExtensionsMessage message, ProtocolVersion version) {
        super(message, version);
        this.msg = message;
    }

    @Override
    public byte[] serializeHandshakeMessageContent() {
        LOGGER.debug("Serializing EncryptedExtensionsMessage");
        if (hasExtensionLengthField()) {
            writeExtensionLength();
            if (hasExtensions()) {
                writeExtensionBytes();
            }
        }
        return getAlreadySerialized();
    }

}
