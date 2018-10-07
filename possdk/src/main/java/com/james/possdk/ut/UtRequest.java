package com.james.possdk.ut;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.james.possdk.okhttp.CommonOkHttpClient;
import com.james.possdk.okhttp.listener.DisposeDataHandle;
import com.james.possdk.okhttp.listener.DisposeDataListener;
import com.james.possdk.okhttp.request.CommonRequest;
import com.james.possdk.okhttp.request.RequestParams;

import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

import static com.james.possdk.ut.UtInfo.TAG;
import static com.james.possdk.ut.UtInfo.UT_URL;

/**
 * Created by James on 2018/10/7.
 */
public class UtRequest implements Runnable{

    private static final int RETRY_TIME = 60;
    private HashMap<String, List<HashMap<String, String>>> mDataMap;
    private boolean isCancel;
    private RequestParams params;
    private DisposeDataHandle disposeDataHandle;
    private Handler mHandler;

    public UtRequest(HashMap<String, List<HashMap<String, String>>> map) {
        this.mDataMap = map;
        this.mHandler = new Handler();
        this.disposeDataHandle = new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.e(TAG, "请求发送成功");
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.e(TAG, "请求发送失败");
                mHandler.postDelayed(UtRequest.this, RETRY_TIME * 1000);
            }
        });
        initParams();
    }

    private void initParams() {
        if (mDataMap.size() > 0) {
            isCancel = false;
            params = new RequestParams();
            Gson gson = new Gson();
            for (String key : mDataMap.keySet()) {
                params.put(key, gson.toJson(mDataMap.get(key)));
            }
        } else {
            isCancel = true;
        }
    }

    @Override
    public void run() {
        if (!isCancel) {
            Request request = CommonRequest.createPostRequest(UT_URL, params);
            CommonOkHttpClient.post(request, disposeDataHandle);
        } else {
            Log.e(TAG, "请求取消");
        }
    }
}
