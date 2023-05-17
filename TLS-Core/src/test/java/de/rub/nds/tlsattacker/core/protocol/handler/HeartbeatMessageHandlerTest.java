/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.tlsattacker.core.protocol.message.HeartbeatMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.HeartbeatMessageParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.HeartbeatMessagePreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.HeartbeatMessageSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class HeartbeatMessageHandlerTest {

    private HeartbeatMessageHandler handler;
    private TlsContext context;

    @Before
    public void setUp() {
        context = new TlsContext();
        handler = new HeartbeatMessageHandler(context);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getParser method, of class HeartbeatMessageHandler.
     */
    @Test
    public void testGetParser() {
        assertTrue(handler.getParser(new byte[1], 0) instanceof HeartbeatMessageParser);
    }

    /**
     * Test of getPreparator method, of class HeartbeatMessageHandler.
     */
    @Test
    public void testGetPreparator() {
        assertTrue(handler.getPreparator(new HeartbeatMessage()) instanceof HeartbeatMessagePreparator);
    }

    /**
     * Test of getSerializer method, of class HeartbeatMessageHandler.
     */
    @Test
    public void testGetSerializer() {
        assertTrue(handler.getSerializer(new HeartbeatMessage()) instanceof HeartbeatMessageSerializer);
    }

    /**
     * Test of adjustTLSContext method, of class HeartbeatMessageHandler.
     */
    @Test
    public void testAdjustTLSContext() {
        HeartbeatMessage message = new HeartbeatMessage();
        handler.adjustTLSContext(message);
        // TODO check that context did not change
    }

}
