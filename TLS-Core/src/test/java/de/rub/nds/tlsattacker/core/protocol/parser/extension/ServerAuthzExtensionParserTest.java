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
import de.rub.nds.tlsattacker.core.protocol.message.extension.ServerAuthzExtensionMessage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ServerAuthzExtensionParserTest {
    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return Arrays.asList(
            new Object[][] { { ExtensionType.SERVER_AUTHZ, ArrayConverter.hexStringToByteArray("000800050400010203"), 5,
                0, 4, ArrayConverter.hexStringToByteArray("00010203") } });
    }

    private final ExtensionType extensionType;
    private final byte[] expectedBytes;
    private final int extensionLength;
    private final int startParsing;
    private final int authzFormatListLength;
    private final byte[] authzFormatList;
    private ServerAuthzExtensionParser parser;
    private ServerAuthzExtensionMessage msg;

    public ServerAuthzExtensionParserTest(ExtensionType extensionType, byte[] expectedBytes, int extensionLength,
        int startParsing, int authzFormatListLength, byte[] authzFormatList) {
        this.extensionType = extensionType;
        this.expectedBytes = expectedBytes;
        this.extensionLength = extensionLength;
        this.startParsing = startParsing;
        this.authzFormatListLength = authzFormatListLength;
        this.authzFormatList = authzFormatList;
    }

    @Test
    public void testParseExtensionMessageContent() {
        parser = new ServerAuthzExtensionParser(startParsing, expectedBytes, Config.createConfig());
        msg = parser.parse();

        assertArrayEquals(extensionType.getValue(), msg.getExtensionType().getValue());
        assertEquals(extensionLength, (long) msg.getExtensionLength().getValue());

        assertEquals(authzFormatListLength, (long) msg.getAuthzFormatListLength().getValue());
        assertArrayEquals(authzFormatList, msg.getAuthzFormatList().getValue());
    }
}
