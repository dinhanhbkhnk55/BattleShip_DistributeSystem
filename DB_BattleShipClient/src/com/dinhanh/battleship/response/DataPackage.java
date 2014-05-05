package com.dinhanh.battleship.response;

public class DataPackage {
    public byte  type;
    public short appID;
    public byte itemID;

    public DataPackage(byte type, short appID, byte itemID){
        this.type   = type;
        this.appID  = appID;
        this.itemID  = itemID;
    }
}