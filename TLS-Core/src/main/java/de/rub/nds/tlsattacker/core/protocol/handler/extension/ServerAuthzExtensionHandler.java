/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.handler.extension;

import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.constants.AuthzDataFormat;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ServerAuthzExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.ServerAuthzExtensionParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.extension.ServerAuthzExtensionPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.ServerAuthzExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

public class ServerAuthzExtensionHandler extends ExtensionHandler<ServerAuthzExtensionMessage> {

    public ServerAuthzExtensionHandler(TlsContext context) {
        super(context);
    }

    @Override
    public ServerAuthzExtensionParser getParser(byte[] message, int pointer, Config config) {
        return new ServerAuthzExtensionParser(pointer, message, config);
    }

    @Override
    public ServerAuthzExtensionPreparator getPreparator(ServerAuthzExtensionMessage message) {
        return new ServerAuthzExtensionPreparator(context.getChooser(), message, getSerializer(message));
    }

    @Override
    public ServerAuthzExtensionSerializer getSerializer(ServerAuthzExtensionMessage message) {
        return new ServerAuthzExtensionSerializer(message);
    }

    @Override
    public void adjustTLSExtensionContext(ServerAuthzExtensionMessage message) {
        context.setServerAuthzDataFormatList(AuthzDataFormat.byteArrayToList(message.getAuthzFormatList().getValue()));
    }

}
