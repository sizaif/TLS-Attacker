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
import com.beust.jcommander.ParameterException;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.connection.InboundConnection;
import de.rub.nds.tlsattacker.core.constants.RunningModeType;

public class ServerDelegate extends Delegate {

    @Parameter(names = "-port", required = true, description = "ServerPort")
    protected Integer port = null;

    public ServerDelegate() {
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void applyDelegate(Config config) {

        config.setDefaultRunningMode(RunningModeType.SERVER);

        int parsedPort = parsePort(port);
        InboundConnection inboundConnection = config.getDefaultServerConnection();
        if (inboundConnection != null) {
            inboundConnection.setPort(parsedPort);
        } else {
            inboundConnection = new InboundConnection(parsedPort);
            config.setDefaultServerConnection(inboundConnection);
        }
    }

    private int parsePort(Integer port) {
        if (port == null) {
            throw new ParameterException("Port must be set, but was not specified");
        }
        if (port < 0 || port > 65535) {
            throw new ParameterException("Port must be in interval [0,65535], but is " + port);
        }
        return port;
    }
}
