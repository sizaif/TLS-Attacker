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
import de.rub.nds.tlsattacker.core.protocol.message.extension.AlpnExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.AlpnExtensionParserTest;
import java.util.Collection;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AlpnExtensionSerializerTest {
    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return AlpnExtensionParserTest.generateData();
    }

    private final ExtensionType extensionType;
    private final byte[] expectedBytes;
    private final int extensionLength;
    private final int startParsing;
    private final int proposedAlpnProtocolsLength;
    private final byte[] proposedAlpnProtocols;
    private AlpnExtensionSerializer serializer;
    private AlpnExtensionMessage message;

    public AlpnExtensionSerializerTest(ExtensionType extensionType, byte[] expectedBytes, int extensionLength,
        int startParsing, int proposedAlpnProtocolsLength, byte[] proposedAlpnProtocols) {
        this.extensionType = extensionType;
        this.expectedBytes = expectedBytes;
        this.extensionLength = extensionLength;
        this.startParsing = startParsing;
        this.proposedAlpnProtocolsLength = proposedAlpnProtocolsLength;
        this.proposedAlpnProtocols = proposedAlpnProtocols;
    }

    @Before
    public void setUp() {
        message = new AlpnExtensionMessage();
        serializer = new AlpnExtensionSerializer(message);
    }

    @Test
    public void testSerializeExtensionContent() {
        message.setExtensionType(extensionType.getValue());
        message.setExtensionLength(extensionLength);
        message.setProposedAlpnProtocolsLength(proposedAlpnProtocolsLength);
        message.setProposedAlpnProtocols(proposedAlpnProtocols);

        assertArrayEquals(expectedBytes, serializer.serialize());
    }

}
