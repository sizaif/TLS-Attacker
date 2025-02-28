/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.crypto.ec;

import java.math.BigInteger;

public class EllipticCurveGost2001SetA extends EllipticCurveOverF2m {

    public EllipticCurveGost2001SetA() {
        super(new BigInteger("115792089237316195423570985008687907853269984665640564039457584007913129639316"),
            new BigInteger("166"),
            new BigInteger("115792089237316195423570985008687907853269984665640564039457584007913129639319"),
            new BigInteger("1"),
            new BigInteger("64033881142927202683649881450433473985931760268884941288852745803908878638612"),
            new BigInteger("115792089237316195423570985008687907853073762908499243225378155805079068850323"));
    }

}
