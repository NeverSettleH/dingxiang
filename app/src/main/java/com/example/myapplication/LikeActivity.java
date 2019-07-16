package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module_public.base.BaseActivity;
import com.example.module_public.routerUtils.Constance;
import com.sum.slike.SuperLikeLayout;
public class LikeActivity extends BaseActivity {
    SuperLikeLayout superLikeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        superLikeLayout = findViewById(R.id.super_like_layout);
        superLikeLayout.setProvider(BitmapProviderFactory.getHDProvider(this));
        findViewById(R.id.like_btn).setOnLongClickListener(new View.OnLongClickListener() {
            long duration = 2000;
            long lastClickTime;
            @Override
            public boolean onLongClick(View v) {
                if(System.currentTimeMillis() - lastClickTime > duration){ // 防抖
                    v.setSelected(!v.isSelected());
                }
                lastClickTime = System.currentTimeMillis();
                if(v.isSelected()){
                    int x = (int)(v.getX() + v.getWidth() / 2);
                    int y = (int)(v.getY() + v.getHeight() / 2);
                    superLikeLayout.launch(x, y);
                }

                return false;
            }



        });
    }

}
