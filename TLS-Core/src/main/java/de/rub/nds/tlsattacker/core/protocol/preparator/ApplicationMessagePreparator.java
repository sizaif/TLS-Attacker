/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.protocol.message.ApplicationMessage;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplicationMessagePreparator extends TlsMessagePreparator<ApplicationMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final ApplicationMessage msg;

    public ApplicationMessagePreparator(Chooser chooser, ApplicationMessage message) {
        super(chooser, message);
        this.msg = message;
    }

    @Override
    protected void prepareProtocolMessageContents() {
        LOGGER.debug("Preparing ApplicationMessage");
        prepareData(msg);
    }

    private void prepareData(ApplicationMessage msg) {
        if (msg.getDataConfig() != null) {
            msg.setData(msg.getDataConfig());
        } else {
            msg.setData(chooser.getLastHandledApplicationMessageData());
        }
        LOGGER.debug("Data: " + ArrayConverter.bytesToHexString(msg.getData().getValue()));
    }

}
