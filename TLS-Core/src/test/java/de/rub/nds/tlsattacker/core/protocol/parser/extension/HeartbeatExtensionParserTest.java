/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.parser.extension;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import de.rub.nds.tlsattacker.core.protocol.message.extension.HeartbeatExtensionMessage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class HeartbeatExtensionParserTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][] {
            { ArrayConverter.hexStringToByteArray("000f000101"), ExtensionType.HEARTBEAT, 1, new byte[] { 1 } } }); // is
                                                                                                                    // the
        // same for
        // TLS10 and
        // TLS11
    }

    private final byte[] extension;
    private final int start;
    private final byte[] completeExtension;
    private final ExtensionType type;
    private final int extensionLength;
    private final byte[] heartbeatMode;

    public HeartbeatExtensionParserTest(byte[] extension, ExtensionType type, int extensionLength,
        byte[] heartbeatMode) {
        this.extension = extension;
        this.start = 0;
        this.completeExtension = extension;
        this.type = type;
        this.extensionLength = extensionLength;
        this.heartbeatMode = heartbeatMode;
    }

    /**
     * Test of parseExtensionMessageContent method, of class HeartbeatExtensionParser.
     */
    @Test
    public void testParseExtensionMessageContent() {
        HeartbeatExtensionParser parser = new HeartbeatExtensionParser(start, extension, Config.createConfig());
        HeartbeatExtensionMessage msg = parser.parse();
        assertArrayEquals(msg.getExtensionBytes().getValue(), completeExtension);
        assertArrayEquals(type.getValue(), msg.getExtensionType().getValue());
        assertTrue(extensionLength == msg.getExtensionLength().getValue());
        assertArrayEquals(heartbeatMode, msg.getHeartbeatMode().getValue());
    }
}
