package com.pullein.common.android.listener;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/8/27
 */
public interface AppProcessStateListener {
    /**
     * 进程进入前台
     */
    void onProcessForeground();

    /**
     * 进程进入后台
     */
    void onProcessBackground();
}
