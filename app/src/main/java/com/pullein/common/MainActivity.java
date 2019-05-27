package com.pullein.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pullein.common.utils.DateFormatUtil;
import com.pullein.common.utils.ZipUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    TextView tvTime;
    TextView tvDate;
    TextView tvDateBeijing;
    TextView tvTimeZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTime = findViewById(R.id.tvTime);
        tvDate = findViewById(R.id.tvDate);
        tvTimeZone = findViewById(R.id.tvTimeZone);
        tvDateBeijing = findViewById(R.id.tvDateBeijing);
        refresh();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            refresh();
        } else {
            String path = Environment.getExternalStorageDirectory() + File.separator + "doc" + File.separator + "dist.tar.gz";
            boolean result = ZipUtil.unZip(path, Environment.getExternalStorageDirectory() + File.separator + "doc" + File.separator);
            Toast.makeText(this, result ? "解压成功" : "解压失败", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(this, FragmentActivity.class));
        }
    }

    public void refresh() {
        long time = System.currentTimeMillis();
        tvTime.setText("时间戳:" + time);
        tvTimeZone.setText("当前手机时区:" + DateFormatUtil.getCurTimeZone());
        tvDate.setText("系统时间:" + DateFormatUtil.format2GMT(time, DateFormatUtil.PATTERN_YMD_HMS));
        tvDateBeijing.setText("东八区时间:" + DateFormatUtil.format2GMT(time, DateFormatUtil.PATTERN_YMD_HMS, DateFormatUtil.GMT_8));
    }
}
