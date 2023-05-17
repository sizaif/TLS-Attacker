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
import de.rub.nds.tlsattacker.core.protocol.message.extension.PWDProtectExtensionMessage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PWDProtectExtensionParserTest {

    /**
     * Generate test data for the parser and serializer
     *
     * Note that the "username" is not actually an encrypted byte string in this test. The parser and serializer don't
     * really care about that. This is just to test if the field is extracted correctly. The actual
     * encryption/decryption is done by the handler/preparator.
     */
    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][] { { ArrayConverter.hexStringToByteArray("001d00050466726564"), 0,
            ExtensionType.PWD_PROTECT, 5, 4, ArrayConverter.hexStringToByteArray("66726564") } });
    }

    private final byte[] expectedBytes;
    private final int start;
    private final ExtensionType type;
    private final int extensionLength;
    private final int usernameLength;
    private final byte[] username;

    public PWDProtectExtensionParserTest(byte[] expectedBytes, int start, ExtensionType type, int extensionLength,
        int usernameLength, byte[] username) {
        this.expectedBytes = expectedBytes;
        this.start = start;
        this.type = type;
        this.extensionLength = extensionLength;
        this.usernameLength = usernameLength;
        this.username = username;
    }

    @Test
    public void testParseExtensionMessageContent() {
        PWDProtectExtensionParser parser = new PWDProtectExtensionParser(start, expectedBytes, Config.createConfig());
        PWDProtectExtensionMessage msg = parser.parse();
        assertArrayEquals(type.getValue(), msg.getExtensionType().getValue());
        assertEquals(extensionLength, (long) msg.getExtensionLength().getValue());
        assertEquals(usernameLength, (long) msg.getUsernameLength().getValue());
        assertArrayEquals(username, msg.getUsername().getValue());
    }
}
