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
import de.rub.nds.tlsattacker.core.protocol.message.extension.EncryptThenMacExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.EncryptThenMacExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class EncryptThenMacExtensionPreparatorTest {
    private final ExtensionType extensionType = ExtensionType.ENCRYPT_THEN_MAC;
    private final int extensionLength = 0;
    private TlsContext context;
    private EncryptThenMacExtensionMessage message;
    private EncryptThenMacExtensionPreparator preparator;

    @Before
    public void setUp() {
        context = new TlsContext();
        message = new EncryptThenMacExtensionMessage();
        preparator = new EncryptThenMacExtensionPreparator(context.getChooser(), message,
            new EncryptThenMacExtensionSerializer(message));
    }

    @Test
    public void testPreparator() {
        preparator.prepare();

        assertArrayEquals(extensionType.getValue(), message.getExtensionType().getValue());
        assertEquals(extensionLength, (long) message.getExtensionLength().getValue());
    }
}
