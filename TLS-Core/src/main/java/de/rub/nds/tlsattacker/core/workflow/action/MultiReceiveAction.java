/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.workflow.action;

import de.rub.nds.tlsattacker.core.protocol.message.AlertMessage;
import de.rub.nds.tlsattacker.core.protocol.ProtocolMessage;
import de.rub.nds.tlsattacker.core.state.State;
import de.rub.nds.tlsattacker.core.workflow.action.executor.ActionOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This action allows the declaration of multiple actions, the right one will selected at runtime. The usage of two
 * actions with the same Messages is forbidden.
 */
@XmlRootElement
public class MultiReceiveAction extends GenericReceiveAction {

    private List<ReceiveAction> expectedActionCandidates;
    @XmlTransient
    private ReceiveAction selectedAction;

    public MultiReceiveAction() {
        super.messages = null;
        super.records = null;
    }

    public MultiReceiveAction(ReceiveAction... receiveActions) {
        this.expectedActionCandidates = Arrays.asList(receiveActions);
        super.messages = null;
        super.records = null;
    }

    @Override
    public boolean executedAsPlanned() {
        return selectedAction.executedAsPlanned();
    }

    public ReceiveAction getSelectedAction() {
        return selectedAction;
    }

    @Override
    public void execute(State state) {
        super.execute(state);
        selectedAction = new ReceiveAction();
        for (ReceiveAction receiveAction : expectedActionCandidates) {
            if (compareExpectedActionsWithReceivedActions2(receiveAction)) {
                selectedAction = receiveAction;
                break;
            }
        }
        selectedAction.setReceivedMessages(super.getReceivedMessages());
        selectedAction.setReceivedRecords(super.getReceivedRecords());
        selectedAction.setExecuted(super.isExecuted());
    }

    public List<ReceiveAction> getExpectedActionCandidates() {
        return expectedActionCandidates;
    }

    private boolean compareExpectedActionsWithReceivedActions(ReceiveAction actionCandidate) {
        List<ProtocolMessage> expectedMessagesCandidate = actionCandidate.getExpectedMessages();
        if (expectedMessagesCandidate.size() != super.getReceivedMessages().size()) {
            return false;
        }
        for (int i = 0; i < expectedMessagesCandidate.size(); i++) {
            ProtocolMessage expectedMessageCandidate = expectedMessagesCandidate.get(i);
            ProtocolMessage receivedMessage = getReceivedMessages().get(i);
            if (expectedMessageCandidate.getClass().equals(receivedMessage.getClass())) {
                return false;
                // could contain different AlertMessages
            } else if (expectedMessageCandidate instanceof AlertMessage && receivedMessage instanceof AlertMessage) {
                return Objects.equals(expectedMessageCandidate, receivedMessage);
            }
        }
        return true;
    }

    private boolean compareExpectedActionsWithReceivedActions2(ReceiveAction actionCandidate) {
        actionCandidate.setReceivedMessages(super.getReceivedMessages());
        return actionCandidate.executedAsPlanned();
    }
}
