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
import de.rub.nds.tlsattacker.core.protocol.message.HelloVerifyRequestMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.HelloVerifyRequestParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.HelloVerifyRequestPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.HelloVerifyRequestSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HelloVerifyRequestHandler extends HandshakeMessageHandler<HelloVerifyRequestMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    public HelloVerifyRequestHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    @Override
    public HelloVerifyRequestParser getParser(byte[] message, int pointer) {
        return new HelloVerifyRequestParser(pointer, message, tlsContext.getChooser().getSelectedProtocolVersion(),
            tlsContext.getConfig());
    }

    @Override
    public HelloVerifyRequestPreparator getPreparator(HelloVerifyRequestMessage message) {
        return new HelloVerifyRequestPreparator(tlsContext.getChooser(), message);
    }

    @Override
    public HelloVerifyRequestSerializer getSerializer(HelloVerifyRequestMessage message) {
        return new HelloVerifyRequestSerializer(message, tlsContext.getChooser().getSelectedProtocolVersion());
    }

    @Override
    public void adjustTLSContext(HelloVerifyRequestMessage message) {
        adjustDTLSCookie(message);
    }

    private void adjustDTLSCookie(HelloVerifyRequestMessage message) {
        byte[] dtlsCookie = message.getCookie().getValue();
        tlsContext.setDtlsCookie(dtlsCookie);
        LOGGER.debug("Set DTLS Cookie in Context to " + ArrayConverter.bytesToHexString(dtlsCookie, false));
        tlsContext.getDigest().reset();
        LOGGER.debug("Resetting MessageDigest");
    }
}
