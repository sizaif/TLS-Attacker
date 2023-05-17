/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer.extension;

import java.util.Collection;
import static org.junit.Assert.assertArrayEquals;

import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ExtendedRandomExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.ExtendedRandomExtensionParserTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ExtendedRandomExtensionSerializerTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return ExtendedRandomExtensionParserTest.generateData();
    }

    private final ExtensionType extensionType;
    private final int extensionLength;
    private final byte[] extendedRandom;
    private final byte[] expectedBytes;
    private ExtendedRandomExtensionMessage message;

    public ExtendedRandomExtensionSerializerTest(ExtensionType extensionType, int extensionLength,
        byte[] extendedRandom, byte[] expectedBytes, int startParsing) {
        this.extensionType = extensionType;
        this.extensionLength = extensionLength;
        this.extendedRandom = extendedRandom;
        this.expectedBytes = expectedBytes;
    }

    @Test
    public void testSerializeExtensionContent() {
        message = new ExtendedRandomExtensionMessage();
        message.setExtensionType(extensionType.getValue());
        message.setExtensionLength(extensionLength);
        message.setExtendedRandom(extendedRandom);
        message.setExtendedRandomLength(extendedRandom.length);

        ExtendedRandomExtensionSerializer serializer = new ExtendedRandomExtensionSerializer(message);

        assertArrayEquals(expectedBytes, serializer.serialize());
    }

}
