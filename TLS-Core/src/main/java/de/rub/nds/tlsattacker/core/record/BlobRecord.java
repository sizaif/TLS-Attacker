/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.record;

import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ProtocolMessageType;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.record.compressor.RecordCompressor;
import de.rub.nds.tlsattacker.core.record.crypto.Encryptor;
import de.rub.nds.tlsattacker.core.record.parser.AbstractRecordParser;
import de.rub.nds.tlsattacker.core.record.parser.BlobRecordParser;
import de.rub.nds.tlsattacker.core.record.preparator.AbstractRecordPreparator;
import de.rub.nds.tlsattacker.core.record.preparator.BlobRecordPreparator;
import de.rub.nds.tlsattacker.core.record.serializer.AbstractRecordSerializer;
import de.rub.nds.tlsattacker.core.record.serializer.BlobRecordSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;

/**
 * A Blob Record is not a record in a conventional sense but is rather a non existing record and represents just a
 * collection of bytes. Is used for unparseable Records and for SSLv2
 */
public class BlobRecord extends AbstractRecord {

    private RecordCryptoComputations computations;

    public BlobRecord() {
    }

    public BlobRecord(Config config) {
        super(config);
    }

    public BlobRecord(Integer maxRecordLengthConfig) {
        super(maxRecordLengthConfig);
    }

    @Override
    public BlobRecordPreparator getRecordPreparator(Chooser chooser, Encryptor encryptor, RecordCompressor compressor,
        ProtocolMessageType type) {
        return new BlobRecordPreparator(chooser, this, encryptor, type, compressor);
    }

    @Override
    public BlobRecordParser getRecordParser(int startposition, byte[] array, ProtocolVersion version) {
        return new BlobRecordParser(startposition, array, version);
    }

    @Override
    public BlobRecordSerializer getRecordSerializer() {
        return new BlobRecordSerializer(this);
    }

    @Override
    public void adjustContext(TlsContext context) {
        // do nothing
    }

    public RecordCryptoComputations getComputations() {
        return computations;
    }

    public void setComputations(RecordCryptoComputations computations) {
        this.computations = computations;
    }

    @Override
    public void prepareComputations() {
        computations = new RecordCryptoComputations();
    }

    @Override
    public String toString() {
        return "BlobRecord{" + "computations=" + computations + '}';
    }
}
