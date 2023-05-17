/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.constants;

import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ProtocolVersionTest {

    public ProtocolVersionTest() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testGetFromValue() {
        ProtocolVersion version = ProtocolVersion.getProtocolVersion(new byte[] { 03, 03 });
        assertTrue(version == ProtocolVersion.TLS12);
    }

    /**
     * Test of gethighestProtocolVersion method, of class ProtocolVersion.
     */
    @Test
    public void testGethighestProtocolVersion() {
        List<ProtocolVersion> versions = new LinkedList<>();
        versions.add(ProtocolVersion.TLS10);
        versions.add(ProtocolVersion.TLS11);
        versions.add(ProtocolVersion.TLS12);
        versions.add(ProtocolVersion.TLS13);
        ProtocolVersion highestProtocolVersion = ProtocolVersion.getHighestProtocolVersion(versions);
        assertArrayEquals(ProtocolVersion.TLS13.getValue(), highestProtocolVersion.getValue());
    }
}
