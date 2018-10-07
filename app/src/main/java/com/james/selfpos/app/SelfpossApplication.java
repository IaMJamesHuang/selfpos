package com.james.selfpos.app;

import android.app.Application;

import com.james.possdk.AppInfo;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by James on 2018/10/3.
 */
public class SelfpossApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
        AppInfo.init(this);
    }

}
