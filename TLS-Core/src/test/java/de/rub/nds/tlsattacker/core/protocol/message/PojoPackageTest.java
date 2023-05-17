/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.message;

import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Test;

public class PojoPackageTest {

    // The package to be tested
    private final String packageName = "de.rub.nds.tlsattacker.core.protocol.message";

    @Test
    public void validate() {
        Validator validator = ValidatorBuilder.create().with(new SetterTester(), new GetterTester()).build();
        validator.validate(packageName);
    }
}
