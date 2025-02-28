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

public class DynamicWorkflowDelegate extends Delegate {

    @Parameter(names = "-dynamic_workflow",
        description = "If this parameter is set, the workflow is only initialized with a ClientHello message (not yet implemented)")
    private Boolean dynamicWorkflow = null;

    public DynamicWorkflowDelegate() {
    }

    public Boolean isDynamicWorkflow() {
        throw new UnsupportedOperationException("DynamicWorkflow is currently not supported.");
    }

    public void setDynamicWorkflow(boolean dynamicWorkflow) {
        throw new UnsupportedOperationException("DynamicWorkflow is currently not supported.");
    }

    @Override
    public void applyDelegate(Config config) {
        throw new UnsupportedOperationException("DynamicWorkflow is currently not supported.");
    }

}
