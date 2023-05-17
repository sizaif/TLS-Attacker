/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer;

import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.ProtocolMessageSerializer;
import de.rub.nds.tlsattacker.core.protocol.message.TlsMessage;

/**
 * @param <T>
 *            The ProtocolMessage that should be serialized
 */
public abstract class TlsMessageSerializer<T extends TlsMessage> extends ProtocolMessageSerializer<T> {

    protected ProtocolVersion version;

    /**
     * Constructor for the ProtocolMessageSerializer
     *
     * @param message
     *                Message that should be serialized
     * @param version
     *                Version of the Protocol
     */
    public TlsMessageSerializer(T message, ProtocolVersion version) {
        super(message);
        this.version = version;
    }

}
