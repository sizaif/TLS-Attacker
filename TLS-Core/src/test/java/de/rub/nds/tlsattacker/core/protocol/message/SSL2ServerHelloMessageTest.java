/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.message;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SSL2ServerHelloMessageTest {

    SSL2ServerHelloMessage message;

    @Before
    public void setUp() {
        message = new SSL2ServerHelloMessage();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class SSL2ServerHelloMessage.
     */
    @Test
    public void testToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SSL2ServerHelloMessage:");
        sb.append("\n  Protocol Version: ").append("null");
        sb.append("\n  Type: ").append("null");
        sb.append("\n  Supported CipherSuites: ").append("null");
        sb.append("\n  SessionIdHit: ").append("null");
        sb.append("\n  Certificate: ").append("null");
        sb.append("\n  SessionID: ").append("null");

        assertEquals(message.toString(), sb.toString());
    }

}
