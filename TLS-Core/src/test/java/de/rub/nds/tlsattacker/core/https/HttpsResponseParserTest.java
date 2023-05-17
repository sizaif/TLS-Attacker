/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.https;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.exceptions.ParserException;
import java.nio.charset.Charset;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class HttpsResponseParserTest {

    private final Config config = Config.createConfig();

    public HttpsResponseParserTest() {
    }

    @Before
    public void setUp() {

    }

    /**
     * Test of parseMessageContent method, of class HttpsResponseParser with an invalid response.
     */
    @Test(expected = ParserException.class)
    public void testParseMessageContentFailed() {
        HttpsResponseParser parser = new HttpsResponseParser(0,
            ArrayConverter.hexStringToByteArray("AAAAAAAAAAAAAAAAAAAAAAAA"), ProtocolVersion.TLS12, config);
        parser.parse();
    }

    /**
     * Test of parseMessageContent method, of class HttpsResponseParser with a valid response.
     */
    @Test
    public void testParseMessageContentSuccess() {
        String message = "HTTP/1.1 200 OK\r\nDate: Mon, 27 Jul 2009 12:28:53 GMT\r\nServer: Apache/2.2.14 (Win32)\r\n"
            + "Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT\r\nContent-Length: 88\r\nContent-Type: text/html\r\nConnection: Closed\r\n\r\ntest";

        HttpsResponseParser parser =
            new HttpsResponseParser(0, message.getBytes(Charset.forName("UTF-8")), ProtocolVersion.TLS12, config);
        HttpsResponseMessage parsedMessage = parser.parse();

        assertEquals(parsedMessage.getResponseStatusCode().getValue(), "200 OK");
        assertEquals(parsedMessage.getResponseProtocol().getValue(), "HTTP/1.1");
        assertEquals(parsedMessage.getResponseContent().getValue(), "test");

        assertEquals(parsedMessage.getHeader().get(0).getHeaderName().getValue(), "Date");
        assertEquals(parsedMessage.getHeader().get(0).getHeaderValue().getValue(), "Mon, 27 Jul 2009 12:28:53 GMT");

        assertEquals(parsedMessage.getHeader().get(1).getHeaderName().getValue(), "Server");
        assertEquals(parsedMessage.getHeader().get(1).getHeaderValue().getValue(), "Apache/2.2.14 (Win32)");

        assertEquals(parsedMessage.getHeader().get(2).getHeaderName().getValue(), "Last-Modified");
        assertEquals(parsedMessage.getHeader().get(2).getHeaderValue().getValue(), "Wed, 22 Jul 2009 19:15:56 GMT");

        assertEquals(parsedMessage.getHeader().get(3).getHeaderName().getValue(), "Content-Length");
        assertEquals(parsedMessage.getHeader().get(3).getHeaderValue().getValue(), "88");

        assertEquals(parsedMessage.getHeader().get(4).getHeaderName().getValue(), "Content-Type");
        assertEquals(parsedMessage.getHeader().get(4).getHeaderValue().getValue(), "text/html");

        assertEquals(parsedMessage.getHeader().get(5).getHeaderName().getValue(), "Connection");
        assertEquals(parsedMessage.getHeader().get(5).getHeaderValue().getValue(), "Closed");
    }
}
