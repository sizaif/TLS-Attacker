/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.message;

import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.protocol.handler.HelloVerifyRequestHandler;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "HelloVerifyRequest")
public class HelloVerifyRequestMessage extends HandshakeMessage {

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.TLS_CONSTANT)
    private ModifiableByteArray protocolVersion = null;

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.LENGTH)
    private ModifiableByte cookieLength = null;

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.COOKIE)
    private ModifiableByteArray cookie = null;

    public HelloVerifyRequestMessage() {
        super(HandshakeMessageType.HELLO_VERIFY_REQUEST);
        isIncludeInDigestDefault = false;
    }

    public HelloVerifyRequestMessage(Config tlsConfig) {
        super(tlsConfig, HandshakeMessageType.HELLO_VERIFY_REQUEST);
        isIncludeInDigestDefault = false;
    }

    public ModifiableByteArray getProtocolVersion() {
        return protocolVersion;
    }

    public ModifiableByteArray getCookie() {
        return cookie;
    }

    public ModifiableByte getCookieLength() {
        return cookieLength;
    }

    public void setProtocolVersion(byte[] protocolVersion) {
        this.protocolVersion = ModifiableVariableFactory.safelySetValue(this.protocolVersion, protocolVersion);
    }

    public void setProtocolVersion(ModifiableByteArray protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public void setCookie(byte[] cookie) {
        this.cookie = ModifiableVariableFactory.safelySetValue(this.cookie, cookie);
    }

    public void setCookie(ModifiableByteArray cookie) {
        this.cookie = cookie;
    }

    public void setCookieLength(byte cookieLength) {
        this.cookieLength = ModifiableVariableFactory.safelySetValue(this.cookieLength, cookieLength);
    }

    public void setCookieLength(ModifiableByte cookieLength) {
        this.cookieLength = cookieLength;
    }

    @Override
    public HelloVerifyRequestHandler getHandler(TlsContext context) {
        return new HelloVerifyRequestHandler(context);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HelloVerifyRequestMessage:");
        sb.append("\n  ProtocolVersion: ");
        if (protocolVersion != null && protocolVersion.getValue() != null) {
            sb.append(ArrayConverter.bytesToHexString(protocolVersion.getValue()));
        } else {
            sb.append("null");
        }
        sb.append("\n  Cookie Length: ");
        if (cookieLength != null && cookieLength.getValue() != null) {
            sb.append(cookieLength.getValue());
        } else {
            sb.append("null");
        }
        sb.append("\n  Cookie: ");
        if (cookie != null && cookie.getValue() != null) {
            sb.append(ArrayConverter.bytesToHexString(cookie.getValue()));
        } else {
            sb.append("null");
        }
        return sb.toString();
    }

    @Override
    public String toShortString() {
        return "HVR";
    }

}
