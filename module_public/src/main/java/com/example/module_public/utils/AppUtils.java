package com.example.module_public.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.example.module_public.base.BaseApplication;

public class AppUtils {

    /**
     * 获取 App 包名
     *
     * @return the application's package name
     */
    public static String getAppPackageName() {
        return BaseApplication.getIns().getPackageName();
    }


    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verName;
    }
}
