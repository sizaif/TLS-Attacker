/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer.extension;

import de.rub.nds.tlsattacker.core.protocol.message.extension.SignedCertificateTimestampExtensionMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SignedCertificateTimestampExtensionSerializer
    extends ExtensionSerializer<SignedCertificateTimestampExtensionMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final SignedCertificateTimestampExtensionMessage message;

    /**
     * Constructor
     *
     * @param message
     *                A SignedCertificateTimestampExtensionMessage
     */
    public SignedCertificateTimestampExtensionSerializer(SignedCertificateTimestampExtensionMessage message) {
        super(message);
        this.message = message;
    }

    /**
     * Serializes the extension
     *
     * @return Serialized extension
     */
    @Override
    public byte[] serializeExtensionContent() {
        appendBytes(message.getSignedTimestamp().getValue());
        LOGGER.debug("Serialized SignedCertificateTimestampExtension with timestamp of length "
            + message.getSignedTimestamp().getValue().length);
        return getAlreadySerialized();
    }

}
