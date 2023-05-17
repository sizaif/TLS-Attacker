/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.workflow.action;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Copy the PreMasterSecret from srcContext, to dstContext.
 *
 */
@XmlRootElement
public class CopyPreMasterSecretAction extends CopyContextFieldAction {

    private static final Logger LOGGER = LogManager.getLogger();

    public CopyPreMasterSecretAction() {
    }

    public CopyPreMasterSecretAction(String srcConnectionAlias, String dstConnectionAlias) {
        super(srcConnectionAlias, dstConnectionAlias);
    }

    @Override
    protected void copyField(TlsContext src, TlsContext dst) {
        dst.setPreMasterSecret(src.getPreMasterSecret());
        LOGGER.debug("Copying PreMasterSecret from " + src + " to " + dst);
        LOGGER.debug(
            "Copied PreMasterSecret is: " + ArrayConverter.bytesToHexString(dst.getPreMasterSecret(), true, true));
        setExecuted(true);
    }

    @Override
    public boolean executedAsPlanned() {
        return isExecuted();
    }

}
