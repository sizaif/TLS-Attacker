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
import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.PskEcDhClientKeyExchangeMessage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PskEcDhClientKeyExchangeParserTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][] { { ArrayConverter.hexStringToByteArray(
            "10000032000f436c69656e745f6964656e7469747920f73171f4379e1897f443a82bcc06d79368f96aad699f10d21505c661fe80655b"),
            HandshakeMessageType.CLIENT_KEY_EXCHANGE, 50, ProtocolVersion.TLS12 },
            { ArrayConverter.hexStringToByteArray(
                "10000032000f436c69656e745f6964656e746974792073f7cf3676cef0cf08b800519732540c8a16062aa5e24fc2360007c265b83f1b"),
                HandshakeMessageType.CLIENT_KEY_EXCHANGE, 50, ProtocolVersion.TLS12 } });
    }

    private final byte[] message;
    private final HandshakeMessageType type;
    private final int length;
    private final ProtocolVersion version;
    private final Config config = Config.createConfig();

    public PskEcDhClientKeyExchangeParserTest(byte[] message, HandshakeMessageType type, int length,
        ProtocolVersion version) {
        this.message = message;
        this.type = type;
        this.length = length;
        this.version = version;
    }

    @Test
    public void testParse() {
        PskEcDhClientKeyExchangeParser parser = new PskEcDhClientKeyExchangeParser(0, message, version, config);
        PskEcDhClientKeyExchangeMessage msg = parser.parse();
        assertArrayEquals(message, msg.getCompleteResultingMessage().getValue());
        assertEquals(type.getValue(), msg.getType().getValue().byteValue());
        assertEquals(length, msg.getLength().getValue().intValue());
    }

}
