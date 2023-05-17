/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.util;

import de.rub.nds.tlsattacker.core.constants.CipherSuite;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ServerCertificateKeyTest {

    /**
     * Test of getServerCertificateKey method, of class ServerCertificateKey.
     */
    @Test
    public void testGetServerCertificateKey() {
        assertEquals(ServerCertificateKey.DH,
            ServerCertificateKey.getServerCertificateKey(CipherSuite.TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA));
        assertEquals(ServerCertificateKey.RSA,
            ServerCertificateKey.getServerCertificateKey(CipherSuite.TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA));
        assertEquals(ServerCertificateKey.RSA,
            ServerCertificateKey.getServerCertificateKey(CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA));
        assertEquals(ServerCertificateKey.EC,
            ServerCertificateKey.getServerCertificateKey(CipherSuite.TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA));
        assertEquals(ServerCertificateKey.NONE,
            ServerCertificateKey.getServerCertificateKey(CipherSuite.TLS_ECDH_anon_WITH_RC4_128_SHA));
    }

}
