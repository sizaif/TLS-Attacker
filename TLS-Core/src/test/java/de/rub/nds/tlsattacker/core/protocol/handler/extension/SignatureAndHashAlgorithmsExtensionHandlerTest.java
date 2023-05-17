/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler.extension;

import de.rub.nds.tlsattacker.core.constants.HashAlgorithm;
import de.rub.nds.tlsattacker.core.constants.SignatureAlgorithm;
import de.rub.nds.tlsattacker.core.protocol.message.extension.SignatureAndHashAlgorithmsExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.SignatureAndHashAlgorithmsExtensionParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.extension.SignatureAndHashAlgorithmsExtensionPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.SignatureAndHashAlgorithmsExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SignatureAndHashAlgorithmsExtensionHandlerTest {

    private SignatureAndHashAlgorithmsExtensionHandler handler;
    private TlsContext context;

    @Before
    public void setUp() {
        context = new TlsContext();
        handler = new SignatureAndHashAlgorithmsExtensionHandler(context);
    }

    /**
     * Test of adjustTLSContext method, of class SignatureAndHashAlgorithmsExtensionHandler.
     */
    @Test
    public void testAdjustTLSContext() {
        SignatureAndHashAlgorithmsExtensionMessage msg = new SignatureAndHashAlgorithmsExtensionMessage();
        msg.setSignatureAndHashAlgorithms(new byte[] { 0, 0 });
        handler.adjustTLSContext(msg);
        assertTrue(context.getClientSupportedSignatureAndHashAlgorithms().size() == 1);
        assertTrue(
            context.getClientSupportedSignatureAndHashAlgorithms().get(0).getHashAlgorithm() == HashAlgorithm.NONE);
        assertTrue(context.getClientSupportedSignatureAndHashAlgorithms().get(0).getSignatureAlgorithm()
            == SignatureAlgorithm.ANONYMOUS);
    }

    /**
     * Test of getParser method, of class SignatureAndHashAlgorithmsExtensionHandler.
     */
    @Test
    public void testGetParser() {
        assertTrue(handler.getParser(new byte[] { 0, 2 }, 0,
            context.getConfig()) instanceof SignatureAndHashAlgorithmsExtensionParser);
    }

    /**
     * Test of getPreparator method, of class SignatureAndHashAlgorithmsExtensionHandler.
     */
    @Test
    public void testGetPreparator() {
        assertTrue(handler.getPreparator(
            new SignatureAndHashAlgorithmsExtensionMessage()) instanceof SignatureAndHashAlgorithmsExtensionPreparator);
    }

    /**
     * Test of getSerializer method, of class SignatureAndHashAlgorithmsExtensionHandler.
     */
    @Test
    public void testGetSerializer() {
        assertTrue(handler.getSerializer(
            new SignatureAndHashAlgorithmsExtensionMessage()) instanceof SignatureAndHashAlgorithmsExtensionSerializer);
    }

}
