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
import de.rub.nds.tlsattacker.core.protocol.message.extension.HeartbeatExtensionMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HeartbeatExtensionSerializer extends ExtensionSerializer<HeartbeatExtensionMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final HeartbeatExtensionMessage msg;

    public HeartbeatExtensionSerializer(HeartbeatExtensionMessage message) {
        super(message);
        this.msg = message;
    }

    @Override
    public byte[] serializeExtensionContent() {
        LOGGER.debug("Serializing HeartbeatExtensionMessage");
        writeHeartbeatMode(msg);
        return getAlreadySerialized();
    }

    private void writeHeartbeatMode(HeartbeatExtensionMessage msg) {
        appendBytes(msg.getHeartbeatMode().getValue());
        LOGGER.debug("HeartbeatMode: " + ArrayConverter.bytesToHexString(msg.getHeartbeatMode().getValue()));
    }
}
