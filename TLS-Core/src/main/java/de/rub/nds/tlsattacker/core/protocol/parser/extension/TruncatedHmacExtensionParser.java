/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.parser.extension;

import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.protocol.message.extension.TruncatedHmacExtensionMessage;

public class TruncatedHmacExtensionParser extends ExtensionParser<TruncatedHmacExtensionMessage> {

    public TruncatedHmacExtensionParser(int startposition, byte[] array, Config config) {
        super(startposition, array, config);
    }

    @Override
    public void parseExtensionMessageContent(TruncatedHmacExtensionMessage msg) {
        // nothing to parse here, it's a opt-in extension
    }

    @Override
    protected TruncatedHmacExtensionMessage createExtensionMessage() {
        return new TruncatedHmacExtensionMessage();
    }

}
