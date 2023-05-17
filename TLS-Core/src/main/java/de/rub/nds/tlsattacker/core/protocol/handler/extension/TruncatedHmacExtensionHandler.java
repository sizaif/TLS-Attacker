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
import de.rub.nds.tlsattacker.core.protocol.message.extension.TruncatedHmacExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.TruncatedHmacExtensionParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.extension.TruncatedHmacExtensionPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.TruncatedHmacExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

public class TruncatedHmacExtensionHandler extends ExtensionHandler<TruncatedHmacExtensionMessage> {

    public TruncatedHmacExtensionHandler(TlsContext context) {
        super(context);
    }

    @Override
    public TruncatedHmacExtensionParser getParser(byte[] message, int pointer, Config config) {
        return new TruncatedHmacExtensionParser(pointer, message, config);
    }

    @Override
    public TruncatedHmacExtensionPreparator getPreparator(TruncatedHmacExtensionMessage message) {
        return new TruncatedHmacExtensionPreparator(context.getChooser(), message, getSerializer(message));
    }

    @Override
    public TruncatedHmacExtensionSerializer getSerializer(TruncatedHmacExtensionMessage message) {
        return new TruncatedHmacExtensionSerializer(message);
    }

    @Override
    public void adjustTLSExtensionContext(TruncatedHmacExtensionMessage message) {
    }

}
