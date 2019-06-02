package com.pullein.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Set;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/4/23
 */
public class SPUtil {
    private static String FILE_NAME = "default_preferences";

    public static void transform(String fileName) {
        FILE_NAME = fileName;
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePre.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePre.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePre.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void putFloat(Context context, String key, float value) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePre.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void putLong(Context context, String key, long value) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePre.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void putStringSet(Context context, String key, Set<String> value) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePre.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    public static <T> void putObject(Context context, String key, T value) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            String str = new String(Base64.encode(bos.toByteArray(), Base64.DEFAULT));
            SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharePre.edit();
            editor.putString(key, str);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            CloseableUtil.close(oos, bos);
        }
    }

    public static <T> T getObject(Context context, String key) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        String res = sharePre.getString(key, null);
        if (TextUtils.isEmpty(res)) {
            return null;
        }
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            //读取字节
            byte[] base64 = Base64.decode(res.getBytes(), Base64.DEFAULT);
            //封装到字节流
            bis = new ByteArrayInputStream(base64);
            //再次封装
            ois = new ObjectInputStream(bis);
            return (T) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            CloseableUtil.close(ois, bis);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getAllMap(Context context) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return (Map<String, Object>) sharePre.getAll();
    }


    public static float getFloat(Context context, String key) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharePre.getFloat(key, -1);
    }

    public static float getFloat(Context context, String key, float def) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharePre.getFloat(key, def);
    }

    public static long getLong(Context context, String key) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharePre.getLong(key, -1);
    }

    public static long getLong(Context context, String key, long def) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharePre.getLong(key, def);
    }

    public static Set<String> getStringSet(Context context, String key) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharePre.getStringSet(key, null);
    }

    public static Set<String> getStringSet(Context context, String key, Set<String> def) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharePre.getStringSet(key, def);
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharePre.getInt(key, -1);
    }

    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharePre.getInt(key, defValue);
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharePre.getString(key, null);
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharePre.getString(key, defaultValue);
    }

    public static Boolean getBoolean(Context context, String key) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharePre.getBoolean(key, false);
    }


    public static Boolean getBoolean(Context context, String key, boolean def) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharePre.getBoolean(key, def);
    }

    public static void clearData(Context context, String fileName) {
        SharedPreferences sharedPre = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPre.edit();
        editor.clear();
        editor.apply();
    }

    public static void clearData(Context context) {
        clearData(context, FILE_NAME);
    }

    public static void removeKey(Context context, String key) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePre.edit();
        editor.remove(key);
        editor.apply();
    }

    public static boolean containsKey(Context context, String key) {
        SharedPreferences sharePre = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharePre.contains(key);
    }
}
