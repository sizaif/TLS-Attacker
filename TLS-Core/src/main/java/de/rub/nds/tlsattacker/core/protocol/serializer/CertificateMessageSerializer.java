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
import de.rub.nds.tlsattacker.core.protocol.message.CertificateMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CertificateMessageSerializer extends HandshakeMessageSerializer<CertificateMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final CertificateMessage msg;

    /**
     * Constructor for the CertificateMessageSerializer
     *
     * @param message
     *                Message that should be serialized
     * @param version
     *                Version of the Protocol
     */
    public CertificateMessageSerializer(CertificateMessage message, ProtocolVersion version) {
        super(message, version);
        this.msg = message;
    }

    @Override
    public byte[] serializeHandshakeMessageContent() {
        LOGGER.debug("Serializing CertificateMessage");
        if (version.isTLS13()) {
            writeRequestContextLength(msg);
            writeRequestContext(msg);
        }
        writeCertificatesListLength(msg);
        writeCertificatesListBytes(msg);
        return getAlreadySerialized();
    }

    /**
     * Writes the RequestContextLength of the CertificateMessage into the final byte[]
     */
    private void writeRequestContextLength(CertificateMessage msg) {
        appendInt(msg.getRequestContextLength().getValue(), HandshakeByteLength.CERTIFICATE_REQUEST_CONTEXT_LENGTH);
        LOGGER.debug("RequestContextLength: " + msg.getRequestContextLength().getValue());
    }

    /**
     * Writes the RequestContext of the CertificateMessage into the final byte[]
     */
    private void writeRequestContext(CertificateMessage msg) {
        appendBytes(msg.getRequestContext().getValue());
        LOGGER.debug("RequestContext: " + ArrayConverter.bytesToHexString(msg.getRequestContext().getValue()));
    }

    /**
     * Writes the CertificateLength of the CertificateMessage into the final byte[]
     */
    private void writeCertificatesListLength(CertificateMessage msg) {
        appendInt(msg.getCertificatesListLength().getValue(), HandshakeByteLength.CERTIFICATES_LENGTH);
        LOGGER.debug("certificatesListLength: " + msg.getCertificatesListLength().getValue());
    }

    /**
     * Writes the Certificate of the CertificateMessage into the final byte[]
     */
    private void writeCertificatesListBytes(CertificateMessage msg) {
        appendBytes(msg.getCertificatesListBytes().getValue());
        LOGGER.debug(
            "certificatesListBytes: " + ArrayConverter.bytesToHexString(msg.getCertificatesListBytes().getValue()));
    }

}
