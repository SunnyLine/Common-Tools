package com.pullein.common.android.editview;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Common-Tools<br>
 * describe ：自动显示删除按钮，使用drawableRight
 *
 * @author xugang
 * @date 2019/9/5
 */
public class CleanableEditText extends AppCompatEditText {
    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;
    private Drawable mClearDrawable;
    private OnClearListener onClearListener;
    private OnKeyBoardHideListener onKeyBoardHideListener;

    public void setOnKeyBoardHideListener(OnKeyBoardHideListener onKeyBoardHideListener) {
        this.onKeyBoardHideListener = onKeyBoardHideListener;
    }

    public void setOnClearListener(OnClearListener onClearListener) {
        this.onClearListener = onClearListener;
    }

    public CleanableEditText(Context context) {
        super(context);
    }

    public CleanableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CleanableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (onKeyBoardHideListener != null && keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == 1) {
            super.onKeyPreIme(keyCode, event);
            onKeyBoardHideListener.onHide(keyCode, event);
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(text.length() > 0);
    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            setSelection(length());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    Drawable drawable = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                            && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                        setText("");
                        if (onClearListener != null) {
                            onClearListener.onClear();
                        }
                    }
                    break;
                default:
                    break;
            }
            return super.onTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setClearIconVisible(boolean visible) {
        if (mClearDrawable == null) {
            mClearDrawable = getCompoundDrawables()[DRAWABLE_RIGHT];
        }
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[DRAWABLE_LEFT], getCompoundDrawables()[DRAWABLE_TOP],
                visible ? mClearDrawable : null, getCompoundDrawables()[DRAWABLE_BOTTOM]);
    }

    public interface OnKeyBoardHideListener {
        void onHide(int keyCode, KeyEvent event);
    }

    public interface OnClearListener {
        void onClear();
    }
}
