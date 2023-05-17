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
import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.CertificateVerifyMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CertificateVerifyParser extends HandshakeMessageParser<CertificateVerifyMessage> {

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
     * @param config
     *                A Config used in the current context
     */
    public CertificateVerifyParser(int pointer, byte[] array, ProtocolVersion version, Config config) {
        super(pointer, array, HandshakeMessageType.CERTIFICATE_VERIFY, version, config);
    }

    @Override
    protected void parseHandshakeMessageContent(CertificateVerifyMessage msg) {
        LOGGER.debug("Parsing CertificateVerifyMessage");
        if (getVersion() == ProtocolVersion.TLS12 || getVersion() == ProtocolVersion.DTLS12 || getVersion().isTLS13()) {
            parseSignatureHashAlgorithm(msg);
        }
        parseSignatureLength(msg);
        parseSignature(msg);
    }

    @Override
    protected CertificateVerifyMessage createHandshakeMessage() {
        return new CertificateVerifyMessage();
    }

    /**
     * Reads the next bytes as the SignatureHashAlgorithm and writes them in the message
     *
     * @param msg
     *            Message to write in
     */
    private void parseSignatureHashAlgorithm(CertificateVerifyMessage msg) {
        msg.setSignatureHashAlgorithm(parseByteArrayField(HandshakeByteLength.SIGNATURE_HASH_ALGORITHM));
        LOGGER.debug(
            "SignatureHashAlgorithm: " + ArrayConverter.bytesToHexString(msg.getSignatureHashAlgorithm().getValue()));
    }

    /**
     * Reads the next bytes as the SignatureLength and writes them in the message
     *
     * @param msg
     *            Message to write in
     */
    private void parseSignatureLength(CertificateVerifyMessage msg) {
        msg.setSignatureLength(parseIntField(HandshakeByteLength.SIGNATURE_LENGTH));
        LOGGER.debug("SignatureLength: " + msg.getSignatureLength().getValue());
    }

    /**
     * Reads the next bytes as the Signature and writes them in the message
     *
     * @param msg
     *            Message to write in
     */
    private void parseSignature(CertificateVerifyMessage msg) {
        msg.setSignature(parseByteArrayField(msg.getSignatureLength().getValue()));
        LOGGER.debug("signature: " + ArrayConverter.bytesToHexString(msg.getSignature().getValue()));
    }

}
