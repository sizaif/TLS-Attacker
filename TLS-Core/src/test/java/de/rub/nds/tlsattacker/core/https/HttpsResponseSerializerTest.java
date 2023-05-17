/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.https;

import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class HttpsResponseSerializerTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() throws UnsupportedEncodingException {
        byte[] msg = "HTTP/1.1 200 OK\r\nHost: rub.com\r\nContent-Type: text/html\r\n\r\ndata\r\n".getBytes("ASCII");

        return Arrays.asList(new Object[][] { { msg, ProtocolVersion.TLS12, msg } });
    }

    private final byte[] msg;
    private final ProtocolVersion version;
    private final byte[] expPart;
    private final Config config = Config.createConfig();

    public HttpsResponseSerializerTest(byte[] msg, ProtocolVersion version, byte[] expPart) {
        this.msg = msg;
        this.version = version;
        this.expPart = expPart;
    }

    /**
     * Test of serializeProtocolMessageContent method, of class HttpsResponseSerializer.
     */
    @Test
    public void testSerializeProtocolMessageContent() {
        HttpsResponseParser parser = new HttpsResponseParser(0, msg, version, config);
        HttpsResponseMessage parsedMsg = parser.parse();
        HttpsResponseSerializer serializer = new HttpsResponseSerializer(parsedMsg, version);

        assertArrayEquals(expPart, serializer.serialize());
    }

}