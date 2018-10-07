package com.james.possdk.log;

import android.util.Log;

/**
 * Created by James on 2018/10/7.
 */
public class JLog {

    public static void d(String TAG, String msg) {
        Log.d(TAG, msg);
    }

    public static void e(String TAG, String msg) {
        Log.e(TAG, msg);
    }

}
