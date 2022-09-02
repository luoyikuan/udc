package com.github.lyk98.iot.usr.udc.handler;

import com.github.lyk98.iot.usr.udc.packet.UdcBasePacket;
import com.github.lyk98.iot.usr.udc.packet.UdcReqPacket;
import com.github.lyk98.iot.usr.udc.packet.UdcResPacketFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lyk
 * @date 2022/9/2 13:41
 */
public class UdcServerHandler extends SimpleChannelInboundHandler<UdcReqPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UdcReqPacket msg) throws Exception {
        byte type = msg.getType();
        String equipmentNo = msg.getEquipmentNo();
        switch (type) {
            case UdcBasePacket.LOGIN_REQ:
                System.out.println("UDC 设备登录成功");
                System.out.println(msg);
                ctx.writeAndFlush(UdcResPacketFactory.login(equipmentNo));
                break;
            case UdcBasePacket.HEART_BEAT_REQ:
                System.out.println("心跳包");
                System.out.println(msg);
                ctx.writeAndFlush(UdcResPacketFactory.heart(equipmentNo));
                break;
            case UdcBasePacket.DATA_REQ:
                System.out.println("UDC 数据包");
                System.out.println(msg);
                System.out.println(new String(msg.getData()));
                ctx.writeAndFlush(UdcResPacketFactory.data(equipmentNo, ("msg = " + new String(msg.getData())).getBytes()));
                break;
            case UdcBasePacket.LOGOUT_REQ:
                System.out.println("UDC 下线请求");
                System.out.println(msg);
                ctx.writeAndFlush(UdcResPacketFactory.logout(equipmentNo));
                break;
            default:
                break;
        }
    }

}
