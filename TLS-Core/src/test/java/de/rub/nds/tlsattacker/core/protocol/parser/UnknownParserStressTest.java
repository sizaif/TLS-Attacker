/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.parser;

import de.rub.nds.tlsattacker.core.constants.ProtocolMessageType;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.UnknownMessage;
import de.rub.nds.tlsattacker.util.tests.IntegrationTests;
import java.util.Random;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class UnknownParserStressTest {

    private UnknownMessageParser parser;
    private final Config config = Config.createConfig();

    /**
     * Test of parse method, of class UnknownParser.
     */
    @Test
    @Category(IntegrationTests.class)
    public void testParse() {
        Random r = new Random(10);
        for (int i = 0; i < 100; i++) {
            byte[] array = new byte[r.nextInt(100)];
            if (array.length != 0) {
                r.nextBytes(array);
                parser = new UnknownMessageParser(0, array, ProtocolVersion.TLS12, ProtocolMessageType.UNKNOWN, config);
                UnknownMessage message = parser.parse();
                assertArrayEquals(array, message.getCompleteResultingMessage().getValue());
            }
        }
    }

}
