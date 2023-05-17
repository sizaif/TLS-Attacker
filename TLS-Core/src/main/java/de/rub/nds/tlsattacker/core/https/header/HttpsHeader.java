/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.https.header;

import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import de.rub.nds.tlsattacker.core.https.header.handler.HttpsHeaderHandler;
import de.rub.nds.tlsattacker.core.protocol.ModifiableVariableHolder;
import de.rub.nds.tlsattacker.core.protocol.Preparator;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import java.io.Serializable;

public abstract class HttpsHeader extends ModifiableVariableHolder implements Serializable {

    protected ModifiableString headerName;

    protected ModifiableString headerValue;

    public HttpsHeader() {
    }

    public ModifiableString getHeaderName() {
        return headerName;
    }

    public void setHeaderName(ModifiableString headerName) {
        this.headerName = headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = ModifiableVariableFactory.safelySetValue(this.headerName, headerName);
    }

    public ModifiableString getHeaderValue() {
        return headerValue;
    }

    public void setHeaderValue(ModifiableString headerValue) {
        this.headerValue = headerValue;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValue = ModifiableVariableFactory.safelySetValue(this.headerValue, headerValue);
    }

    public abstract Preparator getPreparator(Chooser chooser);

    public HttpsHeaderHandler getHandler() {
        return null;
    }
}
