package com.pullein.common.android.web.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/9/25
 */
public class CacheSetting {

    private List<String> cacheHostWhiteList;
    private Map<String, String> interceptMap;
    private boolean cacheEnable;
    private String versionKey;
    private ICheckVersion checkVersion;
    private IDownload download;

    private CacheSetting(Builder builder) {
        cacheEnable = builder.cacheEnable;
        versionKey = builder.versionKey;
        interceptMap = builder.interceptMap;
        cacheHostWhiteList = builder.cacheHostWhiteList;
        checkVersion = builder.checkVersion;
        download = builder.download;
    }

    public IDownload getDownload() {
        return download;
    }

    public void setDownload(IDownload download) {
        this.download = download;
    }

    public ICheckVersion getCheckVersion() {
        return checkVersion;
    }

    public void setCheckVersion(ICheckVersion checkVersion) {
        this.checkVersion = checkVersion;
    }

    public List<String> getCacheHostWhiteList() {
        return cacheHostWhiteList;
    }

    public void setCacheHostWhiteList(List<String> cacheHostWhiteList) {
        this.cacheHostWhiteList = cacheHostWhiteList;
    }

    public Map<String, String> getInterceptMap() {
        return interceptMap;
    }

    public void setInterceptMap(Map<String, String> interceptMap) {
        this.interceptMap = interceptMap;
    }

    public boolean isCacheEnable() {
        return cacheEnable;
    }

    public void setCacheEnable(boolean cacheEnable) {
        this.cacheEnable = cacheEnable;
    }

    public String getVersionKey() {
        return versionKey;
    }

    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }

    public static class Builder {
        private List<String> cacheHostWhiteList;
        private Map<String, String> interceptMap;
        private boolean cacheEnable = false;
        private String versionKey;
        private ICheckVersion checkVersion;
        private IDownload download;

        public Builder() {
            cacheHostWhiteList = new ArrayList<>();
            interceptMap = new HashMap<>();
        }

        public Builder setDownload(IDownload download) {
            this.download = download;
            return this;
        }

        public Builder setCheckVersion(ICheckVersion checkVersion) {
            this.checkVersion = checkVersion;
            return this;
        }

        public Builder addCacheHost(String... host) {
            Collections.addAll(cacheHostWhiteList, host);
            return this;
        }

        public Builder addCacheHost(List<String> list) {
            this.cacheHostWhiteList.addAll(list);
            return this;
        }

        public Builder addIntercept(String fileType, String mineType) {
            this.interceptMap.put(fileType, mineType);
            return this;
        }

        public Builder addIntercept(Map<String, String> interceptMap) {
            this.interceptMap.putAll(interceptMap);
            return this;
        }

        public Builder setCacheEnable(boolean cacheEnable) {
            this.cacheEnable = cacheEnable;
            return this;
        }

        public Builder setVersionKey(String versionKey) {
            this.versionKey = versionKey;
            return this;
        }

        public CacheSetting build() {
            return new CacheSetting(this);
        }
    }

    public class CacheType {
        public static final String CACHE_FILE_JS = ".js";
        public static final String CACHE_FILE_HTML = ".html";
        public static final String CACHE_FILE_CSS = ".css";
        public static final String CACHE_FILE_PNG = ".png";
        public static final String CACHE_FILE_JPG = ".jpg";
        public static final String CACHE_FILE_JPEG = ".jpeg";
        public static final String CACHE_FILE_GIF = ".gif";
        public static final String CACHE_FILE_ICO = ".ico";
    }

    /**
     * @see <a href=" http://tool.oschina.net/commons">Http Content-type 对照表</a>
     */
    public class MimeType {
        public static final String MIME_TYPE_JS = "application/javascript";
        public static final String MIME_TYPE_HTML = "text/html";
        public static final String MIME_TYPE_CSS = "text/css";
        public static final String MIME_TYPE_PNG = "image/png";
        public static final String MIME_TYPE_JPG = "image/jpeg";
        public static final String MIME_TYPE_GIF = "image/gif";
        public static final String MIME_TYPE_ICON = "image/x-icon";
    }
}
