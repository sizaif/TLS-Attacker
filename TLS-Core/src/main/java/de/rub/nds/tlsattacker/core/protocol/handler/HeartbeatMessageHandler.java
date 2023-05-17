/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.tlsattacker.core.protocol.message.HeartbeatMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.HeartbeatMessageParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.HeartbeatMessagePreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.HeartbeatMessageSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

/**
 * Handler for Heartbeat messages: http://tools.ietf.org/html/rfc6520#page-4
 */
public class HeartbeatMessageHandler extends TlsMessageHandler<HeartbeatMessage> {

    public HeartbeatMessageHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    @Override
    public HeartbeatMessageParser getParser(byte[] message, int pointer) {
        return new HeartbeatMessageParser(pointer, message, tlsContext.getChooser().getSelectedProtocolVersion(),
            tlsContext.getConfig());
    }

    @Override
    public HeartbeatMessagePreparator getPreparator(HeartbeatMessage message) {
        return new HeartbeatMessagePreparator(tlsContext.getChooser(), message);
    }

    @Override
    public HeartbeatMessageSerializer getSerializer(HeartbeatMessage message) {
        return new HeartbeatMessageSerializer(message, tlsContext.getChooser().getSelectedProtocolVersion());
    }

    @Override
    public void adjustTLSContext(HeartbeatMessage message) {
        // TODO perhaps something to do here
    }
}
