/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * EllipticCurveType defined in rfc4492: https://tools.ietf.org/html/rfc4492#section-5.4
 */
public enum EllipticCurveType {

    EXPLICIT_PRIME((byte) 1),
    EXPLICIT_CHAR2((byte) 2),
    NAMED_CURVE((byte) 3);

    /** length of the EllipticCurveType in the TLS byte arrays */
    public static final int LENGTH = 1;

    private byte value;

    private static final Map<Byte, EllipticCurveType> MAP;

    private EllipticCurveType(byte value) {
        this.value = value;
    }

    static {
        MAP = new HashMap<>();
        for (EllipticCurveType c : EllipticCurveType.values()) {
            MAP.put(c.value, c);
        }
    }

    public static EllipticCurveType getCurveType(byte value) {
        return MAP.get(value);
    }

    public byte getValue() {
        return value;
    }
}
