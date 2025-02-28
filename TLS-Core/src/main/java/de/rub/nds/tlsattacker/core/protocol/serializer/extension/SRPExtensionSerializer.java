/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer.extension;

import de.rub.nds.tlsattacker.core.constants.ExtensionByteLength;
import de.rub.nds.tlsattacker.core.protocol.message.extension.SRPExtensionMessage;

public class SRPExtensionSerializer extends ExtensionSerializer<SRPExtensionMessage> {

    private final SRPExtensionMessage message;

    public SRPExtensionSerializer(SRPExtensionMessage message) {
        super(message);
        this.message = message;
    }

    @Override
    public byte[] serializeExtensionContent() {
        appendInt(message.getSrpIdentifierLength().getValue(), ExtensionByteLength.SRP_IDENTIFIER_LENGTH);
        appendBytes(message.getSrpIdentifier().getValue());

        return getAlreadySerialized();
    }

}
