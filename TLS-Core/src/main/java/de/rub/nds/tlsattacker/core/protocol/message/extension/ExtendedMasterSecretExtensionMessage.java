/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.message.extension;

import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is the extended_master_secret message.
 *
 * There is no need for any data, the presence of this extension is enough.
 *
 * This extension is defined in RFC7627
 */
@XmlRootElement(name = "ExtendedMasterSecretExtension")
public class ExtendedMasterSecretExtensionMessage extends ExtensionMessage {

    public ExtendedMasterSecretExtensionMessage() {
        super(ExtensionType.EXTENDED_MASTER_SECRET);
    }

    public ExtendedMasterSecretExtensionMessage(Config config) {
        super(ExtensionType.EXTENDED_MASTER_SECRET);
    }
}
