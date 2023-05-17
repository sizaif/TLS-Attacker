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
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.protocol.ModifiableVariableHolder;
import de.rub.nds.tlsattacker.core.protocol.handler.PWDClientKeyExchangeHandler;
import de.rub.nds.tlsattacker.core.protocol.message.computations.PWDComputations;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PWDClientKeyExchange")
public class PWDClientKeyExchangeMessage extends ClientKeyExchangeMessage {

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.LENGTH)
    private ModifiableInteger elementLength;

    @ModifiableVariableProperty
    private ModifiableByteArray element;

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.LENGTH)
    private ModifiableInteger scalarLength;

    @ModifiableVariableProperty
    private ModifiableByteArray scalar;

    protected PWDComputations computations;

    public PWDClientKeyExchangeMessage() {
        super();
    }

    public PWDClientKeyExchangeMessage(Config tlsConfig) {
        super(tlsConfig);
    }

    @Override
    public PWDComputations getComputations() {
        return computations;
    }

    @Override
    public void prepareComputations() {
        if (getComputations() == null) {
            computations = new PWDComputations();
        }
    }

    @Override
    public PWDClientKeyExchangeHandler getHandler(TlsContext context) {
        return new PWDClientKeyExchangeHandler(context);
    }

    public ModifiableInteger getElementLength() {
        return elementLength;
    }

    public void setElementLength(ModifiableInteger elementLength) {
        this.elementLength = elementLength;
    }

    public void setElementLength(int elementLength) {
        this.elementLength = ModifiableVariableFactory.safelySetValue(this.elementLength, elementLength);
    }

    public ModifiableByteArray getElement() {
        return element;
    }

    public void setElement(ModifiableByteArray element) {
        this.element = element;
    }

    public void setElement(byte[] element) {
        this.element = ModifiableVariableFactory.safelySetValue(this.element, element);
    }

    public ModifiableInteger getScalarLength() {
        return scalarLength;
    }

    public void setScalarLength(ModifiableInteger scalarLength) {
        this.scalarLength = scalarLength;
    }

    public void setScalarLength(int scalarLength) {
        this.scalarLength = ModifiableVariableFactory.safelySetValue(this.scalarLength, scalarLength);
    }

    public ModifiableByteArray getScalar() {
        return scalar;
    }

    public void setScalar(ModifiableByteArray scalar) {
        this.scalar = scalar;
    }

    public void setScalar(byte[] scalar) {
        this.scalar = ModifiableVariableFactory.safelySetValue(this.scalar, scalar);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PWDClientKeyExchangeMessage:");
        sb.append("\n  Element: ");
        if (getElement() != null && getElement().getValue() != null) {
            sb.append(ArrayConverter.bytesToHexString(getElement().getValue()));
        } else {
            sb.append("null");
        }
        sb.append("\n  Scalar: ");
        if (getScalar() != null && getScalar().getValue() != null) {
            sb.append(ArrayConverter.bytesToHexString(getScalar().getValue()));
        } else {
            sb.append("null");
        }

        return sb.toString();
    }

    @Override
    public String toCompactString() {
        return "PWD_CLIENT_KEY_EXCHANGE";
    }

    @Override
    public List<ModifiableVariableHolder> getAllModifiableVariableHolders() {
        List<ModifiableVariableHolder> allModifiableVariableHolders = super.getAllModifiableVariableHolders();
        if (computations != null) {
            allModifiableVariableHolders.add(computations);
        }
        return allModifiableVariableHolders;
    }

    @Override
    public String toShortString() {
        return "PWD_CKE";
    }

}
