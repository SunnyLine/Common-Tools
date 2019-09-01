package com.pullein.common.android.web;

import android.net.Uri;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CacheWebClient extends WebViewClient {
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return WebCacheManager.shouldInterceptRequest(view.getContext(), Uri.parse(url));
    }

}
