/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.message;

import de.rub.nds.tlsattacker.core.protocol.message.extension.UserMappingExtensionMessage;
import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.CipherSuite;
import de.rub.nds.tlsattacker.core.constants.CompressionMethod;
import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.handler.ServerHelloHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.TlsMessageHandler;
import de.rub.nds.tlsattacker.core.protocol.message.extension.AlpnExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.CachedInfoExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.CertificateStatusRequestExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.CertificateStatusRequestV2ExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.CertificateTypeExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ClientAuthzExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ClientCertificateTypeExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ClientCertificateUrlExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.CookieExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ECPointFormatExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.EncryptThenMacExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.EncryptedServerNameIndicationExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ExtendedMasterSecretExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ExtendedRandomExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.HeartbeatExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.KeyShareExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.MaxFragmentLengthExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.PaddingExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.PreSharedKeyExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.RecordSizeLimitExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.RenegotiationInfoExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.SRPExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ServerAuthzExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ServerCertificateTypeExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ServerNameIndicationExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.SessionTicketTLSExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.SignedCertificateTimestampExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.SrtpExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.SupportedVersionsExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.TokenBindingExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.TruncatedHmacExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.TrustedCaIndicationExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.sni.ServerNamePair;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "ServerHello")
public class ServerHelloMessage extends HelloMessage {

    private static final byte[] HELLO_RETRY_REQUEST_RANDOM = new byte[] { (byte) 0xCF, (byte) 0x21, (byte) 0xAD,
        (byte) 0x74, (byte) 0xE5, (byte) 0x9A, (byte) 0x61, (byte) 0x11, (byte) 0xBE, (byte) 0x1D, (byte) 0x8C,
        (byte) 0x02, (byte) 0x1E, (byte) 0x65, (byte) 0xB8, (byte) 0x91, (byte) 0xC2, (byte) 0xA2, (byte) 0x11,
        (byte) 0x16, (byte) 0x7A, (byte) 0xBB, (byte) 0x8C, (byte) 0x5E, (byte) 0x07, (byte) 0x9E, (byte) 0x09,
        (byte) 0xE2, (byte) 0xC8, (byte) 0xA8, (byte) 0x33, (byte) 0x9C };

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.TLS_CONSTANT)
    private ModifiableByteArray selectedCipherSuite;

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.TLS_CONSTANT)
    private ModifiableByte selectedCompressionMethod;

    private Boolean autoSetHelloRetryModeInKeyShare = true;

    public ServerHelloMessage(Config tlsConfig) {
        super(tlsConfig, HandshakeMessageType.SERVER_HELLO);
        if (!tlsConfig.getHighestProtocolVersion().isSSL()
            || (tlsConfig.getHighestProtocolVersion().isSSL() && tlsConfig.isAddExtensionsInSSL())) {
            if (tlsConfig.isAddHeartbeatExtension()) {
                addExtension(new HeartbeatExtensionMessage());
            }
            if (tlsConfig.isAddECPointFormatExtension() && !tlsConfig.getHighestProtocolVersion().isTLS13()) {
                addExtension(new ECPointFormatExtensionMessage());
            }
            if (tlsConfig.isAddMaxFragmentLengthExtension()) {
                addExtension(new MaxFragmentLengthExtensionMessage());
            }
            if (tlsConfig.isAddRecordSizeLimitExtension() && !tlsConfig.getHighestProtocolVersion().isTLS13()) {
                addExtension(new RecordSizeLimitExtensionMessage());
            }
            if (tlsConfig.isAddServerNameIndicationExtension()) {
                ServerNameIndicationExtensionMessage extension = new ServerNameIndicationExtensionMessage();
                ServerNamePair pair = new ServerNamePair(tlsConfig.getSniType().getValue(),
                    tlsConfig.getDefaultServerConnection().getHostname().getBytes(Charset.forName("US-ASCII")));
                extension.getServerNameList().add(pair);
                addExtension(extension);
            }

            if (tlsConfig.isAddKeyShareExtension()) {
                addExtension(new KeyShareExtensionMessage(tlsConfig));
            }
            if (tlsConfig.isAddEncryptedServerNameIndicationExtension()) {
                addExtension(new EncryptedServerNameIndicationExtensionMessage());
            }
            if (tlsConfig.isAddExtendedMasterSecretExtension()) {
                addExtension(new ExtendedMasterSecretExtensionMessage());
            }
            if (tlsConfig.isAddSessionTicketTLSExtension()) {
                addExtension(new SessionTicketTLSExtensionMessage());
            }
            if (tlsConfig.isAddSignedCertificateTimestampExtension()) {
                addExtension(new SignedCertificateTimestampExtensionMessage());
            }
            if (tlsConfig.isAddPaddingExtension()) {
                addExtension(new PaddingExtensionMessage());
            }
            if (tlsConfig.isAddRenegotiationInfoExtension()) {
                addExtension(new RenegotiationInfoExtensionMessage());
            }
            if (tlsConfig.isAddTokenBindingExtension()) {
                addExtension(new TokenBindingExtensionMessage());
            }
            if (tlsConfig.isAddCertificateStatusRequestExtension()) {
                addExtension(new CertificateStatusRequestExtensionMessage());
            }
            if (tlsConfig.isAddAlpnExtension()) {
                addExtension(new AlpnExtensionMessage(tlsConfig));
            }
            if (tlsConfig.isAddSRPExtension()) {
                addExtension(new SRPExtensionMessage());
            }
            if (tlsConfig.isAddSRTPExtension()) {
                addExtension(new SrtpExtensionMessage());
            }
            if (tlsConfig.isAddTruncatedHmacExtension()) {
                addExtension(new TruncatedHmacExtensionMessage());
            }
            if (tlsConfig.isAddUserMappingExtension()) {
                addExtension(new UserMappingExtensionMessage());
            }
            if (tlsConfig.isAddCertificateTypeExtension()) {
                addExtension(new CertificateTypeExtensionMessage());
            }
            if (tlsConfig.isAddClientAuthzExtension()) {
                addExtension(new ClientAuthzExtensionMessage());
            }
            if (tlsConfig.isAddServerAuthzExtension()) {
                addExtension(new ServerAuthzExtensionMessage());
            }
            if (tlsConfig.isAddClientCertificateTypeExtension()) {
                addExtension(new ClientCertificateTypeExtensionMessage());
            }
            if (tlsConfig.isAddServerCertificateTypeExtension()) {
                addExtension(new ServerCertificateTypeExtensionMessage());
            }
            if (tlsConfig.isAddEncryptThenMacExtension()) {
                addExtension(new EncryptThenMacExtensionMessage());
            }
            if (tlsConfig.isAddCachedInfoExtension()) {
                addExtension(new CachedInfoExtensionMessage());
            }
            if (tlsConfig.isAddClientCertificateUrlExtension()) {
                addExtension(new ClientCertificateUrlExtensionMessage());
            }
            if (tlsConfig.isAddTrustedCaIndicationExtension()) {
                addExtension(new TrustedCaIndicationExtensionMessage());
            }
            if (tlsConfig.isAddCertificateStatusRequestV2Extension()) {
                addExtension(new CertificateStatusRequestV2ExtensionMessage());
            }
            if (tlsConfig.isAddPreSharedKeyExtension()) {
                addExtension(new PreSharedKeyExtensionMessage(tlsConfig));
            }
            if (tlsConfig.isAddSupportedVersionsExtension()) {
                addExtension(new SupportedVersionsExtensionMessage());
            }
            if (tlsConfig.isAddExtendedRandomExtension()) {
                addExtension(new ExtendedRandomExtensionMessage());
            }
            if (tlsConfig.isAddCookieExtension()) {
                addExtension(new CookieExtensionMessage());
            }
        }
    }

    public ServerHelloMessage() {
        super(HandshakeMessageType.SERVER_HELLO);

    }

    public ModifiableByteArray getSelectedCipherSuite() {
        return selectedCipherSuite;
    }

    public void setSelectedCipherSuite(ModifiableByteArray selectedCipherSuite) {
        this.selectedCipherSuite = selectedCipherSuite;
    }

    public void setSelectedCipherSuite(byte[] value) {
        this.selectedCipherSuite = ModifiableVariableFactory.safelySetValue(this.selectedCipherSuite, value);
    }

    public ModifiableByte getSelectedCompressionMethod() {
        return selectedCompressionMethod;
    }

    public void setSelectedCompressionMethod(ModifiableByte selectedCompressionMethod) {
        this.selectedCompressionMethod = selectedCompressionMethod;
    }

    public void setSelectedCompressionMethod(byte value) {
        this.selectedCompressionMethod =
            ModifiableVariableFactory.safelySetValue(this.selectedCompressionMethod, value);
    }

    public Boolean isTls13HelloRetryRequest() {
        if (this.getRandom() != null && this.getRandom().getValue() != null) {
            return Arrays.equals(this.getRandom().getValue(), HELLO_RETRY_REQUEST_RANDOM);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\n  Protocol Version: ");
        if (getProtocolVersion() != null) {
            sb.append(ProtocolVersion.getProtocolVersion(getProtocolVersion().getValue()));
        } else {
            sb.append("null");
        }
        if (getProtocolVersion() != null && getProtocolVersion().getValue() != null
            && !ProtocolVersion.getProtocolVersion(getProtocolVersion().getValue()).isTLS13()) {
            sb.append("\n  Server Unix Time: ")
                .append(new Date(ArrayConverter.bytesToLong(getUnixTime().getValue()) * 1000));
        }
        sb.append("\n  Server Unix Time: ");
        if (getProtocolVersion() != null) {
            if (!ProtocolVersion.getProtocolVersion(getProtocolVersion().getValue()).isTLS13()) {
                sb.append(new Date(ArrayConverter.bytesToLong(getUnixTime().getValue()) * 1000));
            } else {
                sb.append("null");
            }
        } else {
            sb.append("null");
        }
        sb.append("\n  Server Random: ");
        if (getRandom() != null) {
            sb.append(ArrayConverter.bytesToHexString(getRandom().getValue()));
        } else {
            sb.append("null");
        }
        sb.append("\n  Session ID: ");
        if (getProtocolVersion() != null && getProtocolVersion().getValue() != null) {
            if (!ProtocolVersion.getProtocolVersion(getProtocolVersion().getValue()).isTLS13()) {
                sb.append(ArrayConverter.bytesToHexString(getSessionId().getValue()));
            } else {
                sb.append("null");
            }
        } else {
            sb.append("null");
        }
        sb.append("\n  Selected Cipher Suite: ");
        if (selectedCipherSuite != null && selectedCipherSuite.getValue() != null) {
            sb.append(CipherSuite.getCipherSuite(selectedCipherSuite.getValue()));
        } else {
            sb.append("null");
        }
        sb.append("\n  Selected Compression Method: ");
        if (getProtocolVersion() != null && getProtocolVersion().getValue() != null) {
            if (!ProtocolVersion.getProtocolVersion(getProtocolVersion().getValue()).isTLS13()) {
                sb.append(CompressionMethod.getCompressionMethod(selectedCompressionMethod.getValue()));
            } else {
                sb.append("null");
            }
        } else {
            sb.append("null");
        }
        sb.append("\n  Extensions: ");
        if (getExtensions() == null) {
            sb.append("null");
        } else {
            for (ExtensionMessage e : getExtensions()) {
                sb.append(e.toString());
            }
        }
        return sb.toString();
    }

    @Override
    public ServerHelloHandler getHandler(TlsContext context) {
        return new ServerHelloHandler(context);
    }

    public static byte[] getHelloRetryRequestRandom() {
        return HELLO_RETRY_REQUEST_RANDOM;
    }

    public Boolean isAutoSetHelloRetryModeInKeyShare() {
        return autoSetHelloRetryModeInKeyShare;
    }

    public void setAutoSetHelloRetryModeInKeyShare(Boolean autoSetHelloRetryModeInKeyShare) {
        this.autoSetHelloRetryModeInKeyShare = autoSetHelloRetryModeInKeyShare;
    }

    public boolean setRetryRequestModeInKeyShare() {
        if (Boolean.TRUE.equals(isTls13HelloRetryRequest()) && autoSetHelloRetryModeInKeyShare) {
            return true;
        }
        return false;
    }

    @Override
    public String toCompactString() {
        Boolean isHrr = isTls13HelloRetryRequest();
        String compactString = super.toCompactString();
        if (isHrr != null && isHrr == true) {
            compactString += "(HRR)";
        }
        return compactString;
    }

    @Override
    public String toShortString() {
        if (isTls13HelloRetryRequest()) {
            return "HRR";
        }
        return "SH";
    }
}
