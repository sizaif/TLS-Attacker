/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.parser;

import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.AlertMessage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AlertParserTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][] { { new byte[] { 1, 2 }, 0, new byte[] { 1, 2 }, (byte) 1, (byte) 2 },
            { new byte[] { 4, 3 }, 0, new byte[] { 4, 3 }, (byte) 4, (byte) 3 } });
    }

    private final byte[] message;
    private final int start;
    private final byte[] expectedPart;
    private final byte level;
    private final byte description;
    private final Config config = Config.createConfig();

    public AlertParserTest(byte[] message, int start, byte[] expectedPart, byte level, byte description) {
        this.message = message;
        this.start = start;
        this.expectedPart = expectedPart;
        this.level = level;
        this.description = description;
    }

    /**
     * Test of parse method, of class AlertParser.
     */
    @Test
    public void testParse() {
        AlertParser parser = new AlertParser(start, message, ProtocolVersion.TLS12, config);
        AlertMessage alert = parser.parse();
        assertArrayEquals(expectedPart, alert.getCompleteResultingMessage().getValue());
        assertTrue(level == alert.getLevel().getValue());
        assertTrue(description == alert.getDescription().getValue());
    }
}
