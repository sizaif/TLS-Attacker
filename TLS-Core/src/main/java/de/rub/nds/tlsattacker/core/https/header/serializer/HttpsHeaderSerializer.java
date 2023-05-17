/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.https.header.serializer;

import de.rub.nds.tlsattacker.core.https.header.HttpsHeader;
import java.nio.charset.StandardCharsets;
import de.rub.nds.tlsattacker.core.protocol.Serializer;

public class HttpsHeaderSerializer extends Serializer<HttpsHeader> {

    private final HttpsHeader header;

    public HttpsHeaderSerializer(HttpsHeader header) {
        super();
        this.header = header;
    }

    @Override
    protected byte[] serializeBytes() {
        appendBytes(header.getHeaderName().getValue().getBytes(StandardCharsets.ISO_8859_1));
        appendBytes(": ".getBytes());
        appendBytes(header.getHeaderValue().getValue().getBytes(StandardCharsets.ISO_8859_1));
        appendBytes("\r\n".getBytes());
        return getAlreadySerialized();
    }
}
