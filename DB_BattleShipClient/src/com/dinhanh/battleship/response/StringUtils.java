package com.dinhanh.battleship.response;

import java.util.Vector;

public class StringUtils {

    public static String[] split(String splitStr, String delimiter) {
        StringBuffer token = new StringBuffer();
        Vector<String> tokens = new Vector<String>();
        // split
        char[] chars = splitStr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (delimiter.indexOf(chars[i]) != -1) {
                // we bumbed into a delimiter
                if (token.length() > 0) {
                    tokens.addElement(token.toString());
                    token.setLength(0);
                }
            } else {
                token.append(chars[i]);
            }
        }
        // don't forget the "tail"...
        if (token.length() > 0) {
            tokens.addElement(token.toString());
        }
        // convert the vector into an array
        String[] splitArray = new String[tokens.size()];
        for (int i = 0; i < splitArray.length; i++) {
            splitArray[i] = (String) tokens.elementAt(i);
        }
        return splitArray;
    }

    public static String replaceAll(String str, String pattern, String replace) {
        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();

        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e + pattern.length();
        }
        result.append(str.substring(s));
        return result.toString();
    }

    public static String processUrlAvatar(String url, int width, int height) {
        StringBuffer result = new StringBuffer();
        if (url.startsWith("https")) {
            url = StringUtils.replaceAll(url, "https", "http");
        }
        if (url.startsWith("http://graph.facebook.com/")) {
            int index = url.indexOf("?");
            result.append(url.substring(0, index));
            result.append("?");
            String params = "width=" + width + "&height=" + height;
            result.append(params);
            url = result.toString();
        }
        return url;
    }

    
}
