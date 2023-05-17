/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.workflow.action;

import de.rub.nds.tlsattacker.core.record.cipher.RecordCipher;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActivateEncryptionAction extends ActivateCryptoAction {

    @Override
    protected void activateCrypto(TlsContext tlsContext, RecordCipher recordCipher) {
        LOGGER.info("Setting new encryption cipher and activating encryption");
        tlsContext.getRecordLayer().updateEncryptionCipher(recordCipher);
    }

}
