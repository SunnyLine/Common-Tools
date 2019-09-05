package com.pullein.common.android.editview;

import android.text.InputFilter;
import android.text.Spanned;

import com.pullein.common.android.listener.NoParameterListener;
import com.pullein.common.utils.StringUtil;

/**
 * Common-Tools<br>
 * describe ：通用过滤器，传入正则和回掉
 *
 * @author xugang
 * @date 2019/9/5
 */
public class CommonInputFilter implements InputFilter {
    private String rule;
    private NoParameterListener mListener;

    public CommonInputFilter(String rule) {
        this.rule = rule;
    }

    public CommonInputFilter(String rule, NoParameterListener mListener) {
        this.rule = rule;
        this.mListener = mListener;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (dstart == dend) {
            StringBuffer sb = new StringBuffer(source);
            if (!StringUtil.regular(sb.toString(), rule)) {
                if (mListener != null) {
                    mListener.onResult();
                }
                return "";
            }
        }
        return source;
    }
}
