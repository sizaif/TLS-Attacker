/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import de.rub.nds.tlsattacker.core.protocol.message.extension.RenegotiationInfoExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.RenegotiationInfoExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class RenegotiationInfoExtensionPreparatorTest {

    private final int extensionLength = 2;
    private final byte[] extensionPayload = new byte[] { 0 };
    private final int extensionPayloadLength = 1;
    private TlsContext context;
    private RenegotiationInfoExtensionMessage message;
    private RenegotiationInfoExtensionPreparator preparator;

    @Before
    public void setUp() {
        context = new TlsContext();
        message = new RenegotiationInfoExtensionMessage();
        preparator = new RenegotiationInfoExtensionPreparator(context.getChooser(), message,
            new RenegotiationInfoExtensionSerializer(message));

    }

    @Test
    public void testPreparator() {
        context.getConfig().setDefaultClientRenegotiationInfo(extensionPayload);
        preparator.prepare();

        assertArrayEquals(ExtensionType.RENEGOTIATION_INFO.getValue(), message.getExtensionType().getValue());
        assertEquals(extensionLength, (long) message.getExtensionLength().getValue());
        assertArrayEquals(extensionPayload, message.getRenegotiationInfo().getValue());
        assertEquals(extensionPayloadLength, (long) message.getRenegotiationInfoLength().getValue());
    }

    @Test
    public void testNoContextPrepare() {
        preparator.prepare();
    }

}
