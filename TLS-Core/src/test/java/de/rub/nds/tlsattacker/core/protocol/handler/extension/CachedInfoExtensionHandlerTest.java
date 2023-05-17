/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler.extension;

import de.rub.nds.tlsattacker.core.protocol.message.extension.CachedInfoExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.cachedinfo.CachedObject;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.CachedInfoExtensionParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.extension.CachedInfoExtensionPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.CachedInfoExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class CachedInfoExtensionHandlerTest {

    private final List<CachedObject> cachedObjects =
        Arrays.asList(new CachedObject((byte) 1, 2, new byte[] { 0x01, 0x02 }),
            new CachedObject((byte) 2, 3, new byte[] { 0x01, 0x02, 0x03 }));
    private CachedInfoExtensionHandler handler;
    private TlsContext context;

    @Before
    public void setUp() {
        context = new TlsContext();
        handler = new CachedInfoExtensionHandler(context);
    }

    @Test
    public void testAdjustTLSContext() {
        CachedInfoExtensionMessage msg = new CachedInfoExtensionMessage();
        msg.setCachedInfo(cachedObjects);
        CachedInfoExtensionPreparator preparator =
            new CachedInfoExtensionPreparator(context.getChooser(), msg, new CachedInfoExtensionSerializer(msg));
        preparator.prepare();

        handler.adjustTLSContext(msg);

        assertCachedObjectList(cachedObjects, context.getCachedInfoExtensionObjects());
    }

    @Test
    public void testGetParser() {
        assertTrue(handler.getParser(new byte[0], 0, context.getConfig()) instanceof CachedInfoExtensionParser);
    }

    @Test
    public void testGetPreparator() {
        assertTrue(handler.getPreparator(new CachedInfoExtensionMessage()) instanceof CachedInfoExtensionPreparator);
    }

    @Test
    public void testGetSerializer() {
        assertTrue(handler.getSerializer(new CachedInfoExtensionMessage()) instanceof CachedInfoExtensionSerializer);
    }

    public void assertCachedObjectList(List<CachedObject> expected, List<CachedObject> actual) {
        for (int i = 0; i < expected.size(); i++) {
            CachedObject expectedObject = expected.get(i);
            CachedObject actualObject = actual.get(i);

            assertEquals(expectedObject.getCachedInformationType().getValue(),
                actualObject.getCachedInformationType().getValue());
            assertEquals(expectedObject.getHashValueLength().getValue(), actualObject.getHashValueLength().getValue());
            assertArrayEquals(expectedObject.getHashValue().getValue(), actualObject.getHashValue().getValue());
        }
    }
}
