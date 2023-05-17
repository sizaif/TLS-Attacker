/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.tlsattacker.core.protocol.message.extension.SRPExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.SRPExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class SRPExtensionPreparatorTest {

    private TlsContext context;
    private SRPExtensionPreparator preparator;
    private SRPExtensionMessage message;
    private final byte[] srpIdentifier = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04 };
    private final int srpIdentifierLength = 5;

    @Before
    public void setUp() {
        context = new TlsContext();
        message = new SRPExtensionMessage();
        preparator = new SRPExtensionPreparator(context.getChooser(), message, new SRPExtensionSerializer(message));
    }

    @Test
    public void testPreparator() {
        context.getConfig().setSecureRemotePasswordExtensionIdentifier(srpIdentifier);

        preparator.prepare();

        assertArrayEquals(srpIdentifier, message.getSrpIdentifier().getValue());
        assertEquals(srpIdentifierLength, (long) message.getSrpIdentifierLength().getValue());

    }

}
