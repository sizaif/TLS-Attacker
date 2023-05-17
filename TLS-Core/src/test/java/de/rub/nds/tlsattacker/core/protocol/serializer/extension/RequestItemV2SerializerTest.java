/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer.extension;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.protocol.message.extension.statusrequestv2.RequestItemV2;
import de.rub.nds.tlsattacker.core.protocol.message.extension.statusrequestv2.ResponderId;
import de.rub.nds.tlsattacker.core.protocol.preparator.extension.RequestItemV2Preparator;
import de.rub.nds.tlsattacker.core.protocol.preparator.extension.ResponderIdPreparator;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class RequestItemV2SerializerTest {

    private final RequestItemV2 item = new RequestItemV2(1, 14, 7, 3, new byte[] { 0x01, 0x02, 0x03 });
    private final List<ResponderId> respIdList =
        Arrays.asList(new ResponderId(5, new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05 }));
    private final byte[] respIdListBytes = new byte[] { 0x00, 0x05, 0x01, 0x02, 0x03, 0x04, 0x05 };
    private final byte[] expectedBytes = ArrayConverter.hexStringToByteArray("01000E0007000501020304050003010203");

    @Test
    public void testSerializer() {
        for (ResponderId id : respIdList) {
            ResponderIdPreparator preparatorResponderId = new ResponderIdPreparator(new TlsContext().getChooser(), id);
            preparatorResponderId.prepare();
        }
        RequestItemV2Preparator preparator = new RequestItemV2Preparator(new TlsContext().getChooser(), item);
        preparator.prepare();
        item.setResponderIdList(respIdList);
        item.setResponderIdListBytes(respIdListBytes);
        RequestItemV2Serializer serializer = new RequestItemV2Serializer(item);

        assertArrayEquals(expectedBytes, serializer.serialize());
    }
}
