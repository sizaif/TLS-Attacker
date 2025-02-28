/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.crypto.cipher;

import de.rub.nds.tlsattacker.core.constants.BulkCipherAlgorithm;
import de.rub.nds.tlsattacker.core.constants.CipherAlgorithm;
import de.rub.nds.tlsattacker.util.UnlimitedStrengthEnabler;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.LinkedList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.bouncycastle.asn1.bsi.BSIObjectIdentifiers.algorithm;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 *
 */
public class JavaCipherTest {

    private final static Logger LOGGER = LogManager.getLogger();

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public JavaCipherTest() {
    }

    @Before
    public void setUp() {
        Security.addProvider(new BouncyCastleProvider());
        UnlimitedStrengthEnabler.enable();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void generalTest() {
        List<CipherAlgorithm> algos = new LinkedList<>();
        algos.add(CipherAlgorithm.AES_128_CBC);
        algos.add(CipherAlgorithm.AES_128_CCM);
        algos.add(CipherAlgorithm.AES_128_GCM);
        algos.add(CipherAlgorithm.AES_256_CBC);
        algos.add(CipherAlgorithm.AES_256_CCM);
        algos.add(CipherAlgorithm.AES_256_GCM);
        algos.add(CipherAlgorithm.ARIA_128_CBC);
        algos.add(CipherAlgorithm.ARIA_128_GCM);
        algos.add(CipherAlgorithm.ARIA_256_CBC);
        algos.add(CipherAlgorithm.ARIA_256_GCM);
        algos.add(CipherAlgorithm.CAMELLIA_128_CBC);
        algos.add(CipherAlgorithm.CAMELLIA_128_GCM);
        algos.add(CipherAlgorithm.CAMELLIA_256_CBC);
        algos.add(CipherAlgorithm.CAMELLIA_256_GCM);
        algos.add(CipherAlgorithm.DES_CBC);
        algos.add(CipherAlgorithm.DES_EDE_CBC);
        algos.add(CipherAlgorithm.IDEA_128);
        algos.add(CipherAlgorithm.RC2_128);
        algos.add(CipherAlgorithm.RC4_128);
        algos.add(CipherAlgorithm.SEED_CBC);
        for (CipherAlgorithm algo : algos) {
            byte[] key = new byte[algo.getKeySize()];
            JavaCipher cipher = new JavaCipher(algo, key, false);
        }
    }
}
