package com.github.lyk98.iot.usr.udc.packet;

public class UdcResPacketFactory {

    private UdcResPacketFactory() {
    }

    public static UdcResPacket login(String nquipmentNo) {
        UdcResPacket packet = new UdcResPacket();
        packet.setType(UdcBasePacket.LOGIN_RES);
        packet.setEquipmentNo(nquipmentNo);
        return packet;
    }

    public static UdcResPacket heart(String nquipmentNo) {
        UdcResPacket packet = new UdcResPacket();
        packet.setType(UdcBasePacket.HEART_BEAT_RES);
        packet.setEquipmentNo(nquipmentNo);
        return packet;
    }

    public static UdcResPacket data(String nquipmentNo, byte[] data) {
        UdcResPacket packet = new UdcResPacket();
        packet.setType(UdcBasePacket.DATA_RES);
        packet.setEquipmentNo(nquipmentNo);
        packet.setData(data);
        return packet;
    }

    public static UdcResPacket logout(String nquipmentNo) {
        UdcResPacket packet = new UdcResPacket();
        packet.setType(UdcBasePacket.LOGOUT_RES);
        packet.setEquipmentNo(nquipmentNo);
        return packet;
    }

}
