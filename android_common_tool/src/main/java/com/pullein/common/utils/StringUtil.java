package com.pullein.common.utils;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * Common-Tools<br>
 * describe ： 文本工具类
 *
 * @author xugang
 * @date 2019/7/5
 */
public class StringUtil {

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
     * @return 拼接好的HTML，需要使用{@link StringUtil#fromHtml(String)}
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

    /**
     * 字符串转整形，默认0
     *
     * @param str
     * @return
     */
    public static int str2Integer(String str) {
        int value = 0;
        try {
            value = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 字符串转换浮点数，默认0.0
     *
     * @param str 待转字符串
     * @return
     */
    public static double str2Double(String str) {
        return str2Double(str, 0.0f);
    }

    /**
     * 字符串转浮点数
     *
     * @param str 待转字符串
     * @param def 转换异常默认值
     * @return
     */
    public static double str2Double(String str, double def) {
        double value = def;
        try {
            value = Double.parseDouble(str);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 保留两位小数，但是小数位为零自动去除
     *
     * @param value
     * @return
     */
    public static String double2Str(double value) {
        String result = "0";
        try {
            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.applyPattern("#.##");
            result = decimalFormat.format(value);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 保留两位小数，但是小数位为零自动去除
     *
     * @param value
     * @return
     */
    public static String double2Str(String value) {
        return double2Str(str2Double(value));
    }

    /**
     * 保留两位小数，位数不够自动补零
     *
     * @param value
     * @return
     */
    public static String double2StrTwoDecimal(String value) {
        return double2StrTwoDecimal(str2Double(value));
    }

    /**
     * 保留两位小数，位数不够自动补零
     *
     * @param value
     * @return
     */
    public static String double2StrTwoDecimal(double value) {
        String result = "0.00";
        try {
            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.applyPattern("0.00");
            result = decimalFormat.format(value);
        } catch (ArithmeticException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 正则校验
     * @param data
     * @param regular
     * @return
     */
    public static boolean regular(String data, String regular) {
        if (TextUtils.isEmpty(data)) {
            return false;
        }
        if (TextUtils.isEmpty(regular)) {
            return false;
        }
        return data.matches(regular);
    }
}
