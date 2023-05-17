/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.https.header.parser;

import de.rub.nds.tlsattacker.core.exceptions.ParserException;
import de.rub.nds.tlsattacker.core.https.header.ContentLengthHeader;
import de.rub.nds.tlsattacker.core.https.header.DateHeader;
import de.rub.nds.tlsattacker.core.https.header.ExpiresHeader;
import de.rub.nds.tlsattacker.core.https.header.GenericHttpsHeader;
import de.rub.nds.tlsattacker.core.https.header.HostHeader;
import de.rub.nds.tlsattacker.core.https.header.HttpsHeader;
import de.rub.nds.tlsattacker.core.https.header.LocationHeader;
import de.rub.nds.tlsattacker.core.https.header.TokenBindingHeader;
import de.rub.nds.tlsattacker.core.protocol.Parser;

public class HttpsHeaderParser extends Parser<HttpsHeader> {

    public HttpsHeaderParser(int startposition, byte[] array) {
        super(startposition, array);
    }

    @Override
    public HttpsHeader parse() {
        HttpsHeader header;
        String parsedLine = parseStringTill((byte) 0x0A);
        String[] split = parsedLine.split(": ");
        if (split.length < 2) {
            throw new ParserException("Could not parse " + parsedLine + " as HttpsHeader");
        }
        String headerName = split[0];
        String headerValue =
            parsedLine.replaceFirst(split[0] + ":", "").replaceAll("\n", "").replaceAll("\r", "").trim();
        switch (headerName) {
            case "Host":
                header = new HostHeader();
                break;
            case "Sec-Token-Binding":
                header = new TokenBindingHeader();
                break;
            case "Location":
                header = new LocationHeader();
                break;
            case "Content-Length":
                header = new ContentLengthHeader();
                break;
            case "Expires":
                header = new ExpiresHeader();
                break;
            case "Date":
                header = new DateHeader();
                break;
            default:
                header = new GenericHttpsHeader();
        }
        header.setHeaderName(headerName);
        header.setHeaderValue(headerValue);
        return header;
    }
}
