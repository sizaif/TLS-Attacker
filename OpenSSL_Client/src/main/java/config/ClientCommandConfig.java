/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */
package config;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.config.TLSDelegateConfig;
import de.rub.nds.tlsattacker.core.config.delegate.*;
import de.rub.nds.tlsattacker.core.workflow.factory.WorkflowTraceType;

public class ClientCommandConfig extends TLSDelegateConfig {

    public static final String COMMAND = "client";

    @ParametersDelegate
    private CipherSuiteDelegate ciphersuiteDelegate;
    @ParametersDelegate
    private CompressionDelegate compressionDelegate;
    @ParametersDelegate
    private MaxFragmentLengthDelegate maxFragmentLengthDelegate;
    @ParametersDelegate
    private ProtocolVersionDelegate protocolVersionDelegate;
    @ParametersDelegate
    private NamedGroupsDelegate ellipticCurveDelegate;
    @ParametersDelegate
    private ClientDelegate clientDelegate;
    @ParametersDelegate
    private SignatureAndHashAlgorithmDelegate signatureAndHashAlgorithmDelegate;
    @ParametersDelegate
    private TransportHandlerDelegate transportHandlerDelegate;
    @ParametersDelegate
    private TimeoutDelegate timeoutDelegate;
    @ParametersDelegate
    private WorkflowTypeDelegate workflowTypeDelegate;
    @ParametersDelegate
    private HeartbeatDelegate heartbeatDelegate;
    @ParametersDelegate
    private CertificateDelegate certificateDelegate;
    @ParametersDelegate
    private FilterDelegate filterDelegate;
    @ParametersDelegate
    private ListDelegate listDelegate;
    @ParametersDelegate
    private StarttlsDelegate starttlsDelegate;

    @Parameter(names = "socket_host", description = "flag socket to fuzz's host ")
    private String socket_host = "localhost";

    @Parameter(names = "socket_port", description = "flag with socket to fuzz's port ")
    private Integer socket_port = 9998;

    @Parameter(names = "-workflow_input", description = "A path to a workflow trace that should be exeucted")
    private String workflowInput = null;
    @Parameter(names = "-workflow_output",
            description = "A path in which the executed workflow trace should be stored in")
    private String workflowOutput = null;

    @Parameter(names = "-workflow_in_files", description = "A path to workflow trace files")
    private String workflowsInFiles = null;
    @Parameter(names = "-workflow_out_files", description = "A path to execute workflow trace file to save ")
    private String workflowsOutFiles = null;

    public ClientCommandConfig(GeneralDelegate delegate) {
        super(delegate);
        delegate.setDebug(false);
        this.ciphersuiteDelegate = new CipherSuiteDelegate();
        this.maxFragmentLengthDelegate = new MaxFragmentLengthDelegate();
        this.ellipticCurveDelegate = new NamedGroupsDelegate();
        this.protocolVersionDelegate = new ProtocolVersionDelegate();
        this.clientDelegate = new ClientDelegate();
        this.signatureAndHashAlgorithmDelegate = new SignatureAndHashAlgorithmDelegate();
        this.transportHandlerDelegate = new TransportHandlerDelegate();
        this.timeoutDelegate = new TimeoutDelegate();
        this.workflowTypeDelegate = new WorkflowTypeDelegate();
        this.heartbeatDelegate = new HeartbeatDelegate();
        this.certificateDelegate = new CertificateDelegate();
        this.filterDelegate = new FilterDelegate();
        this.listDelegate = new ListDelegate();
        this.starttlsDelegate = new StarttlsDelegate();
        this.compressionDelegate = new CompressionDelegate();
        addDelegate(listDelegate);
        addDelegate(heartbeatDelegate);
        addDelegate(ciphersuiteDelegate);
        addDelegate(compressionDelegate);
        addDelegate(maxFragmentLengthDelegate);
        addDelegate(ellipticCurveDelegate);
        addDelegate(protocolVersionDelegate);
        addDelegate(clientDelegate);
        addDelegate(signatureAndHashAlgorithmDelegate);
        addDelegate(workflowTypeDelegate);
        addDelegate(transportHandlerDelegate);
        addDelegate(timeoutDelegate);
        addDelegate(certificateDelegate);
        addDelegate(filterDelegate);
        addDelegate(starttlsDelegate);
    }

    @Override
    public Config createConfig() {
        Config config = super.createConfig();

        if (config.getWorkflowTraceType() == null) {
            config.setWorkflowTraceType(WorkflowTraceType.HANDSHAKE);
        }
        return config;
    }

    public String getSocket_host() {
        return socket_host;
    }

    public Integer getSocket_port() {
        return socket_port;
    }

    public String getWorkflowInput() {
        return workflowInput;
    }

    public String getWorkflowOutput() {
        return workflowOutput;
    }

    public String getWorkflowsInFiles() {
        return workflowsInFiles;
    }

    public void setWorkflowsInFiles(String workflowsInFiles) {
        this.workflowsInFiles = workflowsInFiles;
    }

    public String getWorkflowsOutFiles() {
        return workflowsOutFiles;
    }

    public void setWorkflowsOutFiles(String workflowsOutFiles) {
        this.workflowsOutFiles = workflowsOutFiles;
    }
}
