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
import de.rub.nds.tlsattacker.core.protocol.message.HelloVerifyRequestMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.HelloVerifyRequestParserTest;
import java.util.Collection;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class HelloVerifyRequestSerializerTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return HelloVerifyRequestParserTest.generateData();
    }

    private byte[] message;

    private HandshakeMessageType type;
    private int length;

    private byte[] protocolVersion;
    private byte cookieLength;
    private byte[] cookie;

    public HelloVerifyRequestSerializerTest(byte[] message, HandshakeMessageType type, int length,
        byte[] protocolVersion, byte cookieLength, byte[] cookie) {
        this.message = message;
        this.type = type;
        this.length = length;
        this.protocolVersion = protocolVersion;
        this.cookieLength = cookieLength;
        this.cookie = cookie;
    }

    /**
     * Test of serializeHandshakeMessageContent method, of class HelloVerifyRequestSerializer.
     */
    @Test
    public void testSerializeHandshakeMessageContent() {
        HelloVerifyRequestMessage msg = new HelloVerifyRequestMessage();
        msg.setCompleteResultingMessage(message);
        msg.setType(type.getValue());
        msg.setType(type.getValue());
        msg.setProtocolVersion(protocolVersion);
        msg.setCookieLength(cookieLength);
        msg.setCookie(cookie);
        msg.setLength(length);
        HelloVerifyRequestSerializer serializer = new HelloVerifyRequestSerializer(msg, ProtocolVersion.DTLS10);
        assertArrayEquals(message, serializer.serialize());
    }

}
