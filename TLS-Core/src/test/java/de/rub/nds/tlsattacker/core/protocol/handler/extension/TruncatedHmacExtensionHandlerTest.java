/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler.extension;

import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import de.rub.nds.tlsattacker.core.protocol.message.extension.TruncatedHmacExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.TruncatedHmacExtensionParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.extension.TruncatedHmacExtensionPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.TruncatedHmacExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class TruncatedHmacExtensionHandlerTest {

    private TruncatedHmacExtensionHandler handler;
    private TlsContext context;

    @Before
    public void setUp() {
        context = new TlsContext();
        handler = new TruncatedHmacExtensionHandler(context);
    }

    @Test
    public void testAdjustTLSContext() {
        TruncatedHmacExtensionMessage message = new TruncatedHmacExtensionMessage();
        handler.adjustTLSContext(message);
        assertTrue(context.isExtensionProposed(ExtensionType.TRUNCATED_HMAC));
    }

    @Test
    public void testGetParser() {
        assertTrue(handler.getParser(new byte[0], 0, context.getConfig()) instanceof TruncatedHmacExtensionParser);
    }

    @Test
    public void testGetPreparator() {
        assertTrue(
            handler.getPreparator(new TruncatedHmacExtensionMessage()) instanceof TruncatedHmacExtensionPreparator);
    }

    @Test
    public void testGetSerializer() {
        assertTrue(
            handler.getSerializer(new TruncatedHmacExtensionMessage()) instanceof TruncatedHmacExtensionSerializer);
    }
}
