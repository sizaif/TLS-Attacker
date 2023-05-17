/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer;

import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.CertificateMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.CertificateMessageParserTest;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CertificateMessageSerializerTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return CertificateMessageParserTest.generateData();
    }

    private byte[] message;
    private int start;
    private byte[] expectedPart;

    private HandshakeMessageType type;
    private int length;

    private int certificatesLength;
    private byte[] certificateBytes;
    private ProtocolVersion version;

    public CertificateMessageSerializerTest(byte[] message, HandshakeMessageType type, int length,
        int certificatesLength, byte[] certificateBytes, ProtocolVersion version) {
        this.message = message;
        this.start = 0;
        this.expectedPart = message;
        this.type = type;
        this.length = length;
        this.certificatesLength = certificatesLength;
        this.certificateBytes = certificateBytes;
        this.version = version;
    }

    /**
     * Test of serializeHandshakeMessageContent method, of class CertificateMessageSerializer.
     */
    @Test
    public void testSerializeHandshakeMessageContent() {
        CertificateMessage message = new CertificateMessage();
        message.setCertificatesListLength(certificatesLength);
        message.setCertificatesListBytes(certificateBytes);
        message.setLength(length);
        message.setType(type.getValue());
        CertificateMessageSerializer serializer = new CertificateMessageSerializer(message, version);
        assertArrayEquals(expectedPart, serializer.serialize());
    }
}
