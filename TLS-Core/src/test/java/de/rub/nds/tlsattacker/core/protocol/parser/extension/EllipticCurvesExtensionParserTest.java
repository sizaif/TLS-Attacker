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
import de.rub.nds.tlsattacker.core.protocol.message.extension.EllipticCurvesExtensionMessage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class EllipticCurvesExtensionParserTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][] { {
            ArrayConverter.hexStringToByteArray("000a001c001a00170019001c001b0018001a0016000e000d000b000c0009000a"), 0,
            ArrayConverter.hexStringToByteArray("000a001c001a00170019001c001b0018001a0016000e000d000b000c0009000a"),
            ExtensionType.ELLIPTIC_CURVES, 28, 26,
            ArrayConverter.hexStringToByteArray("00170019001c001b0018001a0016000e000d000b000c0009000a") } });
    }

    private final byte[] extension;
    private final int start;
    private final byte[] completeExtension;
    private final ExtensionType type;
    private final int extensionLength;
    private final int curvesLength;
    private final byte[] curves;

    public EllipticCurvesExtensionParserTest(byte[] extension, int start, byte[] completeExtension, ExtensionType type,
        int extensionLength, int curvesLength, byte[] curves) {
        this.extension = extension;
        this.start = start;
        this.completeExtension = completeExtension;
        this.type = type;
        this.extensionLength = extensionLength;
        this.curvesLength = curvesLength;
        this.curves = curves;
    }

    /**
     * Test of parseExtensionMessageContent method, of class EllipticCurvesExtensionParser.
     */
    @Test
    public void testParseExtensionMessageContent() {
        EllipticCurvesExtensionParser parser =
            new EllipticCurvesExtensionParser(start, extension, Config.createConfig());
        EllipticCurvesExtensionMessage msg = parser.parse();
        assertArrayEquals(msg.getExtensionBytes().getValue(), completeExtension);
        assertArrayEquals(type.getValue(), msg.getExtensionType().getValue());
        assertTrue(extensionLength == msg.getExtensionLength().getValue());
        assertArrayEquals(msg.getSupportedGroups().getValue(), curves);
        assertTrue(curvesLength == msg.getSupportedGroupsLength().getValue());
    }

}
