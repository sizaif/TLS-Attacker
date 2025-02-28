/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.crypto.mac;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.util.Memoable;

public class ContinuousMac implements WrappedMac {

    private Mac mac;
    private Memoable underlying;

    public ContinuousMac(Mac mac, Memoable underlying, CipherParameters parameters) {
        this.mac = mac;
        this.underlying = underlying;

        mac.init(parameters);
    }

    public <T extends Mac & Memoable> ContinuousMac(T mac, CipherParameters parameters) {
        this(mac, mac, parameters);
    }

    @Override
    public byte[] calculateMac(byte[] data) {
        mac.update(data, 0, data.length);
        Memoable memoable = underlying.copy();
        byte[] out = new byte[mac.getMacSize()];
        mac.doFinal(out, 0);
        underlying.reset(memoable);
        return out;
    }

    @Override
    public int getMacLength() {
        return mac.getMacSize();
    }

}
