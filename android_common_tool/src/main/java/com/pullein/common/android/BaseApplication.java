package com.pullein.common.android;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.pullein.common.utils.CollectionUtil;
import com.pullein.common.utils.Log;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/6/2
 */
public class BaseApplication extends Application {
    private List<Activity> activityList = Collections.synchronizedList(new LinkedList<Activity>());

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycle();
        captureException();
    }

    protected void captureException() {
        CrashHandler crashHandler = new CrashHandler(this);
        crashHandler.setAutoStartAfterCrash(true)
                .setPrintErrorLog(true);

    }

    /**
     * 获取栈顶的Activity
     *
     * @return
     */
    public Activity getTopActivity() {
        if (CollectionUtil.isEmpty(activityList)) {
            return null;
        }
        return activityList.get(activityList.size() - 1);
    }

    /**
     * 关闭所有的Activity
     */
    public void closeAllActivity() {
        try {
            if (CollectionUtil.isEmpty(activityList)) {
                return;
            }
            Iterator<Activity> iterator = activityList.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                activity.finish();
                iterator.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册Activity生命周期
     */
    public void registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (activity != null) {
                    Log.i(activity.getClass().getSimpleName() + "\tonActivityCreated");
                }
                if (activityList != null) {
                    activityList.add(activity);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (activity != null) {
                    Log.i(activity.getClass().getSimpleName() + "\tonActivityStarted");
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (activity != null) {
                    Log.i(activity.getClass().getSimpleName() + "\tonActivityResumed");
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                if (activity != null) {
                    Log.i(activity.getClass().getSimpleName() + "\tonActivityPaused");
                }
            }

            @Override
            public void onActivityStopped(Activity activity) {
                if (activity != null) {
                    Log.i(activity.getClass().getSimpleName() + "\tonActivityStopped");
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                if (activity != null) {
                    Log.i(activity.getClass().getSimpleName() + "\tonActivitySaveInstanceState");
                }
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (activity != null) {
                    Log.i(activity.getClass().getSimpleName() + "\tonActivityDestroyed");
                }
                if (CollectionUtil.isEmpty(activityList)) {
                    return;
                }
                if (activityList.contains(activity)) {
                    activityList.remove(activity);
                }
            }
        });
    }
}
