/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.config.delegate;

import com.beust.jcommander.Parameter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.SignatureAndHashAlgorithm;
import java.util.Collections;
import java.util.List;

public class SignatureAndHashAlgorithmDelegate extends Delegate {

    @Parameter(names = "-signature_hash_algo",
        description = "Supported Signature and Hash Algorithms separated by comma eg. RSA-SHA512,DSA-SHA512")
    private List<SignatureAndHashAlgorithm> signatureAndHashAlgorithms = null;

    public SignatureAndHashAlgorithmDelegate() {
    }

    public List<SignatureAndHashAlgorithm> getSignatureAndHashAlgorithms() {
        if (signatureAndHashAlgorithms == null) {
            return null;
        }
        return Collections.unmodifiableList(signatureAndHashAlgorithms);
    }

    public void setSignatureAndHashAlgorithms(List<SignatureAndHashAlgorithm> signatureAndHashAlgorithms) {
        this.signatureAndHashAlgorithms = signatureAndHashAlgorithms;
    }

    @Override
    public void applyDelegate(Config config) {
        if (signatureAndHashAlgorithms != null) {
            config.setAddSignatureAndHashAlgorithmsExtension(true);
            config.setDefaultClientSupportedSignatureAndHashAlgorithms(signatureAndHashAlgorithms);
            config.setDefaultServerSupportedSignatureAndHashAlgorithms(signatureAndHashAlgorithms);
            config.setDefaultSelectedSignatureAndHashAlgorithm(signatureAndHashAlgorithms.get(0));
        }
    }
}
