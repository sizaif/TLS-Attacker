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
import de.rub.nds.tlsattacker.core.protocol.message.extension.RenegotiationInfoExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.RenegotiationInfoExtensionSerializer;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import de.rub.nds.tlsattacker.transport.ConnectionEndType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RenegotiationInfoExtensionPreparator extends ExtensionPreparator<RenegotiationInfoExtensionMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final RenegotiationInfoExtensionMessage message;

    public RenegotiationInfoExtensionPreparator(Chooser chooser, RenegotiationInfoExtensionMessage message,
        RenegotiationInfoExtensionSerializer serializer) {
        super(chooser, message, serializer);
        this.message = message;
    }

    @Override
    public void prepareExtensionContent() {
        if (chooser.getContext().getLastClientVerifyData() != null
            && chooser.getContext().getLastServerVerifyData() != null) {
            // We are renegotiating
            if (chooser.getContext().getTalkingConnectionEndType() == ConnectionEndType.CLIENT) {
                message.setRenegotiationInfo(chooser.getContext().getLastClientVerifyData());
            } else {
                message.setRenegotiationInfo(ArrayConverter.concatenate(chooser.getContext().getLastClientVerifyData(),
                    chooser.getContext().getLastServerVerifyData()));
            }
        } else {
            // First time we send this message
            if (chooser.getContext().getTalkingConnectionEndType() == ConnectionEndType.CLIENT) {
                message.setRenegotiationInfo(chooser.getConfig().getDefaultClientRenegotiationInfo());
            } else {
                message.setRenegotiationInfo(chooser.getConfig().getDefaultServerRenegotiationInfo());
            }
        }
        message.setRenegotiationInfoLength(message.getRenegotiationInfo().getValue().length);
        LOGGER.debug("Prepared the RenegotiationInfo extension with info "
            + ArrayConverter.bytesToHexString(message.getRenegotiationInfo().getValue()));
    }

}
