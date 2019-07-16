package com.example.module_public.base;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.dingxiang.mobile.risk.DXRisk;
import com.koolearn.klibrary.ui.android.library.ZLAndroidApplication;


public class BaseApplication extends ZLAndroidApplication {
    private static BaseApplication sInstance;

    public static BaseApplication getIns() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        DXRisk.setup(this);
        if (isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }

    public static boolean isAppDebug() {
        if (StringUtils.isSpace(sInstance.getPackageName())) return false;
        try {
            PackageManager pm = sInstance.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(sInstance.getPackageName(), 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
