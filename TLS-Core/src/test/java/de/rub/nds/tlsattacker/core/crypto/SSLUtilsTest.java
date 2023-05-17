/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.crypto;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.MacAlgorithm;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.junit.Assert;
import org.junit.Test;

public class SSLUtilsTest {

    @Test
    public void testSSLMac() throws NoSuchAlgorithmException, InvalidKeyException {
        for (MacAlgorithm macAlgo : new MacAlgorithm[] { MacAlgorithm.SSLMAC_MD5, MacAlgorithm.SSLMAC_SHA1 }) {
            byte[] input = { 1, 2, 3 };
            byte[] masterSecret = { 0, 1 };
            byte[] clientRdm = { 1 };
            byte[] serverRdm = { 0 };
            byte[] seed = ArrayConverter.concatenate(serverRdm, clientRdm);
            int secretSetSize = 64;
            Mac digest = Mac.getInstance(macAlgo.getJavaName());
            byte[] keyBlock = SSLUtils.calculateKeyBlockSSL3(masterSecret, seed, secretSetSize);
            byte[] macSecret = Arrays.copyOfRange(keyBlock, 0, digest.getMacLength());
            digest.init(new SecretKeySpec(macSecret, macAlgo.getJavaName()));
            digest.update(input);
            byte[] jceResult = digest.doFinal();
            byte[] utilsResult = SSLUtils.calculateSSLMac(input, macSecret, macAlgo);
            Assert.assertArrayEquals(jceResult, utilsResult);
        }
    }

}
