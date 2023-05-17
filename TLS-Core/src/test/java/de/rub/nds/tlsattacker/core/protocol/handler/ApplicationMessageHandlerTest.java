/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.tlsattacker.core.protocol.message.ApplicationMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.ApplicationMessageParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.ApplicationMessagePreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.ApplicationMessageSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ApplicationMessageHandlerTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    private ApplicationMessageHandler handler;
    private TlsContext context;

    @Before
    public void setUp() {
        context = new TlsContext();
        handler = new ApplicationMessageHandler(context);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getParser method, of class ApplicationMessageHandler.
     */
    @Test
    public void testGetParser() {
        assertTrue(handler.getParser(new byte[1], 0) instanceof ApplicationMessageParser);
    }

    /**
     * Test of getPreparator method, of class ApplicationMessageHandler.
     */
    @Test
    public void testGetPreparator() {
        assertTrue(handler.getPreparator(new ApplicationMessage()) instanceof ApplicationMessagePreparator);
    }

    /**
     * Test of getSerializer method, of class ApplicationMessageHandler.
     */
    @Test
    public void testGetSerializer() {
        assertTrue(handler.getSerializer(new ApplicationMessage()) instanceof ApplicationMessageSerializer);
    }

    /**
     * Test of adjustTLSContext method, of class ApplicationMessageHandler.
     */
    @Test
    public void testAdjustTLSContext() {
        ApplicationMessage message = new ApplicationMessage();
        message.setData(new byte[] { 0, 1, 2, 3, 4, 5, 6 });
        handler.adjustTLSContext(message);
        // TODO test that nothing changes (mockito) // ugly
    }

}
