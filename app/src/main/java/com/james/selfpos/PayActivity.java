package com.james.selfpos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.james.selfpos.base.BaseActivity;

/**
 * Created by James on 2018/10/24.
 */
public class PayActivity extends BaseActivity {

    public static Intent getIntent(int price, Context context) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("price", price);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Intent intent = getIntent();
        int price = intent.getIntExtra("price", 0);
        TextView tvPrice = findViewById(R.id.tv_price);
        tvPrice.setText("付款金额：" + price + "元");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(PayActivity.this).setTitle("支付成功！")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_OK);
                        finish();
                    }
                }).setCancelable(false).create().show();
            }
        }, 3000);
    }
}
