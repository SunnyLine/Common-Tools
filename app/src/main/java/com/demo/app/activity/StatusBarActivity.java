package com.demo.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.app.R;

public class StatusBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_white:
                break;
            case R.id.btn_blue:
                break;
            case R.id.btn_pic:
                break;
            default:
                break;
        }
    }
}
