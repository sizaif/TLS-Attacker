/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.tlsattacker.core.protocol.message.SSL2ServerVerifyMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.SSL2ServerVerifyParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.SSL2ServerVerifyPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.HandshakeMessageSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

public class SSL2ServerVerifyHandler extends HandshakeMessageHandler<SSL2ServerVerifyMessage> {

    public SSL2ServerVerifyHandler(TlsContext context) {
        super(context);
    }

    @Override
    public SSL2ServerVerifyParser getParser(byte[] message, int pointer) {
        return new SSL2ServerVerifyParser(message, pointer, tlsContext.getChooser().getSelectedProtocolVersion(),
            tlsContext.getConfig());
    }

    @Override
    public SSL2ServerVerifyPreparator getPreparator(SSL2ServerVerifyMessage message) {
        return new SSL2ServerVerifyPreparator(tlsContext.getChooser(), message);
    }

    @Override
    public void adjustTLSContext(SSL2ServerVerifyMessage message) {
    }

    @Override
    public HandshakeMessageSerializer<SSL2ServerVerifyMessage> getSerializer(SSL2ServerVerifyMessage message) {
        // We currently don't send ServerVerify messages, only receive them.
        return null;
    }

}
