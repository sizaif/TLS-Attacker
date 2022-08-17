/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package main;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import config.ClientCommandConfig;
import de.rub.nds.tlsattacker.core.config.Config;
import de.rub.nds.tlsattacker.core.config.delegate.GeneralDelegate;
import de.rub.nds.tlsattacker.core.config.delegate.ListDelegate;
import de.rub.nds.tlsattacker.core.exceptions.WorkflowExecutionException;
import de.rub.nds.tlsattacker.core.protocol.message.*;
import de.rub.nds.tlsattacker.core.state.State;
import de.rub.nds.tlsattacker.core.workflow.WorkflowExecutor;
import de.rub.nds.tlsattacker.core.workflow.WorkflowExecutorFactory;
import de.rub.nds.tlsattacker.core.workflow.WorkflowTrace;
import de.rub.nds.tlsattacker.core.workflow.WorkflowTraceSerializer;
import de.rub.nds.tlsattacker.core.workflow.action.ReceiveAction;
import de.rub.nds.tlsattacker.core.workflow.action.SendAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static sun.misc.SignalHandler.SIG_DFL;
import static sun.misc.SignalHandler.SIG_IGN;

/**
 * @ClassName main.Client
 * @Auther xdu-jq
 * @Date 2022/8/15 16:56
 **/
public class Client {

    private static final Logger LOGGER = LogManager.getLogger();

    public static boolean stop_soon = false;

    public static void main(String[] args) {
        SignalHandler handler = new ThisSignalHandler();

        Signal termSignal = new Signal("TERM"); // kill
        Signal.handle(termSignal, handler);

        Signal intSignal = new Signal("INT"); // ctrl +c
        Signal.handle(intSignal, handler);

        // 监听信号量 kill
        Signal.handle(intSignal, new SignalHandler() {
            @Override
            public void handle(Signal signal) {
                stop_soon = true;
                System.out.println("signal handle: " + signal.getName());
                System.exit(0);
            }
        });
        // 监听信号量 ctrl +c
        Signal.handle(termSignal, new SignalHandler() {
            @Override
            public void handle(Signal signal) {
                stop_soon = true;
                System.out.println("signal handle: " + signal.getName());
                System.exit(0);
            }
        });

        // 注册关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // 在关闭钩子中执行收尾工作
                // 注意事项：
                // 1.在这里执行的动作不能耗时太久
                // 2.不能在这里再执行注册，移除关闭钩子的操作
                // 3 不能在这里调用System.exit()
                // TODO: 输出日志
                System.out.println("do shutdown hook");
            }
        });

        ClientCommandConfig config = new ClientCommandConfig(new GeneralDelegate());
        JCommander commander = new JCommander(config);
        try {
            commander.parse(args);
            if (config.getGeneralDelegate().isHelp()) {
                commander.usage();
                return;
            }
            ListDelegate list = (ListDelegate) config.getDelegate(ListDelegate.class);
            if (list.isSet()) {
                list.plotListing();
                return;
            }

            try {

                while (!stop_soon) {
                    Config tlsConfig = config.createConfig();
                    Client TlsClient = new Client();
                    config.getGeneralDelegate().setDebug(false);
                    /**
                     * 2022-08-15 21:35:22 TODO: 1. 在启动客户端之前，向fuzz传输信号，通知准备进行握手 2. 2022-08-15 目前暂时想到的 传输信号方式使用 socket
                     * 本侧作为客户端socket_client，fuzz侧作为服务端socket_server 3. socket_client 写入当前时间unix 4. socket_server
                     * 读取内容后，做hash 运算与pre_hash 进行比较如果不同，则认为启动了新客户端client与openssl s_server进行握手 此时fuzz 端将trace_ssL_bits
                     * 清空， 并统计边覆盖率
                     *
                     */
                    // 创建socket udp 客户端
                    DatagramSocket socket_client = new DatagramSocket(9997);
                    try {

                        // 发送
                        long curTime = System.currentTimeMillis();
                        byte[] socket_data = long2byte(curTime);
                        System.out.println("socket_data: " + socket_data);
                        DatagramPacket socket_packet = new DatagramPacket(socket_data, socket_data.length,
                                InetAddress.getByName(config.getSocket_host()), config.getSocket_port());
                        // 发送数据包
                        socket_client.send(socket_packet);
                        System.out.println("send data success");

                        // 接受
                        byte[] receive_buff = new byte[100];
                        boolean rec_ok = false;
                        while (!rec_ok) {
                            // 存放数据包的容器
                            DatagramPacket rec_packet = new DatagramPacket(receive_buff, 0, receive_buff.length);
                            socket_client.receive(rec_packet);
                            byte[] rec_packetData = rec_packet.getData();
                            String rec_data_str = new String(rec_packetData, StandardCharsets.UTF_8);

                            System.out.println("rec_data: " + rec_data_str);
                            System.out.println("rec_data len : " + rec_data_str.length());
                            if (rec_data_str.startsWith("OK")) {

                                System.out.println("receive ok!!!!!!!!!!");
                                rec_ok = true;
                            }
                        }
                    } catch (IOException ioex) {
                        System.out.println(ioex);
                    } finally {
                        if (socket_client != null) {
                            try {
                                socket_client.close();
                            } catch (Exception ioe) {

                            }
                        }
                    }

                    WorkflowTrace trace = null;
                    if (config.getWorkflowInput() != null) {
                        LOGGER.debug("Reading workflow trace from " + config.getWorkflowInput());
                        trace = WorkflowTraceSerializer
                                .secureRead(new FileInputStream(new File(config.getWorkflowInput())));
                    }
                    for (int i = 1; i <= 9; i++) {
                        Random random1 = new Random();
                        int random2 = random1.nextInt(9) + 1;
                        switch (random2) {
                            case 1:
                                trace.addTlsAction(new SendAction(new ClientHelloMessage()));
                            case 2:
                                trace.addTlsAction(new ReceiveAction(new ServerHelloMessage()));
                            case 3:
                                trace.addTlsAction(new ReceiveAction(new CertificateMessage()));
                            case 4:
                                trace.addTlsAction(new ReceiveAction(new ServerHelloDoneMessage()));
                            case 5:
                                trace.addTlsAction(new SendAction((new RSAClientKeyExchangeMessage())));
                            case 6:
                                trace.addTlsAction(new SendAction((new ChangeCipherSpecMessage())));
                            case 7:
                                trace.addTlsAction(new SendAction((new FinishedMessage())));
                            case 8:
                                trace.addTlsAction(new ReceiveAction(new ChangeCipherSpecMessage()));
                            case 9:
                                trace.addTlsAction(new ReceiveAction(new FinishedMessage()));
                        }
                    }

                    State state = TlsClient.startTlsClient(tlsConfig, trace);

                    if (config.getWorkflowOutput() != null) {
                        trace = state.getWorkflowTrace();
                        LOGGER.debug("Writing workflow trace to " + config.getWorkflowOutput());
                        WorkflowTraceSerializer.write(new File(config.getWorkflowOutput()), trace);
                    }

                }
            } catch (Exception e) {
                LOGGER.error("Encountered an uncaught Exception aborting. See debug for more info.", e);
            }
        } catch (ParameterException e) {
            LOGGER.error("Could not parse provided parameters. " + e.getLocalizedMessage(), e);
            commander.usage();
        }

    }

    public State startTlsClient(Config config, WorkflowTrace trace) {
        State state;
        if (trace == null) {
            state = new State(config);
        } else {
            state = new State(config, trace);
        }
        WorkflowExecutor workflowExecutor =
                WorkflowExecutorFactory.createWorkflowExecutor(config.getWorkflowExecutorType(), state);

        try {
            workflowExecutor.executeWorkflow();
        } catch (WorkflowExecutionException ex) {
            LOGGER.warn(
                    "The TLS protocol flow was not executed completely, follow the debug messages for more information.");
            LOGGER.debug(ex.getLocalizedMessage(), ex);
        }
        return state;
    }

    static class ThisSignalHandler implements SignalHandler {

        @Override
        public void handle(Signal sig) {
            System.out.println("Signal handler called for signal " + sig);
            try {
                System.out.println("Handling " + sig.getName());
            } catch (Exception e) {
                System.out.println("handle|Signal handler" + "failed, reason " + e.getMessage());
                e.printStackTrace();
            }

        }
    }

    /**
     * long to byte
     *
     * @param res
     * @return
     */
    public static byte[] long2byte(long res) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte) ((res >> offset) & 0xff);
        }
        return buffer;
    }

    /**
     * bytes to hex
     *
     * @param bytes
     * @return
     */
    public static String bytes2hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String tmp;
        sb.append("[");
        for (byte b : bytes) {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() == 1) {
                tmp = "0" + tmp;// 只有一位的前面补个0
            }
            sb.append(tmp).append(" ");// 每个字节用空格断开
        }
        sb.delete(sb.length() - 1, sb.length());// 删除最后一个字节后面对于的空格
        sb.append("]");
        return sb.toString();
    }
}
