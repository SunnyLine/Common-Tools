package com.pullein.common.utils;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/9/19
 */
public class SpannableUtil {
    private List<TextInfo> list = new ArrayList<>();

    public void clear() {
        list.clear();
    }

    /**
     * 添加字符串
     *
     * @param text    字符串文本
     * @param colorId 字符串颜色
     * @return
     */
    public SpannableUtil addText(String text, @ColorInt int colorId) {
        return addText(text, colorId, 0);
    }

    /**
     * 添加字符串
     *
     * @param text       字符串文本
     * @param colorId    字符串颜色
     * @param sizePixels 字符串字体大小，单位像素 {@link DensityUtil#sp2px(Context, int)}
     * @return
     */
    public SpannableUtil addText(String text, @ColorInt int colorId, int sizePixels) {
        TextInfo textInfo = new TextInfo();
        textInfo.text = text;
        textInfo.colorId = colorId;
        textInfo.textSize = sizePixels;
        list.add(textInfo);
        return this;
    }

    public CharSequence create() {
        StringBuilder sb = new StringBuilder();
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        Iterator<TextInfo> iterator = list.iterator();
        while (iterator.hasNext()) {
            TextInfo textInfo = iterator.next();
            if (TextUtils.isEmpty(textInfo.text)) {
                iterator.remove();
                continue;
            }
            stringBuilder.append(textInfo.text);
            stringBuilder.setSpan(new ForegroundColorSpan(textInfo.colorId), sb.length(), sb.length() + textInfo.text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (textInfo.textSize > 0) {
                stringBuilder.setSpan(new AbsoluteSizeSpan(textInfo.textSize), sb.length(), sb.length() + textInfo.text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            sb.append(textInfo.text);
        }
        clear();
        return stringBuilder;
    }

    class TextInfo {
        String text;
        int textSize;
        @ColorInt
        int colorId;
    }
}
