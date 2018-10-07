package com.james.possdk.bluetooth;

/**
 * Created by James on 2018/10/6.
 */
public class BtMsgReadEvent {

    public int bytes;
    public byte[] buffer;
    public String message;

    public BtMsgReadEvent(int bytes, byte[] buffer) {
        this.buffer = buffer;
        this.bytes = bytes;
        this.message = new String(buffer, 0, bytes);
    }

}
