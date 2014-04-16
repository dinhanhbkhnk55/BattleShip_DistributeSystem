package com.dinhanh.json;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author thinhnv
 */
public class JSONBuilder {

    private JSONObject jsonObj;

    public JSONBuilder() {
        jsonObj = new JSONObject();
    }

    public JSONBuilder builder() {
        jsonObj = new JSONObject();
        return this;
    }

    public JSONBuilder put(String key, Object value) {
        try {
            jsonObj.put(key, value);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public JSONBuilder put(String key, double value) {
        try {
            jsonObj.put(key, value);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public JSONBuilder put(String key, int value) {
        try {
            jsonObj.put(key, value);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public JSONBuilder put(String key, long value) {
        try {
            jsonObj.put(key, value);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public JSONBuilder put(String key, boolean value) {
        try {
            jsonObj.put(key, value);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public JSONBuilder putOpt(String key, Object value) {
        try {
            jsonObj.putOpt(key, value);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public String build() {
        return jsonObj.toString();
    }
}
