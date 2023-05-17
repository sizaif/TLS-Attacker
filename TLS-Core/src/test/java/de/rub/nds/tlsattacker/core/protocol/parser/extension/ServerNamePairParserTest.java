/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.parser.extension;

import de.rub.nds.tlsattacker.core.protocol.message.extension.sni.ServerNamePair;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ServerNamePairParserTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        byte[] rwTestServerNamePairBytes =
            new byte[] { 0x00, 0x00, 0x0b, 0x74, 0x77, 0x69, 0x74, 0x74, 0x65, 0x72, 0x2e, 0x63, 0x6f, 0x6d };
        byte[] rwTestServerName = new byte[] { 0x74, 0x77, 0x69, 0x74, 0x74, 0x65, 0x72, 0x2e, 0x63, 0x6f, 0x6d };
        int rwTestNameLength = 11;
        byte rwTestServerType = 0x00;

        return Arrays.asList(
            new Object[][] { { rwTestServerNamePairBytes, rwTestServerName, rwTestNameLength, rwTestServerType } });
    }

    private final byte[] servernamePairBytes;
    private final byte[] serverName;
    private final int serverNameLength;
    private final byte serverType;

    public ServerNamePairParserTest(byte[] servernamePairBytes, byte[] serverName, int serverNameLength,
        byte serverType) {
        this.servernamePairBytes = servernamePairBytes;
        this.serverName = serverName;
        this.serverNameLength = serverNameLength;
        this.serverType = serverType;
    }

    /**
     * Test of parse method, of class ServerNamePairParser.
     */
    @Test
    public void testParse() {
        ServerNamePairParser parser = new ServerNamePairParser(0, servernamePairBytes);
        ServerNamePair pair = parser.parse();
        assertArrayEquals(serverName, pair.getServerName().getValue());
        assertTrue(serverNameLength == pair.getServerNameLength().getValue());
        assertTrue(serverType == pair.getServerNameType().getValue());
    }

}
