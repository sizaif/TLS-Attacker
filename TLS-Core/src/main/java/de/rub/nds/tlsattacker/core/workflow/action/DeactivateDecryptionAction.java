/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.workflow.action;

import de.rub.nds.tlsattacker.core.record.cipher.RecordCipherFactory;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import static de.rub.nds.tlsattacker.core.workflow.action.DeactivateCryptoAction.LOGGER;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeactivateDecryptionAction extends DeactivateCryptoAction {

    @Override
    protected void deactivateCrypto(TlsContext tlsContext) {
        LOGGER.info("Disabling decryption");
        tlsContext.getRecordLayer().updateDecryptionCipher(RecordCipherFactory.getNullCipher(tlsContext));
    }
}
