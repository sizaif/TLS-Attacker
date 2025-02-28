/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.message;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class AlertMessageTest {

    private AlertMessage message;

    @Before
    public void setUp() {
        message = new AlertMessage();
    }

    @Test
    public void testToString() {
        byte testBytes = (byte) 199;
        StringBuilder sb = new StringBuilder();
        sb.append("AlertMessage:");
        sb.append("\n  Level: ").append("null");
        sb.append("\n  Description: ").append("null");

        assertEquals(sb.toString(), message.toString());

        message.setDescription(testBytes);
        message.setLevel(testBytes);

        sb.setLength(0);
        sb.append("AlertMessage:");
        sb.append("\n  Level: ").append(testBytes);
        sb.append("\n  Description: ").append(testBytes);

        assertEquals(sb.toString(), message.toString());
    }

}
