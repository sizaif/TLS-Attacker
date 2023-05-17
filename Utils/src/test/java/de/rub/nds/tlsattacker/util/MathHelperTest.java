/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.util;

import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MathHelperTest {

    public MathHelperTest() {
    }

    /**
     * Test of intfloordiv method, of class MathHelper.
     */
    @Test
    public void testIntfloordiv_BigInteger_BigInteger() {
    }

    /**
     * Test of intceildiv method, of class MathHelper.
     */
    @Test
    public void testIntceildiv_BigInteger_BigInteger() {
    }

    /**
     * Test of intfloordiv method, of class MathHelper.
     */
    @Test
    public void testIntfloordiv_int_int() {
    }

    /**
     * Test of intceildiv method, of class MathHelper.
     */
    @Test
    public void testIntceildiv_int_int() {
    }

    /**
     * Test of extendedEuclid method, of class MathHelper.
     */
    @Test
    public void testExtendedEuclid() {
    }

    /**
     * Test of gcd method, of class MathHelper.
     */
    @Test
    public void testGcd() {
    }

    /**
     * Test of inverseMod method, of class MathHelper.
     */
    @Test
    public void testInverseMod() {
    }

    /**
     * Test of CRT method, of class MathHelper.
     */
    @Test
    public void testCRT() {
        BigInteger[] congs = { new BigInteger("3"), new BigInteger("4"), new BigInteger("5") };
        BigInteger[] moduli = { new BigInteger("2"), new BigInteger("3"), new BigInteger("2") };
        assertEquals(4, MathHelper.crt(congs, moduli).intValue());

        // computes:
        // x == 2 mod 3
        // x == 3 mod 4
        // x == 1 mod 5
        BigInteger[] congs2 = { new BigInteger("2"), new BigInteger("3"), new BigInteger("1") };
        BigInteger[] moduli2 = { new BigInteger("3"), new BigInteger("4"), new BigInteger("5") };
        assertEquals(11, MathHelper.crt(congs2, moduli2).intValue());
    }

}
