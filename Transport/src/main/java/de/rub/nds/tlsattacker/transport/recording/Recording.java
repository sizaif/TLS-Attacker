/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.transport.recording;

import java.util.LinkedList;
import java.util.List;

public class Recording {

    private final List<RecordedLine> receivedLines;

    private final List<RecordedLine> sentLines;

    /**
     * The Seed of the Random
     */
    private final int seed;

    public Recording(int seed) {
        this.receivedLines = new LinkedList<>();
        this.sentLines = new LinkedList<>();
        this.seed = seed;
    }

    public void addReceivedLine(RecordedLine line) {
        receivedLines.add(line);
    }

    public void addSentLine(RecordedLine line) {
        sentLines.add(line);
    }

    public List<RecordedLine> getReceivedLines() {
        return receivedLines;
    }

    public List<RecordedLine> getSentLines() {
        return sentLines;
    }

    public PlayBackTransportHandler getPlayBackHandler() {
        return new PlayBackTransportHandler(this);
    }

    public int getSeed() {
        return seed;
    }
}
