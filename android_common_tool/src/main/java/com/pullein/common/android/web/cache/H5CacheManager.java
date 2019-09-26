package com.pullein.common.android.web.cache;

import android.content.Context;
import android.net.Uri;
import android.webkit.WebResourceResponse;

import com.pullein.common.utils.Log;

public class H5CacheManager {
    private volatile static H5CacheManager mH5CacheManager = new H5CacheManager();

    private static CacheSetting mCacheSetting;

    private H5CacheManager() {
    }

    public static H5CacheManager getInstance() {
        return mH5CacheManager;
    }

    public static void init(CacheSetting cacheSetting) {
        mCacheSetting = cacheSetting;
    }

    public WebResourceResponse shouldInterceptRequest(final Context context, String requestUrl){
        Uri requestUri = Uri.parse(requestUrl);
        if (!isInterceptor(requestUri)){
            return null;
        }
        String requestHost = requestUri.getHost();
        String requestPath = requestUri.getPath();
        String requestVersion = requestUri.getQueryParameter(mCacheSetting.getVersionKey());
        return null;
    }

    private boolean isInterceptor(Uri requestUri) {
        if (!mCacheSetting.isCacheEnable()) {
            //缓存不可用
            Log.d("缓存未开启");
            return false;
        }
        if (requestUri.getHost() == null || !mCacheSetting.getCacheHostWhiteList().contains(requestUri.getHost())) {
            Log.d("外部链接不做拦截 cur host = " + requestUri.getHost());
            return false;
        }
        String path = requestUri.getPath();
        if (path == null || !path.contains(".")) {
            return false;
        }
        String suffixName = path.substring(path.lastIndexOf("."));
        Log.d("suffixName = " + suffixName);
        return mCacheSetting.getInterceptMap().containsKey(suffixName);
    }
}
