package com.pullein.common.android;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Environment;

import com.pullein.common.android.listener.AppCrashListener;
import com.pullein.common.utils.DateFormatUtil;
import com.pullein.common.utils.FileUtil;
import com.pullein.common.utils.HandlerUtil;
import com.pullein.common.utils.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Common-Tools<br>
 * describe ：APP崩溃
 *
 * @author xugang
 * @date 2019/6/2
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private String crashFileSavePath;
    private boolean isPrintErrorLog;
    private AppCrashListener appCrashListener;

    private CrashHandler(Builder builder) {
        mContext = builder.context;
        crashFileSavePath = builder.crashFileSavePath;
        isPrintErrorLog = builder.isPrintErrorLog;
        appCrashListener = builder.appCrashListener;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (e == null) {
            return;
        }
        if (isPrintErrorLog) {
            writeLog(e);
        }
        if (mDefaultHandler != null) {
            //// 先让默认异常处理器处理
            mDefaultHandler.uncaughtException(t, e);
        }
        HandlerUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (appCrashListener != null) {
                    appCrashListener.onCrash();
                }
            }
        });

    }

    /**
     * 写日志
     */
    private void writeLog(Throwable e) {
        Map<String, String> map = new HashMap<>();
        try {
            PackageInfo packageInfo = AppUtil.getPackageInfo(mContext);
            if (packageInfo != null) {
                String versionName = packageInfo.versionName;
                String versionCode;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                    versionCode = String.valueOf(packageInfo.getLongVersionCode());
                } else {
                    versionCode = String.valueOf(packageInfo.versionCode);
                }
                map.put("versionName", versionName);
                map.put("versionCode", versionCode);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i("Failed to get version number !!!");
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                map.put(field.getName(), field.get(null).toString());
                Log.i(field.getName() + " : " + field.get(null));
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.i("Failed to obtain stack information !!!");
            }
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey() + " = " + entry.getValue() + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            String fileName = "crash-" + DateFormatUtil.format2GMT(System.currentTimeMillis(), "yyyy-MM-dd-HH-mm-ss") + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                FileUtil.createFolder(crashFileSavePath);
                File file = new File(crashFileSavePath, fileName);
                FileUtil.createFile(file.getPath(), new ByteArrayInputStream(sb.toString().getBytes()));
            }
        } catch (Exception ex) {
            Log.d("Failed to save error log " + ex);
        }
    }

    class Builder {
        String crashFileSavePath;
        boolean isPrintErrorLog = true;
        AppCrashListener appCrashListener;
        Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setAppCrashListener(AppCrashListener appCrashListener) {
            this.appCrashListener = appCrashListener;
            return this;
        }

        public Builder setCrashFileSavePath(String crashFileSavePath) {
            this.crashFileSavePath = crashFileSavePath;
            return this;
        }

        public Builder setPrintErrorLog(boolean printErrorLog) {
            isPrintErrorLog = printErrorLog;
            return this;
        }

        public CrashHandler build() {
            return new CrashHandler(this);
        }
    }
}
