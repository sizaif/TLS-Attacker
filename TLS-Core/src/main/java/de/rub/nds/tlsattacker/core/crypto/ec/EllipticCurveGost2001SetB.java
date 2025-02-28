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

public class EllipticCurveGost2001SetB extends EllipticCurveOverF2m {

    public EllipticCurveGost2001SetB() {
        super(new BigInteger("57896044618658097711785492504343953926634992332820282019728792003956564823190"),
            new BigInteger("28091019353058090096996979000309560759124368558014865957655842872397301267595"),
            new BigInteger("57896044618658097711785492504343953926634992332820282019728792003956564823193"),
            new BigInteger("1"),
            new BigInteger("28792665814854611296992347458380284135028636778229113005756334730996303888124"),
            new BigInteger("57896044618658097711785492504343953927102133160255826820068844496087732066703"));
    }

}
