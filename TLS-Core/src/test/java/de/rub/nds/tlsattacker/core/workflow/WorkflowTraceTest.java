/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.workflow;

import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.protocol.message.AlertMessage;
import de.rub.nds.tlsattacker.core.protocol.message.ClientHelloMessage;
import de.rub.nds.tlsattacker.core.protocol.message.HeartbeatMessage;
import de.rub.nds.tlsattacker.core.protocol.message.ServerHelloMessage;
import de.rub.nds.tlsattacker.core.workflow.action.ChangeCipherSuiteAction;
import de.rub.nds.tlsattacker.core.workflow.action.ChangeClientRandomAction;
import de.rub.nds.tlsattacker.core.workflow.action.ReceiveAction;
import de.rub.nds.tlsattacker.core.workflow.action.SendAction;
import de.rub.nds.tlsattacker.core.workflow.action.TlsAction;
import java.util.LinkedList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class WorkflowTraceTest {

    private WorkflowTrace trace;
    private Config config;

    @Before
    public void setUp() {
        config = Config.createConfig();
        trace = new WorkflowTrace();
    }

    /**
     * Test of makeGeneric method, of class WorkflowTrace.
     */
    @Test
    public void testMakeGeneric() {
    }

    /**
     * Test of strip method, of class WorkflowTrace.
     */
    @Test
    public void testStrip() {
    }

    /**
     * Test of reset method, of class WorkflowTrace.
     */
    @Test
    public void testReset() {
    }

    /**
     * Test of getDescription method, of class WorkflowTrace.
     */
    @Test
    public void testGetDescription() {
        trace.setDescription("testDesc");
        assertEquals("testDesc", trace.getDescription());
    }

    /**
     * Test of setDescription method, of class WorkflowTrace.
     */
    @Test
    public void testSetDescription() {
        trace.setDescription("testDesc");
        assertEquals("testDesc", trace.getDescription());
    }

    /**
     * Test of add method, of class WorkflowTrace.
     */
    @Test
    public void testAdd_TLSAction() {
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new SendAction());
        assertTrue(trace.getTlsActions().size() == 3);
        trace.addTlsAction(new ReceiveAction());
        assertTrue(trace.getTlsActions().get(3).equals(new ReceiveAction()));
    }

    /**
     * Test of add method, of class WorkflowTrace.
     */
    @Test
    public void testAdd_int_TLSAction() {
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new SendAction());
        assertTrue(trace.getTlsActions().size() == 3);
        trace.addTlsAction(0, new ReceiveAction());
        assertTrue(trace.getTlsActions().get(0).equals(new ReceiveAction()));
    }

    /**
     * Test of remove method, of class WorkflowTrace.
     */
    @Test
    public void testRemove() {
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new SendAction());
        assertTrue(trace.getTlsActions().size() == 3);
        trace.removeTlsAction(0);
        assertTrue(trace.getTlsActions().size() == 2);
    }

    /**
     * Test of getTlsActions method, of class WorkflowTrace.
     */
    @Test
    public void testGetTLSActions() {
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new ReceiveAction());
        assertTrue(trace.getTlsActions().size() == 2);
        assertEquals(trace.getTlsActions().get(0), new SendAction());
        assertEquals(trace.getTlsActions().get(1), new ReceiveAction());
    }

    /**
     * Test of setTlsActions method, of class WorkflowTrace.
     */
    @Test
    public void testSetTlsActions() {
        LinkedList<TlsAction> actionList = new LinkedList<>();
        actionList.add(new SendAction());
        actionList.add(new ReceiveAction());
        trace.setTlsActions(actionList);
        assertTrue(trace.getTlsActions().size() == 2);
        assertEquals(trace.getTlsActions().get(0), new SendAction());
        assertEquals(trace.getTlsActions().get(1), new ReceiveAction());

    }

    /**
     * Test of getMessageActions method, of class WorkflowTrace.
     */
    @Test
    public void testGetMessageActions() {
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new ReceiveAction());
        trace.addTlsAction(new ChangeClientRandomAction());
        assertTrue(trace.getMessageActions().size() == 2);
        assertEquals(trace.getMessageActions().get(0), new SendAction());
        assertEquals(trace.getMessageActions().get(1), new ReceiveAction());
    }

    /**
     * Test of getReceiveActions method, of class WorkflowTrace.
     */
    @Test
    public void testGetReceiveActions() {
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new ReceiveAction());
        trace.addTlsAction(new ChangeClientRandomAction());
        assertTrue(trace.getReceivingActions().size() == 1);
        assertEquals(trace.getReceivingActions().get(0), new ReceiveAction());
    }

    /**
     * Test of getSendActions method, of class WorkflowTrace.
     */
    @Test
    public void testGetSendActions() {
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new ReceiveAction());
        trace.addTlsAction(new ChangeClientRandomAction());
        assertTrue(trace.getSendingActions().size() == 1);
        assertEquals(trace.getSendingActions().get(0), new SendAction());
    }

    /**
     * Test of getLastAction method, of class WorkflowTrace.
     */
    @Test
    public void testGetLastAction() {
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new ReceiveAction());
        trace.addTlsAction(new ReceiveAction());
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new ReceiveAction());
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new ChangeCipherSuiteAction());
        assertEquals(new ChangeCipherSuiteAction(), trace.getLastAction());
    }

    /**
     * Test of getLastMessageAction method, of class WorkflowTrace.
     */
    @Test
    public void testGetLastMessageAction() {
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new ReceiveAction());
        trace.addTlsAction(new ReceiveAction());
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new ReceiveAction());
        trace.addTlsAction(new SendAction());
        trace.addTlsAction(new ChangeCipherSuiteAction());
        assertEquals(new SendAction(), trace.getLastMessageAction());
        trace.addTlsAction(new ReceiveAction());
        assertEquals(new ReceiveAction(), trace.getLastMessageAction());
    }

    /**
     * Test of executedAsPlanned method, of class WorkflowTrace.
     */
    @Test
    public void testConfiguredLooksLikeActual() {
    }

    /**
     * Test of getName method, of class WorkflowTrace.
     */
    @Test
    public void testGetName() {
        trace.setName("testName");
        assertEquals("testName", trace.getName());
    }

    /**
     * Test of setName method, of class WorkflowTrace.
     */
    @Test
    public void testSetName() {
        trace.setName("testName");
        assertEquals("testName", trace.getName());
    }

    @Test
    public void testGetFirstReceivedMessage() {
        SendAction sClientHello = new SendAction();
        sClientHello.setMessages(new ClientHelloMessage());

        SendAction sHeartbeat = new SendAction();
        sHeartbeat.setMessages(new HeartbeatMessage());

        AlertMessage am = new AlertMessage();
        ServerHelloMessage shm = new ServerHelloMessage();

        ReceiveAction rca = new ReceiveAction();
        ReceiveAction sha = new ReceiveAction();

        rca.setMessages(am);
        sha.setMessages(shm);

        trace.addTlsActions(sClientHello, rca, sHeartbeat, sha);
        assertEquals(am, trace.getFirstReceivedMessage(AlertMessage.class));
        assertEquals(shm, trace.getFirstReceivedMessage(ServerHelloMessage.class));
    }

    @Test
    public void testGetFirstSendMessage() {
        ReceiveAction rcvAlertMessage = new ReceiveAction();
        rcvAlertMessage.setMessages(new AlertMessage());

        ReceiveAction rcvServerHello = new ReceiveAction();
        rcvServerHello.setMessages(new ServerHelloMessage());

        ClientHelloMessage ch = new ClientHelloMessage(config);
        HeartbeatMessage hb = new HeartbeatMessage();

        SendAction sch = new SendAction(ch);
        SendAction shb = new SendAction(hb);

        trace.addTlsActions(sch, rcvAlertMessage, shb, rcvServerHello);
        assertEquals(ch, trace.getFirstSendMessage(ClientHelloMessage.class));
        assertEquals(hb, trace.getFirstSendMessage(HeartbeatMessage.class));
    }
}
