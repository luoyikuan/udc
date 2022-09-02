package com.github.lyk98.iot.usr.udc.packet;

import java.util.Arrays;

/**
 * UDC 请求数据包
 */
public class UdcReqPacket extends UdcBasePacket {
    private String ip;
    private int port;

    public UdcReqPacket(byte type) {
        this.type = type;
    }

    public void unpack(byte[] pack) {
        this.setEquipmentNo(pack, 0);

        if (this.type == LOGIN_REQ) {
            byte tmep = pack[11];
            pack[11] = pack[14];
            pack[14] = tmep;

            tmep = pack[12];
            pack[12] = pack[13];
            pack[13] = tmep;

            tmep = pack[15];
            pack[15] = pack[16];
            pack[16] = tmep;
        }

        if (this.type == HEART_BEAT_REQ || this.type == LOGIN_REQ || this.type == LOGOUT_REQ) {
            StringBuilder sb = new StringBuilder();
            sb.append(pack[11] & 0xFF).append('.');
            sb.append(pack[12] & 0xFF).append('.');
            sb.append(pack[13] & 0xFF).append('.');
            sb.append(pack[14] & 0xFF);
            this.ip = sb.toString();
            this.port = ((pack[15] & 0xFF) << 8) | (pack[16] & 0xFF);
        }
        if (this.type == DATA_REQ) {
            this.data = new byte[pack.length - 12];
            for (int i = 0; i < this.data.length; i++) {
                this.data[i] = pack[11 + i];
            }
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "UdcReqPacket [ip=" + ip + ", port=" + port + ", type=" + type + ", length=" + length + ", equipmentNo="
                + equipmentNo + ", data=" + Arrays.toString(data) + "]";
    }
}
