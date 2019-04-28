package com.pullein.common.android.filter;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Common-Tools<br>
 * describe ：限制长度过滤器
 *
 * @author xugang
 * @date 2019/4/28
 */
public class LimitLengthFilter implements InputFilter {

    private int maxLength;
    private OverstepListener overstepListener;

    public LimitLengthFilter(int maxLength) {
        this(maxLength, null);
    }

    public LimitLengthFilter(int maxLength, OverstepListener overstepListener) {
        this.maxLength = maxLength;
        this.overstepListener = overstepListener;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        int keep = maxLength - (dest.length() - (dend - dstart));
        if (keep <= 0) {
            if (overstepListener != null) {
                overstepListener.onOverstep();
            }
            return "";
        } else if (keep >= end - start) {
            // keep original
            return null;
        } else {
            keep += start;
            if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                --keep;
                if (keep == start) {
                    return "";
                }
            }
            if (overstepListener != null) {
                overstepListener.onOverstep();
            }
            return source.subSequence(start, keep);
        }
    }

    interface OverstepListener {
        void onOverstep();
    }
}

