package com.pullein.common.android.web;

import android.net.Uri;

import java.util.Map;

public interface IWebView {
    void callNativePhone(Uri phoneNumUri);
    void startActivityByUri(Uri uri);
    void openAliPay(String aliPayUri);
    void closeActivity();
    void toast(String msg);
    void onBackPressed();

    void setWebTitle(String title);
    void hideErrorView();
    void showErrorView();
    void hideWebView();
    void showWebView();
    void hideProgressBar();
    void showProgressBar(int progress);
    void loadUrl(String url);
    void loadUrl(String url, Map<String,String> head);
    void reload();
    void stopLoading();

    void onPageStarted(String url);
    void shouldOverrideUrlLoading(String url);
    void onProgress(int progress);
    void onPageFinished(String url);
}
