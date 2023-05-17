/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.parser;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.ServerHelloMessage;

public class SomeTest {

    private final Config config = Config.createConfig();

    public void test() {
        ServerHelloParser parser = new ServerHelloParser(0, ArrayConverter.hexStringToByteArray(
            "020000560303efa16312095c8e65508f5d7ff45e917678ed2f1dd2a39c12d9ca55d785e12b9300130100002e002b00027f1600280024001d00206dacd29525eef4a3a3d1e3e20d9c567dbbab2bd9a01a526bb9d5afa335e94024"),
            ProtocolVersion.TLS13, config);
        ServerHelloMessage parse = parser.parse();
    }
}
