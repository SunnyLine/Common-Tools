package com.pullein.common.android.web;

import android.net.Uri;
import android.text.TextUtils;

public class WebUtil {
    public static boolean isHttp(String str) {
        boolean isHttp = false;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            Uri uri = Uri.parse(str);
            if (uri == null || TextUtils.isEmpty(uri.getScheme())) {
                return false;
            }
            String scheme = uri.getScheme().toLowerCase();
            isHttp = TextUtils.equals("http://", scheme) || TextUtils.equals("https://", scheme) || TextUtils.equals("ftp://", scheme);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isHttp;
    }
}
