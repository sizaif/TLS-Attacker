/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.HandshakeByteLength;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.PskClientKeyExchangeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PskClientKeyExchangeSerializer extends HandshakeMessageSerializer<PskClientKeyExchangeMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final PskClientKeyExchangeMessage msg;

    /**
     * Constructor for the PSKClientKeyExchangeSerializer
     *
     * @param message
     *                Message that should be serialized
     * @param version
     *                Version of the Protocol
     */
    public PskClientKeyExchangeSerializer(PskClientKeyExchangeMessage message, ProtocolVersion version) {
        super(message, version);
        this.msg = message;
    }

    @Override
    public byte[] serializeHandshakeMessageContent() {
        LOGGER.debug("Serializing PSKClientKeyExchangeMessage");
        writePskIdentityLength(msg);
        writePskIdentity(msg);
        return getAlreadySerialized();
    }

    /**
     * Writes the PskIdentityLength of the PskClientKeyExchangeMessage into the final byte[]
     */
    private void writePskIdentityLength(PskClientKeyExchangeMessage msg) {
        appendInt(msg.getIdentityLength().getValue(), HandshakeByteLength.PSK_IDENTITY_LENGTH);
        LOGGER.debug("PskIdentityLength: " + msg.getIdentityLength().getValue());
    }

    /**
     * Writes the pskIdentity of the PskClientKeyExchangeMessage into the final byte[]
     */
    private void writePskIdentity(PskClientKeyExchangeMessage msg) {
        appendBytes(msg.getIdentity().getValue());
        LOGGER.debug("PskIdentity: " + ArrayConverter.bytesToHexString(msg.getIdentity().getValue()));
    }
}
