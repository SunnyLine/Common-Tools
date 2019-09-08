package com.demo.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.app.fragment.WebViewFragment;
import com.pullein.common.android.FragmentHelper;

public class WebViewActivity extends AppCompatActivity {
    WebViewFragment mWebViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mWebViewFragment = new WebViewFragment();
        FragmentHelper.add(getSupportFragmentManager(), R.id.container, mWebViewFragment);
    }

    @Override
    public void onBackPressed() {
        if (mWebViewFragment != null && mWebViewFragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
