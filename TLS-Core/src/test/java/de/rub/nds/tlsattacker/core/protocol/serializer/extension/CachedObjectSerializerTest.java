/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer.extension;

import de.rub.nds.tlsattacker.core.protocol.message.extension.cachedinfo.CachedObject;
import de.rub.nds.tlsattacker.core.protocol.preparator.extension.CachedObjectPreparator;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

public class CachedObjectSerializerTest {

    private CachedObjectSerializer serializer;
    private CachedObject object;
    private CachedObjectPreparator preparator;
    private TlsContext context;

    @Before
    public void setUp() {

    }

    @Test
    public void testSerializeBytes() {
        context = new TlsContext();
        object = new CachedObject((byte) 1, null, null);
        preparator = new CachedObjectPreparator(context.getChooser(), object);
        preparator.prepare();

        serializer = new CachedObjectSerializer(object);
        assertArrayEquals(new byte[] { (byte) 1 }, serializer.serialize());

        object = new CachedObject((byte) 2, 3, new byte[] { 0x01, 0x02, 0x03 });
        preparator = new CachedObjectPreparator(context.getChooser(), object);
        preparator.prepare();
        serializer = new CachedObjectSerializer(object);
        assertArrayEquals(new byte[] { 0x02, 0x03, 0x01, 0x02, 0x03 }, serializer.serialize());
    }
}
