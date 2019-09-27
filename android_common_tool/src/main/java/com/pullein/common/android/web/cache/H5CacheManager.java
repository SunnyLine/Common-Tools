package com.pullein.common.android.web.cache;

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
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URLConnection;

public class H5CacheManager {
    private volatile static H5CacheManager mH5CacheManager = new H5CacheManager();
    private final String h5CacheFolder = "h5Cache";

    private static CacheSetting mCacheSetting;

    private H5CacheManager() {
    }

    public static H5CacheManager getInstance() {
        return mH5CacheManager;
    }

    public void init(CacheSetting cacheSetting) {
        mCacheSetting = cacheSetting;
    }

    public WebResourceResponse shouldInterceptRequest(final Context context, String requestUrl) {
        if (!checkCacheSetting()) {
            return null;
        }
        Uri requestUri = Uri.parse(requestUrl);
        String requestHost = requestUri.getHost();
        String requestPath = requestUri.getPath();
        if (!isInterceptor(requestHost, requestPath)) {
            Log.d("不予缓存  requestHost:" + requestHost + "requestPath:" + requestPath);
            return null;
        }
        String cacheKey = requestHost + requestPath;
        String versionKey = requestHost + requestPath + "version";

        String remoteVersion = TextUtils.isEmpty(mCacheSetting.getVersionKey()) ? null : requestUri.getQueryParameter(mCacheSetting.getVersionKey());
        String localVersion = SPUtil.getString(context, versionKey);
        String localCacheFilePath = SPUtil.getString(context, cacheKey);

        File localCacheFile = null;
        if (!TextUtils.isEmpty(localCacheFilePath)) {
            localCacheFile = new File(localCacheFilePath);
        }
        boolean needDownload = localCacheFile == null || !localCacheFile.exists() || (mCacheSetting.getCheckVersion() != null && mCacheSetting.getCheckVersion().compare(localVersion, remoteVersion));
        if (needDownload) {
            //需要下载新版本
            localCacheFile = download(context, requestHost, requestPath, requestUri);
            if (localCacheFile == null) {
                return null;
            }
        }
        if (localCacheFile.exists()) {
            try {
                Log.d("读取本地缓存:" + localCacheFile.getPath());
                return new WebResourceResponse(getMimeType(requestPath), "UTF-8", new FileInputStream(localCacheFile));
            } catch (FileNotFoundException e) {
                Log.d("读取本地缓存发生FileNotFoundException异常:" + e.toString());
            }
        }
        return null;
    }

    private File download(final Context context, final String requestHost, final String requestPath, Uri requestUri) {
        try {
            final String localCachePath = context.getCacheDir().getPath() + File.separator + h5CacheFolder + File.separator;
            FileUtil.createFolder(localCachePath);
            final String fileName = FileUtil.getFileName(requestPath);
            InputStream inputStream = mCacheSetting.getDownload().download(requestUri.toString());
            if (inputStream == null) {
                return null;
            }
            File file = FileUtil.createFile(localCachePath + fileName, inputStream);
            SPUtil.putString(context, requestHost + requestPath, localCachePath + fileName);
            return file;
        } catch (IOException e) {
            Log.d("下载文件时发生IO异常：" + e.toString());
        } catch (NullPointerException e) {
            Log.d("下载文件时发生空指针异常:" + e.toString());
        } catch (Exception e) {
            Log.d("下载文件时发生未知异常:" + e.toString());
        }
        return null;
    }

    /**
     * @see <a href=" http://tool.oschina.net/commons">Http Content-type 对照表</a>
     */
    private String getMimeType(String requestPath) {
        if (TextUtils.isEmpty(requestPath) || !requestPath.contains(".")) {
            return null;
        }
        String suffixName = requestPath.substring(requestPath.lastIndexOf("."));
        if (mCacheSetting.getInterceptMap().containsKey(suffixName)) {
            return mCacheSetting.getInterceptMap().get(suffixName);
        }
        return URLConnection.guessContentTypeFromName(requestPath);
    }

    private boolean checkCacheSetting() {
        if (mCacheSetting == null) {
            Log.d("请先初始化");
            return false;
        }
        if (mCacheSetting.getDownload() == null) {
            Log.d("请实现下载器，否则无法下载需要缓存的文件");
            return false;
        }
        if (CollectionUtil.isEmpty(mCacheSetting.getInterceptMap())) {
            Log.d("请先设置拦截类型");
            return false;
        }
        if (CollectionUtil.isEmpty(mCacheSetting.getCacheHostWhiteList())) {
            Log.d("缓存host白名单为空");
            return false;
        }
        return true;
    }

    private boolean isInterceptor(String requestHost, String requestPath) {
        if (!mCacheSetting.isCacheEnable()) {
            //缓存不可用
            Log.d("缓存未开启");
            return false;
        }
        if (TextUtils.isEmpty(requestHost) || !mCacheSetting.getCacheHostWhiteList().contains(requestHost)) {
            Log.d("外部链接不做拦截 cur host = " + requestHost);
            return false;
        }
        if (TextUtils.isEmpty(requestPath) || !requestPath.contains(".")) {
            Log.d("request path 为空或者不需要缓存");
            return false;
        }
        String suffixName = requestPath.substring(requestPath.lastIndexOf("."));
        Log.d("suffixName = " + suffixName);
        return mCacheSetting.getInterceptMap().containsKey(suffixName);
    }
}
