package com.pullein.common.android.editview;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Common-Tools<br>
 * describe ：简单TextWatcher，没必要每次使用都要重写三个方法，使用时选择重写
 *
 * @author xugang
 * @date 2019/9/5
 */
public class SimpleTextWatcher implements TextWatcher {


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

