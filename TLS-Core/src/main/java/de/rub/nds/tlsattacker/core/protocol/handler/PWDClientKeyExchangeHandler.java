/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.tlsattacker.core.constants.AlgorithmResolver;
import de.rub.nds.tlsattacker.core.protocol.message.PWDClientKeyExchangeMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.PWDClientKeyExchangeParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.PWDClientKeyExchangePreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.PWDClientKeyExchangeSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

public class PWDClientKeyExchangeHandler extends ClientKeyExchangeHandler<PWDClientKeyExchangeMessage> {
    public PWDClientKeyExchangeHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    @Override
    public PWDClientKeyExchangeParser getParser(byte[] message, int pointer) {
        return new PWDClientKeyExchangeParser(pointer, message, tlsContext.getChooser().getSelectedProtocolVersion(),
            AlgorithmResolver.getKeyExchangeAlgorithm(tlsContext.getChooser().getSelectedCipherSuite()),
            tlsContext.getConfig());
    }

    @Override
    public PWDClientKeyExchangePreparator getPreparator(PWDClientKeyExchangeMessage message) {
        return new PWDClientKeyExchangePreparator(tlsContext.getChooser(), message);
    }

    @Override
    public PWDClientKeyExchangeSerializer getSerializer(PWDClientKeyExchangeMessage message) {
        return new PWDClientKeyExchangeSerializer(message, tlsContext.getChooser().getSelectedProtocolVersion());
    }

    @Override
    public void adjustTLSContext(PWDClientKeyExchangeMessage message) {
        if (message.getComputations() != null) {
            tlsContext.setPWDPE(message.getComputations().getPasswordElement());
            tlsContext.setClientPWDPrivate(message.getComputations().getPrivateKeyScalar());
        }

        adjustPremasterSecret(message);
        adjustMasterSecret(message);
        spawnNewSession();
    }
}