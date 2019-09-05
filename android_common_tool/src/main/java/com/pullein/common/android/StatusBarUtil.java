package com.pullein.common.android;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;

/**
 * Common-Tools<br>
 * describe ：状态栏工具类
 *
 * @author xugang
 * @date 2019/9/5
 */
public class StatusBarUtil {
    /**
     * https://github.com/android/platform_frameworks_base/blob/master/policy/src/com/android/internal/policy/impl/PhoneWindowManager.java#L1076
     *
     * @param activity
     * @param colorId
     */
    public static void setTransparentStyle(Activity activity, @ColorRes int colorId) {
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, colorId));
            }
        }
    }
}
