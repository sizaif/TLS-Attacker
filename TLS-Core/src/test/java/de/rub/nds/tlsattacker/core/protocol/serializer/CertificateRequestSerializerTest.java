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
import de.rub.nds.tlsattacker.core.protocol.message.CertificateRequestMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.CertificateRequestParserTest;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CertificateRequestSerializerTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return CertificateRequestParserTest.generateData();
    }

    private byte[] message;
    private int start;
    private byte[] expectedPart;
    private HandshakeMessageType type;
    private int length;
    private int certTypesCount;
    private byte[] certTypes;
    private int sigHashAlgsLength;
    private byte[] sigHashAlgs;
    private int distinguishedNamesLength;
    private byte[] distinguishedNames;
    private ProtocolVersion version;

    public CertificateRequestSerializerTest(byte[] message, int start, byte[] expectedPart, HandshakeMessageType type,
        int length, int certTypesCount, byte[] certTypes, int sigHashAlgsLength, byte[] sigHashAlgs,
        int distinguishedNamesLength, byte[] distinguishedNames, ProtocolVersion version) {
        this.message = message;
        this.start = start;
        this.expectedPart = expectedPart;
        this.type = type;
        this.length = length;
        this.certTypesCount = certTypesCount;
        this.certTypes = certTypes;
        this.sigHashAlgsLength = sigHashAlgsLength;
        this.sigHashAlgs = sigHashAlgs;
        this.distinguishedNamesLength = distinguishedNamesLength;
        this.distinguishedNames = distinguishedNames;
        this.version = version;
    }

    /**
     * Test of serializeHandshakeMessageContent method, of class CertificateRequestSerializer.
     */
    @Test
    public void testSerializeHandshakeMessageContent() {
        CertificateRequestMessage message = new CertificateRequestMessage();
        message.setLength(length);
        message.setType(type.getValue());
        message.setClientCertificateTypesCount(certTypesCount);
        message.setClientCertificateTypes(certTypes);
        message.setSignatureHashAlgorithmsLength(sigHashAlgsLength);
        message.setSignatureHashAlgorithms(sigHashAlgs);
        message.setDistinguishedNamesLength(distinguishedNamesLength);
        message.setDistinguishedNames(distinguishedNames);
        CertificateRequestSerializer serializer = new CertificateRequestSerializer(message, version);
        assertArrayEquals(expectedPart, serializer.serialize());
    }

}
