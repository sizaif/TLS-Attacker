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
import de.rub.nds.tlsattacker.core.constants.HandshakeByteLength;
import de.rub.nds.tlsattacker.core.constants.KeyExchangeAlgorithm;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.PskDheServerKeyExchangeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PskDheServerKeyExchangeParser extends DHEServerKeyExchangeParser<PskDheServerKeyExchangeMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final ProtocolVersion version;

    /**
     * Constructor for the Parser class
     *
     * @param pointer
     *                Position in the array where the ServerKeyExchangeParser is supposed to start parsing
     * @param array
     *                The byte[] which the ServerKeyExchangeParser is supposed to parse
     * @param version
     *                Version of the Protocol
     * @param config
     *                A Config used in the current context
     */
    public PskDheServerKeyExchangeParser(int pointer, byte[] array, ProtocolVersion version, Config config) {
        super(pointer, array, version, KeyExchangeAlgorithm.DHE_PSK, config);
        this.version = version;
    }

    @Override
    protected void parseHandshakeMessageContent(PskDheServerKeyExchangeMessage msg) {
        LOGGER.debug("Parsing PSKDHEServerKeyExchangeMessage");
        parsePskIdentityHintLength(msg);
        parsePskIdentityHint(msg);
        super.parseDheParams(msg);

    }

    @Override
    protected PskDheServerKeyExchangeMessage createHandshakeMessage() {
        return new PskDheServerKeyExchangeMessage();
    }

    private void parsePskIdentityHintLength(PskDheServerKeyExchangeMessage msg) {
        msg.setIdentityHintLength(parseIntField(HandshakeByteLength.PSK_IDENTITY_LENGTH));
        LOGGER.debug("SerializedPSL-IdentityLength: " + msg.getIdentityHintLength().getValue());
    }

    /**
     * Reads the next bytes as the PSKIdentityHint and writes them in the message
     *
     * @param msg
     *            Message to write in
     */
    private void parsePskIdentityHint(PskDheServerKeyExchangeMessage msg) {
        msg.setIdentityHint(parseByteArrayField(msg.getIdentityHintLength().getValue()));
        LOGGER.debug("SerializedPSK-Identity: " + ArrayConverter.bytesToHexString(msg.getIdentityHint().getValue()));
    }
}
