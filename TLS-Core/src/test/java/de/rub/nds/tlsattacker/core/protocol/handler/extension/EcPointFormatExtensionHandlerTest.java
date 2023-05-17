/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler.extension;

import de.rub.nds.tlsattacker.core.constants.ECPointFormat;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ECPointFormatExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.ECPointFormatExtensionParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.extension.ECPointFormatExtensionPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.ECPointFormatExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EcPointFormatExtensionHandlerTest {

    private EcPointFormatExtensionHandler handler;
    private TlsContext context;

    @Before
    public void setUp() {
        context = new TlsContext();
        handler = new EcPointFormatExtensionHandler(context);
    }

    /**
     * Test of adjustTLSContext method, of class EcPointFormatExtensionHandler.
     */
    @Test
    public void testAdjustTLSContext() {
        ECPointFormatExtensionMessage msg = new ECPointFormatExtensionMessage();
        msg.setPointFormats(new byte[] { 0, 1 });
        handler.adjustTLSContext(msg);
        assertTrue(context.getClientPointFormatsList().size() == 2);
        assertTrue(context.getClientPointFormatsList().contains(ECPointFormat.UNCOMPRESSED));
        assertTrue(context.getClientPointFormatsList().contains(ECPointFormat.ANSIX962_COMPRESSED_PRIME));
    }

    public void testUnadjustableMessage() {
        ECPointFormatExtensionMessage msg = new ECPointFormatExtensionMessage();
        msg.setPointFormats(new byte[] { 5 });
        handler.adjustTLSContext(msg);
        assertTrue(context.getClientPointFormatsList().isEmpty());
    }

    /**
     * Test of getParser method, of class EcPointFormatExtensionHandler.
     */
    @Test
    public void testGetParser() {
        assertTrue(
            handler.getParser(new byte[] { 123 }, 0, context.getConfig()) instanceof ECPointFormatExtensionParser);
    }

    /**
     * Test of getPreparator method, of class EcPointFormatExtensionHandler.
     */
    @Test
    public void testGetPreparator() {
        assertTrue(
            handler.getPreparator(new ECPointFormatExtensionMessage()) instanceof ECPointFormatExtensionPreparator);
    }

    /**
     * Test of getSerializer method, of class EcPointFormatExtensionHandler.
     */
    @Test
    public void testGetSerializer() {
        assertTrue(
            handler.getSerializer(new ECPointFormatExtensionMessage()) instanceof ECPointFormatExtensionSerializer);
    }

}
