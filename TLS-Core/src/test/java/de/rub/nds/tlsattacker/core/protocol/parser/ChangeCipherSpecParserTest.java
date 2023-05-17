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
import de.rub.nds.tlsattacker.core.protocol.message.ChangeCipherSpecMessage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ChangeCipherSpecParserTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][] { { new byte[] { 0x01 }, (byte) 1, ProtocolVersion.TLS12 },
            { new byte[] { 0x05 }, (byte) 5, ProtocolVersion.TLS12 },
            { new byte[] { 0x01 }, (byte) 1, ProtocolVersion.TLS10 },
            { new byte[] { 0x01 }, (byte) 1, ProtocolVersion.TLS11 } });
    }

    private final byte[] message;
    private final ProtocolVersion version;
    private final byte ccsType;
    private final Config config = Config.createConfig();

    public ChangeCipherSpecParserTest(byte[] message, byte ccsType, ProtocolVersion version) {
        this.message = message;
        this.ccsType = ccsType;
        this.version = version;
    }

    /**
     * Test of parse method, of class ChangeCipherSpecParser.
     */
    @Test
    public void testParse() {
        ChangeCipherSpecParser parser = new ChangeCipherSpecParser(0, message, version, config);
        ChangeCipherSpecMessage ccsMessagee = parser.parse();
        assertArrayEquals(message, ccsMessagee.getCompleteResultingMessage().getValue());
        assertTrue(ccsType == ccsMessagee.getCcsProtocolType().getValue()[0]);
    }

}
