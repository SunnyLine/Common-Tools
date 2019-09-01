package com.pullein.common.android.web;

import android.net.Uri;
import android.webkit.DownloadListener;

public class MyDownloadListener implements DownloadListener {
    private IWebView mView;

    MyDownloadListener(IWebView mView) {
        this.mView = mView;
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        Uri uri = Uri.parse(url);
        if (mView != null) {
            try {
                mView.startActivityByUri(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void release() {
        if (mView != null) {
            mView = null;
        }
    }
}
