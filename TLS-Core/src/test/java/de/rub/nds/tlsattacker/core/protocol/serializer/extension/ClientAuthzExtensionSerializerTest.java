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
import de.rub.nds.tlsattacker.core.protocol.message.extension.ClientAuthzExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.ClientAuthzExtensionParserTest;
import java.util.Collection;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ClientAuthzExtensionSerializerTest {
    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return ClientAuthzExtensionParserTest.generateData();
    }

    private final ExtensionType extensionType;
    private final byte[] expectedBytes;
    private final int extensionLength;
    private final int authzFormatListLength;
    private final byte[] authzFormatList;
    private ClientAuthzExtensionMessage msg;
    private ClientAuthzExtensionSerializer serializer;

    public ClientAuthzExtensionSerializerTest(ExtensionType extensionType, byte[] expectedBytes, int extensionLength,
        int startParsing, int authzFormatListLength, byte[] authzFormatList) {
        this.extensionType = extensionType;
        this.expectedBytes = expectedBytes;
        this.extensionLength = extensionLength;
        this.authzFormatListLength = authzFormatListLength;
        this.authzFormatList = authzFormatList;
    }

    @Test
    public void testSerializeExtensionContent() {
        msg = new ClientAuthzExtensionMessage();
        serializer = new ClientAuthzExtensionSerializer(msg);

        msg.setExtensionType(extensionType.getValue());
        msg.setExtensionLength(extensionLength);
        msg.setAuthzFormatListLength(authzFormatListLength);
        msg.setAuthzFormatList(authzFormatList);

        assertArrayEquals(expectedBytes, serializer.serialize());
    }
}
