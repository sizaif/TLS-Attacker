/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.tlsattacker.core.protocol.message.extension.UserMappingExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.UserMappingExtensionSerializer;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserMappingExtensionPreparator extends ExtensionPreparator<UserMappingExtensionMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final UserMappingExtensionMessage msg;

    public UserMappingExtensionPreparator(Chooser chooser, UserMappingExtensionMessage message,
        UserMappingExtensionSerializer serializer) {
        super(chooser, message, serializer);
        msg = message;
    }

    @Override
    public void prepareExtensionContent() {
        msg.setUserMappingType(chooser.getConfig().getUserMappingExtensionHintType().getValue());
        LOGGER.debug("Prepared the user mapping extension with hint type " + msg.getUserMappingType().getValue());
    }

}
