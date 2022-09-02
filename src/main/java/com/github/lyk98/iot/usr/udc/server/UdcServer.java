package com.github.lyk98.iot.usr.udc.server;

import com.github.lyk98.iot.usr.udc.handler.UdcServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * UDC 服务器
 *
 * @author lyk
 * @date 2022/9/2 10:51
 */
public class UdcServer {
    public static void startServer(int port) {
        System.out.println("Udc Server 正在启动...");
        startServer0(port);
        System.out.println("Udc Server 已经关闭...");
    }

    private static void startServer0(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new UdcServerInitializer());

            // Start the server.
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("Udc Server 启动成功...");
            // Wait until the server socket is closed.
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
