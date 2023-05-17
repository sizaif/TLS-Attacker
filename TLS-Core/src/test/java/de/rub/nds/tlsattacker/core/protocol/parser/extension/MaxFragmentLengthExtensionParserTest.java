/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.parser.extension;

import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import de.rub.nds.tlsattacker.core.protocol.message.extension.MaxFragmentLengthExtensionMessage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class MaxFragmentLengthExtensionParserTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][] {});// TODO collect a real
        // maxFragmentLength extension
    }

    private final byte[] extension;
    private final int start;
    private final byte[] completeExtension;
    private final ExtensionType type;
    private final int extensionLength;
    private final byte[] maxFragmentLength;

    public MaxFragmentLengthExtensionParserTest(byte[] extension, int start, byte[] completeExtension,
        ExtensionType type, int extensionLength, byte[] maxFragmentLength) {
        this.extension = extension;
        this.start = start;
        this.completeExtension = completeExtension;
        this.type = type;
        this.extensionLength = extensionLength;
        this.maxFragmentLength = maxFragmentLength;
    }

    /**
     * Test of parseExtensionMessageContent method, of class MaxFragmentLengthExtensionParser.
     */
    @Test
    public void testParseExtensionMessageContent() {
        MaxFragmentLengthExtensionParser parser =
            new MaxFragmentLengthExtensionParser(start, extension, Config.createConfig());
        MaxFragmentLengthExtensionMessage msg = parser.parse();
        assertArrayEquals(msg.getExtensionBytes().getValue(), completeExtension);
        assertArrayEquals(type.getValue(), msg.getExtensionType().getValue());
        assertTrue(extensionLength == msg.getExtensionLength().getValue());
        assertTrue(maxFragmentLength == msg.getMaxFragmentLength().getValue());
    }
}
