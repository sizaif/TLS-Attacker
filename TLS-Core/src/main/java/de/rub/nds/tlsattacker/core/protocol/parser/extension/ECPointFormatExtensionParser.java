/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.parser.extension;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ExtensionByteLength;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ECPointFormatExtensionMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ECPointFormatExtensionParser extends ExtensionParser<ECPointFormatExtensionMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    public ECPointFormatExtensionParser(int startposition, byte[] array, Config config) {
        super(startposition, array, config);
    }

    @Override
    public void parseExtensionMessageContent(ECPointFormatExtensionMessage msg) {
        LOGGER.debug("Parsing ECPointFormatExtensionMessage");
        parsePointFormatsLength(msg);
        parsePointFormat(msg);
    }

    @Override
    protected ECPointFormatExtensionMessage createExtensionMessage() {
        return new ECPointFormatExtensionMessage();
    }

    /**
     * Reads the next bytes as the PointFormatsLength of the Extension and writes them in the message
     *
     * @param msg
     *            Message to write in
     */
    private void parsePointFormatsLength(ECPointFormatExtensionMessage msg) {
        msg.setPointFormatsLength(parseIntField(ExtensionByteLength.EC_POINT_FORMATS));
        LOGGER.debug("PointFormatsLength: " + msg.getPointFormatsLength().getValue());
    }

    /**
     * Reads the next bytes as the PointFormat of the Extension and writes them in the message
     *
     * @param msg
     *            Message to write in
     */
    private void parsePointFormat(ECPointFormatExtensionMessage msg) {
        msg.setPointFormats(parseByteArrayField(msg.getPointFormatsLength().getValue()));
        LOGGER.debug("PointFormats: " + ArrayConverter.bytesToHexString(msg.getPointFormats().getValue()));
    }

}
