package com.james.selfpos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.james.selfpos.base.BaseActivity;

/**
 * Created by James on 2018/10/14.
 */
public class GuideActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        findViewById(R.id.guide_content).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.guide_content) {
            Intent intent = MainActivity.getIntent(this);
            startActivity(intent);
        }
    }
}
