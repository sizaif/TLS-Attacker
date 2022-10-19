/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
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
import de.rub.nds.tlsattacker.core.workflow.*;
import de.rub.nds.tlsattacker.core.workflow.action.ReceiveAction;
import de.rub.nds.tlsattacker.core.workflow.action.SendAction;
import de.rub.nds.tlsattacker.core.workflow.factory.WorkflowTraceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import javax.sound.sampled.Line;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.Objects;
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

    public static boolean isunix = false;

    public static long startnTime;
    public static long startmTime;
    public static long endnTime;
    public static long endmTime;
    public static Date startDate, endDate;

    public static void main(String[] args) {

        isunix = getOsName().equals("unix") ? true : false;

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

                endnTime = System.nanoTime();
                endmTime = System.currentTimeMillis();
                endDate = new Date();
                System.out.println("run time: " + (endnTime - startnTime) + " ns");
                System.out.println("run time: " + (endmTime - startmTime) + " ms");
                System.out.println("run time: " + (endDate.getTime() - startDate.getTime()) + " ms");

            }
        });

        ClientCommandConfig config = new ClientCommandConfig(new GeneralDelegate());
        JCommander commander = new JCommander(config);
        try {
            commander.parse(args);

            // commander.usage();
            if (config.getGeneralDelegate().isHelp()) {
                commander.usage();
                return;
            }
            ListDelegate list = (ListDelegate) config.getDelegate(ListDelegate.class);

            if (list.isSet()) {
                list.plotListing();
                return;
            }
            if (Objects.equals(config.getWorkMode(), "no_udp")) {
                startWithNoUdp(config);
            } else if (Objects.equals(config.getWorkMode(), "udp")) {
                startWithUdp(config);
            }

        } catch (ParameterException e) {
            LOGGER.error("Could not parse provided parameters. " + e.getLocalizedMessage(), e);
            commander.usage();
        }

    }

    public static void startWithUdp(ClientCommandConfig config) {
        try {
            while (!stop_soon) {
                stop_soon = true;
                config.getGeneralDelegate().setDebug(false);
                Config tlsConfig = config.createConfig();
                Client TlsClient = new Client();
                startWorkflow(config, TlsClient);
            }
        } catch (Exception e) {
            LOGGER.error("Encountered an uncaught Exception aborting. See debug for more info.", e);
        }
    }

    public static void startWithNoUdp(ClientCommandConfig config) {
        try {
            while (!stop_soon) {
                stop_soon = true;

                config.getGeneralDelegate().setDebug(false);
                Client TlsClient = new Client();
                startWorkflow(config, TlsClient);
            }

        } catch (Exception e) {
            LOGGER.error("Encountered an uncaught Exception aborting. See debug for more info.", e);
        }
    }

    public static void startWorkflow(ClientCommandConfig config, Client TlsClient) throws IOException {

        // 计算程序运行时间
        startnTime = System.nanoTime();
        startmTime = System.currentTimeMillis();
        startDate = new Date();

        Config tlsConfig = config.createConfig();

        if (config.getWorkflowsOutFiles() != null) {
            if (!Files.exists(Path.of(config.getWorkflowsOutFiles()))) {
                Files.createDirectory(Path.of(config.getWorkflowsOutFiles()));
            }
        }
        if (config.getWorkflowsInFiles() != null) {
            LOGGER.debug("Reading workflow trace files from " + config.getWorkflowsInFiles());
            Files.walkFileTree(Path.of(config.getWorkflowsInFiles()), new FileVisitor<Path>() {
                /**
                 * 在访问任意目录前调用；
                 *
                 * @param  dir
                 * @param  attrs
                 * @throws IOException
                 */
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                    LOGGER.debug("pre visit dir : " + dir);
                    return FileVisitResult.CONTINUE;
                }

                /**
                 * 访问到每个文件时调用；
                 *
                 * @param  file
                 * @param  attrs
                 * @throws IOException
                 */
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                                LOGGER.debug(" visit file is : "+ file);

                    if (Objects.equals(config.getWorkMode(), "udp")) {
                        startudp(config.getSocket_host(), config.getSocket_port());
                    }

                    try {
                        LOGGER.info(
                                "---------------------------------------------------------------------------------------------");
                        WorkflowTrace trace = WorkflowTraceSerializer.secureRead(new FileInputStream(file.toFile()));
                        State state = TlsClient.startTlsClient(tlsConfig, trace);

                        if (config.getWorkflowsOutFiles() != null) {

                            trace = state.getWorkflowTrace();

                            String outfilepre = config.getWorkflowsOutFiles();
                            String file_str = file.toString();
                            String temp_str[];

                            if (isunix) {
                                temp_str = file_str.split("/");
                            } else {
                                temp_str = file_str.split("\\\\");
                            }

                            String old_file_name = temp_str[temp_str.length - 1];
                            String new_file_name = "Execute_" + old_file_name;
                            outfilepre = outfilepre + new_file_name;
//                            LOGGER.debug(" after file name : "+new_file_name +" ; after path: "+outfilepre);
                            LOGGER.info("Writing workflow trace to " + outfilepre);
                            WorkflowTraceSerializer.write(new File(outfilepre), trace);

                        }

                    } catch (Exception e) {
//                                    e.printStackTrace();
                    }

                    return FileVisitResult.CONTINUE;
                }

                /**
                 * 在访问文件失败是调用
                 *
                 * @param  file
                 * @param  exc
                 * @throws IOException
                 */
                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    LOGGER.warn("visit failed : " + file);
                    return FileVisitResult.CONTINUE;
                }

                /**
                 * 在访问任意目录完成后调用；
                 *
                 * @param  dir
                 * @param  exc
                 * @return
                 * @throws IOException
                 */
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });

        }

    }

    public static void startudp(String host, int port) throws SocketException {
        /**
         * 2022-08-15 21:35:22 1. 在启动客户端之前，向fuzz传输信号，通知准备进行握手 2. 2022-08-15 目前暂时想到的 传输信号方式使用 socket
         * 本侧作为客户端socket_client，fuzz侧作为服务端socket_server 3. socket_client 写入当前时间unix 4. socket_server 读取内容后，做hash
         * 运算与pre_hash 进行比较如果不同， 则认为启动了新客户端client与openssl s_server进行握手 此时fuzz 端将trace_ssL_bits清空， 并统计边覆盖率
         *
         */
        // 创建socket udp 客户端
        DatagramSocket socket_client = new DatagramSocket(9997);
        try {

            // 发送
            long curTime = System.currentTimeMillis();
            byte[] socket_data = long2byte(curTime);
            System.out.println("socket_data: " + socket_data);
            DatagramPacket socket_packet =
                    new DatagramPacket(socket_data, socket_data.length, InetAddress.getByName(host), port);
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
            // 计算程序运行时间
            startnTime = System.nanoTime();
            startmTime = System.currentTimeMillis();
            startDate = new Date();

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
     * @param  res
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
     * @param  bytes
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

    public static String getOsName() {
        String osName = System.getProperties().getProperty("os.name");
        if (osName.equals("Linux")) {
            return "unix";
        } else {
            return "win";
        }
    }
}
