package com.pullein.common.android.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Common-Tools<br>
 * describe ：共用的pop框
 *
 * @author xugang
 * @date 2019/4/28
 */
public class CommonPopupWindow extends PopupWindow {

    private View mContextView;
    public static final int WRAP = ViewGroup.LayoutParams.WRAP_CONTENT;
    public static final int MATCH = ViewGroup.LayoutParams.MATCH_PARENT;
    private ViewHelper viewHelper;

    public CommonPopupWindow(Context context, @LayoutRes int layoutId) {
        init(context, layoutId, MATCH, WRAP, true);
    }

    public CommonPopupWindow(View view) {
        init(view, MATCH, WRAP, true);
    }

    public CommonPopupWindow(Context context, @LayoutRes int layoutId, int width, int height) {
        this(context, layoutId, width, height, true);
    }

    public CommonPopupWindow(View view, int width, int height) {
        this(view, width, height, true);
    }

    public CommonPopupWindow(Context context, @LayoutRes int layoutId, int width, int height, boolean isOutsideTouchable) {
        super(context);
        init(context, layoutId, width, height, isOutsideTouchable);
    }

    public CommonPopupWindow(View view, int width, int height, boolean isOutsideTouchable) {
        super(view.getContext());
        init(view, width, height, isOutsideTouchable);
    }

    private void init(Context context, @LayoutRes int layoutId, int width, int height, boolean isOutsideTouchable) {
        init(LayoutInflater.from(context).inflate(layoutId, null), width, height, isOutsideTouchable);
    }

    private void init(View view, int width, int height, boolean isOutsideTouchable) {
        mContextView = view;
        viewHelper = new ViewHelper(view);
        setContentView(view);
        //解决Navigation Bar遮挡PopupWindow的解决方法，这个方法不会影响到状态栏的颜色改变
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(width);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight(height);
        // 使其聚集
        setFocusable(true);
        // 设置允许在外点击消失
        setOutsideTouchable(isOutsideTouchable);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        setBackgroundDrawable(new BitmapDrawable());
    }

    public ViewHelper getViewManager() {
        return viewHelper;
    }

    /**
     * 是否允许PopupWindow外部事件，包含back键
     *
     * @param flag
     * @return
     */
    public CommonPopupWindow setAllowOutsideTouchEvent(boolean flag) {
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        setBackgroundDrawable(flag ? new BitmapDrawable() : null);
        return this;
    }

    public CommonPopupWindow setOutsideAlpha(final Activity activity, float alpha) {
        setWindowAlpha(activity, alpha)
                .setDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        setWindowAlpha(activity, 1);
                    }
                });
        return this;
    }

    public CommonPopupWindow setOutsideAlpha(final Activity activity) {
        setWindowAlpha(activity, 0.3f)
                .setDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        setWindowAlpha(activity, 1);
                    }
                });
        return this;
    }

    /**
     * 自动关闭，从show开始计时
     * 在show之前调用
     *
     * @param delayed 单位秒
     * @return
     */
    public CommonPopupWindow setAutoClose(int delayed) {
        if (delayed < 1) {
            return this;
        }
        new CountDownTimer(1000, delayed * 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                dismiss();
            }
        }.start();
        return this;
    }

    public CommonPopupWindow setAnimStyle(@StyleRes int styleId) {
        setAnimationStyle(styleId);
        return this;
    }

    public CommonPopupWindow setDismissListener(OnDismissListener onDismissListener) {
        setOnDismissListener(onDismissListener);
        return this;
    }

    /**
     * 设置透明度
     *
     * @param alpha
     * @return
     */
    public CommonPopupWindow setWindowAlpha(Activity activity, float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
        return this;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private Point getPoint(WindowManager wm) {
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }
}

