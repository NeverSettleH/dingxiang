package com.example.module_public.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module_public.R;
import com.example.module_public.utils.StatusBarUtil;
import com.freddy.event.CEventCenter;
import com.freddy.event.I_CEventListener;

public class BaseActivity extends AppCompatActivity implements I_CEventListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
            StatusBarUtil.setAndroidNativeLightStatusBar(this, true);
        }
    }

    @Override
    public void onCEvent(String topic, int msgCode, int resultCode, Object obj) {

    }
}
