/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.message.extension;

import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class representing a Extended Random Extension Message, as defined as in
 * https://tools.ietf.org/html/draft-rescorla-tls-extended-random-02
 */
@XmlRootElement(name = "ExtendedRandomExtension")
public class ExtendedRandomExtensionMessage extends ExtensionMessage {

    @ModifiableVariableProperty
    private ModifiableByteArray extendedRandom;

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.LENGTH)
    private ModifiableInteger extendedRandomLength;

    public ExtendedRandomExtensionMessage() {
        super(ExtensionType.EXTENDED_RANDOM);
    }

    public ExtendedRandomExtensionMessage(Config config) {
        super(ExtensionType.EXTENDED_RANDOM);
    }

    public void setExtendedRandom(ModifiableByteArray extendedRandom) {
        this.extendedRandom = extendedRandom;
    }

    public void setExtendedRandom(byte[] extendedRandomBytes) {
        this.extendedRandom = ModifiableVariableFactory.safelySetValue(extendedRandom, extendedRandomBytes);
    }

    public ModifiableByteArray getExtendedRandom() {
        return extendedRandom;
    }

    public ModifiableInteger getExtendedRandomLength() {
        return extendedRandomLength;
    }

    public void setExtendedRandomLength(int length) {
        this.extendedRandomLength = ModifiableVariableFactory.safelySetValue(extendedRandomLength, length);
    }

    public void setExtendedRandomLength(ModifiableInteger pointFormatsLength) {
        this.extendedRandomLength = pointFormatsLength;
    }

}
