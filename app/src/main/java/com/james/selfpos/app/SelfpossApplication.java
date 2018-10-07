package com.james.selfpos.app;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;

import com.james.possdk.AppInfo;
import com.james.possdk.log.JLogCrashHandler;
import com.james.possdk.ut.UTManager;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by James on 2018/10/3.
 */
public class SelfpossApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initZxing();
        initAppInfo();
        initUt();
        initCrashHandler();
    }

    private void initZxing() {
        ZXingLibrary.initDisplayOpinion(this);
    }

    private void initAppInfo() {
        AppInfo.init(this);
    }

    private void initUt() {
        //UtManager依赖于AppInfo的参数，需要先初始化AppInfo，以后再解耦
        UTManager.getInstance().init();
    }

    private void initCrashHandler() {
        Intent intent = new Intent();
        intent.setClassName("com.james.selfpos", "com.james.selfpos.MainActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        JLogCrashHandler.getInstance().init(this, restartIntent);
    }

}
