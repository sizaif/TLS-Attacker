/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler.extension;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.SrtpProtectionProfiles;
import de.rub.nds.tlsattacker.core.protocol.message.extension.SrtpExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.SrtpExtensionParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.extension.SrtpExtensionPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.SrtpExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SrtpExtensionHandler extends ExtensionHandler<SrtpExtensionMessage> {

    private static final Logger LOGGER = LogManager.getLogger();

    public SrtpExtensionHandler(TlsContext context) {
        super(context);
    }

    @Override
    public SrtpExtensionParser getParser(byte[] message, int pointer, Config config) {
        return new SrtpExtensionParser(pointer, message, config);
    }

    @Override
    public SrtpExtensionPreparator getPreparator(SrtpExtensionMessage message) {
        return new SrtpExtensionPreparator(context.getChooser(), message, getSerializer(message));
    }

    @Override
    public SrtpExtensionSerializer getSerializer(SrtpExtensionMessage message) {
        return new SrtpExtensionSerializer(message);
    }

    @Override
    public void adjustTLSExtensionContext(SrtpExtensionMessage message) {
        context.setSecureRealTimeTransportProtocolProtectionProfiles(
            SrtpProtectionProfiles.getProfilesAsArrayList(message.getSrtpProtectionProfiles().getValue()));
        LOGGER.debug("Adjusted the TLS context secure realtime transport protocol protection profiles to "
            + ArrayConverter.bytesToHexString(message.getSrtpProtectionProfiles()));
        context.setSecureRealTimeProtocolMasterKeyIdentifier(message.getSrtpMki().getValue());
        LOGGER.debug("Adjusted the TLS context secure realtime transport protocol master key identifier to "
            + ArrayConverter.bytesToHexString(message.getSrtpMki()));
    }
}
