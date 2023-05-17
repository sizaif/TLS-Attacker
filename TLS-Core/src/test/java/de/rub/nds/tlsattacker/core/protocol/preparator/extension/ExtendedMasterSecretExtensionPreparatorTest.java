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
import de.rub.nds.tlsattacker.core.protocol.message.extension.ExtendedMasterSecretExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.ExtendedMasterSecretExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ExtendedMasterSecretExtensionPreparatorTest {

    private final int extensionLength = 0;
    private TlsContext context;
    private ExtendedMasterSecretExtensionMessage message;
    private ExtendedMasterSecretExtensionPreparator preparator;

    @Before
    public void setUp() {
        context = new TlsContext();
        message = new ExtendedMasterSecretExtensionMessage();
        preparator = new ExtendedMasterSecretExtensionPreparator(context.getChooser(), message,
            new ExtendedMasterSecretExtensionSerializer(message));
    }

    @Test
    public void testPreparator() {

        context.getConfig().setAddExtendedMasterSecretExtension(true);
        preparator.prepare();

        assertArrayEquals(ExtensionType.EXTENDED_MASTER_SECRET.getValue(), message.getExtensionType().getValue());
        assertEquals(extensionLength, (long) message.getExtensionLength().getValue());

    }

    @Test
    public void testNoContextPrepare() {
        preparator.prepare();
    }

}
