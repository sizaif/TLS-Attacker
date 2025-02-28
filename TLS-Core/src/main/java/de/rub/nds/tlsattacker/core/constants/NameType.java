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
 * Name Type for Server Name Indication
 */
public enum NameType {

    HOST_NAME((byte) 0);

    private byte value;

    private static final Map<Byte, NameType> MAP;

    private NameType(byte value) {
        this.value = value;
    }

    static {
        MAP = new HashMap<>();
        for (NameType cm : NameType.values()) {
            MAP.put(cm.value, cm);
        }
    }

    public static NameType getNameType(byte value) {
        return MAP.get(value);
    }

    public byte getValue() {
        return value;
    }

    public byte[] getArrayValue() {
        return new byte[] { value };
    }
}
