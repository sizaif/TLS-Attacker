/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.parser.extension;

import de.rub.nds.tlsattacker.core.constants.ExtensionByteLength;
import de.rub.nds.tlsattacker.core.exceptions.ParserException;
import de.rub.nds.tlsattacker.core.protocol.message.extension.statusrequestv2.RequestItemV2;
import de.rub.nds.tlsattacker.core.protocol.message.extension.statusrequestv2.ResponderId;
import de.rub.nds.tlsattacker.core.protocol.Parser;
import java.util.LinkedList;
import java.util.List;

public class RequestItemV2Parser extends Parser<RequestItemV2> {

    public RequestItemV2Parser(int startposition, byte[] array) {
        super(startposition, array);
    }

    @Override
    public RequestItemV2 parse() {
        RequestItemV2 item = new RequestItemV2();

        item.setRequestType(parseIntField(ExtensionByteLength.CERTIFICATE_STATUS_REQUEST_STATUS_TYPE));
        item.setRequestLength(parseIntField(ExtensionByteLength.CERTIFICATE_STATUS_REQUEST_V2_REQUEST_LENGTH));
        item.setResponderIdListLength(parseIntField(ExtensionByteLength.CERTIFICATE_STATUS_REQUEST_V2_RESPONDER_ID));
        item.setResponderIdListBytes(parseByteArrayField(item.getResponderIdListLength().getValue()));
        item.setRequestExtensionsLength(
            parseIntField(ExtensionByteLength.CERTIFICATE_STATUS_REQUEST_V2_REQUEST_EXTENSION));
        item.setRequestExtensions(parseByteArrayField(item.getRequestExtensionsLength().getValue()));

        int position = 0;
        List<ResponderId> responderIds = new LinkedList<>();

        while (position < item.getResponderIdListBytes().getValue().length) {
            ResponderIdParser parser = new ResponderIdParser(position, item.getResponderIdListBytes().getValue());
            responderIds.add(parser.parse());
            if (position == parser.getPointer()) {
                throw new ParserException("Ran into infinite Loop while parsing ResponderId");
            }
            position = parser.getPointer();
        }
        item.setResponderIdList(responderIds);

        return item;
    }
}
