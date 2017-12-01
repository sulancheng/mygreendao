package com.susu.hh.mygreendao.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * 作者：sucheng on 2017/12/1 10:00
 */

public class Base64Utils {
    // 加密
    public static String getBase64(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.encode(str.getBytes("utf-8"), Base64.DEFAULT), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 解密
    public static String getFromBase64(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.decode(str, Base64.DEFAULT), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
