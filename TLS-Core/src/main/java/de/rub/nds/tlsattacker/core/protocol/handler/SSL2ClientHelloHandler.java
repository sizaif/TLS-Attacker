/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.tlsattacker.core.protocol.message.SSL2ClientHelloMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.SSL2ClientHelloParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.SSL2ClientHelloPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.SSL2ClientHelloSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

public class SSL2ClientHelloHandler extends HandshakeMessageHandler<SSL2ClientHelloMessage> {

    public SSL2ClientHelloHandler(TlsContext context) {
        super(context);
    }

    @Override
    public SSL2ClientHelloParser getParser(byte[] message, int pointer) {
        return new SSL2ClientHelloParser(pointer, message, tlsContext.getChooser().getSelectedProtocolVersion(),
            tlsContext.getConfig());
    }

    @Override
    public SSL2ClientHelloPreparator getPreparator(SSL2ClientHelloMessage message) {
        return new SSL2ClientHelloPreparator(tlsContext.getChooser(), message);
    }

    @Override
    public SSL2ClientHelloSerializer getSerializer(SSL2ClientHelloMessage message) {
        return new SSL2ClientHelloSerializer(message, tlsContext.getChooser().getSelectedProtocolVersion());
    }

    @Override
    public void adjustTLSContext(SSL2ClientHelloMessage message) {
        tlsContext.setClientRandom(message.getChallenge().getValue());
    }

}
