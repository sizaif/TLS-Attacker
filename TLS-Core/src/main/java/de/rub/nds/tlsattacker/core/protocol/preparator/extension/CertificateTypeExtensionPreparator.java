/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.tlsattacker.core.constants.CertificateType;
import de.rub.nds.tlsattacker.core.protocol.message.extension.CertificateTypeExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.ExtensionSerializer;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;

public class CertificateTypeExtensionPreparator extends ExtensionPreparator<CertificateTypeExtensionMessage> {

    private final CertificateTypeExtensionMessage msg;

    public CertificateTypeExtensionPreparator(Chooser chooser, CertificateTypeExtensionMessage message,
        ExtensionSerializer<CertificateTypeExtensionMessage> serializer) {
        super(chooser, message, serializer);
        msg = message;
    }

    @Override
    public void prepareExtensionContent() {
        msg.setCertificateTypes(CertificateType.toByteArray(chooser.getConfig().getCertificateTypeDesiredTypes()));
        msg.setCertificateTypesLength(msg.getCertificateTypes().getValue().length);
        msg.setIsClientMessage(chooser.getConfig().isCertificateTypeExtensionMessageState());
    }

}
