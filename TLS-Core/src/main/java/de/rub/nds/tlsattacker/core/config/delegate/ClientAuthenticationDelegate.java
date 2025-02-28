/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.config.delegate;

import com.beust.jcommander.Parameter;
import de.rub.nds.tlsattacker.core.config.Config;

public class ClientAuthenticationDelegate extends Delegate {

    @Parameter(names = "-client_authentication", description = "YES or NO")
    private Boolean clientAuthentication;

    public ClientAuthenticationDelegate() {
    }

    public Boolean isClientAuthentication() {
        return clientAuthentication;
    }

    public void setClientAuthentication(boolean clientAuthentication) {
        this.clientAuthentication = clientAuthentication;
    }

    @Override
    public void applyDelegate(Config config) {
        if (clientAuthentication != null) {
            config.setClientAuthentication(clientAuthentication);
        }
    }

}
