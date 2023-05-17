/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.https.header.preparator;

import de.rub.nds.tlsattacker.core.https.header.CookieHeader;
import de.rub.nds.tlsattacker.core.protocol.Preparator;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import org.apache.commons.lang3.StringUtils;

public class CookieHeaderPreparator extends Preparator<CookieHeader> {

    private final CookieHeader header;

    public CookieHeaderPreparator(Chooser chooser, CookieHeader header) {
        super(chooser, header);
        this.header = header;
    }

    @Override
    public void prepare() {
        header.setHeaderName("Cookie");
        String headerValue = StringUtils.join(chooser.getHttpsCookieName(), '=', chooser.getHttpsCookieValue());
        header.setHeaderValue(headerValue);
    }

}
