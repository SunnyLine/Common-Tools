package com.pullein.common.android.editview;

import android.text.InputFilter;
import android.text.Spanned;

import com.pullein.common.android.listener.NoParameterListener;

/**
 * Common-Tools<br>
 * describe ：限制长度过滤器
 *
 * @author xugang
 * @date 2019/4/28
 */
public class LimitLengthFilter implements InputFilter {

    private int maxLength;
    private NoParameterListener mListener;

    public LimitLengthFilter(int maxLength) {
        this(maxLength, null);
    }

    public LimitLengthFilter(int maxLength, NoParameterListener mListener) {
        this.maxLength = maxLength;
        this.mListener = mListener;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        int keep = maxLength - (dest.length() - (dend - dstart));
        if (keep <= 0) {
            if (mListener != null) {
                mListener.onResult();
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
            if (mListener != null) {
                mListener.onResult();
            }
            return source.subSequence(start, keep);
        }
    }
}

