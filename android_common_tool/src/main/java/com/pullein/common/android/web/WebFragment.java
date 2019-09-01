package com.pullein.common.android.web;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.pullein.common.R;
import com.pullein.common.android.listener.MulResultListener;
import com.pullein.common.utils.DateFormatUtil;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.pullein.common.utils.DateFormatUtil.PATTERN_YMDHMS;

public abstract class WebFragment extends Fragment implements IWebView, MulResultListener<ValueCallback<Uri>, ValueCallback<Uri[]>> {
    protected MyWebChromeClient myWebChromeClient;
    protected MyWebViewClient myWebViewClient;
    protected MyDownloadListener myDownloadListener;
    //支持拍照上传
    private static final String FILE_CHOOSER = "选择操作";
    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private final static int FCR = 1;

    protected WebView mWebView;

    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface", "SetJavaScriptEnabled"})
    protected void initWebView(View viewGroup,@IdRes int viewId) {
        mWebView = viewGroup.findViewById(viewId);
        mWebView.removeJavascriptInterface("accessibility");
        mWebView.removeJavascriptInterface("accessibilityTraversal");
        mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        // 设置支持本地存储
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setGeolocationEnabled(true);
        //设置支持DomStorage
        mWebView.getSettings().setDomStorageEnabled(true);

        //设置适应屏幕
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        //取得缓存路径
        String appCachePath = getContext().getCacheDir().getAbsolutePath();
        mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        mWebView.getSettings().setAppCachePath(appCachePath);
        //设置缓存
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        //设置支持JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // 页面加载完成后不会出现焦点边框
        mWebView.getSettings().setNeedInitialFocus(false);
        mWebView.getSettings().setSavePassword(false);
        //从5.0开始，默认不支持http/https混合模式，需要单独开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //下面三个各种监听
        myWebChromeClient = new MyWebChromeClient(this);
        myWebChromeClient.setHtmlInputCallback(this);
        myWebViewClient = new MyWebViewClient(this);
        myDownloadListener = new MyDownloadListener(this);
        mWebView.setWebChromeClient(myWebChromeClient);
        mWebView.setWebViewClient(myWebViewClient);
        mWebView.setDownloadListener(myDownloadListener);
    }

    @Override
    public void callNativePhone(Uri phoneNumUri) {
        startActivity(new Intent(Intent.ACTION_DIAL, phoneNumUri)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void startActivityByUri(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void openAliPay(String aliPayUri) {
        try {
            Intent intent = Intent.parseUri(aliPayUri,
                    Intent.URI_INTENT_SCHEME);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setComponent(null);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myDownloadListener != null) {
            myDownloadListener.release();
        }
        if (myWebChromeClient != null) {
            myWebChromeClient.release();
        }
        if (myWebViewClient != null) {
            myWebViewClient.release();
        }
    }

    @Override
    public void onPageStarted(String url) {

    }

    @Override
    public void shouldOverrideUrlLoading(String url) {

    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeActivity() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            WebBackForwardList webBackForwardList = mWebView.copyBackForwardList();
            boolean isRedirectPage = false;
            boolean canGoBackTwoStep = false;
            try {
                WebHistoryItem webHistoryItem = webBackForwardList.getCurrentItem();
                canGoBackTwoStep = mWebView.canGoBackOrForward(-2);
                isRedirectPage = !TextUtils.equals(webHistoryItem.getOriginalUrl(), webHistoryItem.getUrl());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (!isRedirectPage) {
                mWebView.goBack();
                return;
            }

            if (canGoBackTwoStep) {
                mWebView.goBackOrForward(-2);
                return;
            }
            closeActivity();
            return;
        }
        closeActivity();
    }

    @Override
    public void onResult1(ValueCallback<Uri> uriValueCallback) {
        mUM = uriValueCallback;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, FILE_CHOOSER), FCR);
    }

    @Override
    public void onResult2(ValueCallback<Uri[]> valueCallback) {
        if (getActivity() == null || isDetached()) {
            return;
        }
        mUMA = valueCallback;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
                takePictureIntent.putExtra("PhotoPath", mCM);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                mCM = "file:" + photoFile.getAbsolutePath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            } else {
                takePictureIntent = null;
            }
        }

        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");
        Intent[] intentArray;
        if (takePictureIntent != null) {
            intentArray = new Intent[]{takePictureIntent};
        } else {
            intentArray = new Intent[0];
        }
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, FILE_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        startActivityForResult(chooserIntent, FCR);
    }

    // Create an image file
    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat")
        String timeStamp = DateFormatUtil.format2GMT(System.currentTimeMillis(), PATTERN_YMDHMS);
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    //解决android input[type=file] 无法选择图片
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == FCR) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Uri[] results = null;
                if (resultCode == RESULT_OK) {
                    if (null == mUMA) {
                        return;
                    }
                    if (intent == null) {
                        //Capture Photo if no image available
                        if (mCM != null) {
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }

                    if (mUMA != null) {
                        mUMA.onReceiveValue(results);
                        mUMA = null;
                    }
                } else {
                    if (mUMA != null) {
                        mUMA.onReceiveValue(null);
                    }
                    if (mUM != null) {
                        mUM.onReceiveValue(null);
                    }
                }
            } else {
                if (null == mUM) {
                    return;
                }
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
            }
        }
    }
}
