package com.demo.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.demo.app.R;
import com.pullein.common.android.listener.OneParameterListener;
import com.pullein.common.android.view.VerificationCodeLayout;
import com.pullein.common.utils.CommonCallBack;

public class VerificationCodeActivity extends AppCompatActivity {

    private VerificationCodeLayout verificationCodeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        verificationCodeLayout = findViewById(R.id.verificationCodeLayout);
        verificationCodeLayout.setOnResultListener(new OneParameterListener<String>() {
            @Override
            public void onResult(String s) {
                Toast.makeText(VerificationCodeActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
