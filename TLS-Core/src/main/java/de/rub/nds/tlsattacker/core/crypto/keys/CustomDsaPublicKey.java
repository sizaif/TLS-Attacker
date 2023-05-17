/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.crypto.keys;

import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import de.rub.nds.tlsattacker.transport.ConnectionEndType;
import java.math.BigInteger;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAParameterSpec;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@XmlAccessorType(XmlAccessType.FIELD)
public class CustomDsaPublicKey extends CustomPublicKey implements DSAPublicKey {

    private static final Logger LOGGER = LogManager.getLogger();

    private BigInteger dsaP;
    private BigInteger dsaQ;
    private BigInteger dsaG;

    private BigInteger publicKey;

    public CustomDsaPublicKey(BigInteger dsaP, BigInteger dsaQ, BigInteger dsaG, BigInteger publicKey) {
        this.dsaP = dsaP;
        this.dsaQ = dsaQ;
        this.dsaG = dsaG;
        this.publicKey = publicKey;
    }

    private CustomDsaPublicKey() {
        dsaP = null;
        dsaQ = null;
        dsaG = null;
        publicKey = null;
    }

    @Override
    public void adjustInContext(TlsContext context, ConnectionEndType ownerOfKey) {
        LOGGER.debug("Adjusting DSA public key in context");
        if (null == ownerOfKey) {
            throw new IllegalArgumentException("Owner of Key " + ownerOfKey + " is not supported");
        } else {
            switch (ownerOfKey) {
                case CLIENT:
                    context.setClientDsaGenerator(dsaG);
                    context.setClientDsaPrimeP(dsaP);
                    context.setClientDsaPrimeQ(dsaQ);
                    context.setClientDsaPublicKey(publicKey);
                    break;
                case SERVER:
                    context.setServerDsaGenerator(dsaG);
                    context.setServerDsaPrimeP(dsaP);
                    context.setServerDsaPrimeQ(dsaQ);
                    context.setServerDsaPublicKey(publicKey);
                    break;
                default:
                    throw new IllegalArgumentException("Owner of Key " + ownerOfKey + " is not supported");
            }
        }
    }

    public BigInteger getDsaP() {
        return dsaP;
    }

    public BigInteger getDsaQ() {
        return dsaQ;
    }

    public BigInteger getDsaG() {
        return dsaG;
    }

    @Override
    public BigInteger getY() {
        return publicKey;
    }

    @Override
    public DSAParams getParams() {
        return new DSAParameterSpec(dsaP, dsaQ, dsaG);
    }

    @Override
    public String getAlgorithm() {
        return "DSA";
    }

    @Override
    public String getFormat() {
        return "None";
    }

    @Override
    public byte[] getEncoded() {
        throw new UnsupportedOperationException("Not supported yet."); // To
    }

    @Override
    public void adjustInConfig(Config config, ConnectionEndType ownerOfKey) {
        if (null == ownerOfKey) {
            throw new IllegalArgumentException("Owner of Key " + ownerOfKey + " is not supported");
        } else {
            switch (ownerOfKey) {
                case CLIENT:
                    config.setDefaultClientDsaGenerator(dsaG);
                    config.setDefaultClientDsaPrimeP(dsaP);
                    config.setDefaultClientDsaPrimeQ(dsaQ);
                    config.setDefaultClientDsaPublicKey(publicKey);
                    break;
                case SERVER:
                    config.setDefaultServerDsaGenerator(dsaG);
                    config.setDefaultServerDsaPrimeP(dsaP);
                    config.setDefaultServerDsaPrimeQ(dsaQ);
                    config.setDefaultServerDsaPublicKey(publicKey);
                    break;
                default:
                    throw new IllegalArgumentException("Owner of Key " + ownerOfKey + " is not supported");
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.dsaP);
        hash = 59 * hash + Objects.hashCode(this.dsaQ);
        hash = 59 * hash + Objects.hashCode(this.dsaG);
        hash = 59 * hash + Objects.hashCode(this.publicKey);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CustomDsaPublicKey other = (CustomDsaPublicKey) obj;
        if (!Objects.equals(this.dsaP, other.dsaP)) {
            return false;
        }
        if (!Objects.equals(this.dsaQ, other.dsaQ)) {
            return false;
        }
        if (!Objects.equals(this.dsaG, other.dsaG)) {
            return false;
        }
        return Objects.equals(this.publicKey, other.publicKey);
    }

    public void setDsaP(BigInteger dsaP) {
        this.dsaP = dsaP;
    }

    public void setDsaQ(BigInteger dsaQ) {
        this.dsaQ = dsaQ;
    }

    public void setDsaG(BigInteger dsaG) {
        this.dsaG = dsaG;
    }

    public void setPublicKey(BigInteger publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public int keySize() {
        return dsaP.bitLength();
    }
}
