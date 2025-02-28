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

public class EllipticCurveSECT239K1 extends EllipticCurveOverF2m {
    public EllipticCurveSECT239K1() {
        super(BigInteger.ZERO, BigInteger.ONE,
            new BigInteger("800000000000000000004000000000000000000000000000000000000001", 16),
            new BigInteger("29A0B6A887A983E9730988A68727A8B2D126C44CC2CC7B2A6555193035DC", 16),
            new BigInteger("76310804F12E549BDB011C103089E73510ACB275FC312A5DC6B76553F0CA", 16),
            new BigInteger("2000000000000000000000000000005A79FEC67CB6E91F1C1DA800E478A5", 16));
    }
}
