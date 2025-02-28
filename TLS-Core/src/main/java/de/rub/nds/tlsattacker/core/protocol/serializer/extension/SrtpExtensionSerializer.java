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
import de.rub.nds.tlsattacker.core.protocol.message.extension.SrtpExtensionMessage;

public class SrtpExtensionSerializer extends ExtensionSerializer<SrtpExtensionMessage> {

    private final SrtpExtensionMessage msg;

    public SrtpExtensionSerializer(SrtpExtensionMessage message) {
        super(message);
        this.msg = message;
    }

    @Override
    public byte[] serializeExtensionContent() {
        appendInt(msg.getSrtpProtectionProfilesLength().getValue(),
            ExtensionByteLength.SRTP_PROTECTION_PROFILES_LENGTH);
        appendBytes(msg.getSrtpProtectionProfiles().getValue());
        appendInt(msg.getSrtpMkiLength().getValue(), ExtensionByteLength.SRTP_MASTER_KEY_IDENTIFIER_LENGTH);
        if (msg.getSrtpMkiLength().getValue() != 0) {
            appendBytes(msg.getSrtpMki().getValue());
        }

        return getAlreadySerialized();
    }

}
