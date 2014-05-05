package com.dinhanh.battleship.response;

public class MessageType {
    public static final String SPLIT = "##";
    public static final String JOIN = "&&";
    public static final String SUB_JOIN = "//";

    public static final int RQ_UNVAILABLE = -100;

    // ================================================
    // ID MESSAGE
    // ================================================
    
    public static final short ID_CONNECT = 0;
    public static final short ID_REGISTER = 1;
    public static final short ID_LOGIN = 2;
    public static final short ID_JOIN_ROOM = 3;
    public static final short ID_EXTENSION_LOGIC = 4;
    public static final short ID_PUBLIC_CHAT = 5;
    public static final short ID_LEAVE_ROOM = 6;
}

