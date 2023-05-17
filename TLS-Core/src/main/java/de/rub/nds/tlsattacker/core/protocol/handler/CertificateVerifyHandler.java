/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.tlsattacker.core.protocol.message.CertificateVerifyMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.CertificateVerifyParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.CertificateVerifyPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.CertificateVerifySerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

/**
 * Handling of the CertificateVerify protocol message: http://tools.ietf.org/html/rfc5246#section-7.4.8
 *
 * The TLS spec as well as wireshark bring some nice confusions: - The TLS spec says the message consists of only
 * signature bytes - Wireshark says the message consists of the signature length and signature bytes
 *
 * In fact, the certificate message consists of the following fields: - signature algorithm (2 bytes) - signature length
 * (2 bytes) - signature
 *
 * This structure is of course prepended with the handshake message length, as obvious for every handshake message.
 */
public class CertificateVerifyHandler extends HandshakeMessageHandler<CertificateVerifyMessage> {

    public CertificateVerifyHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    @Override
    public CertificateVerifyParser getParser(byte[] message, int pointer) {
        return new CertificateVerifyParser(pointer, message, tlsContext.getChooser().getSelectedProtocolVersion(),
            tlsContext.getConfig());
    }

    @Override
    public CertificateVerifyPreparator getPreparator(CertificateVerifyMessage message) {
        return new CertificateVerifyPreparator(tlsContext.getChooser(), message);
    }

    @Override
    public CertificateVerifySerializer getSerializer(CertificateVerifyMessage message) {
        return new CertificateVerifySerializer(message, tlsContext.getChooser().getSelectedProtocolVersion());
    }

    @Override
    public void adjustTLSContext(CertificateVerifyMessage message) {
        // Maybe check if we can verify signature and set boolean in context
        // //TODO
        // Don't adjust the TLSContext
    }
}
