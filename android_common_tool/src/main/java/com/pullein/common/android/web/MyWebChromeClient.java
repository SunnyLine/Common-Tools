package com.pullein.common.android.web;

import android.net.Uri;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.pullein.common.android.listener.MulResultListener;

import java.net.URI;
import java.net.URISyntaxException;

public class MyWebChromeClient extends WebChromeClient {
    private IWebView mView;
    private MulResultListener<ValueCallback<Uri>, ValueCallback<Uri[]>> htmlInputCallback;

    MyWebChromeClient(IWebView mView) {
        this.mView = mView;
    }

    void setHtmlInputCallback(MulResultListener<ValueCallback<Uri>, ValueCallback<Uri[]>> htmlInputCallback) {
        this.htmlInputCallback = htmlInputCallback;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (mView != null) {
            mView.onProgress(newProgress);
            if (newProgress > 95) {
                mView.hideProgressBar();
            } else {
                mView.showProgressBar(newProgress);
            }
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        try {
            URI url = new URI("http://" + title);
            if (!TextUtils.isEmpty(url.getHost())) {
                return;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (title.contains("404") || title.contains("about:blank") || title.contains("500") || title.contains("Error") || title.contains("找不到网页") || title.contains("网页无法打开")) {
            return;
        }
        if (mView != null) {
            mView.setWebTitle(title);
        }
    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        if (htmlInputCallback != null) {
            htmlInputCallback.onResult1(uploadMsg);
        }
    }

    // For Android > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);
    }

    // For Android > 5.0
    @Override
    public boolean onShowFileChooser(WebView webView,
                                     ValueCallback<Uri[]> filePathCallback,
                                     FileChooserParams fileChooserParams) {
        if (htmlInputCallback != null) {
            htmlInputCallback.onResult2(filePathCallback);
        }
        return true;
    }

    public void release() {
        if (mView != null) {
            mView = null;
        }
    }
}
