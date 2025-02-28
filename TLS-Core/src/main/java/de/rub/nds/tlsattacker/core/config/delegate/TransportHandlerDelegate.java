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
import de.rub.nds.tlsattacker.core.connection.InboundConnection;
import de.rub.nds.tlsattacker.core.connection.OutboundConnection;
import de.rub.nds.tlsattacker.transport.TransportHandlerType;

/**
 * Note: This delegate should always be executed after the ProtocolVersion delegate
 */
public class TransportHandlerDelegate extends Delegate {

    @Parameter(names = "-transport_handler_type", description = "Transport Handler type")
    private TransportHandlerType transportHandlerType = null;

    public TransportHandlerDelegate() {
    }

    public TransportHandlerType getTransportHandlerType() {
        return transportHandlerType;
    }

    public void setTransportHandlerType(TransportHandlerType transportHandlerType) {
        this.transportHandlerType = transportHandlerType;
    }

    @Override
    public void applyDelegate(Config config) {
        if (transportHandlerType == null) {
            return;
        }

        if (config.getDefaultClientConnection() == null) {
            config.setDefaultClientConnection(new OutboundConnection());
        }
        if (config.getDefaultServerConnection() == null) {
            config.setDefaultServerConnection(new InboundConnection());
        }

        config.getDefaultClientConnection().setTransportHandlerType(transportHandlerType);
        config.getDefaultServerConnection().setTransportHandlerType(transportHandlerType);
    }
}
