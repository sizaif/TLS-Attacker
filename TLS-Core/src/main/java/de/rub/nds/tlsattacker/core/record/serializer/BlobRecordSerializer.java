/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.record.serializer;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.record.BlobRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlobRecordSerializer extends AbstractRecordSerializer<BlobRecord> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final BlobRecord record;

    public BlobRecordSerializer(BlobRecord record) {
        this.record = record;
    }

    @Override
    protected byte[] serializeBytes() {
        LOGGER.debug("Serializing BlobRecord");
        writeProtocolMessageBytes(record);
        return getAlreadySerialized();
    }

    private void writeProtocolMessageBytes(BlobRecord record) {
        appendBytes(record.getProtocolMessageBytes().getValue());
        LOGGER.debug(
            "ProtocolMessageBytes: " + ArrayConverter.bytesToHexString(record.getProtocolMessageBytes().getValue()));
    }
}
