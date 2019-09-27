package com.pullein.common.android.web;

import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pullein.common.android.web.cache.H5CacheManager;

public class CacheWebClient extends WebViewClient {
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return H5CacheManager.getInstance().shouldInterceptRequest(view.getContext(), url);
    }
}
