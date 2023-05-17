/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.record.layer;

import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import de.rub.nds.tlsattacker.util.tests.IntegrationTests;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TlsRecordLayerIT {

    private TlsRecordLayer layer;

    public TlsRecordLayerIT() {
    }

    @Before
    public void setUp() {
        layer = new TlsRecordLayer(new TlsContext(Config.createConfig()));
    }

    /**
     * Test of parseRecords method, of class TlsRecordLayer.
     */
    @Test
    @Category(IntegrationTests.class)
    public void testParseRecords() {
        Random random = new Random(0);
        for (int i = 0; i < 1000; i++) {
            byte[] data = new byte[random.nextInt(1000)];
            random.nextBytes(data);
            layer.parseRecordsSoftly(data);
        }
    }
}
