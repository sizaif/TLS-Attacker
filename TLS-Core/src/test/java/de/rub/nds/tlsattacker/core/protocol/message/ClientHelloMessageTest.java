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

public class ClientHelloMessageTest {

    ClientHelloMessage message;

    @Before
    public void setUp() {
        message = new ClientHelloMessage();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class ClientHelloMessage.
     */
    @Test
    public void testToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ClientHelloMessage:");
        sb.append("\n  Protocol Version: ").append("null");
        sb.append("\n  Client Unix Time: ").append("null");
        sb.append("\n  Client Random: ").append("null");
        sb.append("\n  Session ID: ").append("null");
        sb.append("\n  Supported Cipher Suites: ").append("null");
        sb.append("\n  Supported Compression Methods: ").append("null");
        sb.append("\n  Extensions: ").append("null");

        assertEquals(message.toString(), sb.toString());
    }
}
