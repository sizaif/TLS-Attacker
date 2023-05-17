/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.workflow.action.executor;

import de.rub.nds.tlsattacker.core.protocol.message.DtlsHandshakeMessageFragment;
import de.rub.nds.tlsattacker.core.protocol.ProtocolMessage;
import java.util.List;

public class MessageParsingResult {
    private List<ProtocolMessage> messages;
    private List<DtlsHandshakeMessageFragment> messageFragments;

    public MessageParsingResult(List<ProtocolMessage> messages, List<DtlsHandshakeMessageFragment> messageFragments) {
        super();
        this.messages = messages;
        this.messageFragments = messageFragments;
    }

    public List<ProtocolMessage> getMessages() {
        return messages;
    }

    public List<DtlsHandshakeMessageFragment> getMessageFragments() {
        return messageFragments;
    }

}
