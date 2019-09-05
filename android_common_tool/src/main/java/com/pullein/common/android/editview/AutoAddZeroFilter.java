package com.pullein.common.android.editview;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * Common-Tools<br>
 * describe ：浮点数输入框过滤器，输入“.”自动补零
 *
 * @author xugang
 * @date 2019/4/28
 */
public class AutoAddZeroFilter implements InputFilter {

    private String rule = "^[0-9.]{0,}$";

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (dstart == dend) {
            if (dstart == 0) {
                if (".".equals(source)) {
                    return "0.";
                }
            }
            StringBuffer sb = new StringBuffer();
            sb.append(dest).append(source);
            if (TextUtils.isEmpty(sb) || !sb.toString().matches(rule)) {
                return "";
            }
            if (dstart == 1) {
                if ("0".equals(dest.toString())) {
                    if ("0".equals(source)) {
                        return "";
                    }
                    if (".".equals(source)) {
                        return source;
                    }
                }

            }
            if (sb.toString().contains(".") && sb.length() - 1 - sb.toString().lastIndexOf(".") > 2) {
                return "";
            }
        }
        return source;
    }
}
