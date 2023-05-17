/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.tlsattacker.core.protocol.message.extension.cachedinfo.CachedObject;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CachedObjectPreparatorTest {
    private final byte cachedInfoType = 1;
    private final int hashLength = 3;
    private final byte[] hash = new byte[] { 0x01, 0x02, 0x03 };

    @Test
    public void testPreparator() {
        TlsContext context = new TlsContext();
        CachedObject object = new CachedObject(cachedInfoType, hashLength, hash);
        CachedObjectPreparator preparator = new CachedObjectPreparator(context.getChooser(), object);

        preparator.prepare();

        assertEquals(cachedInfoType, (long) object.getCachedInformationType().getValue());
        assertEquals(hashLength, (long) object.getHashValueLength().getValue());
        assertArrayEquals(hash, object.getHashValue().getValue());
    }
}
