package com.alex.sustavzaupravljanjebolnice.db;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class H2Server {
    private static final Logger log = LoggerFactory.getLogger(H2Server.class);


    private H2Server() {
    }
    private static Server tcpServer;

    public static void start() {

        try {

            tcpServer = Server.createTcpServer("-tcp", "-tcpPort", "9092", "-tcpAllowOthers").start();

            log.info("H2 TCP server started");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void stop() {

        if (tcpServer != null) {
            tcpServer.stop();
        }
    }
}