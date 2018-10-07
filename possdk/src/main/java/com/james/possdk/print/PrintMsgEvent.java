package com.james.possdk.print;

/**
 * Created by James on 2018/10/6.
 */
public class PrintMsgEvent {

    public int type;
    public String msg;

    public PrintMsgEvent(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

}
