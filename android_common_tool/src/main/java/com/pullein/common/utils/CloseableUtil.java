package com.pullein.common.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Common-Tools<br>
 * describe ：做流关闭，避免多次try-catch
 *
 * @author xugang
 * @date 2019/4/23
 */
public class CloseableUtil {
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            close(closeable);
        }
    }
}
