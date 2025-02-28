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

@SuppressWarnings("SpellCheckingInspection")
public class EllipticCurveSECT283K1 extends EllipticCurveOverF2m {
    public EllipticCurveSECT283K1() {
        super(BigInteger.ZERO, BigInteger.ONE,
            new BigInteger("800000000000000000000000000000000000000000000000000000000000000000010a1", 16),
            new BigInteger("0503213F78CA44883F1A3B8162F188E553CD265F23C1567A16876913B0C2AC2458492836", 16),
            new BigInteger("01CCDA380F1C9E318D90F95D07E5426FE87E45C0E8184698E45962364E34116177DD2259", 16),
            new BigInteger("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE9AE2ED07577265DFF7F94451E061E163C61", 16));
    }
}
