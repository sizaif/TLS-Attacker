/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.record.layer;

import de.rub.nds.tlsattacker.core.state.TlsContext;

public class RecordLayerFactory {

    public static RecordLayer getRecordLayer(RecordLayerType type, TlsContext context) {
        switch (type) {
            case BLOB:
                return new BlobRecordLayer(context);
            case RECORD:
                return new TlsRecordLayer(context);
            default:
                throw new UnsupportedOperationException("RecordLayerType: " + type.name() + " not supported!");
        }
    }

    private RecordLayerFactory() {
    }
}
