package com.pullein.common.android.web.cache;

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

}
