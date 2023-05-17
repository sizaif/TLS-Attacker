/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.tlsattacker.core.protocol.message.extension.CachedInfoExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.cachedinfo.CachedObject;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.CachedInfoExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

public class CachedInfoExtensionPreparatorTest {

    private TlsContext context;
    private CachedInfoExtensionMessage msg;
    private CachedInfoExtensionPreparator preparator;
    private final List<CachedObject> cachedObjectsClient =
        Arrays.asList(new CachedObject((byte) 1, 2, new byte[] { 0x01, 0x02 }));
    private final List<CachedObject> cachedObjectsServer = Arrays.asList(new CachedObject((byte) 0x02, null, null));
    private final int cachedObjectClientLength = 4;
    private final int cachedObjectServerLength = 1;

    @Before
    public void setUp() {
        context = new TlsContext();
        msg = new CachedInfoExtensionMessage();
        preparator =
            new CachedInfoExtensionPreparator(context.getChooser(), msg, new CachedInfoExtensionSerializer(msg));
    }

    @Test
    public void testPreparator() {
        msg.setCachedInfo(cachedObjectsClient);

        preparator.prepare();

        assertEquals(cachedObjectClientLength, (long) msg.getCachedInfoLength().getValue());
        assertCachedObjectList(cachedObjectsClient, msg.getCachedInfo());

        msg.setCachedInfo(cachedObjectsServer);

        preparator.prepare();

        assertEquals(cachedObjectServerLength, (long) msg.getCachedInfoLength().getValue());
        assertCachedObjectList(cachedObjectsServer, msg.getCachedInfo());

    }

    public void assertCachedObjectList(List<CachedObject> expected, List<CachedObject> actual) {
        for (int i = 0; i < expected.size(); i++) {
            CachedObject expectedObject = expected.get(i);
            CachedObject actualObject = actual.get(i);

            assertEquals(expectedObject.getCachedInformationType().getValue(),
                actualObject.getCachedInformationType().getValue());
            if (expectedObject.getHashValueLength() != null && expectedObject.getHashValueLength().getValue() != null) {
                assertEquals(expectedObject.getHashValueLength().getValue(),
                    actualObject.getHashValueLength().getValue());
            } else {
                assertNull(actualObject.getHashValueLength());
            }
            if (expectedObject.getHashValue() != null && expectedObject.getHashValue().getValue() != null) {
                assertArrayEquals(expectedObject.getHashValue().getValue(), actualObject.getHashValue().getValue());
            } else {
                assertNull(actualObject.getHashValue());
            }
        }
    }
}
