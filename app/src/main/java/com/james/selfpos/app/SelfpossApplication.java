package com.james.selfpos.app;

import android.app.Application;

import com.james.possdk.AppInfo;
import com.james.possdk.ut.UTManager;
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
        //UtManager依赖于AppInfo的参数，需要先初始化AppInfo，以后再解耦
        UTManager.getInstance().init();
    }

}
