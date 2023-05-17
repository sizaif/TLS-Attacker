/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler.extension;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.HeartbeatMode;
import de.rub.nds.tlsattacker.core.exceptions.AdjustmentException;
import de.rub.nds.tlsattacker.core.protocol.message.extension.HeartbeatExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.HeartbeatExtensionParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.extension.HeartbeatExtensionPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.HeartbeatExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HeartbeatExtensionHandler extends ExtensionHandler<HeartbeatExtensionMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    public HeartbeatExtensionHandler(TlsContext context) {
        super(context);
    }

    @Override
    public void adjustTLSExtensionContext(HeartbeatExtensionMessage message) {
        byte[] heartbeatMode = message.getHeartbeatMode().getValue();
        if (heartbeatMode.length != 1) {
            throw new AdjustmentException("Cannot set HeartbeatMode to a reasonable Value");
        }
        HeartbeatMode mode = HeartbeatMode.getHeartbeatMessageType(heartbeatMode[0]);
        if (mode == null) {
            LOGGER.warn("Unknown HeartbeatMode: " + ArrayConverter.bytesToHexString(heartbeatMode));
        } else {
            context.setHeartbeatMode(mode);
        }
    }

    @Override
    public HeartbeatExtensionParser getParser(byte[] message, int pointer, Config config) {
        return new HeartbeatExtensionParser(pointer, message, config);
    }

    @Override
    public HeartbeatExtensionPreparator getPreparator(HeartbeatExtensionMessage message) {
        return new HeartbeatExtensionPreparator(context.getChooser(), message, getSerializer(message));
    }

    @Override
    public HeartbeatExtensionSerializer getSerializer(HeartbeatExtensionMessage message) {
        return new HeartbeatExtensionSerializer(message);
    }
}
