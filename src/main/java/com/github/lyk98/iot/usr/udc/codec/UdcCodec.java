package com.github.lyk98.iot.usr.udc.codec;

import com.github.lyk98.iot.usr.udc.packet.UdcBasePacket;
import com.github.lyk98.iot.usr.udc.packet.UdcReqPacket;
import com.github.lyk98.iot.usr.udc.packet.UdcResPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * UDC 编解码器
 *
 * @author lyk
 * @date 2022/9/2 13:20
 */
public class UdcCodec extends ByteToMessageCodec<UdcBasePacket> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 编码
     *
     * @param ctx
     * @param msg
     * @param out
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, UdcBasePacket msg, ByteBuf out) throws Exception {
        byte[] pack = ((UdcResPacket) msg).pack();
        out.writeBytes(pack);
    }

    /**
     * 解码
     *
     * @param ctx
     * @param in
     * @param out
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte flag = in.readByte();

        if (flag != UdcBasePacket.FLAG) {
            throw new Exception("头部flag位错误");
        }

        UdcReqPacket req = new UdcReqPacket(in.readByte());
        req.setLength(in.readByte(), in.readByte());

        byte[] dataBuf = new byte[req.getLength() - 4];
        in.readBytes(dataBuf);

        if (dataBuf[dataBuf.length - 1] != UdcReqPacket.FLAG) {
            throw new Exception("尾部flag位错误");
        }

        req.unpack(dataBuf);
        out.add(req);
    }
}
