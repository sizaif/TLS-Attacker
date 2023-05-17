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
import de.rub.nds.tlsattacker.core.protocol.message.extension.ExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.Preparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.ExtensionSerializer;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @param <T>
 *            The ExtensionMessage that should be prepared
 */
public abstract class ExtensionPreparator<T extends ExtensionMessage> extends Preparator<T> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final ExtensionMessage msg;
    private byte[] content;
    private final ExtensionSerializer<T> serializer;

    public ExtensionPreparator(Chooser chooser, T message, ExtensionSerializer<T> serializer) {
        super(chooser, message);
        this.msg = message;
        this.serializer = serializer;
    }

    @Override
    public final void prepare() {
        prepareExtensionType(msg);
        prepareExtensionContent();
        content = serializer.serializeExtensionContent();
        prepareExtensionLength(msg);
        prepareExtensionBytes(msg);
    }

    @Override
    public final void afterPrepare() {
        prepareExtensionType(msg);
        afterPrepareExtensionContent();
        content = serializer.serializeExtensionContent();
        prepareExtensionLength(msg);
        prepareExtensionBytes(msg);
    }

    public abstract void prepareExtensionContent();

    public void afterPrepareExtensionContent() {

    }

    private void prepareExtensionType(ExtensionMessage msg) {
        msg.setExtensionType(msg.getExtensionTypeConstant().getValue());
        LOGGER.debug("ExtensionType: " + ArrayConverter.bytesToHexString(msg.getExtensionType().getValue()));
    }

    private void prepareExtensionLength(ExtensionMessage msg) {
        msg.setExtensionLength(content.length);
        LOGGER.debug("ExtensionLength: " + msg.getExtensionLength().getValue());
    }

    private void prepareExtensionBytes(ExtensionMessage msg) {
        msg.setExtensionBytes(serializer.serialize());
        LOGGER.debug("ExtensionBytes: " + ArrayConverter.bytesToHexString(msg.getExtensionBytes().getValue()));
    }

}
