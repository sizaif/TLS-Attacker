/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.tlsattacker.core.protocol.message.SrpClientKeyExchangeMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.SrpClientKeyExchangeParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.SrpClientKeyExchangePreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.SrpClientKeyExchangeSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

/**
 * Handler for SRP ClientKeyExchange messages
 *
 */
public class SrpClientKeyExchangeHandler extends ClientKeyExchangeHandler<SrpClientKeyExchangeMessage> {

    public SrpClientKeyExchangeHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    @Override
    public SrpClientKeyExchangeParser getParser(byte[] message, int pointer) {
        return new SrpClientKeyExchangeParser(pointer, message, tlsContext.getChooser().getSelectedProtocolVersion(),
            tlsContext.getConfig());
    }

    @Override
    public SrpClientKeyExchangePreparator getPreparator(SrpClientKeyExchangeMessage message) {
        return new SrpClientKeyExchangePreparator(tlsContext.getChooser(), message);
    }

    @Override
    public SrpClientKeyExchangeSerializer getSerializer(SrpClientKeyExchangeMessage message) {
        return new SrpClientKeyExchangeSerializer(message, tlsContext.getChooser().getSelectedProtocolVersion());
    }

    @Override
    public void adjustTLSContext(SrpClientKeyExchangeMessage message) {
        adjustPremasterSecret(message);
        adjustMasterSecret(message);
        spawnNewSession();
    }
}
