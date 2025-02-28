/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.PskDheServerKeyExchangeMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.PskDheServerKeyExchangeParserTest;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PskDheServerKeyExchangeSerializerTest {

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return PskDheServerKeyExchangeParserTest.generateData();
    }

    private final byte[] expectedPart;

    private HandshakeMessageType type;
    private int length;
    private int PskIdentityHintLength;
    private byte[] PskIdentityHint;
    private ProtocolVersion version;

    public PskDheServerKeyExchangeSerializerTest(byte[] message, HandshakeMessageType type, int length,
        int PskIdentityHintLength, byte[] PskIdentityHint, ProtocolVersion version) {
        this.expectedPart = message;
        this.type = type;
        this.length = length;
        this.version = version;
        this.PskIdentityHintLength = PskIdentityHintLength;
        this.PskIdentityHint = PskIdentityHint;
    }

    /**
     * Test of serializeHandshakeMessageContent method, of class PskDheServerKeyExchangeSerializer.
     */
    @Test
    public void testSerializeHandshakeMessageContent() {
        PskDheServerKeyExchangeMessage msg = new PskDheServerKeyExchangeMessage();
        msg.setCompleteResultingMessage(expectedPart);
        msg.setType(type.getValue());
        msg.setLength(length);
        msg.setIdentityHint(PskIdentityHint);
        msg.setIdentityHintLength(PskIdentityHintLength);
        msg.setModulusLength(128);
        msg.setModulus(ArrayConverter.hexStringToByteArray(
            "b10b8f96a080e01dde92de5eae5d54ec52c99fbcfb06a3c69a6a9dca52d23b616073e28675a23d189838ef1e2ee652c013ecb4aea906112324975c3cd49b83bfaccbdd7d90c4bd7098488e9c219a73724effd6fae5644738faa31a4ff55bccc0a151af5f0dc8b4bd45bf37df365c1a65e68cfda76d4da708df1fb2bc2e4a4371"));
        msg.setGeneratorLength(128);
        msg.setGenerator(ArrayConverter.hexStringToByteArray(
            "a4d1cbd5c3fd34126765a442efb99905f8104dd258ac507fd6406cff14266d31266fea1e5c41564b777e690f5504f213160217b4b01b886a5e91547f9e2749f4d7fbd7d3b9a92ee1909d0d2263f80a76a6a24c087a091f531dbf0a0169b6a28ad662a4d18e73afa32d779d5918d08bc8858f4dcef97c2a24855e6eeb22b3b2e5"));
        msg.setPublicKey(ArrayConverter.hexStringToByteArray(
            "70dd13c4bca8c96983bdf065ce9517eb44114a4cf4cdbc55b3bfdabde8510faa38142139409378b90e3ceba61167056fc8b0ee088132183e48b986ed468eeaaf435c9dea3a5d9c01b63a3aae176971a2e1142674675dedca8a8e91093cd42246b4fa37893d7e66534a59461935274955e5dc623c9897a6c4a8501f37427e079d"));
        msg.setPublicKeyLength(128);
        PskDheServerKeyExchangeSerializer serializer = new PskDheServerKeyExchangeSerializer(msg, version);
        assertArrayEquals(expectedPart, serializer.serialize());

    }

}
