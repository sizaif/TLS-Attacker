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
import java.util.Random;

public enum HeartbeatMode {

    PEER_ALLOWED_TO_SEND((byte) 1),
    PEER_NOT_ALLOWED_TO_SEND((byte) 2);

    private byte value;

    private static final Map<Byte, HeartbeatMode> MAP;

    private HeartbeatMode(byte value) {
        this.value = value;
    }

    static {
        MAP = new HashMap<>();
        for (HeartbeatMode cm : HeartbeatMode.values()) {
            MAP.put(cm.value, cm);
        }
    }

    public static HeartbeatMode getHeartbeatMessageType(byte value) {
        return MAP.get(value);
    }

    public byte getValue() {
        return value;
    }

    public byte[] getArrayValue() {
        return new byte[] { value };
    }

    public static HeartbeatMode getRandom(Random random) {
        HeartbeatMode c = null;
        while (c == null) {
            Object[] o = MAP.values().toArray();
            c = (HeartbeatMode) o[random.nextInt(o.length)];
        }
        return c;
    }
}
