/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.crypto.ec;

import de.rub.nds.tlsattacker.core.constants.NamedGroup;
import java.math.BigInteger;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing EllipticCurve, CurveFactory, EllipticCurveOverFp and EllipticCurveOverF2m
 */
public class EllipticCurveTest {
    /*
     * Please notice that these tests can provide correctness only in a probabilistic sense. (Though with a very high
     * probability, since the curve parameters are very large.)
     */

    private Random rnd;
    private Point inf;

    @Before
    public void setUp() {
        this.rnd = new Random();
        this.inf = new Point();
    }

    @Test
    public void test() {
        final int implemented = 46;
        int counter = 0;

        for (NamedGroup name : NamedGroup.values()) {

            try {
                EllipticCurve curve = CurveFactory.getCurve(name);
                Point basePoint = curve.getBasePoint();
                BigInteger basePointOrder = curve.getBasePointOrder();

                this.testCurveParameters(curve, basePoint);

                this.testCurveGroupLaws(curve, basePoint, basePointOrder);

                this.testCurveArithmetic(curve, basePoint, basePointOrder);

                this.testDecompression(curve, basePoint);

                counter++;
            } catch (UnsupportedOperationException e) {
            }
        }

        if (counter != implemented) {
            fail();
        }

    }

    private void testCurveParameters(EllipticCurve curve, Point basePoint) {
        assertEquals(true, curve.isOnCurve(basePoint));

        // Constructing a point, that is not on the curve, to ensure that the
        // first test was no false positive.
        BigInteger x = basePoint.getFieldX().getData();
        BigInteger y = basePoint.getFieldY().getData();
        BigInteger wrongX = x.add(BigInteger.ONE);
        Point wrongPoint = curve.getPoint(wrongX, y);

        assertEquals(false, curve.isOnCurve(wrongPoint));
    }

    private void testCurveGroupLaws(EllipticCurve curve, Point basePoint, BigInteger basePointOrder) {
        Point inv = curve.inverse(basePoint);

        assertNotEquals(inf, basePoint);
        assertNotEquals(inf, inv);
        assertNotEquals(inv, basePoint);

        // -0 == 0
        assertEquals(inf, curve.inverse(inf));

        // ord(p) * p == 0
        Point result = curve.mult(basePointOrder, basePoint);
        assertEquals(inf, result);

        // p - p == 0
        result = curve.add(basePoint, inv);
        assertEquals(inf, result);

        // (ord(p)-1) * p == -p
        result = curve.mult(basePointOrder.subtract(BigInteger.ONE), basePoint);
        assertEquals(inv, result);

        // (ord(p)+1) * p == p
        result = curve.mult(basePointOrder.add(BigInteger.ONE), basePoint);
        assertEquals(basePoint, result);

        // 0 + 0 == 0
        result = curve.add(inf, inf);
        assertEquals(inf, result);

        // 0 + p == p
        result = curve.add(inf, basePoint);
        assertEquals(basePoint, result);

        // p + 0 == p
        result = curve.add(basePoint, inf);
        assertEquals(basePoint, result);
    }

    private void testCurveArithmetic(EllipticCurve curve, Point basePoint, BigInteger basePointOrder) {
        for (int i = 0; i < 2; i++) {
            // Testing for a random r
            // This should work for r>ord(p) too
            BigInteger random = new BigInteger(basePointOrder.bitLength() + 1, rnd);
            if (random.equals(basePointOrder)) {
                random = random.flipBit(0);
            }
            Point inv = curve.inverse(basePoint);

            // r*p+(ord(p)-r)*p == 0
            Point r1 = curve.mult(random, basePoint);
            Point r2 = curve.mult(basePointOrder.subtract(random), basePoint);
            Point result = curve.add(r1, r2);
            assertNotEquals(inf, r1);
            assertNotEquals(inf, r2);
            assertEquals(inf, result);
            assertEquals(r1, curve.inverse(r2));
            assertEquals(r2, curve.inverse(r1));

            // (r+1)*p+(ord(p)-r)*p == p
            r1 = curve.mult(random.add(BigInteger.ONE), basePoint);
            result = curve.add(r1, r2);
            assertNotEquals(r1, inf);
            assertEquals(basePoint, result);

            // (r-1)*p+(ord(p)-r)*p == -p
            r1 = curve.mult(random.subtract(BigInteger.ONE), basePoint);
            result = curve.add(r1, r2);
            assertNotEquals(r1, inf);
            assertEquals(inv, result);
        }
    }

    private void testDecompression(EllipticCurve curve, Point basePoint) {
        Point decompressed = curve.createAPointOnCurve(basePoint.getFieldX().getData());

        // two points share the same x-coordinate - apply inverse if necessary
        if (!decompressed.getFieldY().getData().equals(basePoint.getFieldY().getData())) {
            decompressed = curve.inverse(decompressed);
        }
        assertEquals(decompressed, basePoint);

        if (curve instanceof EllipticCurveOverF2m) {
            Point decompressed0 = curve.createAPointOnCurve(BigInteger.ZERO);
            assertTrue(curve.isOnCurve(decompressed0));
        }
    }
}