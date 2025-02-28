/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.parser.cert;

import de.rub.nds.tlsattacker.core.protocol.Parser;
import de.rub.nds.tlsattacker.core.record.AbstractRecord;
import java.util.List;

/**
 * //TODO I am not sure if this implementation is so smart since it extends Parser which is designed for Message objects
 * and is called ByteSeperator //I Think another logical abstraction is needed here
 */
public class CleanRecordByteSeperator extends Parser<List<AbstractRecord>> {

    private final List<AbstractRecord> records;
    private final int defaultMaxSize;

    public CleanRecordByteSeperator(List<AbstractRecord> records, int defaultMaxSize, int startposition, byte[] array) {
        super(startposition, array);
        this.records = records;
        this.defaultMaxSize = defaultMaxSize;
    }

    @Override
    public List<AbstractRecord> parse() {
        for (AbstractRecord record : records) {
            Integer maxData = record.getMaxRecordLengthConfig();
            if (maxData == null) {
                maxData = defaultMaxSize;
            }
            record.setCleanProtocolMessageBytes(parseArrayOrTillEnd(maxData));
        }
        return records;
    }

}
