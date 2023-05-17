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
import de.rub.nds.tlsattacker.core.protocol.message.extension.ClientCertificateUrlExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.ClientCertificateUrlExtensionParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.extension.ClientCertificateUrlExtensionPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.ClientCertificateUrlExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ClientCertificateUrlExtensionHandlerTest {
    private ClientCertificateUrlExtensionHandler handler;
    private TlsContext context;

    @Before
    public void setUp() {
        context = new TlsContext();
        handler = new ClientCertificateUrlExtensionHandler(context);
    }

    @Test
    public void testAdjustTLSContext() {
        ClientCertificateUrlExtensionMessage message = new ClientCertificateUrlExtensionMessage();
        handler.adjustTLSContext(message);
        assertTrue(context.isExtensionProposed(ExtensionType.CLIENT_CERTIFICATE_URL));
    }

    @Test
    public void testGetParser() {
        assertTrue(
            handler.getParser(new byte[0], 0, context.getConfig()) instanceof ClientCertificateUrlExtensionParser);
    }

    @Test
    public void testGetPreparator() {
        assertTrue(handler.getPreparator(
            new ClientCertificateUrlExtensionMessage()) instanceof ClientCertificateUrlExtensionPreparator);
    }

    @Test
    public void testGetSerializer() {
        assertTrue(handler.getSerializer(
            new ClientCertificateUrlExtensionMessage()) instanceof ClientCertificateUrlExtensionSerializer);
    }
}
