package com.pullein.common.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/4/23
 */
public class HandlerUtil {
    public static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    public HandlerUtil() {
    }

    public static void runOnUiThread(Runnable runnable) {
        MAIN_HANDLER.post(runnable);
    }

    public static void runOnUiThreadDelay(Runnable runnable, long delayMillis) {
        MAIN_HANDLER.postDelayed(runnable, delayMillis);
    }

    public static void removeRunable(Runnable runnable) {
        MAIN_HANDLER.removeCallbacks(runnable);
    }
}
