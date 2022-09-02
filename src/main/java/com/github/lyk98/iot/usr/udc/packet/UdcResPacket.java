package com.github.lyk98.iot.usr.udc.packet;

import java.util.Objects;

/**
 * UDC 响应数据包
 */
public class UdcResPacket extends UdcBasePacket {

    public UdcResPacket() {
        this.length = 16;
    }

    @Override
    public void setData(byte[] data) {
        super.setData(data);
        this.length += this.data.length;
    }

    public byte[] pack() {
        byte[] buf = new byte[this.length];
        buf[0] = FLAG;
        buf[1] = this.type;
        buf[2] = this.getLengthHigh();
        buf[3] = this.getLengthLow();

        byte[] equipmentNoBytes = this.getEquipmentNoBytes();
        for (int i = 0; i < equipmentNoBytes.length; i++) {
            buf[4 + i] = equipmentNoBytes[i];
        }

        if (this.type == DATA_RES) {
            if (Objects.nonNull(this.data) && this.data.length > 0) {
                for (int i = 0; i < this.data.length; i++) {
                    buf[15 + i] = this.data[i];
                }
            }
        }

        buf[this.length - 1] = FLAG;
        return buf;
    }

}
