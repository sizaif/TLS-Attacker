/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.record.parser;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.ProtocolMessageType;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.record.BlobRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlobRecordParser extends AbstractRecordParser<BlobRecord> {

    private static final Logger LOGGER = LogManager.getLogger();

    public BlobRecordParser(int startposition, byte[] array, ProtocolVersion version) {
        super(startposition, array, version);
    }

    @Override
    public BlobRecord parse() {
        LOGGER.debug("Parsing BlobRecord");
        BlobRecord record = new BlobRecord();
        record.setContentMessageType(ProtocolMessageType.UNKNOWN);
        parseProtocolMessageBytes(record);
        record.setCompleteRecordBytes(getAlreadyParsed());
        return record;
    }

    private void parseProtocolMessageBytes(BlobRecord record) {
        record.setProtocolMessageBytes(parseByteArrayField(getBytesLeft()));
        LOGGER.debug(
            "ProtocolMessageBytes: " + ArrayConverter.bytesToHexString(record.getProtocolMessageBytes().getValue()));
    }
}
