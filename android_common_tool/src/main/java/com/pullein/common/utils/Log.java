package com.pullein.common.utils;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/4/23
 */
public class Log {
    private static boolean isPrint = true;
    private static String defaultTag = "======Log======";

    private Log() {
    }

    public static void setPrintEnable(boolean isDebug) {
        Log.isPrint = isDebug;
    }

    public static void setTag(String tag) {
        defaultTag = tag;
    }

    public static int i(Object o) {
        return isPrint && o != null ? android.util.Log.i(defaultTag, o.toString()) : -1;
    }

    public static int i(String m) {
        return isPrint && m != null ? android.util.Log.i(defaultTag, m) : -1;
    }

    public static int v(String tag, String msg) {
        return isPrint && msg != null ? android.util.Log.v(tag, msg) : -1;
    }

    public static int d(String tag, String msg) {
        return isPrint && msg != null ? android.util.Log.d(tag, msg) : -1;
    }

    public static int i(String tag, String msg) {
        return isPrint && msg != null ? android.util.Log.i(tag, msg) : -1;
    }

    public static int w(String tag, String msg) {
        return isPrint && msg != null ? android.util.Log.w(tag, msg) : -1;
    }

    public static int e(String tag, String msg) {
        return isPrint && msg != null ? android.util.Log.e(tag, msg) : -1;
    }

    public static int v(String tag, Object... msg) {
        return isPrint ? android.util.Log.v(tag, getLogMessage(msg)) : -1;
    }

    public static int d(String tag, Object... msg) {
        return isPrint ? android.util.Log.d(tag, getLogMessage(msg)) : -1;
    }

    public static int i(String tag, Object... msg) {
        return isPrint ? android.util.Log.i(tag, getLogMessage(msg)) : -1;
    }

    public static int w(String tag, Object... msg) {
        return isPrint ? android.util.Log.w(tag, getLogMessage(msg)) : -1;
    }

    public static int e(String tag, Object... msg) {
        return isPrint ? android.util.Log.e(tag, getLogMessage(msg)) : -1;
    }

    private static String getLogMessage(Object... msg) {
        if (msg != null && msg.length > 0) {
            StringBuilder sb = new StringBuilder();
            Object[] var2 = msg;
            int var3 = msg.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object s = var2[var4];
                if (s != null) {
                    sb.append(s.toString());
                }
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static int v(String tag, String msg, Throwable tr) {
        return isPrint && msg != null ? android.util.Log.v(tag, msg, tr) : -1;
    }

    public static int d(String tag, String msg, Throwable tr) {
        return isPrint && msg != null ? android.util.Log.d(tag, msg, tr) : -1;
    }

    public static int i(String tag, String msg, Throwable tr) {
        return isPrint && msg != null ? android.util.Log.i(tag, msg, tr) : -1;
    }

    public static int w(String tag, String msg, Throwable tr) {
        return isPrint && msg != null ? android.util.Log.w(tag, msg, tr) : -1;
    }

    public static int e(String tag, String msg, Throwable tr) {
        return isPrint && msg != null ? android.util.Log.e(tag, msg, tr) : -1;
    }

    public static int v(Object tag, String msg) {
        return isPrint ? android.util.Log.v(tag.getClass().getSimpleName(), msg) : -1;
    }

    public static int d(Object tag, String msg) {
        return isPrint ? android.util.Log.d(tag.getClass().getSimpleName(), msg) : -1;
    }

    public static int i(Object tag, String msg) {
        return isPrint ? android.util.Log.i(tag.getClass().getSimpleName(), msg) : -1;
    }

    public static int w(Object tag, String msg) {
        return isPrint ? android.util.Log.w(tag.getClass().getSimpleName(), msg) : -1;
    }

    public static int e(Object tag, String msg) {
        return isPrint ? android.util.Log.e(tag.getClass().getSimpleName(), msg) : -1;
    }
}
