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
import de.rub.nds.tlsattacker.core.constants.HKDFAlgorithm;
import de.rub.nds.tlsattacker.core.exceptions.CryptoException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * HKDF functions computation for TLS 1.3
 */
public class HKDFunction {

    public static final String KEY = "key";

    public static final String IV = "iv";

    public static final String FINISHED = "finished";

    public static final String DERIVED = "derived";

    public static final String BINDER_KEY_EXT = "ext binder";

    public static final String BINDER_KEY_RES = "res binder";

    public static final String CLIENT_EARLY_TRAFFIC_SECRET = "c e traffic";

    public static final String EARLY_EXPORTER_MASTER_SECRET = "e exp master";

    public static final String CLIENT_HANDSHAKE_TRAFFIC_SECRET = "c hs traffic";

    public static final String SERVER_HANDSHAKE_TRAFFIC_SECRET = "s hs traffic";

    public static final String CLIENT_APPLICATION_TRAFFIC_SECRET = "c ap traffic";

    public static final String SERVER_APPLICATION_TRAFFIC_SECRET = "s ap traffic";

    public static final String EXPORTER_MASTER_SECRET = "exp master";

    public static final String ESNI_IV = "esni iv";

    public static final String ESNI_KEY = "esni key";

    public static final String RESUMPTION_MASTER_SECRET = "res master";

    public static final String RESUMPTION = "resumption";

    public static final String TRAFFICUPD = "traffic upd";

    private HKDFunction() {
    }

    /**
     * Computes HKDF-Extract output as defined in RFC 5869
     *
     * @param  hkdfAlgorithm
     *                                                                The HKDFAlgorithm
     * @param  salt
     *                                                                The Salt
     * @param  ikm
     *                                                                The IKM
     * @return                                                        The HKDF-Extracted output
     * @throws de.rub.nds.tlsattacker.core.exceptions.CryptoException
     */
    public static byte[] extract(HKDFAlgorithm hkdfAlgorithm, byte[] salt, byte[] ikm) throws CryptoException {
        try {
            Mac mac = Mac.getInstance(hkdfAlgorithm.getMacAlgorithm().getJavaName());
            if (salt == null || salt.length == 0) {
                salt = new byte[mac.getMacLength()];
                Arrays.fill(salt, (byte) 0);
            }
            SecretKeySpec keySpec = new SecretKeySpec(salt, hkdfAlgorithm.getMacAlgorithm().getJavaName());
            mac.init(keySpec);
            mac.update(ikm);
            return mac.doFinal();
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            throw new CryptoException(ex);
        }
    }

    /**
     * Computes HKDF-Expand output as defined in RFC 5869
     *
     * @param  hkdfAlgorithm
     *                                                                The HKDF Algorithm
     * @param  prk
     *                                                                THE prk
     * @param  info
     *                                                                The info
     * @param  outLen
     *                                                                The output Length
     * @return                                                        The expanded bytes
     * @throws de.rub.nds.tlsattacker.core.exceptions.CryptoException
     */
    public static byte[] expand(HKDFAlgorithm hkdfAlgorithm, byte[] prk, byte[] info, int outLen)
        throws CryptoException {
        try {
            Mac mac = Mac.getInstance(hkdfAlgorithm.getMacAlgorithm().getJavaName());
            SecretKeySpec keySpec = new SecretKeySpec(prk, hkdfAlgorithm.getMacAlgorithm().getJavaName());
            mac.init(keySpec);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] ti = new byte[0];
            int i = 1;
            while (stream.toByteArray().length < outLen) {
                mac.update(ti);
                mac.update(info);
                if (Integer.toHexString(i).length() % 2 != 0) {
                    mac.update(ArrayConverter.hexStringToByteArray("0" + Integer.toHexString(i)));
                } else {
                    mac.update(ArrayConverter.hexStringToByteArray(Integer.toHexString(i)));
                }
                ti = mac.doFinal();
                if (ti.length == 0) {
                    throw new CryptoException("Could not expand HKDF. Mac Algorithm of 0 size");
                }
                stream.write(ti);
                i++;
            }
            return Arrays.copyOfRange(stream.toByteArray(), 0, outLen);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException | IllegalArgumentException ex) {
            throw new CryptoException(ex);
        }
    }

    /**
     * Computes the HKDF-Label as defined in TLS 1.3
     */
    private static byte[] labelEncoder(byte[] hashValue, String labelIn, int outLen) {
        String label = "tls13 " + labelIn;
        int labelLength = label.getBytes().length;
        int hashValueLength = hashValue.length;
        byte[] result =
            ArrayConverter.concatenate(ArrayConverter.intToBytes(outLen, 2), ArrayConverter.intToBytes(labelLength, 1),
                label.getBytes(), ArrayConverter.intToBytes(hashValueLength, 1), hashValue);
        return result;
    }

    /**
     * Computes Derive-Secret output as defined in TLS 1.3
     *
     * @param  hkdfAlgorithm
     *                                                                The HKDF Algorithm
     * @param  hashAlgorithm
     *                                                                The Hash Algorithm
     * @param  prk
     *                                                                The prk
     * @param  labelIn
     *                                                                The label input
     * @param  toHash
     *                                                                The data to hash
     * @return                                                        The derivedSecret
     * @throws de.rub.nds.tlsattacker.core.exceptions.CryptoException
     */
    public static byte[] deriveSecret(HKDFAlgorithm hkdfAlgorithm, String hashAlgorithm, byte[] prk, String labelIn,
        byte[] toHash) throws CryptoException {
        try {
            MessageDigest hashFunction = MessageDigest.getInstance(hashAlgorithm);
            hashFunction.update(toHash);
            byte[] hashValue = hashFunction.digest();
            int outLen = Mac.getInstance(hkdfAlgorithm.getMacAlgorithm().getJavaName()).getMacLength();
            return expandLabel(hkdfAlgorithm, prk, labelIn, hashValue, outLen);
        } catch (NoSuchAlgorithmException ex) {
            throw new CryptoException("Could not initialize HKDF", ex);
        }
    }

    static byte[] deriveSecret(HKDFAlgorithm hkdfAlgorithm, byte[] hexStringToByteArray, String tls13Derived,
        byte[] hexStringToByteArray0) {
        throw new UnsupportedOperationException("Not supported yet."); // To
        // change
        // body
        // of
        // generated
        // methods,
        // choose
        // Tools
        // |
        // Templates.
    }

    /**
     * Computes HKDF-Expand-Label output as defined in TLS 1.3
     *
     * @param  hkdfAlgorithm
     *                                                                The HKDF Algorithm
     * @param  prk
     *                                                                The Prk
     * @param  labelIn
     *                                                                The InputLabel
     * @param  hashValue
     *                                                                The hash value
     * @param  outLen
     *                                                                The output length
     * @return                                                        The expanded Label bytes
     * @throws de.rub.nds.tlsattacker.core.exceptions.CryptoException
     */
    public static byte[] expandLabel(HKDFAlgorithm hkdfAlgorithm, byte[] prk, String labelIn, byte[] hashValue,
        int outLen) throws CryptoException {
        byte[] info = labelEncoder(hashValue, labelIn, outLen);
        return expand(hkdfAlgorithm, prk, info, outLen);
    }
}
