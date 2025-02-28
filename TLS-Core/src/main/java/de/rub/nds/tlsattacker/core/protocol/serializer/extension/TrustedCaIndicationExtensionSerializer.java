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
import de.rub.nds.tlsattacker.core.protocol.message.extension.TrustedCaIndicationExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.trustedauthority.TrustedAuthority;

public class TrustedCaIndicationExtensionSerializer extends ExtensionSerializer<TrustedCaIndicationExtensionMessage> {

    private final TrustedCaIndicationExtensionMessage msg;

    public TrustedCaIndicationExtensionSerializer(TrustedCaIndicationExtensionMessage message) {
        super(message);
        msg = message;
    }

    @Override
    public byte[] serializeExtensionContent() {
        appendInt(msg.getTrustedAuthoritiesLength().getValue(), ExtensionByteLength.TRUSTED_AUTHORITY_LIST_LENGTH);

        for (TrustedAuthority ta : msg.getTrustedAuthorities()) {
            TrustedAuthoritySerializer serializer = new TrustedAuthoritySerializer(ta);
            appendBytes(serializer.serialize());
        }

        return getAlreadySerialized();
    }

}
