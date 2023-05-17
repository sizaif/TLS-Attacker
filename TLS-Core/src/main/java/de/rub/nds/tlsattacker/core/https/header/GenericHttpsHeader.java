/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.https.header;

import de.rub.nds.modifiablevariable.util.IllegalStringAdapter;
import de.rub.nds.tlsattacker.core.https.header.preparator.GenericHttpsHeaderPreparator;
import de.rub.nds.tlsattacker.core.protocol.Preparator;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class GenericHttpsHeader extends HttpsHeader {

    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String headerNameConfig;

    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String headerValueConfig;

    public GenericHttpsHeader() {
    }

    public GenericHttpsHeader(String headerNameConfig, String headerValueConfig) {
        this.headerNameConfig = headerNameConfig;
        this.headerValueConfig = headerValueConfig;
    }

    public String getHeaderNameConfig() {
        return headerNameConfig;
    }

    public void setHeaderNameConfig(String headerNameConfig) {
        this.headerNameConfig = headerNameConfig;
    }

    public String getHeaderValueConfig() {
        return headerValueConfig;
    }

    public void setHeaderValueConfig(String headerValueConfig) {
        this.headerValueConfig = headerValueConfig;
    }

    @Override
    public GenericHttpsHeaderPreparator getPreparator(Chooser chooser) {
        return new GenericHttpsHeaderPreparator(chooser, this);
    }
}
