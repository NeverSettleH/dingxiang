package com.example.module_main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.module_public.base.BaseActivity;
import com.freddy.event.CEventCenter;

public class EventCenterActivity extends BaseActivity implements View.OnClickListener {

    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_center);
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSend) {
            CEventCenter.dispatchEvent("test", 0, 0, "CEventCenter发送的事件");
        }
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, EventCenterActivity.class));
    }
}
