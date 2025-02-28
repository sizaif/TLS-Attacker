/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.exceptions;

public class ContextHandlingException extends RuntimeException {

    public ContextHandlingException() {
        super();
    }

    public ContextHandlingException(String message) {
        super(message);
    }

    public ContextHandlingException(String message, Throwable cause) {
        super(message, cause);
    }
}
