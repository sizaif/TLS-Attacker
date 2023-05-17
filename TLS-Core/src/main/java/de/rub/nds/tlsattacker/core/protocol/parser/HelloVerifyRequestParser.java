/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.parser;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.HandshakeByteLength;
import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.HelloVerifyRequestMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HelloVerifyRequestParser extends HandshakeMessageParser<HelloVerifyRequestMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    public HelloVerifyRequestParser(int pointer, byte[] array, ProtocolVersion version, Config config) {
        super(pointer, array, HandshakeMessageType.HELLO_VERIFY_REQUEST, version, config);
    }

    @Override
    protected void parseHandshakeMessageContent(HelloVerifyRequestMessage msg) {
        LOGGER.debug("Parsing HelloVerifyRequestMessage");
        parseProtocolVersion(msg);
        parseCookieLength(msg);
        parseCookie(msg);
    }

    @Override
    protected HelloVerifyRequestMessage createHandshakeMessage() {
        return new HelloVerifyRequestMessage();
    }

    private void parseProtocolVersion(HelloVerifyRequestMessage msg) {
        msg.setProtocolVersion(parseByteArrayField(HandshakeByteLength.VERSION));
        LOGGER.debug("ProtocolVersion: " + ArrayConverter.bytesToHexString(msg.getProtocolVersion().getValue()));
    }

    private void parseCookieLength(HelloVerifyRequestMessage msg) {
        msg.setCookieLength(parseByteField(HandshakeByteLength.DTLS_HANDSHAKE_COOKIE_LENGTH));
        LOGGER.debug("CookieLength: " + msg.getCookieLength().getValue());
    }

    private void parseCookie(HelloVerifyRequestMessage msg) {
        msg.setCookie(parseByteArrayField(msg.getCookieLength().getValue()));
        LOGGER.debug("Cookie: " + ArrayConverter.bytesToHexString(msg.getCookie().getValue()));
    }

}
