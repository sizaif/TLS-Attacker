/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.https;

import de.rub.nds.tlsattacker.core.https.header.HttpsHeader;
import de.rub.nds.tlsattacker.core.protocol.preparator.TlsMessagePreparator;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;

public class HttpsRequestPreparator extends TlsMessagePreparator<HttpsRequestMessage> {

    private final HttpsRequestMessage message;

    public HttpsRequestPreparator(Chooser chooser, HttpsRequestMessage message) {
        super(chooser, message);
        this.message = message;
    }

    @Override
    protected void prepareProtocolMessageContents() {
        message.setRequestPath(chooser.getConfig().getDefaultHttpsRequestPath());
        message.setRequestProtocol("HTTP/1.1");
        message.setRequestType("GET");
        for (HttpsHeader header : message.getHeader()) {
            header.getPreparator(chooser).prepare();
        }
    }

}
