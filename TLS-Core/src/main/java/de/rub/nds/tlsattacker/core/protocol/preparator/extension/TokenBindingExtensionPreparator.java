/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.tlsattacker.core.constants.TokenBindingKeyParameters;
import de.rub.nds.tlsattacker.core.protocol.message.extension.TokenBindingExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.TokenBindingExtensionSerializer;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import java.io.ByteArrayOutputStream;

public class TokenBindingExtensionPreparator extends ExtensionPreparator<TokenBindingExtensionMessage> {

    private final TokenBindingExtensionMessage message;

    public TokenBindingExtensionPreparator(Chooser chooser, TokenBindingExtensionMessage message,
        TokenBindingExtensionSerializer serializer) {
        super(chooser, message, serializer);
        this.message = message;
    }

    @Override
    public void prepareExtensionContent() {

        message.setTokenbindingVersion(chooser.getConfig().getDefaultTokenBindingVersion().getByteValue());
        ByteArrayOutputStream tokenbindingKeyParameters = new ByteArrayOutputStream();
        for (TokenBindingKeyParameters kp : chooser.getConfig().getDefaultTokenBindingKeyParameters()) {
            tokenbindingKeyParameters.write(kp.getValue());
        }
        message.setTokenbindingKeyParameters(tokenbindingKeyParameters.toByteArray());
        message.setParameterListLength(message.getTokenbindingKeyParameters().getValue().length);
    }

}
