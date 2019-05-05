package com.pullein.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pullein.common.utils.Log;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/5/5
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(getClass().getSimpleName() + "=== onCreate ===");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.i(getClass().getSimpleName() + "=== onPostCreate ===");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(getClass().getSimpleName() + "=== onStart ===");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(getClass().getSimpleName() + "=== onRestart ===");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(getClass().getSimpleName() + "=== onResume ===");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(getClass().getSimpleName() + "=== onPause ===");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(getClass().getSimpleName() + "=== onStop ===");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(getClass().getSimpleName() + "=== onDestroy ===");
    }
}
