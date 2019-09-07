package com.demo.app;

import android.app.Application;

import com.pullein.common.android.AppUtil;
import com.pullein.common.android.ApplicationHelper;
import com.pullein.common.android.CrashHandler;
import com.pullein.common.android.listener.AppProcessStateListener;
import com.pullein.common.utils.Log;

import java.io.File;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/8/28
 */
public class MyApp extends Application implements AppProcessStateListener {
    ApplicationHelper helper;
    CrashHandler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        if (AppUtil.isMainProcess(this)) {
            if (helper == null) {
                helper = new ApplicationHelper();
            }
            helper.registerActivityLifecycle(this);
            helper.setAppProcessStateListener(this);
            if (handler == null) {
                handler = new CrashHandler.Builder(this)
                        .setPrintErrorLog(true)
                        .setCrashFileSavePath(getCacheDir().getPath() + File.separator + "crash" + File.separator)
                        .build();
            }
        }
    }

    public boolean isAppForeground() {
        if (helper != null) {
            return helper.isAppForeground();
        }
        return false;
    }

    @Override
    public void onProcessForeground() {
        Log.d("========", "onProcessForeground");
    }

    @Override
    public void onProcessBackground() {
        Log.d("========", "onProcessBackground");
    }
}
