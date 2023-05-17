/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.tlsattacker.core.protocol.message.SupplementalDataMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.SupplementalDataParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.SupplementalDataPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.SupplementalDataSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

public class SupplementalDataHandler extends HandshakeMessageHandler<SupplementalDataMessage> {
    public SupplementalDataHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    @Override
    public SupplementalDataParser getParser(byte[] message, int pointer) {
        return new SupplementalDataParser(pointer, message, tlsContext.getChooser().getSelectedProtocolVersion(),
            tlsContext.getConfig());
    }

    @Override
    public SupplementalDataPreparator getPreparator(SupplementalDataMessage message) {
        return new SupplementalDataPreparator(this.tlsContext.getChooser(), message);
    }

    @Override
    public SupplementalDataSerializer getSerializer(SupplementalDataMessage message) {
        return new SupplementalDataSerializer(message, this.tlsContext.getSelectedProtocolVersion());
    }

    @Override
    public void adjustTLSContext(SupplementalDataMessage message) {

    }
}
