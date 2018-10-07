package com.james.possdk.ut;

import android.os.Handler;
import android.os.HandlerThread;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by James on 2018/10/7.
 */
public class UTManager {

    private static final int DELAY_TIME = 60;

    private static UTManager mInstance;
    private volatile HashMap<String, List<HashMap<String, String>>> mUtDataMap;
    private ExecutorService  mExecutor;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private Runnable mRunnable;
    private boolean isInit;

    private UTManager() {
        mExecutor = Executors.newSingleThreadExecutor();
        mHandlerThread = new HandlerThread("ut-handler-thread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        mUtDataMap = new HashMap<>();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mUtDataMap.size() != 0) {
                    UtRequest request = new UtRequest(mUtDataMap);
                    mExecutor.execute(request);
                    mUtDataMap.clear();
                }
                mHandler.postDelayed(mRunnable, DELAY_TIME * 1000);
            }
        };
    }

    public static UTManager getInstance() {
        if (mInstance == null) {
            synchronized (UTManager.class) {
                if (mInstance == null) {
                    mInstance = new UTManager();
                }
            }
        }
        return mInstance;
    }

    public void init() {
        if (!isInit) {
            isInit = true;
            mHandler.postDelayed(mRunnable, DELAY_TIME * 1000);
        } else {
            throw new IllegalStateException("UTManager不能重复初始化");
        }
    }

    public synchronized void put(BaseUtDto dto) {
        if (mUtDataMap.get(dto.getKey()) == null) {
            mUtDataMap.put(dto.getKey(), new LinkedList<HashMap<String, String>>());
        }
        mUtDataMap.get(dto.getKey()).add(dto.getValue());
    }

}
