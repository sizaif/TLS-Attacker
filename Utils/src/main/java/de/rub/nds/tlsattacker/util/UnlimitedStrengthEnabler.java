/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;

/**
 * restriction. Partially taken from: https://github.com/jruby/jruby/blob/0 c345e1b186bd457ebd96143c0816abe93b18fdf
 * /core/src/main/java/org/jruby/util/SecurityHelper.java
 */
public class UnlimitedStrengthEnabler {

    public static void enable() {
        try {
            if (Cipher.getMaxAllowedKeyLength("AES") < 256) {
                Class jceSecurity = Class.forName("javax.crypto.JceSecurity");
                Field isRestricted = jceSecurity.getDeclaredField("isRestricted");
                if (Modifier.isFinal(isRestricted.getModifiers())) {
                    Field modifiers = Field.class.getDeclaredField("modifiers");
                    modifiers.setAccessible(true);
                    modifiers.setInt(isRestricted, isRestricted.getModifiers() & ~Modifier.FINAL);
                    modifiers.setAccessible(false);
                }
                isRestricted.setAccessible(true);
                isRestricted.setBoolean(null, false);
                isRestricted.setAccessible(false);
            }
        } catch (IllegalAccessException | ClassNotFoundException | IllegalArgumentException | NoSuchAlgorithmException
            | NoSuchFieldException | SecurityException ex) {
            System.out.println("It is not possible to use unrestricted policy with this JDK, "
                + "consider reconfiguration: " + ex.getLocalizedMessage());
        }
    }

    private UnlimitedStrengthEnabler() {
    }
}
