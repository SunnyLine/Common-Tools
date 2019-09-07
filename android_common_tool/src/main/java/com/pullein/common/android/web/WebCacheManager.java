package com.pullein.common.android.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;

import com.pullein.common.utils.CollectionUtil;
import com.pullein.common.utils.FileUtil;
import com.pullein.common.utils.Log;
import com.pullein.common.utils.SPUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebCacheManager {
    private volatile static WebCacheManager mWebCacheManager = new WebCacheManager();
    private static final String TAG = ">>>>>XWebView-Interceptor>>>>>";
    private static Map<String, String> interceptMap = new HashMap<>();
    private List<String> whiteListHost = new ArrayList<>();
    private boolean cacheEnable = false;

    private WebCacheManager() {
    }

    public static WebCacheManager getInstance() {
        return mWebCacheManager;
    }

    static {
        interceptMap.put(".js", "application/javascript");
        interceptMap.put(".html", "text/html");
        interceptMap.put(".htm", "text/html");
        interceptMap.put(".css", "text/css");
        interceptMap.put(".png", "image/png");
        interceptMap.put(".jpg", "image/jpeg");
        interceptMap.put(".jpeg", "image/jpeg");
        interceptMap.put(".gif", "image/gif");
        interceptMap.put(".ico", "image/x-icon");
    }

    /**
     * 初始化添加缓存白名单
     *
     * @param hosts
     */
    public synchronized void setCacheWhiteHost(String... hosts) {
        whiteListHost.clear();
        if (!CollectionUtil.isEmpty(hosts)) {
            whiteListHost.addAll(Arrays.asList(hosts));
        }
    }

    public synchronized void setCacheEnable(boolean cacheEnable) {
        this.cacheEnable = cacheEnable;
    }

    public synchronized void clearLocalCache(Context context) {
        String h5CachePath = context.getCacheDir().getPath() + File.separator + "h5Cache" + File.separator;
        try {
            File file = new File(h5CachePath);
            FileUtil.deleteFolder(file);
        } catch (Exception e) {
            Log.e(TAG + e.toString());
        }
    }

    public synchronized WebResourceResponse shouldInterceptRequest(final Context context, Uri requestUri) {
        final String remoteUrl = requestUri.toString();
        Log.d(TAG + "拦截时间:" + System.currentTimeMillis() + "\t拦截地址:" + remoteUrl);
        if (isInterceptor(requestUri)) {
            Log.d(TAG + "符合拦截条件，开始拦截");
            String filePath = SPUtil.getString(context, remoteUrl);
            File localFile;
            if (TextUtils.isEmpty(filePath)) {
                //本地没有则去网络下载
                Log.d(TAG + "本地无缓存，开始网络下载......");
                localFile = download(context, requestUri);
                if (localFile == null) {
                    Log.d(TAG + "本地无缓存，下载失败");
                }
            } else {
                //SP中有记录，本地文件缺失则重新下载
                localFile = new File(filePath);
                if (!localFile.exists()) {
                    Log.d(TAG + "本地缓存缺失，无法使用，重新下载......");
                    localFile = download(context, requestUri);
                    if (localFile == null) {
                        Log.d(TAG + "下载失败");
                    }
                } else {
                    Log.d(TAG + "----本地有缓存记录----");
                }
            }
            if (localFile == null || !localFile.exists()) {
                Log.d(TAG + "Over--网络下载失败或者本地文件缺失，无法使用缓存!!!");
                return null;
            }
            try {
                return new WebResourceResponse(getMimeType(remoteUrl), "UTF-8", new FileInputStream(localFile));
            } catch (FileNotFoundException e) {
                Log.d(TAG + "存在缓存文件，发生异常");
            }
        } else {
            Log.d(TAG + "不符合拦截条件 remoteUrl ： " + remoteUrl);
        }
        return null;
    }

    @SuppressLint("CheckResult")
    private File download(final Context context, final Uri requestUri) {
        String remoteUrl = requestUri.toString();
        Log.d(TAG + "无现有缓存可用，开始下载:" + remoteUrl);
        File localFile = null;
        try {
            URL url = new URL(remoteUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream is = null;
            if (connection.getResponseCode() == 200) {
                connection.getInputStream();
            }
            String path = context.getCacheDir().getPath() + File.separator + "h5Cache" + File.separator;
            FileUtil.createFolder(path);
            String name = remoteUrl.substring(remoteUrl.lastIndexOf("/") + 1);
            localFile = FileUtil.createFile(path + name, is);
            SPUtil.putString(context, remoteUrl, path + name);
            Log.d(TAG + "下载完成，缓存文件保存路径" + localFile.getPath());
        } catch (MalformedURLException e) {
            Log.d(TAG + "URL异常，下载失败：" + remoteUrl + "\t" + e.toString());
        } catch (IOException e) {
            Log.d(TAG + "IO 异常，下载失败：" + remoteUrl + "\t" + e.toString());
        } catch (Exception e) {
            Log.d(TAG + "其他异常，下载失败：" + remoteUrl + "\t" + e.toString());
        }
        return localFile;
    }

    private boolean isInterceptor(Uri requestUri) {
        if (!cacheEnable) {
            //缓存不可用
            Log.d(TAG + "缓存未开启");
            return false;
        }
        if (requestUri.getHost() == null || !whiteListHost.contains(requestUri.getHost())) {
            Log.d(TAG + "外部链接不做拦截 cur host = " + requestUri.getHost());
            return false;
        }
        String remoteUrl = requestUri.toString();
        if (!remoteUrl.contains(".")) {
            return false;
        }
        String suffixName = remoteUrl.substring(remoteUrl.lastIndexOf("."));
        Log.d(TAG + "suffixName = " + suffixName);
        return interceptMap.containsKey(suffixName);
    }

    /**
     * @see <a href=" http://tool.oschina.net/commons">Http Content-type 对照表</a>
     */
    private String getMimeType(String remoteUrl) {
        if (TextUtils.isEmpty(remoteUrl) || !remoteUrl.contains(".")) {
            return null;
        }
        String suffixName = remoteUrl.substring(remoteUrl.lastIndexOf("."));
        if (interceptMap.containsKey(suffixName)) {
            return interceptMap.get(suffixName);
        }
        return URLConnection.guessContentTypeFromName(remoteUrl);
    }
}
