/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.transport;

/**
 * Defines the connection end. Either client or server.
 */
public enum ConnectionEndType {

    CLIENT,
    SERVER;

    public ConnectionEndType getPeer() {
        if (this == CLIENT) {
            return SERVER;
        } else {
            return CLIENT;
        }
    }

}
