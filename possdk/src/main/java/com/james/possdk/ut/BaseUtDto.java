package com.james.possdk.ut;

import com.james.possdk.AppInfo;

import java.util.HashMap;

/**
 * Created by James on 2018/10/7.
 */
public abstract class BaseUtDto implements UtInterface{

    private HashMap<String, String> mValueMap;

    public BaseUtDto() {
        mValueMap.put("dType", AppInfo.dType);
        mValueMap.put("dVersion", AppInfo.dType);
        mValueMap.put("appCode", String.valueOf(AppInfo.appCode));
        mValueMap.put("appVersion", AppInfo.appVersion);
    }

    public void put(String key, String value) {
        mValueMap.put(key, value);
    }

    @Override
    public HashMap<String, String> getValue() {
        return mValueMap;
    }
}
