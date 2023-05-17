/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.workflow.action;

import de.rub.nds.tlsattacker.core.state.TlsContext;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChangeWriteEpochAction extends ChangeEpochAction {

    public ChangeWriteEpochAction() {
    }

    public ChangeWriteEpochAction(int epoch) {
        super(epoch);
    }

    @Override
    protected void changeEpoch(TlsContext tlsContext) {
        LOGGER.info("Changed write epoch");
        tlsContext.setWriteEpoch(epoch);
    }

}
