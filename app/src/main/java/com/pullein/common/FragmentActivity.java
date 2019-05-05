package com.pullein.common;

import android.os.Bundle;

import com.pullein.common.android.FragmentHelper;
import com.pullein.common.fragment.FragmentA;

public class FragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentA fragmentA = new FragmentA();
        FragmentHelper.addToBackStack(getSupportFragmentManager(),R.id.conContainer,fragmentA);
    }
}
