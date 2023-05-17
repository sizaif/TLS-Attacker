/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.https.header;

import de.rub.nds.modifiablevariable.HoldsModifiableVariable;
import de.rub.nds.tlsattacker.core.https.header.preparator.TokenBindingHeaderPreparator;
import de.rub.nds.tlsattacker.core.protocol.Preparator;
import de.rub.nds.tlsattacker.core.tokenbinding.TokenBindingMessage;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;

public class TokenBindingHeader extends HttpsHeader {

    @HoldsModifiableVariable
    private TokenBindingMessage message;

    public TokenBindingHeader() {
        message = new TokenBindingMessage();
    }

    public TokenBindingMessage getMessage() {
        return message;
    }

    public void setMessage(TokenBindingMessage message) {
        this.message = message;
    }

    @Override
    public TokenBindingHeaderPreparator getPreparator(Chooser chooser) {
        return new TokenBindingHeaderPreparator(chooser, this);
    }
}
