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
import de.rub.nds.tlsattacker.core.workflow.factory.WorkflowTraceType;

public class WorkflowTypeDelegate extends Delegate {

    @Parameter(names = "-workflow_trace_type", description = "Type of the workflow trace")
    private WorkflowTraceType workflowTraceType = null;

    public WorkflowTypeDelegate() {
    }

    public WorkflowTraceType getWorkflowTraceType() {
        return workflowTraceType;
    }

    public void setWorkflowTraceType(WorkflowTraceType workflowTraceType) {
        this.workflowTraceType = workflowTraceType;
    }

    @Override
    public void applyDelegate(Config config) {
        if (workflowTraceType != null) {
            config.setWorkflowTraceType(workflowTraceType);
        }
    }
}
