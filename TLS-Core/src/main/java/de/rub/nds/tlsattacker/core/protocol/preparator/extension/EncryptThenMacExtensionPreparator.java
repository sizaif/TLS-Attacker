/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.tlsattacker.core.protocol.message.extension.EncryptThenMacExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.ExtensionSerializer;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;

public class EncryptThenMacExtensionPreparator extends ExtensionPreparator<EncryptThenMacExtensionMessage> {

    public EncryptThenMacExtensionPreparator(Chooser chooser, EncryptThenMacExtensionMessage message,
        ExtensionSerializer<EncryptThenMacExtensionMessage> serializer) {
        super(chooser, message, serializer);
    }

    @Override
    public void prepareExtensionContent() {
        // Nothing to prepare here, since it's an opt-in extension
    }

}
