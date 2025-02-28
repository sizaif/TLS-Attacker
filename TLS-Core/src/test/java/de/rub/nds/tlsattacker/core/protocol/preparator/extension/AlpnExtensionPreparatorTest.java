/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import de.rub.nds.tlsattacker.core.protocol.message.extension.AlpnExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.AlpnExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class AlpnExtensionPreparatorTest {

    private TlsContext context;
    private AlpnExtensionPreparator preparator;
    private AlpnExtensionMessage msg;
    private final String announcedProtocols = "h2";
    private final byte proposedAlpnProtocolLength = 2;
    private final int ALPNExtensionLength = 3;
    private byte[] protocolsWithLength;

    @Before
    public void setUp() {
        context = new TlsContext();
        msg = new AlpnExtensionMessage(Config.createConfig());
        preparator = new AlpnExtensionPreparator(context.getChooser(), msg, new AlpnExtensionSerializer(msg));
        protocolsWithLength =
            ArrayConverter.concatenate(new byte[] { proposedAlpnProtocolLength }, announcedProtocols.getBytes());
    }

    @Test
    public void testPreparator() {

        context.getConfig().setDefaultProposedAlpnProtocols(new String[] { announcedProtocols });

        preparator.prepare();

        assertArrayEquals(ExtensionType.ALPN.getValue(), msg.getExtensionType().getValue());
        assertEquals(ALPNExtensionLength, (long) msg.getProposedAlpnProtocolsLength().getValue());
        assertArrayEquals(protocolsWithLength, msg.getProposedAlpnProtocols().getValue());
    }

}
