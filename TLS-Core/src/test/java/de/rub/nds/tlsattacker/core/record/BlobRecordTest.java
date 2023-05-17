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
import de.rub.nds.tlsattacker.core.record.cipher.RecordCipherFactory;
import de.rub.nds.tlsattacker.core.record.compressor.RecordCompressor;
import de.rub.nds.tlsattacker.core.record.crypto.Encryptor;
import de.rub.nds.tlsattacker.core.record.crypto.RecordEncryptor;
import de.rub.nds.tlsattacker.core.record.parser.BlobRecordParser;
import de.rub.nds.tlsattacker.core.record.preparator.BlobRecordPreparator;
import de.rub.nds.tlsattacker.core.record.serializer.BlobRecordSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class BlobRecordTest {

    private BlobRecord record;
    private Chooser chooser;
    private Encryptor encryptor;
    private RecordCompressor compressor;

    @Before
    public void setUp() {
        Config config = Config.createConfig();
        record = new BlobRecord(config);
        TlsContext ctx = new TlsContext(config);
        chooser = ctx.getChooser();
        encryptor = new RecordEncryptor(RecordCipherFactory.getNullCipher(ctx), ctx);
        compressor = new RecordCompressor(ctx);

    }

    /**
     * Test of getRecordPreparator method, of class BlobRecord.
     */
    @Test
    public void testGetRecordPreparator() {
        assertEquals(record.getRecordPreparator(chooser, encryptor, compressor, ProtocolMessageType.ALERT).getClass(),
            BlobRecordPreparator.class);
    }

    /**
     * Test of getRecordParser method, of class BlobRecord.
     */
    @Test
    public void testGetRecordParser() {
        assertEquals(record.getRecordParser(0, new byte[0], ProtocolVersion.TLS10).getClass(), BlobRecordParser.class);
        assertEquals(record.getRecordParser(0, new byte[0], ProtocolVersion.TLS11).getClass(), BlobRecordParser.class);
        assertEquals(record.getRecordParser(0, new byte[0], ProtocolVersion.TLS12).getClass(), BlobRecordParser.class);
        assertEquals(record.getRecordParser(0, new byte[0], ProtocolVersion.TLS13).getClass(), BlobRecordParser.class);
    }

    /**
     * Test of getRecordSerializer method, of class BlobRecord.
     */
    @Test
    public void testGetRecordSerializer() {
        assertEquals(record.getRecordSerializer().getClass(), BlobRecordSerializer.class);
    }

    /**
     * Test of adjustContext method, of class BlobRecord.
     */
    @Test
    public void testAdjustContext() {
    }

}
