/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.transport.exception;

public class InvalidTransportHandlerStateException extends Exception {

    public InvalidTransportHandlerStateException() {
    }

    public InvalidTransportHandlerStateException(String string) {
        super(string);
    }

    public InvalidTransportHandlerStateException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public InvalidTransportHandlerStateException(Throwable throwable) {
        super(throwable);
    }

    public InvalidTransportHandlerStateException(String string, Throwable throwable, boolean bln, boolean bln1) {
        super(string, throwable, bln, bln1);
    }

}
