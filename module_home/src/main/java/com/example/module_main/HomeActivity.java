package com.example.module_main;

import android.os.Environment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module_public.base.BaseActivity;
import com.example.module_public.routerUtils.Constance;
import com.freddy.event.CEventCenter;
import com.ixuea.android.downloader.DownloadService;
import com.ixuea.android.downloader.callback.DownloadListener;
import com.ixuea.android.downloader.callback.DownloadManager;
import com.ixuea.android.downloader.domain.DownloadInfo;
import java.io.File;

@Route(path = Constance.ROUTER_URL_HOME)
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    String downLoadUrl = "https://center-1255473234.cos.ap-beijing.myqcloud.com/176116.epub?q-sign-algorithm=sha1&q-ak=AKIDAhyuLV6x2LvlF6G3LE1hk2sMp43ypNDv&q-sign-time=1560138436;1560142036&q-key-time=1560138436;1560142036&q-header-list=&q-url-param-list=&q-signature=2c8b21d69dfd4b68e90c3de1d6be8da089aedd1e&x-cos-security-token=a6884b540d8bd3ca8d515221455afb1c2811498a10001";
    private ImageView imageBook;
    private TextView tv_download_info;
    DownloadInfo downloadInfo;
    DownloadManager downloadManager;
    private Button btnEventCenter;
    private Button btnWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imageBook = findViewById(R.id.image_book);
        tv_download_info = findViewById(R.id.tv_download_info);
        initData();
        CEventCenter.registerEventListener(this, "test");
        btnEventCenter = findViewById(R.id.btn_event_center);
        btnWebView = findViewById(R.id.btn_web_view);
        btnEventCenter.setOnClickListener(this);
        btnWebView.setOnClickListener(this);
    }

    private void initData() {
        downloadManager = DownloadService.getDownloadManager(getApplicationContext());

        downloadInfo = downloadManager.getDownloadById(downLoadUrl);
    }

    @Override
    public void onCEvent(String topic, int msgCode, int resultCode, Object obj) {
        // 事件回调
        switch (topic) {
            case "test": {
                btnEventCenter.setText((String) obj);
                break;
            }

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CEventCenter.unregisterEventListener(this, "test");
    }

    @Override
    public void onClick(View v) {
        if (v == btnEventCenter) {
            EventCenterActivity.startActivity(this);
        }
        if (v == btnWebView) {
            WebViewActivity.startActivity(this);
        }
    }
}
