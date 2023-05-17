/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.unittest.helper;

import de.rub.nds.tlsattacker.transport.ConnectionEndType;
import de.rub.nds.tlsattacker.transport.TransportHandler;
import java.io.IOException;

public class FakeTransportHandler extends TransportHandler {
    /**
     * Data that will be returned on a fetchData() call
     */
    private byte[] fetchableByte;
    private byte[] sendByte;
    private Boolean opened = false;

    public FakeTransportHandler(ConnectionEndType type) {
        super(0, 0, type);
        fetchableByte = new byte[0];
    }

    public byte[] getSendByte() {
        return sendByte;
    }

    public byte[] getFetchableByte() {
        return fetchableByte;
    }

    public void setFetchableByte(byte[] fetchableByte) {
        this.fetchableByte = fetchableByte;
    }

    @Override
    public void closeConnection() {
        opened = false;
    }

    @Override
    public byte[] fetchData() throws IOException {
        byte[] answer = fetchableByte;
        fetchableByte = new byte[0];
        return answer;
    }

    @Override
    public void sendData(byte[] data) throws IOException {
        sendByte = data;
    }

    @Override
    public void initialize() throws IOException {
        opened = true;
    }

    @Override
    public boolean isClosed() throws IOException {
        return !opened;
    }

    @Override
    public void closeClientConnection() throws IOException {
        if (!isClosed())
            opened = false;
    }

    @Override
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public void preInitialize() throws IOException {
    }

}
