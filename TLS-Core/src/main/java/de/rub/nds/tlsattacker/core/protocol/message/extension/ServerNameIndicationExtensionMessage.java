/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.message.extension;

import de.rub.nds.modifiablevariable.HoldsModifiableVariable;
import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import de.rub.nds.tlsattacker.core.protocol.ModifiableVariableHolder;
import de.rub.nds.tlsattacker.core.protocol.message.extension.sni.ServerNamePair;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Describes Server Name Indication extension from http://tools.ietf.org/html/rfc6066
 */
@XmlRootElement(name = "ServerNameIndicationExtension")
public class ServerNameIndicationExtensionMessage extends ExtensionMessage {

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.LENGTH)
    private ModifiableInteger serverNameListLength;

    @ModifiableVariableProperty
    private ModifiableByteArray serverNameListBytes;

    @HoldsModifiableVariable
    private List<ServerNamePair> serverNameList = new LinkedList<>();

    public ServerNameIndicationExtensionMessage() {
        super(ExtensionType.SERVER_NAME_INDICATION);
    }

    public ServerNameIndicationExtensionMessage(Config config) {
        super(ExtensionType.SERVER_NAME_INDICATION);
    }

    public ModifiableInteger getServerNameListLength() {
        return serverNameListLength;
    }

    public void setServerNameListLength(ModifiableInteger serverNameListLength) {
        this.serverNameListLength = serverNameListLength;
    }

    public void setServerNameListLength(int length) {
        this.serverNameListLength = ModifiableVariableFactory.safelySetValue(serverNameListLength, length);
    }

    public ModifiableByteArray getServerNameListBytes() {
        return serverNameListBytes;
    }

    public void setServerNameListBytes(ModifiableByteArray serverNameListBytes) {
        this.serverNameListBytes = serverNameListBytes;
    }

    public void setServerNameListBytes(byte[] bytes) {
        this.serverNameListBytes = ModifiableVariableFactory.safelySetValue(serverNameListBytes, bytes);
    }

    public List<ServerNamePair> getServerNameList() {
        return serverNameList;
    }

    public void setServerNameList(List<ServerNamePair> serverNameList) {
        this.serverNameList = serverNameList;
    }

    @Override
    public List<ModifiableVariableHolder> getAllModifiableVariableHolders() {
        List<ModifiableVariableHolder> holders = super.getAllModifiableVariableHolders();
        return holders;
    }
}
