/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.workflow.action;

import java.math.BigInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.rub.nds.tlsattacker.core.exceptions.WorkflowExecutionException;
import de.rub.nds.tlsattacker.core.state.State;

public class ChangeServerRsaParametersAction extends ConnectionBoundAction {

    private static final Logger LOGGER = LogManager.getLogger();

    private BigInteger modulus = null;
    private BigInteger publicExponent = null;
    private BigInteger privateExponent = null;

    public ChangeServerRsaParametersAction(BigInteger modulus, BigInteger publicExponent, BigInteger privateExponent) {
        this.modulus = modulus;
        this.publicExponent = publicExponent;
        this.privateExponent = privateExponent;
    }

    public ChangeServerRsaParametersAction() {
    }

    @Override
    public void execute(State state) throws WorkflowExecutionException {
        if (isExecuted()) {
            throw new WorkflowExecutionException("Action already executed!");
        }

        state.getTlsContext().setServerRSAModulus(modulus);
        state.getTlsContext().setServerRSAPublicKey(publicExponent);
        state.getTlsContext().setServerRSAPrivateKey(privateExponent);

        setExecuted(true);
        LOGGER.info("Changed server RSA parameters");
    }

    @Override
    public void reset() {
        setExecuted(false);
    }

    @Override
    public boolean executedAsPlanned() {
        return isExecuted();
    }
}
