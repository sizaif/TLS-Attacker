/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.serializer.extension.alpn;

import de.rub.nds.tlsattacker.core.constants.ExtensionByteLength;
import de.rub.nds.tlsattacker.core.protocol.message.extension.alpn.AlpnEntry;
import de.rub.nds.tlsattacker.core.protocol.Serializer;
import java.nio.charset.StandardCharsets;

public class AlpnEntrySerializer extends Serializer<AlpnEntry> {

    private final AlpnEntry entry;

    public AlpnEntrySerializer(AlpnEntry entry) {
        this.entry = entry;
    }

    @Override
    protected byte[] serializeBytes() {
        appendInt(entry.getAlpnEntryLength().getValue(), ExtensionByteLength.ALPN_ENTRY_LENGTH);
        appendBytes(entry.getAlpnEntry().getValue().getBytes(StandardCharsets.ISO_8859_1));
        return getAlreadySerialized();
    }

}
