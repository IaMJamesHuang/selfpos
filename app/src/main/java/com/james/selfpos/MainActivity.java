package com.james.selfpos;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.james.selfpos.base.BaseActivity;
import com.james.selfpos.model.BuyGoodModel;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;

import static com.uuzuche.lib_zxing.activity.CodeUtils.RESULT_TYPE;

public class MainActivity extends BaseActivity implements View.OnClickListener , ItemListAdapter.OnPriceChangeListener{

    private static final int REQUEST_ZXING = 0x00;
    private static final int REQUEST_PAY = 0x01;

    public static final int OP_TIME = 120;

    private TextView tvScanItem, tvCheckout, tvTimeRest, tvPrcie;
    private ListView lvItems;

    private Handler mTimeHandler;
    private Runnable mTimeRunnable;
    private ItemListAdapter mAdapter;

    private boolean isStartTrace = false;
    private int mRestTime = OP_TIME;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initState();
        isStartTrace = true;
    }

    private void initView() {
        tvTimeRest = findViewById(R.id.tv_time_rest);
        tvScanItem = findViewById(R.id.tv_scan_item);
        tvCheckout = findViewById(R.id.tv_check_out);
        tvPrcie = findViewById(R.id.tv_price);
        lvItems = findViewById(R.id.lv_item);

        tvScanItem.setOnClickListener(this);
        tvCheckout.setOnClickListener(this);

        mAdapter = new ItemListAdapter(this);
        lvItems.setAdapter(mAdapter);
        mAdapter.setList(new ArrayList<BuyGoodModel>());
        mAdapter.setListener(this);
    }

    private void initState() {
        mTimeHandler = new Handler(Looper.getMainLooper());
        mTimeRunnable = new Runnable() {
            @Override
            public void run() {
                if (isStartTrace) {
                    --mRestTime;
//                    if (mRestTime == 0) {
//                        returnToGuide();
//                    } else {
                        tvTimeRest.setText(mRestTime + "秒");
                        mTimeHandler.postDelayed(mTimeRunnable, 1000);
 //                   }
                }
            }
        };
        mTimeHandler.post(mTimeRunnable);
    }

    private void returnToGuide() {
        new AlertDialog.Builder(this)
                .setTitle("本次交易超时，交易已被关闭！")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_scan_item) {
            navScanItem();
        } else if (id == R.id.tv_check_out) {
            navCheckOut();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mTimeHandler.removeCallbacks(mTimeRunnable);
        Intent intent = new Intent(MainActivity.this, ScanItemActivity.class);
        startActivityForResult(intent, REQUEST_ZXING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRestTime = OP_TIME;
        mTimeHandler.post(mTimeRunnable);
        if (requestCode == REQUEST_ZXING && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            int type = bundle.getInt(CodeUtils.RESULT_TYPE);
            if (type == CodeUtils.RESULT_SUCCESS) {
                String itemCode = bundle.getString(CodeUtils.RESULT_STRING);
                mAdapter.addItem(ItemCenter.queryItem(itemCode));
            }
        } else if (requestCode == REQUEST_PAY && resultCode == RESULT_OK) {
            new AlertDialog.Builder(MainActivity.this).setTitle("本次交易已完成，返回引导页！")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setCancelable(false).create().show();
        }
    }

    private void navScanItem() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    1);
        } else {
            mTimeHandler.removeCallbacks(mTimeRunnable);
            Intent intent = new Intent(MainActivity.this, ScanItemActivity.class);
            startActivityForResult(intent, REQUEST_ZXING);
        }
    }

    private void navCheckOut() {
        mTimeHandler.removeCallbacks(mTimeRunnable);
        Intent intent = PayActivity.getIntent(mAdapter.getTotal(), this);
        startActivityForResult(intent, REQUEST_PAY);
    }

    @Override
    public void onPriceChange(int price) {
        tvPrcie.setText(price + "元");
    }
}
