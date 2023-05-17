/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer;

import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.AlertMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.AlertParserTest;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AlertSerializerTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return AlertParserTest.generateData();
    }

    private final byte[] expectedPart;
    private final byte level;
    private final byte description;

    public AlertSerializerTest(byte[] message, int start, byte[] expectedPart, byte level, byte description) {
        this.expectedPart = expectedPart;
        this.level = level;
        this.description = description;
    }

    /**
     * Test of serializeProtocolMessageContent method, of class AlertSerializer.
     */
    @Test
    public void testSerializeProtocolMessageContent() {
        AlertMessage message = new AlertMessage();
        message.setLevel(level);
        message.setDescription(description);
        message.setCompleteResultingMessage(expectedPart);
        AlertSerializer serializer = new AlertSerializer(message, ProtocolVersion.TLS12);
        assertArrayEquals(expectedPart, serializer.serialize());
    }

}
