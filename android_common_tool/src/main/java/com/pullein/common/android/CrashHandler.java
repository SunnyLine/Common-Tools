package com.pullein.common.android;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

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
    private boolean isAutoStartAfterCrash = true;
    private boolean isPrintErrorLog = true;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private String crashFileSavePath;

    public CrashHandler(Context mContext) {
        this.mContext = mContext;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        crashFileSavePath = Environment.getExternalStorageState() + File.separator + "crash" + File.separator;
    }

    public CrashHandler setAutoStartAfterCrash(boolean autoStartAfterCrash) {
        isAutoStartAfterCrash = autoStartAfterCrash;
        return this;
    }

    public CrashHandler setPrintErrorLog(boolean printErrorLog) {
        isPrintErrorLog = printErrorLog;
        return this;
    }

    public CrashHandler setCrashFileSavePath(String crashFileSavePath) {
        this.crashFileSavePath = crashFileSavePath;
        return this;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (e == null) {
            return;
        }
        Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();
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
                if (mContext instanceof BaseApplication) {
                    ((BaseApplication) mContext).closeAllActivity();
                }
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        if (isAutoStartAfterCrash) {
            HandlerUtil.runOnUiThreadDelay(new Runnable() {
                @Override
                public void run() {
                    Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }
            }, 2 * 1000);
        }
    }

    /**
     * 写日志
     */
    private void writeLog(Throwable e) {
        Map<String, String> map = new HashMap<>();
        try {
            PackageInfo packageInfo = AppUtil.getPackageInfo(mContext);
            if (packageInfo != null) {
                String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String versionCode = packageInfo.versionCode + "";
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
}
