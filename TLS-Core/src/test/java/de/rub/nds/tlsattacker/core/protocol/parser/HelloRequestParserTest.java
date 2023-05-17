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
import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.HelloRequestMessage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class HelloRequestParserTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][] {}); // TODO add Testcases!
    }

    // TODO get a true message
    private final byte[] message;

    private final HandshakeMessageType type;
    private final int length;
    private final Config config = Config.createConfig();

    public HelloRequestParserTest(byte[] message, int start, byte[] expectedPart, HandshakeMessageType type,
        int length) {
        this.message = message;
        this.type = type;
        this.length = length;
    }

    /**
     * Test of parse method, of class HelloRequestParser.
     */
    @Test
    public void testParse() {
        HelloRequestParser parser = new HelloRequestParser(0, message, ProtocolVersion.TLS12, config);
        HelloRequestMessage msg = parser.parse();
        assertArrayEquals(message, msg.getCompleteResultingMessage().getValue());
        assertTrue(msg.getLength().getValue() == length);
        assertTrue(msg.getType().getValue() == type.getValue());
    }

}
