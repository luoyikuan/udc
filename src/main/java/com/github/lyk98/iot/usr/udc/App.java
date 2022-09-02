package com.github.lyk98.iot.usr.udc;

import com.github.lyk98.iot.usr.udc.server.UdcServer;

/**
 * @author lyk
 * @date 2022/9/2 14:54
 */
public class App {
    public static void main(String[] args) {
        UdcServer.startServer(1126);
    }
}
