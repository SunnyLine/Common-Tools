package com.pullein.common.android.web;

import android.net.Uri;

import java.util.Map;

public interface IWebView {
    void callNativePhone(Uri phoneNumUri);

    void startActivityByUri(Uri uri);

    void startActivityByScheme(String url);

    void closeActivity();

    void toast(String msg);

    boolean onBackPressed();

    void setWebTitle(String title);

    void setWebCacheEnable(boolean enable);

    void setCacheWhiteHost(String... hosts);

    void hideErrorView();

    void showErrorView();

    void hideWebView();

    void showWebView();

    void hideProgressBar();

    void showProgressBar(int progress);

    void loadUrl(String url);

    void loadUrl(String url, Map<String, String> head);

    void reload();

    void stopLoading();

    void onPageStarted(String url);

    boolean shouldOverrideUrlLoading(String url);

    void onProgress(int progress);

    void onPageFinished(String url);

    void release();
}
