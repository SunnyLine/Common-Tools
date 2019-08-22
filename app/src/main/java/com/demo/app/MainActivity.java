package com.demo.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pullein.common.android.view.CommonPopupWindow;
import com.pullein.common.utils.DensityUtil;
import com.zyyoona7.wheel.WheelView;

import java.util.Arrays;

import static com.pullein.common.android.view.CommonPopupWindow.MATCH;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button3;
    CommonPopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button3 = findViewById(R.id.button3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                //验证码
                startActivity(new Intent(this, VerificationCodeActivity.class));
                break;
            case R.id.button2:
                //悬浮框
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.canDrawOverlays(this)) {
                        startService(new Intent(this, FlowService.class));
                    } else {
                        new AlertDialog.Builder(this)
                                .setMessage("需要取得权限以使用悬浮窗")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                                        startActivityForResult(intent, 100);
                                    }
                                }).show();
                    }
                    return;
                }
                startService(new Intent(this, FlowService.class));
                break;
            case R.id.button3:
                popupWindow = new CommonPopupWindow(this, R.layout.layout_date, MATCH, DensityUtil.dip2px(this, 340));
                WheelView<String> wvDate = popupWindow.getViewManager().findView(R.id.wv_date);
                WheelView<String> wvTime = popupWindow.getViewManager().findView(R.id.wv_time);
                wvDate.setData(Arrays.asList("今天", "明天"));
                wvTime.setData(Arrays.asList("9:00~10:00", "10:00~11:00", "11:00~12:00", "13:00~14:00", "14:00~15:00", "15:00~16:00"));
                popupWindow.showAsDropDown(button3);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 ) {
            Toast.makeText(this, "----" + resultCode, Toast.LENGTH_SHORT).show();
        }
    }
}
