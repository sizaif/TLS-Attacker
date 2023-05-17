/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer.extension;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.ExtensionByteLength;
import de.rub.nds.tlsattacker.core.protocol.message.extension.EarlyDataExtensionMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * RFC draft-ietf-tls-tls13-21
 */
public class EarlyDataExtensionSerializer extends ExtensionSerializer<EarlyDataExtensionMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final EarlyDataExtensionMessage msg;

    public EarlyDataExtensionSerializer(EarlyDataExtensionMessage message) {
        super(message);
        this.msg = message;
    }

    @Override
    public byte[] serializeExtensionContent() {
        LOGGER.debug("Serializing EarlyDataExtensionMessage");
        if (msg.isNewSessionTicketExtension()) {
            serializeMaxEarlyData();
        }
        return getAlreadySerialized();
    }

    private void serializeMaxEarlyData() {
        appendInt(msg.getMaxEarlyDataSize().getValue(), ExtensionByteLength.MAX_EARLY_DATA_SIZE_LENGTH);
        LOGGER.debug("MaxEarlyDataSize: " + msg.getMaxEarlyDataSize());
    }

}
