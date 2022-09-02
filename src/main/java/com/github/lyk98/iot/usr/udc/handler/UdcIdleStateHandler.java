package com.github.lyk98.iot.usr.udc.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.ChannelInputShutdownReadComplete;
import io.netty.handler.timeout.IdleStateEvent;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * 心跳
 *
 * @author lyk
 * @date 2022/9/2 13:35
 */
public class UdcIdleStateHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        SocketAddress remoteAddress = ctx.channel().remoteAddress();
        InetSocketAddress remoteSocketAddress = (InetSocketAddress) remoteAddress;
        String ip = remoteSocketAddress.getAddress().getHostAddress();
        int port = remoteSocketAddress.getPort();

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case READER_IDLE:// 读客户端数据超时
                    ctx.close();
                    System.out.println("设备下线...");
                    break;
                case WRITER_IDLE:
                    break;
                case ALL_IDLE:
                    break;
            }
        } else if (evt instanceof ChannelInputShutdownReadComplete) {// 客户端连接关闭
            ctx.close();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
