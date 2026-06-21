package com.alex.sustavzaupravljanjebolnice.db;

import org.h2.tools.Server;

public class H2Server {

    private static Server tcpServer;

    public static void start() {

        try {

            tcpServer = Server.createTcpServer("-tcp", "-tcpPort", "9092", "-tcpAllowOthers").start();

            System.out.println("H2 TCP server started");

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