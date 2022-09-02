package com.github.lyk98.iot.usr.udc.packet;

import java.io.Serializable;
import java.util.Objects;

/**
 * UDC 数据包
 *
 * @author lyk
 * @date 2022/9/2 10:43
 */
public class UdcBasePacket implements Serializable {
    public static final byte FLAG = (byte) 0x7B;

    public static final byte HEART_BEAT_REQ = (byte) 0x01; // 心跳请求
    public static final byte HEART_BEAT_RES = (byte) 0x81; // 心跳响应

    public static final byte DATA_REQ = (byte) 0x09; // 上报数据
    public static final byte DATA_RES = (byte) 0x89; // 下发数据

    public static final byte LOGOUT_REQ = (byte) 0x82; // 下线请求
    public static final byte LOGOUT_RES = (byte) 0x02; // 下线响应

    public static final byte LOGIN_REQ = (byte) 0x03; // 登录请求
    public static final byte LOGIN_RES = (byte) 0x83; // 登录响应

    /**
     * 数据包类型
     */
    protected byte type;

    /**
     * 数据包长度
     */
    protected int length;


    /**
     * 设备号
     */
    protected String equipmentNo;

    /**
     * 数据
     */
    protected byte[] data;

    public void setLength(byte lenHigh, byte lenLow) {
        this.length = (lenLow & 0xFF) | ((lenHigh & 0xFF) << 8);
    }

    public byte getLengthHigh() {
        return (byte) ((this.length >>> 8) & 0xFF);
    }

    public byte getLengthLow() {
        return (byte) (this.length & 0xFF);
    }

    public void setEquipmentNo(byte[] en) {
        if (Objects.isNull(en) || en.length != 11) {
            throw new RuntimeException("设备号 byte 数组 格式错误");
        }

        int end = 0;
        for (int i = 0; i < en.length; i++) {
            if (en[i] == (byte) 0x00) {
                end = i;
                break;
            }
        }

        this.equipmentNo = new String(en, 0, end + 1);
    }

    public void setEquipmentNo(byte[] en, int offset) {
        if (Objects.isNull(en) || en.length - offset < 11) {
            throw new RuntimeException("设备号 byte 数组 格式错误");
        }

        int end = 0;
        for (int i = offset; i < en.length; i++) {
            if (en[i] == (byte) 0x00) {
                break;
            }
            end = i;
        }

        this.equipmentNo = new String(en, offset, end + 1);
    }

    protected byte[] getEquipmentNoBytes() {
        byte[] result = new byte[11];

        if (Objects.isNull(this.equipmentNo)) {
            throw new RuntimeException("设备号不存在");
        }

        byte[] bytes = this.equipmentNo.getBytes();
        if (bytes.length > 11) {
            throw new RuntimeException("设备号长度不能大于 11 byte");
        }

        for (int i = 0; i < bytes.length; i++) {
            result[i] = bytes[i];
        }

        return result;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
