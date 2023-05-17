/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.certificate;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.UnformattedByteArrayAdapter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.AlgorithmResolver;
import de.rub.nds.tlsattacker.core.constants.CertificateKeyType;
import de.rub.nds.tlsattacker.core.constants.GOSTCurve;
import de.rub.nds.tlsattacker.core.constants.HandshakeByteLength;
import de.rub.nds.tlsattacker.core.constants.NamedGroup;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.constants.SignatureAndHashAlgorithm;
import de.rub.nds.tlsattacker.core.crypto.keys.CustomDHPrivateKey;
import de.rub.nds.tlsattacker.core.crypto.keys.CustomDSAPrivateKey;
import de.rub.nds.tlsattacker.core.crypto.keys.CustomDhPublicKey;
import de.rub.nds.tlsattacker.core.crypto.keys.CustomDsaPublicKey;
import de.rub.nds.tlsattacker.core.crypto.keys.CustomECPrivateKey;
import de.rub.nds.tlsattacker.core.crypto.keys.CustomEcPublicKey;
import de.rub.nds.tlsattacker.core.crypto.keys.CustomPrivateKey;
import de.rub.nds.tlsattacker.core.crypto.keys.CustomPublicKey;
import de.rub.nds.tlsattacker.core.crypto.keys.CustomRSAPrivateKey;
import de.rub.nds.tlsattacker.core.crypto.keys.CustomRsaPublicKey;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import de.rub.nds.tlsattacker.core.util.CertificateUtils;
import de.rub.nds.tlsattacker.transport.ConnectionEndType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.crypto.tls.Certificate;

@XmlAccessorType(XmlAccessType.FIELD)
public class CertificateKeyPair implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger();

    private final CertificateKeyType certPublicKeyType;

    private final CertificateKeyType certSignatureType;

    private final GOSTCurve gostCurve;

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private final byte[] certificateBytes;

    @XmlElements(value = { @XmlElement(type = CustomDhPublicKey.class, name = "DhPublicKey"),
        @XmlElement(type = CustomDsaPublicKey.class, name = "DsaPublicKey"),
        @XmlElement(type = CustomRsaPublicKey.class, name = "RsaPublicKey"),
        @XmlElement(type = CustomEcPublicKey.class, name = "EcPublicKey") })
    private final CustomPublicKey publicKey;

    @XmlElements(value = { @XmlElement(type = CustomDHPrivateKey.class, name = "DhPrivateKey"),
        @XmlElement(type = CustomDSAPrivateKey.class, name = "DsaPrivateKey"),
        @XmlElement(type = CustomRSAPrivateKey.class, name = "RsaPrivateKey"),
        @XmlElement(type = CustomECPrivateKey.class, name = "EcPrivateKey") })
    private final CustomPrivateKey privateKey;

    private final NamedGroup signatureGroup;

    private final NamedGroup publicKeyGroup;

    private CertificateKeyPair() {
        this.certPublicKeyType = null;
        this.certSignatureType = null;
        this.certificateBytes = null;
        this.publicKey = null;
        this.privateKey = null;
        this.signatureGroup = null;
        this.publicKeyGroup = null;
        this.gostCurve = null;
    }

    public CertificateKeyPair(CertificateKeyType certPublicKeyType, CertificateKeyType certSignatureType,
        byte[] certificateBytes, CustomPublicKey publicKey, CustomPrivateKey privateKey, NamedGroup signatureGroup,
        NamedGroup publicKeyGroup) {
        this.certPublicKeyType = certPublicKeyType;
        this.certSignatureType = certSignatureType;
        this.certificateBytes = certificateBytes;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.signatureGroup = signatureGroup;
        this.publicKeyGroup = publicKeyGroup;
        gostCurve = null;

    }

    public CertificateKeyPair(Certificate cert, PrivateKey key) throws IOException {
        this.certPublicKeyType = getPublicKeyType(cert);
        this.certSignatureType = getSignatureType(cert);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        cert.encode(stream);
        this.certificateBytes = stream.toByteArray();
        this.privateKey = CertificateUtils.parseCustomPrivateKey(key);
        this.publicKey = CertificateUtils.parseCustomPublicKey(CertificateUtils.parsePublicKey(cert));
        this.publicKeyGroup = getPublicNamedGroup(cert);
        this.signatureGroup = getSignatureNamedGroup(cert);
        if (certPublicKeyType == CertificateKeyType.GOST01 || certPublicKeyType == CertificateKeyType.GOST12) {
            gostCurve = getGostCurve(cert);
        } else {
            gostCurve = null;
        }
    }

    public CertificateKeyPair(Certificate cert) {
        this.certPublicKeyType = getPublicKeyType(cert);
        this.certSignatureType = getSignatureType(cert);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            cert.encode(stream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.certificateBytes = stream.toByteArray();
        this.privateKey = null;
        this.publicKey = CertificateUtils.parseCustomPublicKey(CertificateUtils.parsePublicKey(cert));
        this.publicKeyGroup = getPublicNamedGroup(cert);
        this.signatureGroup = getSignatureNamedGroup(cert);
        if (certPublicKeyType == CertificateKeyType.GOST01 || certPublicKeyType == CertificateKeyType.GOST12) {
            gostCurve = getGostCurve(cert);
        } else {
            gostCurve = null;
        }
    }

    public CertificateKeyPair(byte[] certificateBytes, PrivateKey privateKey, PublicKey publicKey) {
        this.certPublicKeyType = CertificateKeyType.RSA;
        this.certSignatureType = CertificateKeyType.RSA;
        // To get the same output as cert.encode() but using the raw bytes
        // pack them accordingly
        this.certificateBytes = ArrayConverter.concatenate(
            ArrayConverter.intToBytes(certificateBytes.length + HandshakeByteLength.CERTIFICATES_LENGTH,
                HandshakeByteLength.CERTIFICATES_LENGTH),
            ArrayConverter.intToBytes(certificateBytes.length, HandshakeByteLength.CERTIFICATES_LENGTH),
            certificateBytes);
        this.publicKeyGroup = null;
        this.signatureGroup = null;
        gostCurve = null;
        this.privateKey = CertificateUtils.parseCustomPrivateKey(privateKey);
        this.publicKey = CertificateUtils.parseCustomPublicKey(publicKey);
    }

    public CertificateKeyPair(Certificate cert, PrivateKey privateKey, PublicKey publicKey) throws IOException {
        this.certPublicKeyType = getPublicKeyType(cert);
        this.certSignatureType = getSignatureType(cert);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        cert.encode(stream);
        this.certificateBytes = stream.toByteArray();
        this.publicKeyGroup = getPublicNamedGroup(cert);
        this.signatureGroup = getSignatureNamedGroup(cert);
        if (certPublicKeyType == CertificateKeyType.GOST01 || certPublicKeyType == CertificateKeyType.GOST12) {
            gostCurve = getGostCurve(cert);
        } else {
            gostCurve = null;
        }
        this.privateKey = CertificateUtils.parseCustomPrivateKey(privateKey);
        this.publicKey = CertificateUtils.parseCustomPublicKey(publicKey);
    }

    public CertificateKeyPair(CertificateKeyType certPublicKeyType, CertificateKeyType certSignatureType, File certFile,
        File privateKeyFile) throws CertificateException, IOException {
        this.certPublicKeyType = certPublicKeyType;
        this.certSignatureType = certSignatureType;
        Certificate certificate = PemUtil.readCertificate(certFile);
        this.publicKey = CertificateUtils.parseCustomPublicKey(PemUtil.readPublicKey(certFile));
        this.privateKey = CertificateUtils.parseCustomPrivateKey(PemUtil.readPrivateKey(privateKeyFile));
        certificateBytes = certificate.getCertificateAt(0).getEncoded();
        signatureGroup = getSignatureNamedGroup(certificate);
        publicKeyGroup = getPublicNamedGroup(certificate);
        if (certPublicKeyType == CertificateKeyType.GOST01 || certPublicKeyType == CertificateKeyType.GOST12) {
            gostCurve = getGostCurve(certificate);
        } else {
            gostCurve = null;
        }
    }

    private CertificateKeyType getPublicKeyType(Certificate cert) {
        if (cert.isEmpty()) {
            throw new IllegalArgumentException("Empty CertChain provided!");
        }
        AlgorithmIdentifier algorithm = cert.getCertificateAt(0).getSubjectPublicKeyInfo().getAlgorithm();
        switch (algorithm.getAlgorithm().getId()) {
            case "1.2.840.113549.1.1.1":
                return CertificateKeyType.RSA;
            case "1.2.840.10045.2.1":
                return CertificateKeyType.ECDH;
            case "1.2.840.10045.4.1":
            case "1.2.840.10045.4.2":
            case "1.2.840.10045.4.3.1":
            case "1.2.840.10045.4.3.2":
            case "1.2.840.10045.4.3.3":
            case "1.2.840.10045.4.3.4":
                return CertificateKeyType.ECDSA;
            case "1.2.840.113549.1.3.1":
                return CertificateKeyType.DH;
            case "1.2.840.10040.4.1":
                return CertificateKeyType.DSS;
            case "1.2.643.2.2.19":
                return CertificateKeyType.GOST01;
            case "1.2.643.7.1.1.1.1":
            case "1.2.643.7.1.1.1.2":
                return CertificateKeyType.GOST12;
            default:
                LOGGER.warn("Unknown algorithm ID: " + algorithm.getAlgorithm().getId() + " using \"NONE\"");
                return CertificateKeyType.NONE;
        }
    }

    public GOSTCurve getGostCurve() {
        return gostCurve;
    }

    private GOSTCurve getGostCurve(Certificate cert) {
        if (cert.isEmpty()) {
            throw new IllegalArgumentException("Empty CertChain provided!");
        }
        AlgorithmIdentifier algorithmIdentifier = cert.getCertificateAt(0).getSubjectPublicKeyInfo().getAlgorithm();
        switch (((ASN1ObjectIdentifier) ((ASN1Sequence) algorithmIdentifier.getParameters()).getObjectAt(0)).getId()) {
            case "1.2.643.2.2.35.1":
                return GOSTCurve.GostR3410_2001_CryptoPro_A;
            case "1.2.643.2.2.35.2":
                return GOSTCurve.GostR3410_2001_CryptoPro_B;
            case "1.2.643.2.2.35.3":
                return GOSTCurve.GostR3410_2001_CryptoPro_C;
            case "1.2.643.2.2.36.0":
                return GOSTCurve.GostR3410_2001_CryptoPro_XchA;
            case "1.2.643.2.2.36.1":
                return GOSTCurve.GostR3410_2001_CryptoPro_XchB;
            case "1.2.643.7.1.1.1.2":
                return GOSTCurve.Tc26_Gost_3410_12_256_paramSetA;
            case "1.2.643.7.1.2.1.2.1":
                return GOSTCurve.Tc26_Gost_3410_12_512_paramSetA;
            case "1.2.643.7.1.2.1.2.2":
                return GOSTCurve.Tc26_Gost_3410_12_512_paramSetB;
            case "1.2.643.7.1.1.1.5":
                return GOSTCurve.Tc26_Gost_3410_12_512_paramSetC;
            default:
                return null;
        }
    }

    private CertificateKeyType getSignatureType(Certificate cert) {
        if (cert.isEmpty()) {
            throw new IllegalArgumentException("Empty CertChain provided!");
        }
        AlgorithmIdentifier algorithm = cert.getCertificateAt(0).getSignatureAlgorithm();
        if (algorithm.getAlgorithm().getId().startsWith("1.2.840.113549.1.1.")) {
            return CertificateKeyType.RSA;
        }
        switch (algorithm.getAlgorithm().getId()) {
            case "1.2.840.10045.4.3.1":
            case "1.2.840.10045.4.3.2":
            case "1.2.840.10045.4.3.3":
                return CertificateKeyType.ECDSA;
            case "2.16.840.1.101.3.4.3.1":
            case "2.16.840.1.101.3.4.3.2":
            case "2.16.840.1.101.3.4.3.13":
            case "2.16.840.1.101.3.4.3.14":
            case "2.16.840.1.101.3.4.3.15":
            case "2.16.840.1.101.3.4.3.16":
                return CertificateKeyType.DSS;
            case "1.2.643.2.2.3":
                return CertificateKeyType.GOST01;
            case "1.2.643.7.1.1.3.2":
            case "1.2.643.7.1.1.3.3":
                return CertificateKeyType.GOST12;
            default:
                LOGGER.warn("Unknown algorithm ID: " + algorithm.getAlgorithm().getId() + " using \"NONE\"");
                return CertificateKeyType.NONE;
        }
    }

    private NamedGroup getSignatureNamedGroup(Certificate cert) {
        if (cert.isEmpty()) {
            throw new IllegalArgumentException("Empty CertChain provided!");
        }
        if (!(publicKey instanceof CustomEcPublicKey)
            || (certSignatureType != CertificateKeyType.ECDH && certSignatureType != CertificateKeyType.ECDSA)) {
            return null;
        }
        if (((CustomEcPublicKey) publicKey).getGostCurve() != null) {
            return null;
        }
        // TODO Okay - we currently do not support mixed group ecdsa
        // pubKey/signature certificates
        // i am not sure if they are actually allowed to exist- we assume that
        // the signature group is
        // the same as for the public key
        try {
            ASN1ObjectIdentifier publicKeyParameters = (ASN1ObjectIdentifier) cert.getCertificateAt(0)
                .getSubjectPublicKeyInfo().getAlgorithm().getParameters();
            return NamedGroup.fromJavaName(ECNamedCurveTable.getName(publicKeyParameters));
        } catch (Exception ex) {
            LOGGER.warn("Could not determine EC public key group", ex);
            return null;
        }
    }

    private NamedGroup getPublicNamedGroup(Certificate cert) {
        if (cert.isEmpty()) {
            throw new IllegalArgumentException("Empty CertChain provided!");
        }
        if (!(publicKey instanceof CustomEcPublicKey)) {
            return null;
        }
        if (((CustomEcPublicKey) publicKey).getGostCurve() != null) {
            return null;
        }
        try {
            ASN1ObjectIdentifier publicKeyParameters = (ASN1ObjectIdentifier) cert.getCertificateAt(0)
                .getSubjectPublicKeyInfo().getAlgorithm().getParameters();
            return NamedGroup.fromJavaName(ECNamedCurveTable.getName(publicKeyParameters));
        } catch (Exception ex) {
            LOGGER.warn("Could not determine EC public key group", ex);
            return null;
        }
    }

    public CertificateKeyType getCertPublicKeyType() {
        return certPublicKeyType;
    }

    public CertificateKeyType getCertSignatureType() {
        return certSignatureType;
    }

    public byte[] getCertificateBytes() {
        return certificateBytes;
    }

    public CustomPublicKey getPublicKey() {
        return publicKey;
    }

    public CustomPrivateKey getPrivateKey() {
        return privateKey;
    }

    public NamedGroup getSignatureGroup() {
        return signatureGroup;
    }

    public NamedGroup getPublicKeyGroup() {
        return publicKeyGroup;
    }

    public void adjustInConfig(Config config, ConnectionEndType connectionEnd) {
        publicKey.adjustInConfig(config, connectionEnd);
        if (privateKey != null) {
            privateKey.adjustInConfig(config, connectionEnd);
        }
        config.setDefaultExplicitCertificateKeyPair(this);
        if (gostCurve != null) {
            config.setDefaultSelectedGostCurve(gostCurve);
        }
    }

    public void adjustInContext(TlsContext context, ConnectionEndType connectionEnd) {
        if (context.getSelectedProtocolVersion() != ProtocolVersion.TLS13) {
            publicKey.adjustInContext(context, connectionEnd);
        }
        if (privateKey != null) {
            privateKey.adjustInContext(context, connectionEnd);
        }
        if (!context.getChooser().getSelectedCipherSuite().isTLS13()
            && AlgorithmResolver.getCertificateKeyType(context.getChooser().getSelectedCipherSuite())
                == CertificateKeyType.ECDH) {
            context.setSelectedGroup(publicKeyGroup);
        } else {
            context.setEcCertificateCurve(publicKeyGroup);
        }
        context.setEcCertificateSignatureCurve(signatureGroup);
        if (context.getConfig().getAutoAdjustSignatureAndHashAlgorithm()) {
            SignatureAndHashAlgorithm sigHashAlgo =
                SignatureAndHashAlgorithm.forCertificateKeyPair(this, context.getChooser());

            if (sigHashAlgo == SignatureAndHashAlgorithm.GOSTR34102012_512_GOSTR34112012_512
                || sigHashAlgo == SignatureAndHashAlgorithm.GOSTR34102012_256_GOSTR34112012_256
                || sigHashAlgo == SignatureAndHashAlgorithm.GOSTR34102001_GOSTR3411) {
                context.setSelectedGostCurve(gostCurve);
                LOGGER.debug("Adjusting selected GOST curve:" + gostCurve);
            }

            LOGGER.debug("Setting selected SignatureAndHash algorithm to:" + sigHashAlgo);
            context.setSelectedSignatureAndHashAlgorithm(sigHashAlgo);
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.certPublicKeyType);
        hash = 29 * hash + Objects.hashCode(this.certSignatureType);
        hash = 29 * hash + Arrays.hashCode(this.certificateBytes);
        hash = 29 * hash + Objects.hashCode(this.publicKey);
        hash = 29 * hash + Objects.hashCode(this.privateKey);
        hash = 29 * hash + Objects.hashCode(this.signatureGroup);
        hash = 29 * hash + Objects.hashCode(this.publicKeyGroup);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CertificateKeyPair other = (CertificateKeyPair) obj;
        if (this.certPublicKeyType != other.certPublicKeyType) {
            return false;
        }
        if (this.certSignatureType != other.certSignatureType) {
            return false;
        }
        if (!Arrays.equals(this.certificateBytes, other.certificateBytes)) {
            return false;
        }
        if (!Objects.equals(this.publicKey, other.publicKey)) {
            return false;
        }
        if (!Objects.equals(this.privateKey, other.privateKey)) {
            return false;
        }
        if (this.signatureGroup != other.signatureGroup) {
            return false;
        }
        return this.publicKeyGroup == other.publicKeyGroup;
    }

    public boolean isCertificateParsable() {
        try {
            Certificate cert = Certificate.parse(new ByteArrayInputStream(certificateBytes));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "CertificateKeyPair{" + "certPublicKeyType=" + certPublicKeyType + ", certSignatureType="
            + certSignatureType + ", certificateBytes=" + Arrays.toString(certificateBytes) + ", publicKey=" + publicKey
            + ", privateKey=" + privateKey + ", signatureGroup=" + signatureGroup + ", publicKeyGroup=" + publicKeyGroup
            + '}';
    }

    public boolean isUsable(CertificateKeyType neededPublicKeyType,
        CertificateKeyType preferredSignatureCertSignatureType) {

        if (neededPublicKeyType == CertificateKeyType.ECDH || neededPublicKeyType == CertificateKeyType.ECDSA) {
            if (certPublicKeyType == CertificateKeyType.ECDH || certPublicKeyType == CertificateKeyType.ECDSA) {
                if (preferredSignatureCertSignatureType == certSignatureType) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            if (neededPublicKeyType == certPublicKeyType && preferredSignatureCertSignatureType == certSignatureType) {
                return true;
            } else {
                return false;
            }
        }

    }
}
