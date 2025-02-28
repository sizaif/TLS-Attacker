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
import de.rub.nds.tlsattacker.core.constants.CertificateType;
import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ClientCertificateTypeExtensionMessage;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ClientCertificateTypeExtensionParserTest {
    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][] {
            { ExtensionType.CLIENT_CERTIFICATE_TYPE, ArrayConverter.hexStringToByteArray("0013000100"), 1, 0, null,
                Arrays.asList(CertificateType.X509), false },
            { ExtensionType.CLIENT_CERTIFICATE_TYPE, ArrayConverter.hexStringToByteArray("001300020100"), 2, 0, 1,
                Arrays.asList(CertificateType.X509), true },
            { ExtensionType.CLIENT_CERTIFICATE_TYPE, ArrayConverter.hexStringToByteArray("00130003020100"), 3, 0, 2,
                Arrays.asList(CertificateType.OPEN_PGP, CertificateType.X509), true } });
    }

    private final ExtensionType extensionType;
    private final byte[] expectedBytes;
    private final int extensionLength;
    private final int startParsing;
    private final Integer certificateTypesLength;
    private final List<CertificateType> certificateTypes;
    private final boolean isClientState;
    private ClientCertificateTypeExtensionParser parser;
    private ClientCertificateTypeExtensionMessage msg;

    public ClientCertificateTypeExtensionParserTest(ExtensionType extensionType, byte[] expectedBytes,
        int extensionLength, int startParsing, Integer certificateTypesLength, List<CertificateType> certificateTypes,
        boolean isClientState) {
        this.extensionType = extensionType;
        this.expectedBytes = expectedBytes;
        this.extensionLength = extensionLength;
        this.startParsing = startParsing;
        this.certificateTypesLength = certificateTypesLength;
        this.certificateTypes = certificateTypes;
        this.isClientState = isClientState;
    }

    @Before
    public void setUp() {
        parser = new ClientCertificateTypeExtensionParser(startParsing, expectedBytes, Config.createConfig());
    }

    @Test
    public void testParseExtensionMessageContent() {
        msg = parser.parse();

        assertArrayEquals(extensionType.getValue(), msg.getExtensionType().getValue());
        assertEquals(extensionLength, (long) msg.getExtensionLength().getValue());

        if (certificateTypesLength != null) {
            assertEquals(certificateTypesLength, msg.getCertificateTypesLength().getValue());
        } else {
            assertNull(msg.getCertificateTypesLength());
        }
        assertArrayEquals(CertificateType.toByteArray(certificateTypes), msg.getCertificateTypes().getValue());
    }

}
