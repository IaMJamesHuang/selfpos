package com.james.selfpos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.james.selfpos.base.BaseActivity;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * Created by James on 2018/10/24.
 */
public class ScanItemActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_item);
        getSupportActionBar().hide();
        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, captureFragment).commit();
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Log.e("ScanItemActivity", "onAnalyzeSuccess: ");
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            ScanItemActivity.this.setResult(RESULT_OK, resultIntent);
            ScanItemActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            ScanItemActivity.this.setResult(RESULT_OK, resultIntent);
            ScanItemActivity.this.finish();
        }

    };
}
