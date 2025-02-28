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
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class HttpsRequestParserTest {

    private final Config config = Config.createConfig();

    public HttpsRequestParserTest() {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of parseMessageContent method, of class HttpsRequestParser with an invalid request.
     */
    @Test(expected = ParserException.class)
    public void testParseMessageContentFailed() {
        HttpsRequestParser parser = new HttpsRequestParser(0,
            ArrayConverter.hexStringToByteArray("AAAAAAAAAAAAAAAAAAAAAAAA"), ProtocolVersion.TLS12, config);
        parser.parse();
    }

    /**
     * Test of parseMessageContent method, of class HttpsRequestParser with an valid request.
     */
    @Test
    public void testParseMessageContentSuccess() {
        String message = "GET /index.html HTTP/1.1\r\nUser-Agent: Test\r\nHost: www.rub.de\r\n\r\n";

        HttpsRequestParser parser =
            new HttpsRequestParser(0, message.getBytes(Charset.forName("UTF-8")), ProtocolVersion.TLS12, config);
        HttpsRequestMessage parsedMessage = parser.parse();

        assertEquals(parsedMessage.getRequestType().getValue(), "GET");
        assertEquals(parsedMessage.getRequestPath().getValue(), "/index.html");
        assertEquals(parsedMessage.getRequestProtocol().getValue(), "HTTP/1.1");

        assertEquals(parsedMessage.getHeader().get(0).getHeaderName().getValue(), "User-Agent");
        assertEquals(parsedMessage.getHeader().get(0).getHeaderValue().getValue(), "Test");

        assertEquals(parsedMessage.getHeader().get(1).getHeaderName().getValue(), "Host");
        assertEquals(parsedMessage.getHeader().get(1).getHeaderValue().getValue(), "www.rub.de");
    }

}
