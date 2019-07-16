package com.example.module_main;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.module_public.base.BaseActivity;

public class WebViewActivity extends BaseActivity {

    private WebView mWebView;
    private WebSettings mSettings;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mWebView = findViewById(R.id.webview);
        // 过度前关闭硬件加速
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        //过度前开启硬件加速
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        mWebView.loadUrl("https://www.baidu.com");
        initWebSetting();
    }

    private void initWebSetting() {
        mSettings = mWebView.getSettings();
        //图片加载
        if(Build.VERSION.SDK_INT >= 19){
            mSettings.setLoadsImagesAutomatically(true);
        }else {
            mSettings.setLoadsImagesAutomatically(false);
        }
        //js处理
        mSettings.setJavaScriptEnabled(true);
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //缩放处理
        mSettings.setUseWideViewPort(true);
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setSupportZoom(true);
        mSettings.setSupportMultipleWindows(true);
        mSettings.setDisplayZoomControls(true);
        //内容布局
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mSettings.supportMultipleWindows();
        //文件缓存
        mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mSettings.setAllowFileAccess(true);
        //其他设置
        mSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        mSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        mSettings.setDefaultTextEncodingName("utf-8"); //设置编码格式
        mSettings.setPluginState(WebSettings.PluginState.OFF); //设置是否支持flash插件
        mSettings.setDefaultFontSize(20); //设置默认字体大小
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, WebViewActivity.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
