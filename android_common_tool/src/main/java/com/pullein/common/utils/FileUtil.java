package com.pullein.common.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * Common-Tools<br>
 * describe ：文件操作
 *
 * @author xugang
 * @date 2019/4/28
 */
public class FileUtil {

    /**
     * Uri 转换器。为了兼容7.0以上版本，Uri.from;Uri.parse等方法不再直接使用
     */
    public static Uri file2Uri(Context context, final String filePath) {
        return file2Uri(context, new File(filePath));
    }

    /**
     * Uri 转换器。为了兼容7.0以上版本，Uri.from;Uri.parse等方法不再直接使用
     */
    public static Uri file2Uri(Context context, final File file) {
        if (Build.VERSION_CODES.M < Build.VERSION.SDK_INT) {
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * 初始化文件夹
     */
    public static boolean initFolder(String folderPath) {
        File file = new File(folderPath);
        return file.exists() || file.mkdirs();
    }

    /**
     * 读取本地文件
     */
    public static byte[] read(String filePath) {
        File file = new File(filePath);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            CloseableUtil.close(buf);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 创建文件
     */
    public static File createFile(String filePath, InputStream netInputStream) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fileOutputStream = null;
        try {
            file.createNewFile();

            fileOutputStream = new FileOutputStream(file);
            byte[] b = new byte[1024];

            int length;
            while ((length = netInputStream.read(b)) != -1) {
                fileOutputStream.write(b, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseableUtil.close(netInputStream, fileOutputStream);
        }
        return file;
    }

    /**
     * 转换文件大小
     */
    public static String fileSize2String(long fileLen) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileLen < 1024L) {
            fileSizeString = df.format((double) fileLen) + "B";
        } else if (fileLen < 1048576L) {
            fileSizeString = df.format((double) fileLen / 1024.0D) + "K";
        } else if (fileLen < 1073741824L) {
            fileSizeString = df.format((double) fileLen / 1048576.0D) + "M";
        } else {
            fileSizeString = df.format((double) fileLen / 1.073741824E9D) + "G";
        }

        return fileSizeString;
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(File file) {
        return file != null && file.exists() && file.delete();
    }

    /**
     * 删除文件夹及子文件
     */
    public static boolean deleteFolder(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles != null && childFiles.length > 0) {
                File[] var3 = childFiles;
                int var4 = childFiles.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    File child = var3[var5];
                    if (!deleteFolder(child)) {
                        return false;
                    }
                }
            }
        }
        return file.delete();
    }

    /**
     * 读取文件文本
     */
    public static String getFileOutputString(String path) {
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new FileReader(path), 8 * 1024);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append("\n").append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseableUtil.close(bufferedReader);
        }
        return sb.toString();
    }


}
