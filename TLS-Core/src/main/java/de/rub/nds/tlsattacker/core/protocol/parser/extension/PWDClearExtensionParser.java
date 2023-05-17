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
import de.rub.nds.tlsattacker.core.constants.ExtensionByteLength;
import de.rub.nds.tlsattacker.core.protocol.message.extension.PWDClearExtensionMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PWDClearExtensionParser extends ExtensionParser<PWDClearExtensionMessage> {
    private static final Logger LOGGER = LogManager.getLogger();

    public PWDClearExtensionParser(int startposition, byte[] array, Config config) {
        super(startposition, array, config);
    }

    @Override
    public void parseExtensionMessageContent(PWDClearExtensionMessage msg) {
        LOGGER.debug("Parsing PWDClearExtensionMessage");
        parseUsernameLength(msg);
        parseUsername(msg);
    }

    @Override
    protected PWDClearExtensionMessage createExtensionMessage() {
        return new PWDClearExtensionMessage();
    }

    /**
     * Reads the next bytes as the username length of the Extension and writes them in the message
     *
     * @param msg
     *            Message to write in
     */
    private void parseUsernameLength(PWDClearExtensionMessage msg) {
        msg.setUsernameLength(parseIntField(ExtensionByteLength.PWD_NAME));
        LOGGER.debug("UsernameLength: " + msg.getUsernameLength().getValue());
    }

    /**
     * Reads the next bytes as the username of the Extension and writes them in the message
     *
     * @param msg
     *            Message to write in
     */
    private void parseUsername(PWDClearExtensionMessage msg) {
        msg.setUsername(new String(parseByteArrayField(msg.getUsernameLength().getValue())));
        LOGGER.debug("Username: " + msg.getUsername().getValue());
    }
}
