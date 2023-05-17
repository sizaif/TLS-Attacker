/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler.extension;

import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.protocol.message.extension.PWDClearExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.PWDClearExtensionParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.extension.PWDClearExtensionPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.PWDClearExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

public class PWDClearExtensionHandler extends ExtensionHandler<PWDClearExtensionMessage> {
    public PWDClearExtensionHandler(TlsContext context) {
        super(context);
    }

    @Override
    public void adjustTLSExtensionContext(PWDClearExtensionMessage message) {
        context.setClientPWDUsername(message.getUsername().getValue());
    }

    @Override
    public PWDClearExtensionParser getParser(byte[] message, int pointer, Config config) {
        return new PWDClearExtensionParser(pointer, message, config);
    }

    @Override
    public PWDClearExtensionPreparator getPreparator(PWDClearExtensionMessage message) {
        return new PWDClearExtensionPreparator(context.getChooser(), message, getSerializer(message));
    }

    @Override
    public PWDClearExtensionSerializer getSerializer(PWDClearExtensionMessage message) {
        return new PWDClearExtensionSerializer(message);
    }
}
