/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.tlsattacker.core.protocol.message.SSL2ClientMasterKeyMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.HandshakeMessageParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.SSL2ClientMasterKeyPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.SSL2ClientMasterKeySerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

public class SSL2ClientMasterKeyHandler extends HandshakeMessageHandler<SSL2ClientMasterKeyMessage> {

    public SSL2ClientMasterKeyHandler(TlsContext context) {
        super(context);
    }

    @Override
    public HandshakeMessageParser<SSL2ClientMasterKeyMessage> getParser(byte[] message, int pointer) {
        // We currently don't receive ClientMasterKey messages, only send them.
        return null;
    }

    @Override
    public SSL2ClientMasterKeyPreparator getPreparator(SSL2ClientMasterKeyMessage message) {
        return new SSL2ClientMasterKeyPreparator(tlsContext.getChooser(), message);
    }

    @Override
    public SSL2ClientMasterKeySerializer getSerializer(SSL2ClientMasterKeyMessage message) {
        return new SSL2ClientMasterKeySerializer(message, tlsContext.getChooser().getSelectedProtocolVersion());
    }

    @Override
    public void adjustTLSContext(SSL2ClientMasterKeyMessage message) {
        byte[] premasterSecret = message.getComputations().getPremasterSecret().getValue();
        tlsContext.setPreMasterSecret(premasterSecret);
        tlsContext.setClearKey(message.getClearKeyData().getValue());
        if (tlsContext.getChooser().getSSL2CipherSuite().getBlockSize() != 0) {
            tlsContext.setSSL2Iv(message.getKeyArgData().getValue());
        }
    }

}
