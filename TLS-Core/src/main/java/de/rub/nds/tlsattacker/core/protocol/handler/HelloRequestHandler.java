/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.tlsattacker.core.protocol.message.HelloRequestMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.HelloRequestParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.HelloRequestPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.HelloRequestSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

public class HelloRequestHandler extends HandshakeMessageHandler<HelloRequestMessage> {

    public HelloRequestHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    @Override
    public HelloRequestParser getParser(byte[] message, int pointer) {
        return new HelloRequestParser(pointer, message, tlsContext.getChooser().getSelectedProtocolVersion(),
            tlsContext.getConfig());
    }

    @Override
    public HelloRequestPreparator getPreparator(HelloRequestMessage message) {
        return new HelloRequestPreparator(tlsContext.getChooser(), message);
    }

    @Override
    public HelloRequestSerializer getSerializer(HelloRequestMessage message) {
        return new HelloRequestSerializer(message, tlsContext.getChooser().getSelectedProtocolVersion());
    }

    @Override
    public void adjustTLSContext(HelloRequestMessage message) {
        // we adjust nothing
    }
}
