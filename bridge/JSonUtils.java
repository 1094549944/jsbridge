package com.example.zhangyuke.mywebview;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import java.text.DateFormat;
import java.util.logging.Logger;

/**
 * Created by zhangyuke on 2018/11/6.
 */

public final class JSonUtils {
    private static final String TAG = JSonUtils.class.getSimpleName();

    private JSonUtils() {
        //Utility classes should not have a public or default constructor.
    }

    public static <T> T parseObjectWithoutException(String text, Class<T> clazz) {
        try {
            return JSON.parseObject(text, clazz);
        } catch (Exception ex) {
            Log.e(TAG, "parseObjectWithoutException, exception = " + ex.getMessage());
            return null;
        }
    }

    public static JSONObject parseObjectWithoutException(String text) {
        try {
            return JSON.parseObject(text);
        } catch (Exception ex) {
            Log.e(TAG, "parseObjectWithoutException, exception = " + ex.getMessage());
            return null;
        }
    }
}

