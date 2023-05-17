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
import de.rub.nds.tlsattacker.core.protocol.message.SrpServerKeyExchangeMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.SrpServerKeyExchangeParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.SrpServerKeyExchangePreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.SrpServerKeyExchangeSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import java.math.BigInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SrpServerKeyExchangeHandler extends ServerKeyExchangeHandler<SrpServerKeyExchangeMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    public SrpServerKeyExchangeHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    @Override
    public SrpServerKeyExchangeParser getParser(byte[] message, int pointer) {
        return new SrpServerKeyExchangeParser(pointer, message, tlsContext.getChooser().getSelectedProtocolVersion(),
            tlsContext.getConfig());
    }

    @Override
    public SrpServerKeyExchangePreparator getPreparator(SrpServerKeyExchangeMessage message) {
        return new SrpServerKeyExchangePreparator(tlsContext.getChooser(), message);
    }

    @Override
    public SrpServerKeyExchangeSerializer getSerializer(SrpServerKeyExchangeMessage message) {
        return new SrpServerKeyExchangeSerializer(message, tlsContext.getChooser().getSelectedProtocolVersion());
    }

    @Override
    public void adjustTLSContext(SrpServerKeyExchangeMessage message) {
        adjustSRPGenerator(message);
        adjustSRPModulus(message);
        adjustSalt(message);
        adjustServerPublicKey(message);
        if (message.getComputations() != null && message.getComputations().getPrivateKey() != null) {
            adjustServerPrivateKey(message);
        }
    }

    private void adjustSRPGenerator(SrpServerKeyExchangeMessage message) {
        tlsContext.setSRPGenerator(new BigInteger(1, message.getGenerator().getValue()));
        LOGGER.debug("SRP Generator: " + tlsContext.getSRPGenerator());
    }

    private void adjustSRPModulus(SrpServerKeyExchangeMessage message) {
        tlsContext.setSRPModulus(new BigInteger(1, message.getModulus().getValue()));
        LOGGER.debug("SRP Modulus: " + tlsContext.getSRPModulus());
    }

    private void adjustServerPublicKey(SrpServerKeyExchangeMessage message) {
        tlsContext.setServerSRPPublicKey(new BigInteger(1, message.getPublicKey().getValue()));
        LOGGER.debug("Server PublicKey: " + tlsContext.getServerSRPPublicKey());
    }

    private void adjustServerPrivateKey(SrpServerKeyExchangeMessage message) {
        tlsContext.setServerSRPPrivateKey(message.getComputations().getPrivateKey().getValue());
        LOGGER.debug("Server PrivateKey: " + tlsContext.getServerSRPPrivateKey());
    }

    private void adjustSalt(SrpServerKeyExchangeMessage message) {
        tlsContext.setSRPServerSalt(message.getSalt().getValue());
        LOGGER.debug("SRP Salt: " + ArrayConverter.bytesToHexString(tlsContext.getSRPServerSalt()));
    }
}
