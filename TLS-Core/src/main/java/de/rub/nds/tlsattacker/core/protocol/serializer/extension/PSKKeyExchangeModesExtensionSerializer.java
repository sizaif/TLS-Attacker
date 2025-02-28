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
import de.rub.nds.tlsattacker.core.protocol.message.extension.PSKKeyExchangeModesExtensionMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * RFC draft-ietf-tls-tls13-21
 */
public class PSKKeyExchangeModesExtensionSerializer extends ExtensionSerializer<PSKKeyExchangeModesExtensionMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final PSKKeyExchangeModesExtensionMessage msg;

    public PSKKeyExchangeModesExtensionSerializer(PSKKeyExchangeModesExtensionMessage message) {
        super(message);
        this.msg = message;
    }

    @Override
    public byte[] serializeExtensionContent() {
        LOGGER.debug("Serializing PSKKeyExchangeModesExtensionMessage");
        writeKeyExchangeModesListLength(msg);
        writeKeyExchangeModesListBytes(msg);
        return getAlreadySerialized();
    }

    private void writeKeyExchangeModesListLength(PSKKeyExchangeModesExtensionMessage msg) {
        appendInt(msg.getKeyExchangeModesListLength().getValue(), ExtensionByteLength.PSK_KEY_EXCHANGE_MODES_LENGTH);
        LOGGER.debug("KeyExchangeModesListLength: " + msg.getKeyExchangeModesListLength().getValue());
    }

    private void writeKeyExchangeModesListBytes(PSKKeyExchangeModesExtensionMessage msg) {
        appendBytes(msg.getKeyExchangeModesListBytes().getValue());
        LOGGER.debug("KeyExchangeModesListBytes: "
            + ArrayConverter.bytesToHexString(msg.getKeyExchangeModesListBytes().getValue()));
    }
}
