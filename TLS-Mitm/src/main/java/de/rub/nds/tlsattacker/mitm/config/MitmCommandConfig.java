/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.mitm.config;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import de.rub.nds.tlsattacker.core.config.TLSDelegateConfig;
import de.rub.nds.tlsattacker.core.config.delegate.CertificateDelegate;
import de.rub.nds.tlsattacker.core.config.delegate.CipherSuiteDelegate;
import de.rub.nds.tlsattacker.core.config.delegate.ExecutorTypeDelegate;
import de.rub.nds.tlsattacker.core.config.delegate.FilterDelegate;
import de.rub.nds.tlsattacker.core.config.delegate.GeneralDelegate;
import de.rub.nds.tlsattacker.core.config.delegate.HeartbeatDelegate;
import de.rub.nds.tlsattacker.core.config.delegate.ListDelegate;
import de.rub.nds.tlsattacker.core.config.delegate.MaxFragmentLengthDelegate;
import de.rub.nds.tlsattacker.core.config.delegate.MitmDelegate;
import de.rub.nds.tlsattacker.core.config.delegate.MitmWorkflowTypeDelegate;
import de.rub.nds.tlsattacker.core.config.delegate.NamedGroupsDelegate;
import de.rub.nds.tlsattacker.core.config.delegate.ProtocolVersionDelegate;
import de.rub.nds.tlsattacker.core.config.delegate.SignatureAndHashAlgorithmDelegate;
import de.rub.nds.tlsattacker.core.config.delegate.TimeoutDelegate;
import de.rub.nds.tlsattacker.core.config.delegate.TransportHandlerDelegate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MitmCommandConfig extends TLSDelegateConfig {

    private static final Logger LOGGER = LogManager.getLogger();

    @ParametersDelegate
    private CipherSuiteDelegate ciphersuiteDelegate;
    @ParametersDelegate
    private ProtocolVersionDelegate protocolVersionDelegate;
    @ParametersDelegate
    private NamedGroupsDelegate ellipticCurveDelegate;
    @ParametersDelegate
    private MitmDelegate mitmDelegate;
    @ParametersDelegate
    private SignatureAndHashAlgorithmDelegate signatureAndHashAlgorithmDelegate;
    @ParametersDelegate
    private MitmWorkflowTypeDelegate mitmWorkflowTypeDelegate;
    @ParametersDelegate
    private TransportHandlerDelegate transportHandlerDelegate;
    @ParametersDelegate
    private HeartbeatDelegate heartbeatDelegate;
    @ParametersDelegate
    private MaxFragmentLengthDelegate maxFragmentLengthDelegate;
    @ParametersDelegate
    private CertificateDelegate certificateDelegate;
    @ParametersDelegate
    private FilterDelegate filterDelegate;
    @ParametersDelegate
    private ListDelegate listDelegate;
    @ParametersDelegate
    private TimeoutDelegate timeoutDelegate;
    @ParametersDelegate
    private ExecutorTypeDelegate executorTypeDelegate;

    @Parameter(names = "-workflow_input", description = "A path to a workflow trace that should be exeucted")
    private String workflowInput = null;
    @Parameter(names = "-workflow_output",
        description = "A path in which the executed workflow trace should be stored in")
    private String workflowOutput = null;

    public MitmCommandConfig(GeneralDelegate delegate) {
        super(delegate);
        this.ciphersuiteDelegate = new CipherSuiteDelegate();
        this.heartbeatDelegate = new HeartbeatDelegate();
        this.ellipticCurveDelegate = new NamedGroupsDelegate();
        this.protocolVersionDelegate = new ProtocolVersionDelegate();
        this.mitmDelegate = new MitmDelegate();
        this.signatureAndHashAlgorithmDelegate = new SignatureAndHashAlgorithmDelegate();
        this.transportHandlerDelegate = new TransportHandlerDelegate();
        this.mitmWorkflowTypeDelegate = new MitmWorkflowTypeDelegate();
        this.maxFragmentLengthDelegate = new MaxFragmentLengthDelegate();
        this.certificateDelegate = new CertificateDelegate();
        this.filterDelegate = new FilterDelegate();
        this.listDelegate = new ListDelegate();
        this.timeoutDelegate = new TimeoutDelegate();
        this.executorTypeDelegate = new ExecutorTypeDelegate();

        addDelegate(maxFragmentLengthDelegate);
        addDelegate(ciphersuiteDelegate);
        addDelegate(ellipticCurveDelegate);
        addDelegate(protocolVersionDelegate);
        addDelegate(mitmDelegate);
        addDelegate(signatureAndHashAlgorithmDelegate);
        addDelegate(heartbeatDelegate);
        addDelegate(transportHandlerDelegate);
        addDelegate(certificateDelegate);
        addDelegate(mitmWorkflowTypeDelegate);
        addDelegate(filterDelegate);
        addDelegate(listDelegate);
        addDelegate(timeoutDelegate);
        addDelegate(executorTypeDelegate);
    }

    public String getWorkflowInput() {
        return workflowInput;
    }

    public String getWorkflowOutput() {
        return workflowOutput;
    }

}
