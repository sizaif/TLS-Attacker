/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.protocol.message.extension.SessionTicketTLSExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.SessionTicketTLSExtensionSerializer;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionTicketTLSExtensionPreparator extends ExtensionPreparator<SessionTicketTLSExtensionMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final SessionTicketTLSExtensionMessage message;

    public SessionTicketTLSExtensionPreparator(Chooser chooser, SessionTicketTLSExtensionMessage message,
        SessionTicketTLSExtensionSerializer serializer) {
        super(chooser, message, serializer);
        this.message = message;
    }

    /**
     * Parses the content of a SessionTicketTLSExtensionMessage of the TLSContext
     */
    @Override
    public void prepareExtensionContent() {
        message.getSessionTicket().setIdentity(chooser.getLatestSessionTicket());
        message.getSessionTicket().setIdentityLength(chooser.getLatestSessionTicket().length);
        LOGGER.debug("Prepared the SessionTicketTLSExtension with Ticket "
            + ArrayConverter.bytesToHexString(message.getSessionTicket().getIdentity().getValue()));
    }

}
