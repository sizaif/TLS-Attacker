/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.tlsattacker.core.constants.CertificateType;
import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ClientCertificateTypeExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.ClientCertificateTypeExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ClientCertificateTypeExtensionPreparatorTest {

    private TlsContext context;
    private ClientCertificateTypeExtensionPreparator preparator;
    private ClientCertificateTypeExtensionMessage msg;
    private final List<CertificateType> certList = Arrays.asList(CertificateType.OPEN_PGP, CertificateType.X509);
    private final int extensionLength = 3;
    private final int cerListLength = 2;

    @Before
    public void setUp() {
        context = new TlsContext();
        msg = new ClientCertificateTypeExtensionMessage();
        preparator = new ClientCertificateTypeExtensionPreparator(context.getChooser(), msg,
            new ClientCertificateTypeExtensionSerializer(msg));
    }

    @Test
    public void testPreparator() {
        context.getConfig().setClientCertificateTypeDesiredTypes(certList);

        preparator.prepare();

        assertArrayEquals(ExtensionType.CLIENT_CERTIFICATE_TYPE.getValue(), msg.getExtensionType().getValue());
        assertEquals(extensionLength, (long) msg.getExtensionLength().getValue());
        assertArrayEquals(CertificateType.toByteArray(certList), msg.getCertificateTypes().getValue());
        assertEquals(cerListLength, (long) msg.getCertificateTypesLength().getValue());
    }

}
