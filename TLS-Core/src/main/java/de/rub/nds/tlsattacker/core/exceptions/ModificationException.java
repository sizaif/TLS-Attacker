/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.exceptions;

/**
 * Thrown when problems by modification application appear.
 */
public class ModificationException extends RuntimeException {

    public ModificationException() {
        super();
    }

    public ModificationException(String message) {
        super(message);
    }

    public ModificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
