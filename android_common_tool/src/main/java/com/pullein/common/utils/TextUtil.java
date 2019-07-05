package com.pullein.common.utils;

import android.text.Html;
import android.text.Spanned;

/**
 * Common-Tools<br>
 * describe ： 文本工具类
 *
 * @author xugang
 * @date 2019/7/5
 */
public class TextUtil {

    /**
     * 生成一个有颜色文本的html,不能直接填充View
     *
     * @return
     */
    public static String getHtml(String text, String textColor) {
        String htmlTemplate = "<font color = \"%1$s\">%2$s</font>";
        return String.format(htmlTemplate, textColor, text);
    }

    /**
     * 获取一个含有多种颜色的HTML,不能直接填充View<br>
     * eg:<br>TextUtil.getHtml("Today"+TextUtil.getHtml(" is ","#FF4400")+TextUtil.getHtml(" nice ","#00FF60")+TextUtil.getHtml(" day.","#EE90FF"))<br>
     *
     * @return 拼接好的HTML，需要使用{@link TextUtil#fromHtml(String)}
     */
    public static String getHtml(String mixedText) {
        String htmlTemplate = "<html>%1$s</html>";
        return String.format(htmlTemplate, mixedText);
    }

    /**
     * 得到一个解析后的颜色文本，直接用于填充UI<br>
     * eg:<br>TextUtil.fromHtml("Java", "#FF60EE")
     *
     * @param text
     * @param textColor
     * @return
     */
    public static Spanned fromHtml(String text, String textColor) {
        return fromHtml(getHtml(text, textColor));
    }

    /**
     * 得到一个解析后的颜色文本,一个字符串有多种颜色或者不连续的字符串需要上色使用此方法，直接用于填充UI
     * eg:<br> TextUtil.fromHtml("Today", TextUtil.getHtml(" is ", "#FF60EE"), TextUtil.getHtml(" nice ", "#90E60EE"),"day!!!")
     *
     * @param strings
     * @return
     */
    public static Spanned fromMixedHtml(String... strings) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        for (String text : strings) {
            text = text.replace("<html>", "").replace("</html>", "");
            builder.append(text);
        }
        builder.append("</html>");
        return fromHtml(builder.toString());
    }

    /**
     * 得到一个解析后的颜色文本，直接用于填充UI
     *
     * @param html 必须是html
     * @return 将html Spanned
     * @see Html#fromHtml(String, int, Html.ImageGetter, Html.TagHandler)
     */
    public static Spanned fromHtml(String html) {
        Spanned text;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            text = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT);
        } else {
            text = Html.fromHtml(html);
        }
        return text;
    }
}
