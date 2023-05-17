/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.tlsattacker.core.protocol.message.UnknownHandshakeMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.UnknownHandshakeParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.UnknownHandshakePreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.UnknownHandshakeSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

public class UnknownHandshakeHandler extends HandshakeMessageHandler<UnknownHandshakeMessage> {

    public UnknownHandshakeHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    @Override
    public void adjustTLSContext(UnknownHandshakeMessage message) {
        // nothing to adjust here
    }

    @Override
    public UnknownHandshakeParser getParser(byte[] message, int pointer) {
        return new UnknownHandshakeParser(pointer, message, tlsContext.getChooser().getSelectedProtocolVersion(),
            tlsContext.getConfig());
    }

    @Override
    public UnknownHandshakePreparator getPreparator(UnknownHandshakeMessage message) {
        return new UnknownHandshakePreparator(tlsContext.getChooser(), message);
    }

    @Override
    public UnknownHandshakeSerializer getSerializer(UnknownHandshakeMessage message) {
        return new UnknownHandshakeSerializer(message, tlsContext.getChooser().getSelectedProtocolVersion());
    }
}
