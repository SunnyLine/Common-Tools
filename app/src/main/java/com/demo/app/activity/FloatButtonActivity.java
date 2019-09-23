package com.demo.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.app.BuildConfig;
import com.demo.app.R;
import com.pullein.common.android.view.FloatingButtonManager;

public class FloatButtonActivity extends AppCompatActivity {

    private FloatingButtonManager floatingButtonManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_button);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher_round);
        floatingButtonManager = new FloatingButtonManager(imageView);
        floatingButtonManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FloatButtonActivity.this, "点击了悬浮框", Toast.LENGTH_SHORT).show();
            }
        });
        floatingButtonManager = new FloatingButtonManager(imageView);

    }

    public void show(View view) {
        //悬浮框
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                if (floatingButtonManager.isAddFlowButton()) {
                    floatingButtonManager.removeFlowButton();
                } else {
                    floatingButtonManager.showFlowButton();
                }
            } else {
                new AlertDialog.Builder(this)
                        .setMessage("需要取得权限以使用悬浮窗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String uri = "package:" + BuildConfig.APPLICATION_ID;
                                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                                intent.setData(Uri.parse(uri));
                                startActivity(intent);
                            }
                        }).show();
            }
            return;
        }
        floatingButtonManager.showFlowButton();
    }

    public void hide(View view) {
        if (floatingButtonManager != null) {
            floatingButtonManager.removeFlowButton();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (floatingButtonManager != null) {
            floatingButtonManager.removeFlowButton();
        }
    }
}
