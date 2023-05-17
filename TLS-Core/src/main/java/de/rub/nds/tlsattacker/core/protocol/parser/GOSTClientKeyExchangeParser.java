/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.parser;

import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.GOSTClientKeyExchangeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GOSTClientKeyExchangeParser extends ClientKeyExchangeParser<GOSTClientKeyExchangeMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    public GOSTClientKeyExchangeParser(int startposition, byte[] array, ProtocolVersion version, Config config) {
        super(startposition, array, version, config);
    }

    @Override
    protected void parseHandshakeMessageContent(GOSTClientKeyExchangeMessage msg) {
        LOGGER.debug("Parsing GOSTClientKeyExchangeMessage");
        msg.setKeyTransportBlob(parseByteArrayField(msg.getLength().getValue()));
    }

    @Override
    protected GOSTClientKeyExchangeMessage createHandshakeMessage() {
        return new GOSTClientKeyExchangeMessage();
    }

}
