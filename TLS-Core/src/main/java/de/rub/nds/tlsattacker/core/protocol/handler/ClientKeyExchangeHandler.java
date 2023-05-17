/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.AlgorithmResolver;
import de.rub.nds.tlsattacker.core.constants.HandshakeByteLength;
import de.rub.nds.tlsattacker.core.constants.PRFAlgorithm;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.crypto.PseudoRandomFunction;
import de.rub.nds.tlsattacker.core.crypto.SSLUtils;
import de.rub.nds.tlsattacker.core.exceptions.CryptoException;
import de.rub.nds.tlsattacker.core.protocol.message.ClientKeyExchangeMessage;
import de.rub.nds.tlsattacker.core.record.cipher.cryptohelper.KeySet;
import de.rub.nds.tlsattacker.core.record.cipher.cryptohelper.KeySetGenerator;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import de.rub.nds.tlsattacker.core.state.session.IdSession;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import java.security.NoSuchAlgorithmException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @param <MessageT>
 *                   The ClientKeyExchangeMessage that should be Handled
 */
public abstract class ClientKeyExchangeHandler<MessageT extends ClientKeyExchangeMessage>
    extends HandshakeMessageHandler<MessageT> {

    private static final Logger LOGGER = LogManager.getLogger();

    public ClientKeyExchangeHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    public void adjustPremasterSecret(ClientKeyExchangeMessage message) {
        if (message.getComputations().getPremasterSecret() != null) {
            byte[] premasterSecret = message.getComputations().getPremasterSecret().getValue();
            tlsContext.setPreMasterSecret(premasterSecret);
            LOGGER.debug("Set PremasterSecret in Context to " + ArrayConverter.bytesToHexString(premasterSecret));
        } else {
            LOGGER.debug("Did not set in Context PremasterSecret");
        }
    }

    protected byte[] calculateMasterSecret(ClientKeyExchangeMessage message) throws CryptoException {
        Chooser chooser = tlsContext.getChooser();
        if (chooser.getSelectedProtocolVersion() == ProtocolVersion.SSL3) {
            LOGGER.debug("Calculate SSL MasterSecret with Client and Server Nonces, which are: "
                + ArrayConverter.bytesToHexString(message.getComputations().getClientServerRandom().getValue()));
            return SSLUtils.calculateMasterSecretSSL3(chooser.getPreMasterSecret(),
                message.getComputations().getClientServerRandom().getValue());
        } else {
            PRFAlgorithm prfAlgorithm = AlgorithmResolver.getPRFAlgorithm(chooser.getSelectedProtocolVersion(),
                chooser.getSelectedCipherSuite());
            if (chooser.isUseExtendedMasterSecret()) {
                LOGGER.debug("Calculating ExtendedMasterSecret");
                byte[] sessionHash = tlsContext.getDigest().digest(chooser.getSelectedProtocolVersion(),
                    chooser.getSelectedCipherSuite());
                LOGGER.debug("Premastersecret: " + ArrayConverter.bytesToHexString(chooser.getPreMasterSecret()));

                LOGGER.debug("SessionHash: " + ArrayConverter.bytesToHexString(sessionHash));
                byte[] extendedMasterSecret = PseudoRandomFunction.compute(prfAlgorithm, chooser.getPreMasterSecret(),
                    PseudoRandomFunction.EXTENDED_MASTER_SECRET_LABEL, sessionHash, HandshakeByteLength.MASTER_SECRET);
                return extendedMasterSecret;
            } else {
                LOGGER.debug("Calculating MasterSecret");
                byte[] masterSecret = PseudoRandomFunction.compute(prfAlgorithm, chooser.getPreMasterSecret(),
                    PseudoRandomFunction.MASTER_SECRET_LABEL,
                    message.getComputations().getClientServerRandom().getValue(), HandshakeByteLength.MASTER_SECRET);
                return masterSecret;
            }
        }
    }

    public void adjustMasterSecret(ClientKeyExchangeMessage message) {
        byte[] masterSecret;
        try {
            masterSecret = calculateMasterSecret(message);
        } catch (CryptoException ex) {
            throw new UnsupportedOperationException("Could not calculate masterSecret", ex);
        }
        tlsContext.setMasterSecret(masterSecret);
        LOGGER.debug("Set MasterSecret in Context to " + ArrayConverter.bytesToHexString(masterSecret));
    }

    protected void spawnNewSession() {
        if (tlsContext.getChooser().getServerSessionId().length != 0) {
            IdSession session =
                new IdSession(tlsContext.getChooser().getServerSessionId(), tlsContext.getChooser().getMasterSecret());
            tlsContext.addNewSession(session);
            LOGGER.debug("Spawning new resumable Session");
        }
    }

    private KeySet getKeySet(TlsContext context) {
        try {
            LOGGER.debug("Generating new KeySet");
            return KeySetGenerator.generateKeySet(context);
        } catch (NoSuchAlgorithmException | CryptoException ex) {
            throw new UnsupportedOperationException("The specified Algorithm is not supported", ex);
        }
    }
}
