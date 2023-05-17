/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.tlsattacker.core.protocol.message.extension.sni.ServerNamePair;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ServerNamePairPreparatorTest {

    private TlsContext context;
    private ServerNamePair pair;
    private ServerNamePairPreparator preparator;
    private final byte[] serverName = new byte[] { 0x01, 0x02 };
    private final byte serverNameType = 1;
    private final int serverNameLength = 2;

    @Before
    public void setUp() {
        context = new TlsContext();
        pair = new ServerNamePair(serverNameType, serverName);
        preparator = new ServerNamePairPreparator(context.getChooser(), pair);
    }

    /**
     * Test of prepare method, of class ServerNamePairPreparator.
     */
    @Test
    public void testPrepare() {
        preparator.prepare();

        assertArrayEquals(serverName, pair.getServerName().getValue());
        assertEquals(serverNameType, (long) pair.getServerNameType().getValue());
        assertEquals(serverNameLength, (long) pair.getServerNameLength().getValue());
    }

}
