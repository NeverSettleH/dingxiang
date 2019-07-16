package com.example.module_public.routerUtils;

import com.alibaba.android.arouter.launcher.ARouter;

public class RouterCenter {
    public static void toHome() {
        ARouter.getInstance().build(Constance.ROUTER_URL_HOME).navigation();
    }

    public static void toPersonal() {
        ARouter.getInstance().build(Constance.ROUTER_URL_PERSONAL).navigation();
    }

    public static void toAlter() {
        ARouter.getInstance().build(Constance.ROUTER_URL_ALTER).navigation();
    }

    public static void toMain() {
        ARouter.getInstance().build(Constance.ROUTER_URL_MAIN).navigation();
    }


}
