/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.crypto.keys;

import de.rub.nds.tlsattacker.core.constants.NamedGroup;
import java.math.BigInteger;
import java.security.Security;
import java.security.spec.ECParameterSpec;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import static org.junit.Assert.assertNotNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CustomECPrivateKeyTest {

    @Parameters
    public static Iterable<Object[]> createParameters() {
        List<Object[]> testValues = new ArrayList<>();
        for (NamedGroup curve : NamedGroup.getImplemented()) {
            if (curve.isStandardCurve() && !curve.name().contains("BRAIN")) {
                // TODO do not skip brainpool once asn1 tool is integrated
                testValues.add(new Object[] { curve });

            }
        }
        return testValues;
    }

    @Parameter(0)
    public NamedGroup curve;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    /**
     * Test of getParams method, of class CustomECPrivateKey.
     */
    @Test
    public void testGetParams() {
        Security.addProvider(new BouncyCastleProvider());
        CustomECPrivateKey key = new CustomECPrivateKey(BigInteger.TEN, curve);
        ECParameterSpec params = key.getParams();
        assertNotNull(params);
    }
}
