package com.pullein.common.android.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.annotation.IdRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.pullein.common.utils.Log;
import com.pullein.common.utils.SPUtil;

import static android.content.Context.WINDOW_SERVICE;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

/**
 * Common-Tools<br>
 * describe ：悬浮按钮
 *
 * @author xugang
 * @date 2019/7/28
 */
public class FloatingButtonManager {
    private static final String TAG = "FloatingButton";

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private ViewHelper mViewHelper;

    private int windowWidth;
    private int windowHeight;

    private boolean isAddFlowButton = false;

    private final static String FLOW_X = "flow_x";
    private final static String FLOW_Y = "flow_y";

    private View.OnClickListener mOnClickListener;

    public FloatingButtonManager(View view) {
        Context context = view.getContext();
        mViewHelper = new ViewHelper(view);
        view.setOnTouchListener(new FloatingOnTouchListener());
        mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(metric);
        windowWidth = metric.widthPixels;
        windowHeight = metric.heightPixels;
        Log.d(TAG, "windowWidth = " + windowWidth + "\twindowHeight = " + windowHeight);
        mLayoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.gravity = Gravity.NO_GRAVITY;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.x = SPUtil.getInt(context, FLOW_X, -windowWidth / 2);
        mLayoutParams.y = SPUtil.getInt(context, FLOW_Y, 0);
    }

    public boolean isAddFlowButton() {
        return isAddFlowButton;
    }

    public void setOnClickListener(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void hideFlowButton() {
        try {
            if (!isAddFlowButton) {
                return;
            }
            mViewHelper.getRootView().setVisibility(View.GONE);
            notifyFlowButton();
        } catch (Exception e) {
            Log.e(TAG, "hideFlowButton Exception :" + e.toString());
        }
    }

    public void showFlowButton() {
        try {
            if (!isAddFlowButton) {
                addFlowButton();
                return;
            }
            mViewHelper.getRootView().setVisibility(View.VISIBLE);
            notifyFlowButton();
        } catch (Exception e) {
            Log.e(TAG, "showFlowButton Exception :" + e.toString());
        }
    }

    public void removeFlowButton() {
        try {
            if (isAddFlowButton) {
                isAddFlowButton = false;
                mWindowManager.removeView(mViewHelper.getRootView());
            }
        } catch (Exception e) {
            Log.e(TAG, "removeFlowButton Exception :" + e.toString());
        }
    }

    public void addFlowButton() {
        try {
            if (!isAddFlowButton) {
                isAddFlowButton = true;
                mViewHelper.getRootView().setVisibility(View.VISIBLE);
                mWindowManager.addView(mViewHelper.getRootView(), mLayoutParams);
            }
        } catch (Exception e) {
            Log.e(TAG, "addFlowButton Exception :" + e.toString());
            isAddFlowButton = false;
        }
    }

    public void notifyFlowButton() {
        try {
            mWindowManager.updateViewLayout(mViewHelper.getRootView(), mLayoutParams);
        } catch (Exception e) {
            Log.e(TAG, "notifyFlowButton Exception :" + e.toString());
        }
    }

    public <T extends View> T findView(@IdRes int viewId) {
        return mViewHelper.findView(viewId);
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;
        private int downX;
        private int downY;
        private final int offset = 10;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    downX = (int) event.getRawX();
                    downY = (int) event.getRawY();
                    return false;
                case ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    mLayoutParams.x = mLayoutParams.x + movedX;
                    mLayoutParams.y = mLayoutParams.y + movedY;
                    notifyFlowButton();
                    return true;
                case ACTION_UP:
                    if (Math.abs(downX - x) < offset && Math.abs(downY - y) < offset) {
                        if (mOnClickListener != null) {
                            mOnClickListener.onClick(v);
                        }
                        return true;
                    }
                    if (x < (windowWidth / 2)) {
                        mLayoutParams.x = -((windowWidth - mViewHelper.getRootView().getMeasuredWidth()) / 2);
                    } else {
                        mLayoutParams.x = (windowWidth - mViewHelper.getRootView().getMeasuredWidth()) / 2;
                    }
                    notifyFlowButton();
                    SPUtil.putInt(mViewHelper.getRootView().getContext(), FLOW_X, mLayoutParams.x);
                    SPUtil.putInt(mViewHelper.getRootView().getContext(), FLOW_Y, mLayoutParams.y);

                    return true;
                default:
                    break;
            }
            return false;
        }
    }
}
