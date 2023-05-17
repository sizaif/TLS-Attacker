/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.tlsattacker.core.constants.AuthzDataFormat;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ClientAuthzExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.ExtensionSerializer;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;

public class ClientAuthzExtensionPreparator extends ExtensionPreparator<ClientAuthzExtensionMessage> {

    private final ClientAuthzExtensionMessage msg;

    public ClientAuthzExtensionPreparator(Chooser chooser, ClientAuthzExtensionMessage message,
        ExtensionSerializer<ClientAuthzExtensionMessage> serializer) {
        super(chooser, message, serializer);
        msg = message;
    }

    @Override
    public void prepareExtensionContent() {
        msg.setAuthzFormatListLength(chooser.getConfig().getClientAuthzExtensionDataFormat().size());
        msg.setAuthzFormatList(
            AuthzDataFormat.listToByteArray(chooser.getConfig().getClientAuthzExtensionDataFormat()));
    }

}
