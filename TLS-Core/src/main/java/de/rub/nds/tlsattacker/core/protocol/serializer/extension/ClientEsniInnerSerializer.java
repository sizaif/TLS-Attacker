/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer.extension;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.ExtensionByteLength;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ClientEsniInner;
import de.rub.nds.tlsattacker.core.protocol.Serializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientEsniInnerSerializer extends Serializer<ClientEsniInner> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final ClientEsniInner clientEsniInner;

    public ClientEsniInnerSerializer(ClientEsniInner clientEsniInner) {
        this.clientEsniInner = clientEsniInner;
    }

    @Override
    protected byte[] serializeBytes() {
        LOGGER.debug("Serializing ClientEsniInner");
        this.writeNonce(this.clientEsniInner);
        this.writeServerNameListLength(this.clientEsniInner);
        this.writeServerNameListBytes(this.clientEsniInner);
        this.writePadding(this.clientEsniInner);
        return getAlreadySerialized();
    }

    private void writeNonce(ClientEsniInner msg) {
        appendBytes(msg.getClientNonce().getValue());
        LOGGER.debug("Nonce: " + ArrayConverter.bytesToHexString(msg.getClientNonce().getValue()));
    }

    private void writeServerNameListLength(ClientEsniInner msg) {
        appendInt(clientEsniInner.getServerNameListLength().getValue(), ExtensionByteLength.SERVER_NAME_LIST);
        LOGGER.debug("ServerNameListLength: " + msg.getServerNameListLength().getValue());
    }

    private void writeServerNameListBytes(ClientEsniInner msg) {
        appendBytes(clientEsniInner.getServerNameListBytes().getValue());
        LOGGER
            .debug("ServerNameListBytes: " + ArrayConverter.bytesToHexString(msg.getServerNameListBytes().getValue()));
    }

    private void writePadding(ClientEsniInner msg) {
        appendBytes(clientEsniInner.getPadding().getValue());
        LOGGER.debug("Padding: " + ArrayConverter.bytesToHexString(msg.getPadding().getValue()));
    }
}