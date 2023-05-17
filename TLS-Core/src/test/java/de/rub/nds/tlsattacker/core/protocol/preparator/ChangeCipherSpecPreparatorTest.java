/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator;

import de.rub.nds.tlsattacker.core.protocol.message.ChangeCipherSpecMessage;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ChangeCipherSpecPreparatorTest {

    private ChangeCipherSpecPreparator preparator;
    private ChangeCipherSpecMessage message;
    private TlsContext context;

    @Before
    public void setUp() {
        this.context = new TlsContext();
        this.message = new ChangeCipherSpecMessage();
        preparator = new ChangeCipherSpecPreparator(context.getChooser(), message);
    }

    /**
     * Test of prepareProtocolMessageContents method, of class ChangeCipherSpecPreparator.
     */
    @Test
    public void testPrepare() {
        preparator.prepare();
        assertTrue(message.getCcsProtocolType().getValue()[0] == 1);
    }
}
