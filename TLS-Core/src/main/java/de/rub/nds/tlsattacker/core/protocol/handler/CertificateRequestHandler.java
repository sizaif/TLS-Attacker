/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import com.google.common.collect.Sets;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.ClientCertificateType;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.constants.SignatureAndHashAlgorithm;
import de.rub.nds.tlsattacker.core.protocol.message.CertificateRequestMessage;
import de.rub.nds.tlsattacker.core.protocol.message.extension.SignatureAndHashAlgorithmsExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.CertificateRequestParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.CertificateRequestPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.CertificateRequestSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CertificateRequestHandler extends HandshakeMessageHandler<CertificateRequestMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    public CertificateRequestHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    @Override
    public CertificateRequestParser getParser(byte[] message, int pointer) {
        return new CertificateRequestParser(pointer, message, tlsContext.getChooser().getSelectedProtocolVersion(),
            tlsContext.getConfig());
    }

    @Override
    public CertificateRequestPreparator getPreparator(CertificateRequestMessage message) {
        return new CertificateRequestPreparator(tlsContext.getChooser(), message);
    }

    @Override
    public CertificateRequestSerializer getSerializer(CertificateRequestMessage message) {
        return new CertificateRequestSerializer(message, tlsContext.getChooser().getSelectedProtocolVersion());
    }

    @Override
    public void adjustTLSContext(CertificateRequestMessage message) {
        if (tlsContext.getChooser().getSelectedProtocolVersion().isTLS13()) {
            adjustCertificateRequestContext(message);
            adjustServerSupportedSignatureAndHashAlgorithms(message);
        } else {
            adjustClientCertificateTypes(message);
            adjustDistinguishedNames(message);
            if (tlsContext.getChooser().getSelectedProtocolVersion() == ProtocolVersion.TLS12
                || tlsContext.getChooser().getSelectedProtocolVersion() == ProtocolVersion.DTLS12) {
                adjustServerSupportedSignatureAndHashAlgorithms(message);
            }
        }
    }

    private void adjustServerSupportedSignatureAndHashAlgorithms(CertificateRequestMessage message) {
        List<SignatureAndHashAlgorithm> algoList;
        if (tlsContext.getChooser().getSelectedProtocolVersion().isTLS13()) {
            SignatureAndHashAlgorithmsExtensionMessage extension =
                message.getExtension(SignatureAndHashAlgorithmsExtensionMessage.class);
            if (extension != null) {
                algoList = convertSignatureAndHashAlgorithms(extension.getSignatureAndHashAlgorithms().getValue());
            } else {
                if (message.getSignatureHashAlgorithms() != null) {
                    algoList = convertSignatureAndHashAlgorithms(message.getSignatureHashAlgorithms().getValue());
                } else {
                    algoList = new LinkedList<>();
                }
            }
        } else {
            if (message.getSignatureHashAlgorithms() != null) {
                algoList = convertSignatureAndHashAlgorithms(message.getSignatureHashAlgorithms().getValue());
            } else {
                algoList = new LinkedList<>();
            }
        }
        tlsContext.setServerSupportedSignatureAndHashAlgorithms(algoList);
        LOGGER.debug("Set ServerSupportedSignatureAndHashAlgorithms to " + algoList.toString());
    }

    private void adjustDistinguishedNames(CertificateRequestMessage message) {
        if (message.getDistinguishedNames() != null && message.getDistinguishedNames().getValue() != null) {
            byte[] distinguishedNames = message.getDistinguishedNames().getValue();
            tlsContext.setDistinguishedNames(distinguishedNames);
            LOGGER.debug(
                "Set DistinguishedNames in Context to " + ArrayConverter.bytesToHexString(distinguishedNames, false));
        } else {
            LOGGER.debug("Not adjusting DistinguishedNames");
        }
    }

    private void adjustClientCertificateTypes(CertificateRequestMessage message) {
        List<ClientCertificateType> clientCertTypes =
            convertClientCertificateTypes(message.getClientCertificateTypes().getValue());
        tlsContext.setClientCertificateTypes(clientCertTypes);
        LOGGER.debug("Set ClientCertificateType in Context to " + clientCertTypes.toString());
    }

    private List<ClientCertificateType> convertClientCertificateTypes(byte[] bytesToConvert) {
        List<ClientCertificateType> list = new LinkedList<>();
        for (byte b : bytesToConvert) {
            ClientCertificateType type = ClientCertificateType.getClientCertificateType(b);
            if (type == null) {
                LOGGER.warn("Cannot convert:" + b + " to a ClientCertificateType");
            } else {
                list.add(type);
            }
        }
        return list;
    }

    private List<SignatureAndHashAlgorithm> convertSignatureAndHashAlgorithms(byte[] bytesToConvert) {
        if (bytesToConvert.length % 2 != 0) {
            LOGGER.warn("Cannot convert:" + ArrayConverter.bytesToHexString(bytesToConvert, false)
                + " to a List<SignatureAndHashAlgorithm>");
            return new LinkedList<>();
        }
        List<SignatureAndHashAlgorithm> list = new LinkedList<>();

        for (int i = 0; i < bytesToConvert.length; i += 2) {
            byte[] copied = new byte[2];
            copied[0] = bytesToConvert[i];
            copied[1] = bytesToConvert[i + 1];
            SignatureAndHashAlgorithm algo = SignatureAndHashAlgorithm.getSignatureAndHashAlgorithm(copied);
            list.add(algo);
        }
        return list;
    }

    private void adjustSelectedSignatureAndHashAlgorithm() {
        if (Collections.disjoint(tlsContext.getChooser().getClientSupportedSignatureAndHashAlgorithms(),
            tlsContext.getChooser().getServerSupportedSignatureAndHashAlgorithms())) {
            LOGGER.warn("Client and Server have no signature and hash algorithm in common");
        } else {
            Sets.SetView<SignatureAndHashAlgorithm> intersection = Sets.intersection(
                Sets.newHashSet(tlsContext.getChooser().getClientSupportedSignatureAndHashAlgorithms()),
                Sets.newHashSet(tlsContext.getChooser().getServerSupportedSignatureAndHashAlgorithms()));
            SignatureAndHashAlgorithm algo = (SignatureAndHashAlgorithm) intersection.toArray()[0];
            tlsContext.setSelectedSignatureAndHashAlgorithm(algo);
            LOGGER.debug("Adjusting selected signature and hash algorithm to: " + algo.name());

        }
    }

    private void adjustCertificateRequestContext(CertificateRequestMessage msg) {
        tlsContext.setCertificateRequestContext(msg.getCertificateRequestContext().getValue());
    }
}
