package com.demo.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.app.fragment.WebViewFragment;
import com.pullein.common.android.FragmentHelper;
import com.pullein.common.android.web.WebCacheManager;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        FragmentHelper.add(getSupportFragmentManager(),R.id.container,new WebViewFragment());
    }
}
