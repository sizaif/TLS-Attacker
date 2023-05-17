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
import de.rub.nds.tlsattacker.core.protocol.message.extension.RecordSizeLimitExtensionMessage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class RecordSizeLimitExtensionParserTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][] { { ArrayConverter.hexStringToByteArray("001C00022000"), 0,
            ArrayConverter.hexStringToByteArray("001C00022000"), ExtensionType.RECORD_SIZE_LIMIT, 2,
            ArrayConverter.hexStringToByteArray("2000") } });
    }

    private final byte[] extension;
    private final int start;
    private final byte[] completeExtension;
    private final ExtensionType type;
    private final int extensionLength;
    private final byte[] recordSizeLimit;

    public RecordSizeLimitExtensionParserTest(byte[] extension, int start, byte[] completeExtension, ExtensionType type,
        int extensionLength, byte[] recordSizeLimit) {
        this.extension = extension;
        this.start = start;
        this.completeExtension = completeExtension;
        this.type = type;
        this.extensionLength = extensionLength;
        this.recordSizeLimit = recordSizeLimit;
    }

    /**
     * Test of parseExtensionMessageContent method of class RecordSizeLimitExtensionParser.
     */
    @Test
    public void testParseExtensionMessageContent() {
        RecordSizeLimitExtensionParser parser =
            new RecordSizeLimitExtensionParser(start, extension, Config.createConfig());
        RecordSizeLimitExtensionMessage message = parser.parse();
        assertArrayEquals(message.getExtensionBytes().getValue(), completeExtension);
        assertArrayEquals(type.getValue(), message.getExtensionType().getValue());
        assertTrue(extensionLength == message.getExtensionLength().getValue());
        assertArrayEquals(recordSizeLimit, message.getRecordSizeLimit().getValue());
    }
}
