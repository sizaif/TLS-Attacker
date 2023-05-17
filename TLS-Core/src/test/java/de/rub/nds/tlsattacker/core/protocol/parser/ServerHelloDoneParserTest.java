/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.parser;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.ServerHelloDoneMessage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ServerHelloDoneParserTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][] {
            { ArrayConverter.hexStringToByteArray("0e000000"), HandshakeMessageType.SERVER_HELLO_DONE, 0,
                ProtocolVersion.TLS12 },
            { ArrayConverter.hexStringToByteArray("0e000000"), HandshakeMessageType.SERVER_HELLO_DONE, 0,
                ProtocolVersion.TLS10 },
            { ArrayConverter.hexStringToByteArray("0e000000"), HandshakeMessageType.SERVER_HELLO_DONE, 0,
                ProtocolVersion.TLS11 } });
    }

    private byte[] message;

    private HandshakeMessageType type;
    private int length;
    private ProtocolVersion version;
    private final Config config = Config.createConfig();

    public ServerHelloDoneParserTest(byte[] message, HandshakeMessageType type, int length, ProtocolVersion version) {
        this.message = message;
        this.type = type;
        this.length = length;
        this.version = version;
    }

    /**
     * Test of parse method, of class ServerHelloDoneParser.
     */
    @Test
    public void testParse() {
        ServerHelloDoneParser parser = new ServerHelloDoneParser(0, message, version, config);
        ServerHelloDoneMessage msg = parser.parse();
        assertArrayEquals(message, msg.getCompleteResultingMessage().getValue());
        assertTrue(msg.getLength().getValue() == length);
        assertTrue(msg.getType().getValue() == type.getValue());

    }

}
