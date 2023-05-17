/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.transport.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ClientTcpNoDelayTransportHandlerTest {

    private final static Logger LOGGER = LogManager.getLogger();

    private ClientTcpNoDelayTransportHandler handler;

    @Before
    public void setUp() {
    }

    @Test
    public void testInitialize() throws IOException {
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(0));
            serverSocketChannel.configureBlocking(false);
            handler =
                new ClientTcpNoDelayTransportHandler(0, 0, "localhost", serverSocketChannel.socket().getLocalPort());
            handler.initialize();
            SocketChannel acceptChannel = serverSocketChannel.accept();
            assertNotNull(acceptChannel);
            assertTrue(handler.isInitialized());
        } finally {
            if (serverSocketChannel != null) {
                try {
                    serverSocketChannel.close();
                } catch (IOException ex) {
                    LOGGER.warn(ex);
                }
            }
        }
    }

}
