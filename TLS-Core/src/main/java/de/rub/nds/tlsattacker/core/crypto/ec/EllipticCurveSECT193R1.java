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
public class EllipticCurveSECT193R1 extends EllipticCurveOverF2m {
    public EllipticCurveSECT193R1() {
        super(new BigInteger("0017858FEB7A98975169E171F77B4087DE098AC8A911DF7B01", 16),
            new BigInteger("00FDFB49BFE6C3A89FACADAA7A1E5BBC7CC1C2E5D831478814", 16),
            new BigInteger("2000000000000000000000000000000000000000000008001", 16),
            new BigInteger("01F481BC5F0FF84A74AD6CDF6FDEF4BF6179625372D8C0C5E1", 16),
            new BigInteger("0025E399F2903712CCF3EA9E3A1AD17FB0B3201B6AF7CE1B05", 16),
            new BigInteger("01000000000000000000000000C7F34A778F443ACC920EBA49", 16));
    }
}
