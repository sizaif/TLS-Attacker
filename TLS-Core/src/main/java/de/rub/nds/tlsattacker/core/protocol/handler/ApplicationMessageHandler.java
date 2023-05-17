/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.protocol.message.ApplicationMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.ApplicationMessageParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.ApplicationMessagePreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.ApplicationMessageSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplicationMessageHandler extends TlsMessageHandler<ApplicationMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    public ApplicationMessageHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    @Override
    public ApplicationMessageParser getParser(byte[] message, int pointer) {
        return new ApplicationMessageParser(pointer, message, tlsContext.getChooser().getSelectedProtocolVersion(),
            tlsContext.getConfig());
    }

    @Override
    public ApplicationMessagePreparator getPreparator(ApplicationMessage message) {
        return new ApplicationMessagePreparator(tlsContext.getChooser(), message);
    }

    @Override
    public ApplicationMessageSerializer getSerializer(ApplicationMessage message) {
        return new ApplicationMessageSerializer(message, tlsContext.getChooser().getSelectedProtocolVersion());
    }

    @Override
    public void adjustTLSContext(ApplicationMessage message) {
        tlsContext.setLastHandledApplicationMessageData(message.getData().getValue());
        String readableAppData = ArrayConverter.bytesToHexString(tlsContext.getLastHandledApplicationMessageData());
        if (tlsContext.getTalkingConnectionEndType() == tlsContext.getChooser().getMyConnectionPeer()) {
            LOGGER.debug("Received Data:" + readableAppData);
        } else {
            LOGGER.debug("Send Data:" + readableAppData);
        }
    }
}
