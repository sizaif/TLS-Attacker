/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.message;

import de.rub.nds.tlsattacker.core.state.SessionTicket;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class NewSessionTicketMessageTest {

    NewSessionTicketMessage message;

    @Before
    public void setUp() {
        message = new NewSessionTicketMessage();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class NewSessionTicketMessage.
     */
    @Test
    public void testToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NewSessionTicketMessage:");
        sb.append("\n  TicketLifeTimeHint: ").append("null");
        sb.append("\n  TicketLength: ").append("null");
        sb.append("\n  Ticket: ").append(new SessionTicket().toString());

        assertEquals(message.toString(), sb.toString());
    }

}
