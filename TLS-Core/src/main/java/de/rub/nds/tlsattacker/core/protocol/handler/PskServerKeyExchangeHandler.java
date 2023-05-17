/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.tlsattacker.core.protocol.message.PskServerKeyExchangeMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.PskServerKeyExchangeParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.PskServerKeyExchangePreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.PskServerKeyExchangeSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

public class PskServerKeyExchangeHandler extends ServerKeyExchangeHandler<PskServerKeyExchangeMessage> {

    public PskServerKeyExchangeHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    @Override
    public PskServerKeyExchangeParser getParser(byte[] message, int pointer) {
        return new PskServerKeyExchangeParser(pointer, message, tlsContext.getChooser().getSelectedProtocolVersion(),
            tlsContext.getConfig());
    }

    @Override
    public PskServerKeyExchangePreparator getPreparator(PskServerKeyExchangeMessage message) {
        return new PskServerKeyExchangePreparator(tlsContext.getChooser(), message);
    }

    @Override
    public PskServerKeyExchangeSerializer getSerializer(PskServerKeyExchangeMessage message) {
        return new PskServerKeyExchangeSerializer(message, tlsContext.getChooser().getSelectedProtocolVersion());
    }

    @Override
    public void adjustTLSContext(PskServerKeyExchangeMessage message) {
    }
}
