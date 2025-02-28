/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.util;

public class Time {

    /**
     * Unix time means number of seconds since 1970, in GMT time zone. Date.getTime() returns number of milliseconds
     * since 1970 in GMT, thus we convert it to seconds.
     * 
     * @return unix time
     */
    public static long getUnixTime() {

        // long millis = new Date().getTime();
        long sec = System.currentTimeMillis() / 1000;

        return sec;
    }

    private Time() {
    }
}
