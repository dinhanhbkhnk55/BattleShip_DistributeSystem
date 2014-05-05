package com.dinhanh.battleship.response;
import com.dinhanh.json.JSONObject;

public class ContentPackage {

    public short segment;
    public String data;

    private String command;
    private String content;

    public ContentPackage(byte ptype, short appID, byte itemID, short segment) {
        this.segment = segment;
    }

    public void setData(String data) {
        this.data = data;
        String[] strs = StringUtils.split(data, MessageType.SPLIT);
        command = strs[0];
        content = strs[1];
    }

    public String getCommand() {
        return command;
    }

    public String getContent() {
        return this.content;
    }

    public String addContent(String content) {
        this.data = this.data + content;
        this.content = this.content + content;
        return this.content + content;
    }

    public JSONObject getJsonContent() {
        try {
            JSONObject json = new JSONObject(getContent());
            return json;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
