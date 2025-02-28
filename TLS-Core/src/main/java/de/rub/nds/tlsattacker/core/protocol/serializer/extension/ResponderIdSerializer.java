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
import de.rub.nds.tlsattacker.core.protocol.message.extension.statusrequestv2.ResponderId;
import de.rub.nds.tlsattacker.core.protocol.Serializer;

public class ResponderIdSerializer extends Serializer<ResponderId> {

    private final ResponderId id;

    public ResponderIdSerializer(ResponderId id) {
        this.id = id;
    }

    @Override
    protected byte[] serializeBytes() {
        appendInt(id.getIdLength().getValue(), ExtensionByteLength.CERTIFICATE_STATUS_REQUEST_V2_RESPONDER_ID);
        appendBytes(id.getId().getValue());

        return getAlreadySerialized();
    }

}
