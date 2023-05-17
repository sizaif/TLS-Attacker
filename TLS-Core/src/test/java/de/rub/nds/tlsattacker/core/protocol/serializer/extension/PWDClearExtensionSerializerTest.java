/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer.extension;

import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import de.rub.nds.tlsattacker.core.protocol.message.extension.PWDClearExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.PWDClearExtensionParserTest;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PWDClearExtensionSerializerTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return PWDClearExtensionParserTest.generateData();
    }

    private final byte[] expectedBytes;
    private final int start;
    private final ExtensionType type;
    private final int extensionLength;
    private final int usernameLength;
    private final String username;
    private PWDClearExtensionMessage message;
    private PWDClearExtensionSerializer serializer;

    public PWDClearExtensionSerializerTest(byte[] expectedBytes, int start, ExtensionType type, int extensionLength,
        int usernameLength, String username) {
        this.expectedBytes = expectedBytes;
        this.start = start;
        this.type = type;
        this.extensionLength = extensionLength;
        this.usernameLength = usernameLength;
        this.username = username;
    }

    @Before
    public void setUp() {
        message = new PWDClearExtensionMessage();
        serializer = new PWDClearExtensionSerializer(message);
    }

    @Test
    public void testSerializeExtensionContent() {
        message.setExtensionType(type.getValue());
        message.setExtensionLength(extensionLength);
        message.setUsername(username);
        message.setUsernameLength(usernameLength);

        assertArrayEquals(expectedBytes, serializer.serialize());
    }

}