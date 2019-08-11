package com.pullein.common.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/4/28
 */
public class KeyboardUtil {
    /**
     * 关闭软键盘
     */
    public static void closeKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示虚拟键盘
     *
     * @param v     EditView
     * @param flags 建议使用:InputMethodManager.SHOW_IMPLICIT
     */
    public static void showKeyboard(final View v, final int flags) {
        //加一个Delay 是因为系统键盘不能够在页面初始化立即弹出，需要延时
        HandlerUtil.runOnUiThreadDelay(new Runnable() {
            @Override
            public void run() {
                v.requestFocus();
                if (v instanceof EditText) {
                    String text = ((EditText) v).getText().toString().trim();
                    if (!TextUtils.isEmpty(text)) {
                        ((EditText) v).setSelection(text.length());
                    }
                }
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v, flags);
            }
        }, 200);
    }
}
