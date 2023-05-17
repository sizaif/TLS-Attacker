/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import static de.rub.nds.modifiablevariable.util.ArrayConverter.bytesToHexString;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.CertificateStatusRequestType;
import de.rub.nds.tlsattacker.core.protocol.message.extension.CertificateStatusRequestExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.ExtensionSerializer;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CertificateStatusRequestExtensionPreparator
    extends ExtensionPreparator<CertificateStatusRequestExtensionMessage> {

    private static final Logger LOGGER = LogManager.getLogger();
    private final CertificateStatusRequestExtensionMessage msg;

    public CertificateStatusRequestExtensionPreparator(Chooser chooser,
        CertificateStatusRequestExtensionMessage message,
        ExtensionSerializer<CertificateStatusRequestExtensionMessage> serializer) {
        super(chooser, message, serializer);
        msg = message;
    }

    @Override
    public void prepareExtensionContent() {
        msg.setCertificateStatusRequestType(
            chooser.getConfig().getCertificateStatusRequestExtensionRequestType().getCertificateStatusRequestValue());
        LOGGER.debug("Prepared the CertificateStatusRequestExtension with request type " + CertificateStatusRequestType
            .getCertificateStatusRequestType(msg.getCertificateStatusRequestType().getValue()));
        msg.setResponderIDList(chooser.getConfig().getCertificateStatusRequestExtensionResponderIDList());
        msg.setResponderIDListLength(msg.getResponderIDList().getValue().length);
        LOGGER.debug("Prepared the CertificateStatusRequestExtension with responder ID list "
                + ArrayConverter.bytesToHexString(msg.getResponderIDList()));
        msg.setRequestExtension(chooser.getConfig().getCertificateStatusRequestExtensionRequestExtension());
        msg.setRequestExtensionLength(msg.getRequestExtension().getValue().length);
        LOGGER.debug("Prepared the CertificateStatusRequestExtension with request extension "
                + ArrayConverter.bytesToHexString(msg.getRequestExtension()));
    }

}
