/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator;

import de.rub.nds.tlsattacker.core.protocol.message.GOSTClientKeyExchangeMessage;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.GOST3411Digest;

public class GOST01ClientKeyExchangePreparator extends GOSTClientKeyExchangePreparator {

    public GOST01ClientKeyExchangePreparator(Chooser chooser, GOSTClientKeyExchangeMessage msg) {
        super(chooser, msg);
    }

    @Override
    protected Digest getKeyAgreementDigestAlgorithm() {
        return new GOST3411Digest();
    }

    @Override
    protected String getKeyPairGeneratorAlgorithm() {
        return "ECGOST3410";
    }

    @Override
    protected ASN1ObjectIdentifier getEncryptionParameters() {
        return CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_A_ParamSet;
    }
}
