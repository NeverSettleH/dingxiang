package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.dingxiang.mobile.risk.DXRisk;
import com.example.module_public.base.BaseActivity;
import com.example.module_public.routerUtils.Constance;
import com.example.module_public.routerUtils.RouterCenter;
import com.koolearn.android.kooreader.KooReader;
import com.koolearn.android.kooreader.libraryService.BookCollectionShadow;
import com.koolearn.kooreader.book.Book;
import com.sum.slike.BitmapProvider;
import com.sum.slike.SuperLikeLayout;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@Route(path = Constance.ROUTER_URL_MAIN)
public class MainActivity extends BaseActivity implements View.OnClickListener {
    String TAG = "DXTOKEN";
    String appId = "fa11f16635e1a987139538628eeedf45";
    private final BookCollectionShadow myCollection = new BookCollectionShadow();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(MainActivity.this);
        findViewById(R.id.dingxiang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRiskComptTest();
            }
        });
        findViewById(R.id.router_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterCenter.toHome();
            }
        });
        findViewById(R.id.router_personal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterCenter.toPersonal();
            }
        });
        findViewById(R.id.epub_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLike();
            }


        });
        doRxJava();
    }

    private void doRxJava() {
        Observable.create(new ObservableOnSubscribe<Integer>() {//初始化observable
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "Observable emit 1" + "\n");
                e.onNext(1);
                Log.e(TAG, "Observable emit 2" + "\n");
                e.onNext(2);
                Log.e(TAG, "Observable emit 3" + "\n");
                e.onNext(3);
                e.onComplete();
                Log.e(TAG, "Observable emit 4" + "\n");
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {//订阅
            // 第二步：初始化Observer
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                i++;
                if (i == 2) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError : value : " + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete" + "\n");
            }
        });
    }

    private void goToLike() {
        startActivity(new Intent(this, LikeActivity.class));
    }

    private void startRiskComptTest() {
        // 整个过程由于是耗时操作，必须要在非UI线程上执行，否则会有crash
        new Thread() {
            @Override
            public void run() {

                long time = System.currentTimeMillis();

                // 私有化配置
                HashMap<String, String> params = new HashMap<String, String>();
                // 私有化部署服务端url
                params.put(DXRisk.KEY_URL, "http://39.98.78.149:7776");
                // 开启线上数据备份
                params.put(DXRisk.KEY_BACKUP, DXRisk.VALUE_ENABLE_BACKUP);

                // 获取设备指纹token
                final String token = DXRisk.getToken(appId, params);

                Log.i(TAG, "=====for test token cost time: " + (System.currentTimeMillis() - time));
                Log.i(TAG, "=====for test token length: " + token.length());
                Log.i(TAG, "=====for test token length: " + token);
                Log.i(TAG, "=====for test end to get token=====");

                if (token.length() != 40) {
                    Log.e(TAG, "========== \r\ntoken length is too long\r\n==========");
                }


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "token:" + token, Toast.LENGTH_LONG).show();

                    }

                });


            }
        }.start();
    }

    private void openBook(Book data) {
        KooReader.openBookActivity(this, data, null);
        overridePendingTransition(com.ninestars.android.R.anim.tran_fade_in, com.ninestars.android.R.anim.tran_fade_out);
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
