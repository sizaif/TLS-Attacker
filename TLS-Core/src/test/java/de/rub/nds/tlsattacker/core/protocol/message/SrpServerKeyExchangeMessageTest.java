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

public class SrpServerKeyExchangeMessageTest {

    SrpServerKeyExchangeMessage message;

    @Before
    public void setUp() {
        message = new SrpServerKeyExchangeMessage();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class SrpServerKeyExchangeMessage.
     */
    @Test
    public void testToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SrpServerKeyExchangeMessage:");
        sb.append("\n  Modulus p: ").append("null");
        sb.append("\n  Generator g: ").append("null");
        sb.append("\n  Public Key: ").append("null");
        sb.append("\n  Signature and Hash Algorithm: ").append("null");
        sb.append("\n  Signature: ").append("null");

        assertEquals(message.toString(), sb.toString());
    }
}
