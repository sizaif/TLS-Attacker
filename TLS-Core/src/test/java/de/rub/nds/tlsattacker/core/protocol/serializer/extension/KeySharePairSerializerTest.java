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
import de.rub.nds.tlsattacker.core.protocol.message.extension.keyshare.KeyShareEntry;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

public class KeySharePairSerializerTest {

    private KeyShareEntrySerializer serializer;
    private KeyShareEntry entry;

    public KeySharePairSerializerTest() {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of serializeBytes method, of class KeyShareEntrySerializer.
     */
    @Test
    public void testSerializeBytes() {
        entry = new KeyShareEntry();
        entry.setPublicKeyLength(32);
        entry.setGroup(ArrayConverter.hexStringToByteArray("001D"));
        entry.setPublicKey(
            ArrayConverter.hexStringToByteArray("2a981db6cdd02a06c1763102c9e741365ac4e6f72b3176a6bd6a3523d3ec0f4c"));
        serializer = new KeyShareEntrySerializer(entry);
        byte[] result = serializer.serialize();
        assertArrayEquals(ArrayConverter
            .hexStringToByteArray("001D00202a981db6cdd02a06c1763102c9e741365ac4e6f72b3176a6bd6a3523d3ec0f4c"), result);
    }

}
