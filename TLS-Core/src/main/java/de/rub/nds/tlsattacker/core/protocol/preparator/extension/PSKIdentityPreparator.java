/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.ExtensionByteLength;
import de.rub.nds.tlsattacker.core.protocol.message.extension.psk.PSKIdentity;
import de.rub.nds.tlsattacker.core.protocol.Preparator;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import de.rub.nds.tlsattacker.util.TimeHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PSKIdentityPreparator extends Preparator<PSKIdentity> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final PSKIdentity pskIdentity;

    public PSKIdentityPreparator(Chooser chooser, PSKIdentity pskIdentity) {
        super(chooser, pskIdentity);
        this.pskIdentity = pskIdentity;
    }

    @Override
    public void prepare() {
        LOGGER.debug("Preparing PSK identity");
        prepareIdentity();
        prepareObfuscatedTicketAge();
    }

    private void prepareIdentity() {
        pskIdentity.setIdentity(pskIdentity.getIdentityConfig());
        pskIdentity.setIdentityLength(pskIdentity.getIdentity().getValue().length);
    }

    private void prepareObfuscatedTicketAge() {
        pskIdentity.setObfuscatedTicketAge(
            getObfuscatedTicketAge(pskIdentity.getTicketAgeAddConfig(), pskIdentity.getTicketAgeConfig()));
    }

    private byte[] getObfuscatedTicketAge(byte[] ticketAgeAdd, String ticketAge) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            LocalDateTime ticketDate = LocalDateTime.parse(ticketAge, dateTimeFormatter);
            BigInteger difference = BigInteger.valueOf(Duration.between(ticketDate, LocalDateTime.now()).toMillis());
            BigInteger addValue = BigInteger.valueOf(ArrayConverter.bytesToLong(ticketAgeAdd));
            BigInteger mod = BigInteger.valueOf(2).pow(32);
            difference = difference.add(addValue);
            difference = difference.mod(mod);
            byte[] obfTicketAge =
                ArrayConverter.longToBytes(difference.longValue(), ExtensionByteLength.TICKET_AGE_LENGTH);

            LOGGER.debug("Calculated ObfuscatedTicketAge: " + ArrayConverter.bytesToHexString(obfTicketAge));
            return obfTicketAge;
        } catch (Exception e) {
            LOGGER.warn("Could not parse ticketAge: " + ticketAge + " - Using empty obfuscated ticket age instead", e);
            return new byte[0];
        }
    }

}
