/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.tlsattacker.core.protocol.message.PskEcDhClientKeyExchangeMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.PskEcDhClientKeyExchangeParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.PskEcDhClientKeyExchangePreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.PskEcDhClientKeyExchangeSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

public class PskEcDhClientKeyExchangeHandler extends ECDHClientKeyExchangeHandler<PskEcDhClientKeyExchangeMessage> {

    public PskEcDhClientKeyExchangeHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    @Override
    public PskEcDhClientKeyExchangeParser getParser(byte[] message, int pointer) {
        return new PskEcDhClientKeyExchangeParser(pointer, message,
            tlsContext.getChooser().getSelectedProtocolVersion(), tlsContext.getConfig());
    }

    @Override
    public PskEcDhClientKeyExchangePreparator getPreparator(PskEcDhClientKeyExchangeMessage message) {
        return new PskEcDhClientKeyExchangePreparator(tlsContext.getChooser(), message);
    }

    @Override
    public PskEcDhClientKeyExchangeSerializer getSerializer(PskEcDhClientKeyExchangeMessage message) {
        return new PskEcDhClientKeyExchangeSerializer(message, tlsContext.getChooser().getSelectedProtocolVersion());
    }

    @Override
    public void adjustTLSContext(PskEcDhClientKeyExchangeMessage message) {
        adjustPremasterSecret(message);
        adjustMasterSecret(message);
        spawnNewSession();
    }
}
