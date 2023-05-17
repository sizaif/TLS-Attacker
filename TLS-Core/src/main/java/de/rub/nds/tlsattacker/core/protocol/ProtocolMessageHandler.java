/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol;

import de.rub.nds.tlsattacker.core.state.TlsContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ProtocolMessageHandler<MessageT extends ProtocolMessage> implements Handler<MessageT> {
    protected static final Logger LOGGER = LogManager.getLogger();
    /**
     * tls context
     */
    protected final TlsContext tlsContext;

    public ProtocolMessageHandler(TlsContext tlsContext) {
        this.tlsContext = tlsContext;
    }

    @Override
    public abstract ProtocolMessageParser<MessageT> getParser(byte[] message, int pointer);

    @Override
    public abstract ProtocolMessagePreparator<MessageT> getPreparator(MessageT message);

    @Override
    public abstract ProtocolMessageSerializer<MessageT> getSerializer(MessageT message);

    /**
     * Performs additional preparations after parsing the message (e.g. ESNI decryption/parsing).
     *
     * @param message
     */
    public void prepareAfterParse(MessageT message) {
    }
}
