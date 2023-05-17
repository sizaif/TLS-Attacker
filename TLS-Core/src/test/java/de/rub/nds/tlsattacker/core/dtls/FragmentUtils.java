/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.dtls;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.protocol.message.DtlsHandshakeMessageFragment;
import org.bouncycastle.util.Arrays;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FragmentUtils {

    public static final int DEFAULT_MESSAGE_LENGTH = 10;

    public static DtlsHandshakeMessageFragment fragment(int messageSeq, int fragmentOffset, int fragmentLength,
        byte content[], int epoch) {
        DtlsHandshakeMessageFragment fragment = new DtlsHandshakeMessageFragment();
        fragment.setFragmentOffset(fragmentOffset);
        fragment.setFragmentLength(fragmentLength);
        fragment.setMessageSeq(messageSeq);
        fragment.setContent(content);
        fragment.setLength(DEFAULT_MESSAGE_LENGTH);
        fragment.setType(HandshakeMessageType.UNKNOWN.getValue());
        fragment.setEpoch(epoch);
        return fragment;
    }

    public static DtlsHandshakeMessageFragment fragment(int messageSeq, int fragmentOffset, int fragmentLength,
        int epoch) {
        return fragment(messageSeq, fragmentOffset, fragmentLength, new byte[fragmentLength], epoch);
    }

    public static DtlsHandshakeMessageFragment fragmentOfMsg(int messageSeq, int fragmentOffset, int fragmentLength,
        byte msgContent[], int epoch) {
        byte content[] = Arrays.copyOfRange(msgContent, fragmentOffset, fragmentOffset + fragmentLength);
        return fragment(messageSeq, fragmentOffset, fragmentLength, content, epoch);
    }

    public static void checkFragment(DtlsHandshakeMessageFragment fragment, int expectedOffset, int expectedLength,
        byte[] expectedContent) {
        assertNotNull(fragment);
        assertEquals(expectedOffset, fragment.getFragmentOffset().getValue().intValue());
        assertEquals(expectedLength, fragment.getFragmentLength().getValue().intValue());
        assertArrayEquals(expectedContent, fragment.getContent().getValue());
    }
}
