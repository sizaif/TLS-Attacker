/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.message.extension.cachedinfo;

import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.tlsattacker.core.protocol.ModifiableVariableHolder;

public class CachedObject extends ModifiableVariableHolder {

    @ModifiableVariableProperty
    private ModifiableByte cachedInformationType;
    @ModifiableVariableProperty
    // Hash Value Length 1 Byte
    private ModifiableInteger hashValueLength;
    @ModifiableVariableProperty
    private ModifiableByteArray hashValue;

    // Preparator values
    byte cachedInformationTypeConfig;
    Integer hashValueLengthConfig;
    byte[] hashValueConfig;

    public CachedObject(byte preparatorCachedInformationType, Integer preparatorHashValueLength,
        byte[] preparatorHashValue) {
        this.cachedInformationTypeConfig = preparatorCachedInformationType;
        this.hashValueLengthConfig = preparatorHashValueLength;
        this.hashValueConfig = preparatorHashValue;
    }

    public CachedObject() {
    }

    public ModifiableByte getCachedInformationType() {
        return cachedInformationType;
    }

    public void setCachedInformationType(ModifiableByte cachedInformationType) {
        this.cachedInformationType = cachedInformationType;
    }

    public void setCachedInformationType(byte cachedInformationType) {
        this.cachedInformationType =
            ModifiableVariableFactory.safelySetValue(this.cachedInformationType, cachedInformationType);
    }

    public ModifiableInteger getHashValueLength() {
        return hashValueLength;
    }

    public void setHashValueLength(ModifiableInteger hashValueLength) {
        this.hashValueLength = hashValueLength;
    }

    public void setHashValueLength(Integer hashValueLength) {
        this.hashValueLength = ModifiableVariableFactory.safelySetValue(this.hashValueLength, hashValueLength);
    }

    public ModifiableByteArray getHashValue() {
        return hashValue;
    }

    public void setHashValue(ModifiableByteArray hashValue) {
        this.hashValue = hashValue;
    }

    public void setHashValue(byte[] hashValue) {
        this.hashValue = ModifiableVariableFactory.safelySetValue(this.hashValue, hashValue);
    }

    public byte getCachedInformationTypeConfig() {
        return cachedInformationTypeConfig;
    }

    public void setCachedInformationTypeConfig(byte cachedInformationTypeConfig) {
        this.cachedInformationTypeConfig = cachedInformationTypeConfig;
    }

    public Integer getHashValueLengthConfig() {
        return hashValueLengthConfig;
    }

    public void setPreparatorHashValueLength(int preparatorHashValueLength) {
        this.hashValueLengthConfig = preparatorHashValueLength;
    }

    public byte[] getHashValueConfig() {
        return hashValueConfig;
    }

    public void setHashValueConfig(byte[] hashValueConfig) {
        this.hashValueConfig = hashValueConfig;
    }

}
