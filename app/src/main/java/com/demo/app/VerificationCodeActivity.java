package com.demo.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pullein.common.android.view.VerificationCodeLayout;
import com.pullein.common.utils.CommonCallBack;
import com.pullein.common.utils.Log;

public class VerificationCodeActivity extends AppCompatActivity {

    private VerificationCodeLayout verificationCodeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        verificationCodeLayout = findViewById(R.id.verificationCodeLayout);
        verificationCodeLayout.setOnResultListener(new CommonCallBack<String>() {
            @Override
            public void onResult(String s) {
                Log.d("============",s);
            }
        });
    }
}
