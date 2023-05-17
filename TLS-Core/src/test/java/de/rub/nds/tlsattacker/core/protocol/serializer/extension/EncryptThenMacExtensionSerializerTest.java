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
import de.rub.nds.tlsattacker.core.protocol.message.extension.EncryptThenMacExtensionMessage;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

public class EncryptThenMacExtensionSerializerTest {
    private final ExtensionType extensionType = ExtensionType.ENCRYPT_THEN_MAC;
    private final byte[] expectedBytes = new byte[] { 0x00, 0x16, 0x00, 0x00 };
    private final int extensionLength = 0;
    private EncryptThenMacExtensionMessage message;
    private EncryptThenMacExtensionSerializer serializer;

    @Before
    public void setUp() {
        message = new EncryptThenMacExtensionMessage();
        serializer = new EncryptThenMacExtensionSerializer(message);
    }

    @Test
    public void testSerializeExtensionContent() {
        message.setExtensionType(extensionType.getValue());
        message.setExtensionLength(extensionLength);

        assertArrayEquals(expectedBytes, serializer.serialize());
    }
}
