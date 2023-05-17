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
import de.rub.nds.tlsattacker.core.protocol.message.extension.TruncatedHmacExtensionMessage;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

public class TruncatedHmacExtensionSerializerTest {

    private final ExtensionType extensionType = ExtensionType.TRUNCATED_HMAC;
    private final byte[] expectedBytes = new byte[] { 0x00, 0x04, 0x00, 0x00 };
    private final int extensionLength = 0;
    private TruncatedHmacExtensionMessage message;
    private TruncatedHmacExtensionSerializer serializer;

    @Before
    public void setUp() {
        message = new TruncatedHmacExtensionMessage();
        serializer = new TruncatedHmacExtensionSerializer(message);
    }

    @Test
    public void testSerializeExtensionContent() {
        message.setExtensionType(extensionType.getValue());
        message.setExtensionLength(extensionLength);

        assertArrayEquals(expectedBytes, serializer.serialize());
    }
}
