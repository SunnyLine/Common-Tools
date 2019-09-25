package com.pullein.common.android.web.cache;

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

    class CacheType{
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
    class MimeType{
        public static final String MIME_TYPE_JS = "application/javascript";
        public static final String MIME_TYPE_HTML = "text/html";
        public static final String MIME_TYPE_CSS = "text/css";
        public static final String MIME_TYPE_PNG = "image/png";
        public static final String MIME_TYPE_JPG = "image/jpeg";
        public static final String MIME_TYPE_GIF = "image/gif";
        public static final String MIME_TYPE_ICON = "image/x-icon";
    }
}
