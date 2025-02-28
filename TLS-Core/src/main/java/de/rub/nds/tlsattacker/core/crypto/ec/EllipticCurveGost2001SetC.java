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

public class EllipticCurveGost2001SetC extends EllipticCurveOverF2m {

    public EllipticCurveGost2001SetC() {
        super(new BigInteger("70390085352083305199547718019018437841079516630045180471284346843705633502616"),
            new BigInteger("32858"),
            new BigInteger("70390085352083305199547718019018437841079516630045180471284346843705633502619"),
            new BigInteger("0"),
            new BigInteger("29818893917731240733471273240314769927240550812383695689146495261604565990247"),
            new BigInteger("70390085352083305199547718019018437840920882647164081035322601458352298396601"));
    }

}
