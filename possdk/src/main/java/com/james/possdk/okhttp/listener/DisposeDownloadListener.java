package com.james.possdk.okhttp.listener;

/**
 * Created by James on 2018/10/2.
 */
public interface DisposeDownloadListener extends DisposeDataListener{

    void onProgress(int progrss);

}
