/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol;

public abstract class ProtocolMessageSerializer<T extends ProtocolMessage> extends Serializer<T> {

    protected final T message;

    public ProtocolMessageSerializer(T message) {
        this.message = message;
    }

    @Override
    protected final byte[] serializeBytes() {
        return serializeProtocolMessageContent();
    }

    public abstract byte[] serializeProtocolMessageContent();
}
