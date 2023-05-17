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
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.tlsattacker.core.https.header.preparator.ContentLengthHeaderPreparator;
import de.rub.nds.tlsattacker.core.protocol.Preparator;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import javax.xml.bind.annotation.XmlTransient;

public class ContentLengthHeader extends HttpsHeader {

    private ModifiableInteger length;

    @XmlTransient
    private int configLength;

    public ContentLengthHeader() {
    }

    @Override
    public ContentLengthHeaderPreparator getPreparator(Chooser chooser) {
        return new ContentLengthHeaderPreparator(chooser, this);
    }

    public ModifiableInteger getLength() {
        return length;
    }

    public void setLength(ModifiableInteger length) {
        this.length = length;
    }

    public void setLength(int length) {
        this.length = ModifiableVariableFactory.safelySetValue(this.length, length);
    }

    public int getConfigLength() {
        return configLength;
    }

    public void setConfigLength(int configLength) {
        this.configLength = configLength;
    }

}
