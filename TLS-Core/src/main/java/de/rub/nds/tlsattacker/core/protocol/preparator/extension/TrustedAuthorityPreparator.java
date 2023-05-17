/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.tlsattacker.core.protocol.message.extension.trustedauthority.TrustedAuthority;
import de.rub.nds.tlsattacker.core.protocol.Preparator;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;

public class TrustedAuthorityPreparator extends Preparator<TrustedAuthority> {

    private final TrustedAuthority object;

    public TrustedAuthorityPreparator(Chooser chooser, TrustedAuthority object) {
        super(chooser, object);
        this.object = object;
    }

    @Override
    public void prepare() {
        object.setIdentifierType(object.getIdentifierTypeConfig());
        if (object.getSha1HashConfig() != null) {
            object.setSha1Hash(object.getSha1HashConfig());
        }
        if (object.getDistinguishedNameLengthConfig() != null) {
            object.setDistinguishedNameLength(object.getDistinguishedNameLengthConfig());
        }
        if (object.getDistinguishedNameConfig() != null) {
            object.setDistinguishedName(object.getDistinguishedNameConfig());
        }
    }

}
