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
import de.rub.nds.tlsattacker.core.protocol.message.extension.statusrequestv2.RequestItemV2;
import de.rub.nds.tlsattacker.core.protocol.message.extension.statusrequestv2.ResponderId;
import de.rub.nds.tlsattacker.core.protocol.Serializer;

public class RequestItemV2Serializer extends Serializer<RequestItemV2> {

    private final RequestItemV2 reqItem;

    public RequestItemV2Serializer(RequestItemV2 reqItem) {
        this.reqItem = reqItem;
    }

    @Override
    protected byte[] serializeBytes() {
        appendInt(reqItem.getRequestType().getValue(), ExtensionByteLength.CERTIFICATE_STATUS_REQUEST_STATUS_TYPE);
        appendInt(reqItem.getRequestLength().getValue(),
            ExtensionByteLength.CERTIFICATE_STATUS_REQUEST_V2_REQUEST_LENGTH);
        appendInt(reqItem.getResponderIdListLength().getValue(),
            ExtensionByteLength.CERTIFICATE_STATUS_REQUEST_V2_RESPONDER_ID);

        if (reqItem.getResponderIdList() != null) {
            for (ResponderId id : reqItem.getResponderIdList()) {
                ResponderIdSerializer serializer = new ResponderIdSerializer(id);
                appendBytes(serializer.serialize());
            }
        }

        appendInt(reqItem.getRequestExtensionsLength().getValue(),
            ExtensionByteLength.CERTIFICATE_STATUS_REQUEST_V2_REQUEST_EXTENSION);
        appendBytes(reqItem.getRequestExtensions().getValue());

        return getAlreadySerialized();
    }

}
