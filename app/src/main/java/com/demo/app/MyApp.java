package com.demo.app;

import android.app.Application;
import android.text.TextUtils;

import com.pullein.common.android.AppUtil;
import com.pullein.common.android.ApplicationHelper;
import com.pullein.common.android.CrashHandler;
import com.pullein.common.android.listener.AppProcessStateListener;
import com.pullein.common.android.web.cache.CacheDownListener;
import com.pullein.common.android.web.cache.CacheSetting;
import com.pullein.common.android.web.cache.H5CacheManager;
import com.pullein.common.android.web.cache.ICheckVersion;
import com.pullein.common.android.web.cache.IDownload;
import com.pullein.common.utils.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/8/28
 */
public class MyApp extends Application implements AppProcessStateListener {
    ApplicationHelper helper;
    CrashHandler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        if (AppUtil.isMainProcess(this)) {
            if (helper == null) {
                helper = new ApplicationHelper();
            }
            helper.registerActivityLifecycle(this);
            helper.setAppProcessStateListener(this);
            if (handler == null) {
                handler = new CrashHandler.Builder(this)
                        .setPrintErrorLog(true)
                        .setCrashFileSavePath(getCacheDir().getPath() + File.separator + "crash" + File.separator)
                        .build();
            }
            H5CacheManager.getInstance().init(new CacheSetting.Builder().setVersionKey("v")
                    .setCacheEnable(true)
                    .addCacheHost("aliyun.com", "ets2.cn")
                    .addIntercept(CacheSetting.CacheType.CACHE_FILE_CSS, CacheSetting.MimeType.MIME_TYPE_CSS)
                    .addIntercept(CacheSetting.CacheType.CACHE_FILE_GIF, CacheSetting.MimeType.MIME_TYPE_GIF)
                    .addIntercept(CacheSetting.CacheType.CACHE_FILE_JPEG, CacheSetting.MimeType.MIME_TYPE_JPG)
                    .addIntercept(CacheSetting.CacheType.CACHE_FILE_JPG, CacheSetting.MimeType.MIME_TYPE_JPG)
                    .addIntercept(CacheSetting.CacheType.CACHE_FILE_PNG, CacheSetting.MimeType.MIME_TYPE_PNG)
                    .addIntercept(CacheSetting.CacheType.CACHE_FILE_JS, CacheSetting.MimeType.MIME_TYPE_JS)
                    .setCheckVersion(new ICheckVersion() {
                        @Override
                        public boolean compare(String localVersion, String remoteVersion) {
                            if (TextUtils.isEmpty(localVersion) || TextUtils.isEmpty(remoteVersion)) {
                                return false;
                            }
                            return false;
                        }
                    })
                    .setDownload(new IDownload() {
                        @Override
                        public InputStream download(String requestUri) throws IOException {
                            OkHttpClient client = new OkHttpClient.Builder()
                                    .connectTimeout(10, TimeUnit.SECONDS)
                                    .readTimeout(10, TimeUnit.SECONDS)
                                    .writeTimeout(10, TimeUnit.SECONDS)
                                    .build();
                            Request request = new Request.Builder()
                                    .url(requestUri)
                                    .build();
                            Response response = client.newCall(request).execute();
                            return response == null || response.body() == null ? null : response.body().byteStream();

                        }
                    }).build());
        }
    }

    public boolean isAppForeground() {
        if (helper != null) {
            return helper.isAppForeground();
        }
        return false;
    }

    @Override
    public void onProcessForeground() {
        Log.d("========", "onProcessForeground");
    }

    @Override
    public void onProcessBackground() {
        Log.d("========", "onProcessBackground");
    }
}
