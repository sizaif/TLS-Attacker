/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler.extension;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.AuthzDataFormat;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ClientAuthzExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.ClientAuthzExtensionParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.extension.ClientAuthzExtensionPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.ClientAuthzExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ClientAuthzExtensionHandlerTest {

    private final byte[] authzFormatListAsBytes = ArrayConverter.hexStringToByteArray("00010203");
    private final List<AuthzDataFormat> authzFormatList = Arrays.asList(AuthzDataFormat.X509_ATTR_CERT,
        AuthzDataFormat.SAML_ASSERTION, AuthzDataFormat.X509_ATTR_CERT_URL, AuthzDataFormat.SAML_ASSERTION_URL);
    private ClientAuthzExtensionHandler handler;
    private TlsContext context;

    @Before
    public void setUp() {
        context = new TlsContext();
        handler = new ClientAuthzExtensionHandler(context);
    }

    @Test
    public void testAdjustTLSContext() {
        ClientAuthzExtensionMessage msg = new ClientAuthzExtensionMessage();
        msg.setAuthzFormatList(authzFormatListAsBytes);

        handler.adjustTLSContext(msg);

        assertThat(authzFormatList, is(context.getClientAuthzDataFormatList()));
    }

    @Test
    public void testGetParser() {
        assertTrue(handler.getParser(new byte[0], 0, context.getConfig()) instanceof ClientAuthzExtensionParser);
    }

    @Test
    public void testGetPreparator() {
        assertTrue(handler.getPreparator(new ClientAuthzExtensionMessage()) instanceof ClientAuthzExtensionPreparator);
    }

    @Test
    public void testGetSerializer() {
        assertTrue(handler.getSerializer(new ClientAuthzExtensionMessage()) instanceof ClientAuthzExtensionSerializer);
    }
}
