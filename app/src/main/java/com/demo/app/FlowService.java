package com.demo.app;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.pullein.common.android.view.ViewHelper;
import com.pullein.common.utils.Log;
import com.pullein.common.utils.SPUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.SoftReference;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newScheduledThreadPool;

/**
 * ZTOAndroidHand<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/8/10
 */
public class FlowService extends Service {

    public final static String TAG = "FlowService";

    public static final int WHAT_OVER_FLOW = 0;
    public static final int WHAT_HIDE_FLOW = -1;
    public static final int WHAT_SHOW_FLOW = -2;
    public static final int WHAT_INIT_FLOW = -3;
    public static final int WHAT_NOTIFY_FLOW = -4;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private ViewHelper mViewHelper;
    private static InnerHandler mHandler;
    private ScheduledExecutorService executorService;

    private int windowWidth;
    private int windowHeight;

    private boolean isAddFlowButton = false;

    private final static String FLOW_X = "flow_x";
    private final static String FLOW_Y = "flow_y";

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new InnerHandler(this);
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
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
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;

        TextView textView = new TextView(this);
        textView.setBackgroundResource(R.color.colorAccent);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setText("window x = " + mLayoutParams.x + "\ny = " + mLayoutParams.y);
        mViewHelper = new ViewHelper(textView);

        if (executorService == null) {
            executorService = newScheduledThreadPool(1);
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    long timeNow = System.currentTimeMillis();
                }
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

    public void hideFlowButton() {
        try {
            if (!isAddFlowButton) {
                return;
            }
            mViewHelper.getRootView().setVisibility(View.GONE);
            notifyFlowButton();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeFlowButton() {
        try {
            if (isAddFlowButton) {
                isAddFlowButton = false;
                mWindowManager.removeView(mViewHelper.getRootView());
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public void addFlowButton() {
        try {
            if (!isAddFlowButton) {
                isAddFlowButton = true;
                mWindowManager.addView(mViewHelper.getRootView(), mLayoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyFlowButton() {
        try {
            mWindowManager.updateViewLayout(mViewHelper.getRootView(), mLayoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeFlowButton();
        if (mViewHelper != null) {
            mViewHelper = null;
        }
        executorService = null;
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    private void showFloatingWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                addFlowButton();
            }
        } else {
            addFlowButton();
        }
        mViewHelper.getRootView().setOnTouchListener(new FloatingOnTouchListener());
    }

    public void onClick(View v) {

    }

    public static void controlFlow(@FlowHandlerType int what) {
        if (mHandler != null) {
            mHandler.sendEmptyMessage(what);
        }
    }

    private static final class InnerHandler extends Handler {
        private final SoftReference<FlowService> mService;

        public InnerHandler(FlowService service) {
            this.mService = new SoftReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "收到一条消息");
            if (mService.get() == null) {
                return;
            }
            try {
                switch (msg.what) {
                    case WHAT_OVER_FLOW:
                        mService.get().stopSelf();
                        mService.clear();
                        break;
                    case WHAT_HIDE_FLOW:
                        mService.get().hideFlowButton();
                        break;
                    case WHAT_SHOW_FLOW:
                        mService.get().showFlowButton();
                        break;
                    case WHAT_INIT_FLOW:
                        break;
                    case WHAT_NOTIFY_FLOW:
                        mService.get().notifyFlowButton();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setText(int x, int y) {
        TextView textView = (TextView) mViewHelper.getRootView();
        if (textView != null) {
            textView.setText("Touch x = " + x + "\ny = " + y + "\nwindow x = " + mLayoutParams.x + "\ny = " + mLayoutParams.y);
        }
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;
        private int mX;
        private int mY;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    mX = (int) event.getRawX();
                    mY = (int) event.getRawY();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    mLayoutParams.x = mLayoutParams.x + movedX;
                    mLayoutParams.y = mLayoutParams.y + movedY;
                    setText(x, y);
                    notifyFlowButton();
                    return true;
                case MotionEvent.ACTION_UP:
//                    int changeX = Math.abs(mX - x);
//                    int changeY = Math.abs(mY - y);
//                    if (changeX < 10 && changeY < 10) {
//                        onClick(view);
//                        return true;
//                    }
//                    if (x < (windowWidth / 2)) {
//                        mLayoutParams.x = -(windowWidth / 2);
//                    } else {
//                        mLayoutParams.x = windowWidth - DensityUtil.dip2px(FlowService.this, mViewHelper.getRootView().getMeasuredWidth());
//                    }
//                    notifyFlowButton();
                    SPUtil.putInt(FlowService.this, FLOW_X, mLayoutParams.x);
                    SPUtil.putInt(FlowService.this, FLOW_Y, mLayoutParams.y);

                    return true;
                default:
                    break;
            }
            return false;
        }
    }

    @IntDef({WHAT_OVER_FLOW, WHAT_HIDE_FLOW, WHAT_SHOW_FLOW, WHAT_INIT_FLOW, WHAT_NOTIFY_FLOW})
    @Retention(RetentionPolicy.SOURCE)
    @interface FlowHandlerType {
    }
}
