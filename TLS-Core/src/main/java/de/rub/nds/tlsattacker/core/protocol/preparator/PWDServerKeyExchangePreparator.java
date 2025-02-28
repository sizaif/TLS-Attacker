/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.ECPointFormat;
import de.rub.nds.tlsattacker.core.constants.EllipticCurveType;
import de.rub.nds.tlsattacker.core.constants.NamedGroup;
import de.rub.nds.tlsattacker.core.crypto.ec.CurveFactory;
import de.rub.nds.tlsattacker.core.crypto.ec.EllipticCurve;
import de.rub.nds.tlsattacker.core.crypto.ec.EllipticCurveOverFp;
import de.rub.nds.tlsattacker.core.crypto.ec.Point;
import de.rub.nds.tlsattacker.core.crypto.ec.PointFormatter;
import de.rub.nds.tlsattacker.core.exceptions.CryptoException;
import de.rub.nds.tlsattacker.core.exceptions.PreparationException;
import de.rub.nds.tlsattacker.core.protocol.message.PWDServerKeyExchangeMessage;
import de.rub.nds.tlsattacker.core.protocol.message.computations.PWDComputations;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PWDServerKeyExchangePreparator extends ServerKeyExchangePreparator<PWDServerKeyExchangeMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    protected final PWDServerKeyExchangeMessage msg;

    public PWDServerKeyExchangePreparator(Chooser chooser, PWDServerKeyExchangeMessage msg) {
        super(chooser, msg);
        this.msg = msg;
    }

    @Override
    public void prepareHandshakeMessageContents() {
        LOGGER.debug("Preparing PWDServerKeyExchangeMessage");
        msg.prepareComputations();
        prepareCurveType(msg);
        NamedGroup group = selectNamedGroup(msg);
        EllipticCurve curve = CurveFactory.getCurve(group);
        msg.setNamedGroup(group.getValue());
        prepareSalt(msg);
        prepareSaltLength(msg);

        try {
            preparePasswordElement(msg);
        } catch (CryptoException e) {
            throw new PreparationException("Failed to generate password element", e);
        }
        prepareScalarElement(msg);
    }

    protected void preparePasswordElement(PWDServerKeyExchangeMessage msg) throws CryptoException {
        NamedGroup group = selectNamedGroup(msg);
        EllipticCurve curve = CurveFactory.getCurve(selectNamedGroup(msg));
        Point passwordElement = PWDComputations.computePasswordElement(chooser, curve);
        msg.getComputations().setPasswordElement(passwordElement);

        LOGGER.debug("PasswordElement.x: " + ArrayConverter
            .bytesToHexString(ArrayConverter.bigIntegerToByteArray(passwordElement.getFieldX().getData())));
    }

    protected NamedGroup selectNamedGroup(PWDServerKeyExchangeMessage msg) {
        NamedGroup namedGroup;
        if (chooser.getConfig().isEnforceSettings()) {
            namedGroup = chooser.getConfig().getDefaultSelectedNamedGroup();
        } else {
            Set<NamedGroup> serverSet = new HashSet<>();
            Set<NamedGroup> clientSet = new HashSet<>();
            for (int i = 0; i < chooser.getClientSupportedNamedGroups().size(); i++) {
                NamedGroup group = chooser.getClientSupportedNamedGroups().get(i);
                if (group.isStandardCurve()) {
                    EllipticCurve curve = CurveFactory.getCurve(group);
                    if (curve instanceof EllipticCurveOverFp) {
                        clientSet.add(group);
                    }
                }
            }
            for (int i = 0; i < chooser.getConfig().getDefaultServerNamedGroups().size(); i++) {
                NamedGroup group = chooser.getConfig().getDefaultServerNamedGroups().get(i);
                if (group.isStandardCurve()) {
                    EllipticCurve curve = CurveFactory.getCurve(group);
                    if (curve instanceof EllipticCurveOverFp) {
                        serverSet.add(group);
                    }
                }
            }
            serverSet.retainAll(clientSet);
            if (serverSet.isEmpty()) {
                LOGGER.warn("No common NamedGroup - falling back to default");
                namedGroup = chooser.getConfig().getDefaultSelectedNamedGroup();
            } else {
                if (serverSet.contains(chooser.getConfig().getDefaultSelectedNamedGroup())) {
                    namedGroup = chooser.getConfig().getDefaultSelectedNamedGroup();
                } else {
                    namedGroup = (NamedGroup) serverSet.toArray()[0];
                }
            }
        }
        return namedGroup;
    }

    protected void prepareSalt(PWDServerKeyExchangeMessage msg) {
        msg.setSalt(chooser.getConfig().getDefaultServerPWDSalt());
        LOGGER.debug("Salt: " + ArrayConverter.bytesToHexString(msg.getSalt().getValue()));
    }

    protected void prepareSaltLength(PWDServerKeyExchangeMessage msg) {
        msg.setSaltLength(msg.getSalt().getValue().length);
        LOGGER.debug("SaltLength: " + msg.getSaltLength().getValue());
    }

    protected void prepareCurveType(PWDServerKeyExchangeMessage msg) {
        msg.setCurveType(EllipticCurveType.NAMED_CURVE.getValue());
    }

    protected List<ECPointFormat> getPointFormatList() {
        List<ECPointFormat> sharedPointFormats = new ArrayList<>(chooser.getServerSupportedPointFormats());

        if (sharedPointFormats.isEmpty()) {
            LOGGER.warn("Don't know which point format to use for PWD. " + "Check if pointFormats is set in config.");
            sharedPointFormats = chooser.getConfig().getDefaultServerSupportedPointFormats();
        }

        List<ECPointFormat> unsupportedFormats = new ArrayList<>();

        if (!chooser.getConfig().isEnforceSettings()) {
            List<ECPointFormat> clientPointFormats = chooser.getClientSupportedPointFormats();
            for (ECPointFormat f : sharedPointFormats) {
                if (!clientPointFormats.contains(f)) {
                    unsupportedFormats.add(f);
                }
            }
        }

        sharedPointFormats.removeAll(unsupportedFormats);
        if (sharedPointFormats.isEmpty()) {
            sharedPointFormats = new ArrayList<>(chooser.getConfig().getDefaultServerSupportedPointFormats());
        }

        return sharedPointFormats;
    }

    protected void prepareScalarElement(PWDServerKeyExchangeMessage msg) {
        EllipticCurve curve = CurveFactory.getCurve(selectNamedGroup(msg));
        PWDComputations.PWDKeyMaterial keyMaterial =
            PWDComputations.generateKeyMaterial(curve, msg.getComputations().getPasswordElement(), chooser);

        msg.getComputations().setPrivateKeyScalar(keyMaterial.privateKeyScalar);
        LOGGER.debug("Private: "
            + ArrayConverter.bytesToHexString(ArrayConverter.bigIntegerToByteArray(keyMaterial.privateKeyScalar)));

        prepareScalar(msg, keyMaterial.scalar);
        prepareScalarLength(msg);

        prepareElement(msg, keyMaterial.element);
        prepareElementLength(msg);
    }

    protected void prepareScalar(PWDServerKeyExchangeMessage msg, BigInteger scalar) {
        msg.setScalar(ArrayConverter.bigIntegerToByteArray(scalar));
        LOGGER.debug("Scalar: " + ArrayConverter.bytesToHexString(ArrayConverter.bigIntegerToByteArray(scalar)));
    }

    protected void prepareScalarLength(PWDServerKeyExchangeMessage msg) {
        msg.setScalarLength(msg.getScalar().getValue().length);
        LOGGER.debug("ScalarLength: " + msg.getScalarLength());
    }

    protected void prepareElement(PWDServerKeyExchangeMessage msg, Point element) {
        byte[] serializedElement = PointFormatter.formatToByteArray(chooser.getConfig().getDefaultSelectedNamedGroup(),
            element, chooser.getConfig().getDefaultSelectedPointFormat());
        msg.setElement(serializedElement);
        LOGGER.debug("Element: " + ArrayConverter.bytesToHexString(serializedElement));
    }

    protected void prepareElementLength(PWDServerKeyExchangeMessage msg) {
        msg.setElementLength(msg.getElement().getValue().length);
        LOGGER.debug("ElementLength: " + msg.getElementLength());
    }
}
