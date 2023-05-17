/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.ApplicationMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplicationMessageSerializer extends TlsMessageSerializer<ApplicationMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Constructor for the ApplicationMessageSerializer
     *
     * @param message
     *                Message that should be serialized
     * @param version
     *                Version of the Protocol
     */
    public ApplicationMessageSerializer(ApplicationMessage message, ProtocolVersion version) {
        super(message, version);
    }

    @Override
    public byte[] serializeProtocolMessageContent() {
        LOGGER.debug("Serializing ApplicationMessage");
        writeData();
        return getAlreadySerialized();
    }

    /**
     * Writes the data of the ApplicationMessage into the final byte[]
     */
    private void writeData() {
        appendBytes(message.getData().getValue());
        LOGGER.debug("Data: " + ArrayConverter.bytesToHexString(message.getData().getValue()));
    }

}
