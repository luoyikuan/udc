package com.github.lyk98.iot.usr.udc.handler;

import com.github.lyk98.iot.usr.udc.codec.UdcCodec;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author lyk
 * @date 2022/9/2 13:39
 */
public class UdcServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new UdcCodec());
        pipeline.addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast(new UdcIdleStateHandler());
        pipeline.addLast(new UdcServerHandler());
    }
}
