/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.parser;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.FinishedMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FinishedParser extends HandshakeMessageParser<FinishedMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Constructor for the Parser class
     *
     * @param pointer
     *                Position in the array where the HandshakeMessageParser is supposed to start parsing
     * @param array
     *                The byte[] which the HandshakeMessageParser is supposed to parse
     * @param version
     *                Version of the Protocol
     */
    public FinishedParser(int pointer, byte[] array, ProtocolVersion version, Config config) {
        super(pointer, array, HandshakeMessageType.FINISHED, version, config);
    }

    @Override
    protected void parseHandshakeMessageContent(FinishedMessage msg) {
        LOGGER.debug("Parsing FinishedMessage");
        parseVerifyData(msg);
    }

    @Override
    protected FinishedMessage createHandshakeMessage() {
        return new FinishedMessage();
    }

    /**
     * Reads the next bytes as the VerifyData and writes them in the message
     *
     * @param msg
     *            Message to write in
     */
    private void parseVerifyData(FinishedMessage msg) {
        msg.setVerifyData(parseByteArrayField(msg.getLength().getValue()));
        LOGGER.debug("VerifyData: " + ArrayConverter.bytesToHexString(msg.getVerifyData().getValue()));
    }

}
