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
import de.rub.nds.tlsattacker.core.protocol.message.extension.ServerCertificateTypeExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.ExtensionSerializer;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import de.rub.nds.tlsattacker.transport.ConnectionEndType;

public class ServerCertificateTypeExtensionPreparator
    extends ExtensionPreparator<ServerCertificateTypeExtensionMessage> {

    private final ServerCertificateTypeExtensionMessage msg;

    public ServerCertificateTypeExtensionPreparator(Chooser chooser, ServerCertificateTypeExtensionMessage message,
        ExtensionSerializer<ServerCertificateTypeExtensionMessage> serializer) {
        super(chooser, message, serializer);
        msg = message;
    }

    @Override
    public void prepareExtensionContent() {
        msg.setCertificateTypes(
            CertificateType.toByteArray(chooser.getConfig().getServerCertificateTypeDesiredTypes()));
        msg.setCertificateTypesLength(msg.getCertificateTypes().getValue().length);

        if (chooser.getTalkingConnectionEnd() == ConnectionEndType.CLIENT) {
            msg.setIsClientMessage(true);
        } else {
            msg.setIsClientMessage(false);
        }
    }

}
