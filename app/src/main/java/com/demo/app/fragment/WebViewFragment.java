package com.demo.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.app.R;
import com.pullein.common.android.web.BaseWebFragment;

public class WebViewFragment extends BaseWebFragment implements View.OnClickListener {
    String url = "https://ets2.cn";

    private ConstraintLayout mErrorLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mErrorLayout = view.findViewById(R.id.conErrorView);
        view.findViewById(R.id.tvReload).setOnClickListener(this);
        view.findViewById(R.id.tvClose).setOnClickListener(this);
        initWebView(view, R.id.webview);
        loadUrl(url);
    }

    @Override
    public void hideErrorView() {
        mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView() {
        stopLoading();
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvReload:
                reload();
                break;
            case R.id.tvClose:
                closeActivity();
                break;
        }
    }
}
