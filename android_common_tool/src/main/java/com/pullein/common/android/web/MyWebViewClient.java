package com.pullein.common.android.web;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.pullein.common.utils.Log;

public class MyWebViewClient extends CacheWebClient {
    private boolean loadSuccess;
    private IWebView mView;

    MyWebViewClient(IWebView mView) {
        this.mView = mView;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        loadSuccess = true;
        view.getSettings().setBlockNetworkImage(true);
        if (mView != null) {
            mView.hideErrorView();
            mView.onPageStarted(url);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        view.getSettings().setBlockNetworkImage(false);
        if (mView != null) {
            if (loadSuccess) {
                mView.showWebView();
                mView.onPageFinished(url);
                return;
            }
            mView.hideProgressBar();
            mView.hideWebView();
            mView.showErrorView();
        }
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            boolean result = dealOverrideUrlLoading(view, url);
            if (result) {
                return true;
            }
        }
        return super.shouldOverrideUrlLoading(view, url);
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Uri uri = request.getUrl();
            boolean result = dealOverrideUrlLoading(view, uri.toString());
            if (result) {
                return true;
            }
        }
        return super.shouldOverrideUrlLoading(view, request);
    }


    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            loadSuccess = false;
            Log.e("onReceivedError failingUrl = " + request.getUrl() + "\terrorCode = " + error.getErrorCode() + "\tdescription = " + error.getDescription());
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            loadSuccess = false;
            Log.e("onReceivedError failingUrl = " + failingUrl + "\terrorCode = " + errorCode + "\tdescription = " + description);
        }
    }

    private boolean dealOverrideUrlLoading(WebView view, String requestUrl) {
        if (alipay(requestUrl)) {
            return true;
        }
        if (requestUrl.startsWith("tel:")) {
            if (mView != null) {
                mView.callNativePhone(Uri.parse(requestUrl));
            }
            return true;
        }
        if (requestUrl.startsWith("weixin://") || requestUrl.startsWith("alipays://")) {
            if (mView != null) {
                try {
                    mView.startActivityByUri(Uri.parse(requestUrl));
                } catch (Exception e) {
                    if (mView != null) {
                        mView.toast(requestUrl.startsWith("weixin://") ? "请先安装微信" : "请先安装支付宝");
                    }
                }
            }
            return true;
        }
        if (mView != null) {
            return mView.shouldOverrideUrlLoading(requestUrl);
        }
        return false;
    }


    private boolean alipay(final String url) {
        if (url.contains("unitradeadapter.alipay.com")) {
            try {
                if (mView != null) {
                    mView.openAliPay(url);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public void release() {
        if (mView != null) {
            mView = null;
        }
    }
}
