package com.james.possdk.log;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by James on 2018/10/7.
 */
public interface CrashUploader {

    void uploadCrashMessage(ConcurrentHashMap<String, Object> info);

}
