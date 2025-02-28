/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer.extension;

import de.rub.nds.tlsattacker.core.protocol.message.extension.ExtendedMasterSecretExtensionMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtendedMasterSecretExtensionSerializer extends ExtensionSerializer<ExtendedMasterSecretExtensionMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    public ExtendedMasterSecretExtensionSerializer(ExtendedMasterSecretExtensionMessage message) {
        super(message);
    }

    /**
     * Serializes the extended master secret extension. There is no data to serialize; it is a "just present" extension.
     *
     * @return Serialized bytes of the extended master secret extension
     */
    @Override
    public byte[] serializeExtensionContent() {
        LOGGER.debug("Serialized the extended master secret extension.");
        return getAlreadySerialized();
    }

}
