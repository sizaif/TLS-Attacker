/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler.factory;

import de.rub.nds.tlsattacker.core.constants.AlgorithmResolver;
import de.rub.nds.tlsattacker.core.constants.CipherSuite;
import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.constants.KeyExchangeAlgorithm;
import de.rub.nds.tlsattacker.core.constants.ProtocolMessageType;
import de.rub.nds.tlsattacker.core.protocol.ProtocolMessage;
import de.rub.nds.tlsattacker.core.protocol.handler.AlertHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.ApplicationMessageHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.CertificateMessageHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.CertificateRequestHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.CertificateStatusHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.CertificateVerifyHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.ChangeCipherSpecHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.ClientHelloHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.ClientKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.DHClientKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.DHEServerKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.ECDHClientKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.ECDHEServerKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.EncryptedExtensionsHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.EndOfEarlyDataHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.FinishedHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.GOSTClientKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.HandshakeMessageHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.HeartbeatMessageHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.HelloRequestHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.HelloVerifyRequestHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.KeyUpdateHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.NewSessionTicketHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.PWDClientKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.PWDServerKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.ProtocolMessageHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.PskClientKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.PskDhClientKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.PskDheServerKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.PskEcDhClientKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.PskEcDheServerKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.PskRsaClientKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.PskServerKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.RSAClientKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.ServerHelloDoneHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.ServerHelloHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.SrpClientKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.SrpServerKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.SupplementalDataHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.UnknownHandshakeHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.UnknownMessageHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.AlpnExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.CachedInfoExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.CertificateStatusRequestExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.CertificateStatusRequestV2ExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.CertificateTypeExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.ClientAuthzExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.ClientCertificateTypeExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.ClientCertificateUrlExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.CookieExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.EarlyDataExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.EcPointFormatExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.EllipticCurvesExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.EncryptThenMacExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.EncryptedServerNameIndicationExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.ExtendedMasterSecretExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.ExtendedRandomExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.ExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.GreaseExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.HeartbeatExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.KeyShareExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.MaxFragmentLengthExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.PSKKeyExchangeModesExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.PWDClearExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.PWDProtectExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.PaddingExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.PasswordSaltExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.PreSharedKeyExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.RecordSizeLimitExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.RenegotiationInfoExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.ServerAuthzExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.ServerCertificateTypeExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.ServerNameIndicationExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.SessionTicketTlsExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.SignatureAndHashAlgorithmsExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.SignedCertificateTimestampExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.SrpExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.SrtpExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.SupportedVersionsExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.TokenBindingExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.TruncatedHmacExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.TrustedCaIndicationExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.UnknownExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.UserMappingExtensionHandler;
import de.rub.nds.tlsattacker.core.protocol.message.ClientKeyExchangeMessage;
import de.rub.nds.tlsattacker.core.protocol.message.ECDHEServerKeyExchangeMessage;
import de.rub.nds.tlsattacker.core.protocol.message.HandshakeMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ExtensionMessage;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HandlerFactory {

    private static final Logger LOGGER = LogManager.getLogger();

    public static ProtocolMessageHandler<? extends ProtocolMessage> getHandler(TlsContext context,
        ProtocolMessageType protocolType, HandshakeMessageType handshakeType) {
        if (protocolType == null) {
            throw new RuntimeException("Cannot retrieve Handler, ProtocolMessageType is null");
        }
        try {
            switch (protocolType) {
                case HANDSHAKE:
                    HandshakeMessageType hmt = HandshakeMessageType.getMessageType(handshakeType.getValue());
                    return HandlerFactory.getHandshakeHandler(context, hmt);
                case CHANGE_CIPHER_SPEC:
                    return new ChangeCipherSpecHandler(context);
                case ALERT:
                    return new AlertHandler(context);
                case APPLICATION_DATA:
                    return new ApplicationMessageHandler(context);
                case HEARTBEAT:
                    return new HeartbeatMessageHandler(context);
                default:
                    return new UnknownMessageHandler(context, protocolType);
            }
        } catch (UnsupportedOperationException e) {
            // Could not get the correct handler, getting an
            // unknownMessageHandler instead(always successful)
            return new UnknownHandshakeHandler(context);
        }
    }

    public static HandshakeMessageHandler<? extends HandshakeMessage> getHandshakeHandler(TlsContext context,
        HandshakeMessageType type) {
        try {
            switch (type) {
                case CERTIFICATE:
                    return new CertificateMessageHandler(context);
                case CERTIFICATE_REQUEST:
                    return new CertificateRequestHandler(context);
                case CERTIFICATE_STATUS:
                    return new CertificateStatusHandler(context);
                case CERTIFICATE_VERIFY:
                    return new CertificateVerifyHandler(context);
                case CLIENT_HELLO:
                    return new ClientHelloHandler(context);
                case CLIENT_KEY_EXCHANGE:
                    return getClientKeyExchangeHandler(context);
                case ENCRYPTED_EXTENSIONS:
                    return new EncryptedExtensionsHandler(context);
                case END_OF_EARLY_DATA:
                    return new EndOfEarlyDataHandler(context);
                case FINISHED:
                    return new FinishedHandler(context);
                case HELLO_REQUEST:
                    return new HelloRequestHandler(context);
                case HELLO_VERIFY_REQUEST:
                    return new HelloVerifyRequestHandler(context);
                case NEW_SESSION_TICKET:
                    return new NewSessionTicketHandler(context);
                case KEY_UPDATE:
                    return new KeyUpdateHandler(context);
                case SERVER_HELLO:
                    return new ServerHelloHandler(context);
                case SERVER_HELLO_DONE:
                    return new ServerHelloDoneHandler(context);
                case SERVER_KEY_EXCHANGE:
                    return getServerKeyExchangeHandler(context);
                case SUPPLEMENTAL_DATA:
                    return new SupplementalDataHandler(context);
                case UNKNOWN:
                default:
                    return new UnknownHandshakeHandler(context);
            }
        } catch (UnsupportedOperationException e) {
            LOGGER.debug("Could not retrieve correct Handler, returning UnknownHandshakeHandler", e);
        }
        return new UnknownHandshakeHandler(context);
    }

    /**
     * Returns the correct extension Handler for a specified ExtensionType in a HandshakeMessage
     *
     * @param  context
     *                 Current TlsContext
     * @param  type
     *                 Type of the Extension
     * @return         Correct ExtensionHandler
     */
    public static ExtensionHandler<? extends ExtensionMessage> getExtensionHandler(TlsContext context,
        ExtensionType type) {
        try {
            switch (type) {
                case ALPN:
                    return new AlpnExtensionHandler(context);
                case CACHED_INFO:
                    return new CachedInfoExtensionHandler(context);
                case CERT_TYPE:
                    return new CertificateTypeExtensionHandler(context);
                case CLIENT_AUTHZ:
                    return new ClientAuthzExtensionHandler(context);
                case CLIENT_CERTIFICATE_TYPE:
                    return new ClientCertificateTypeExtensionHandler(context);
                case CLIENT_CERTIFICATE_URL:
                    return new ClientCertificateUrlExtensionHandler(context);
                case EARLY_DATA:
                    return new EarlyDataExtensionHandler(context);
                case EC_POINT_FORMATS:
                    return new EcPointFormatExtensionHandler(context);
                case ELLIPTIC_CURVES:
                    return new EllipticCurvesExtensionHandler(context);
                case ENCRYPT_THEN_MAC:
                    return new EncryptThenMacExtensionHandler(context);
                case ENCRYPTED_SERVER_NAME_INDICATION:
                    return new EncryptedServerNameIndicationExtensionHandler(context);
                case EXTENDED_MASTER_SECRET:
                    return new ExtendedMasterSecretExtensionHandler(context);
                case HEARTBEAT:
                    return new HeartbeatExtensionHandler(context);
                case EXTENDED_RANDOM:
                    return new ExtendedRandomExtensionHandler(context);
                case KEY_SHARE_OLD:
                case KEY_SHARE:
                    return new KeyShareExtensionHandler(context, type);
                case MAX_FRAGMENT_LENGTH:
                    return new MaxFragmentLengthExtensionHandler(context);
                case RECORD_SIZE_LIMIT:
                    return new RecordSizeLimitExtensionHandler(context);
                case PADDING:
                    return new PaddingExtensionHandler(context);
                case PRE_SHARED_KEY:
                    return new PreSharedKeyExtensionHandler(context);
                case PSK_KEY_EXCHANGE_MODES:
                    return new PSKKeyExchangeModesExtensionHandler(context);
                case RENEGOTIATION_INFO:
                    return new RenegotiationInfoExtensionHandler(context);
                case SERVER_AUTHZ:
                    return new ServerAuthzExtensionHandler(context);
                case SERVER_CERTIFICATE_TYPE:
                    return new ServerCertificateTypeExtensionHandler(context);
                case SERVER_NAME_INDICATION:
                    return new ServerNameIndicationExtensionHandler(context);
                case SESSION_TICKET:
                    return new SessionTicketTlsExtensionHandler(context);
                case SIGNATURE_AND_HASH_ALGORITHMS:
                    return new SignatureAndHashAlgorithmsExtensionHandler(context);
                case SIGNED_CERTIFICATE_TIMESTAMP:
                    return new SignedCertificateTimestampExtensionHandler(context);
                case SRP:
                    return new SrpExtensionHandler(context);
                case STATUS_REQUEST:
                    return new CertificateStatusRequestExtensionHandler(context);
                case STATUS_REQUEST_V2:
                    return new CertificateStatusRequestV2ExtensionHandler(context);
                case SUPPORTED_VERSIONS:
                    return new SupportedVersionsExtensionHandler(context);
                case TOKEN_BINDING:
                    return new TokenBindingExtensionHandler(context);
                case TRUNCATED_HMAC:
                    return new TruncatedHmacExtensionHandler(context);
                case TRUSTED_CA_KEYS:
                    return new TrustedCaIndicationExtensionHandler(context);
                case UNKNOWN:
                    return new UnknownExtensionHandler(context);
                case USER_MAPPING:
                    return new UserMappingExtensionHandler(context);
                case USE_SRTP:
                    return new SrtpExtensionHandler(context);
                case PWD_PROTECT:
                    return new PWDProtectExtensionHandler(context);
                case PWD_CLEAR:
                    return new PWDClearExtensionHandler(context);
                case PASSWORD_SALT:
                    return new PasswordSaltExtensionHandler(context);
                case COOKIE:
                    return new CookieExtensionHandler(context);
                case GREASE_00:
                case GREASE_01:
                case GREASE_02:
                case GREASE_03:
                case GREASE_04:
                case GREASE_05:
                case GREASE_06:
                case GREASE_07:
                case GREASE_08:
                case GREASE_09:
                case GREASE_10:
                case GREASE_11:
                case GREASE_12:
                case GREASE_13:
                case GREASE_14:
                case GREASE_15:
                    return new GreaseExtensionHandler(context);
                default:
                    throw new UnsupportedOperationException(type.name() + " Extension are not supported yet");
            }

        } catch (UnsupportedOperationException e) {
            LOGGER.debug("Could not retrieve correct Handler, returning UnknownExtensionHandler", e);
        }
        return new UnknownExtensionHandler(context);
    }

    private static ClientKeyExchangeHandler<? extends ClientKeyExchangeMessage>
        getClientKeyExchangeHandler(TlsContext context) {
        CipherSuite cs = context.getChooser().getSelectedCipherSuite();
        KeyExchangeAlgorithm algorithm = AlgorithmResolver.getKeyExchangeAlgorithm(cs);
        switch (algorithm) {
            case RSA:
                return new RSAClientKeyExchangeHandler(context);
            case ECDHE_ECDSA:
            case ECDH_ECDSA:
            case ECDH_RSA:
            case ECDHE_RSA:
                return new ECDHClientKeyExchangeHandler(context);
            case DHE_DSS:
            case DHE_RSA:
            case DH_ANON:
            case DH_DSS:
            case DH_RSA:
                return new DHClientKeyExchangeHandler(context);
            case DHE_PSK:
                return new PskDhClientKeyExchangeHandler(context);
            case ECDHE_PSK:
                return new PskEcDhClientKeyExchangeHandler(context);
            case PSK_RSA:
                return new PskRsaClientKeyExchangeHandler(context);
            case PSK:
                return new PskClientKeyExchangeHandler(context);
            case SRP_SHA_DSS:
            case SRP_SHA_RSA:
            case SRP_SHA:
                return new SrpClientKeyExchangeHandler(context);
            case VKO_GOST01:
            case VKO_GOST12:
                return new GOSTClientKeyExchangeHandler(context);
            case ECCPWD:
                return new PWDClientKeyExchangeHandler(context);
            default:
                throw new UnsupportedOperationException("Algorithm " + algorithm + " NOT supported yet.");
        }
    }

    private static HandshakeMessageHandler<? extends HandshakeMessage> getServerKeyExchangeHandler(TlsContext context) {
        // TODO: There should be a server KeyExchangeHandler
        CipherSuite cs = context.getChooser().getSelectedCipherSuite();
        KeyExchangeAlgorithm algorithm = AlgorithmResolver.getKeyExchangeAlgorithm(cs);
        switch (algorithm) {
            case ECDHE_ECDSA:
            case ECDH_ECDSA:
            case ECDH_RSA:
            case ECDHE_RSA:
            case ECDH_ANON:
                return new ECDHEServerKeyExchangeHandler<>(context);
            case DHE_DSS:
            case DHE_RSA:
            case DH_ANON:
            case DH_DSS:
            case DH_RSA:
                return new DHEServerKeyExchangeHandler(context);
            case PSK:
                return new PskServerKeyExchangeHandler(context);
            case DHE_PSK:
                return new PskDheServerKeyExchangeHandler(context);
            case ECDHE_PSK:
                return new PskEcDheServerKeyExchangeHandler(context);
            case SRP_SHA_DSS:
            case SRP_SHA_RSA:
            case SRP_SHA:
                return new SrpServerKeyExchangeHandler(context);
            case ECCPWD:
                return new PWDServerKeyExchangeHandler(context);
            default:
                throw new UnsupportedOperationException("Algorithm " + algorithm + " NOT supported yet.");
        }
    }

    private HandlerFactory() {
    }
}
