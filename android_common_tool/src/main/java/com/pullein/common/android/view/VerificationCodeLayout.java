package com.pullein.common.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pullein.common.R;
import com.pullein.common.utils.CommonCallBack;
import com.pullein.common.utils.KeyboardUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_DEL;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/7/29
 */
public class VerificationCodeLayout extends LinearLayout implements View.OnClickListener, InputFilter {

    private Drawable editBg;
    private int colorId;
    private float textSize;
    private float editWidth;
    private int editNumber;
    private EditText editText;
    private TextView[] textViews;
    private List<CharSequence> msg = new ArrayList<>();
    private int index = 0;
    private boolean autoInput = true;
    private boolean autoComplete = true;
    private CommonCallBack<String> mResultListener;

    public VerificationCodeLayout(Context context) {
        this(context, null);
    }

    public VerificationCodeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerificationCodeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        initParameter(context, attrs, defStyleAttr);
    }

    private void initParameter(Context context, AttributeSet attributeSet, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.VerificationCodeView);
        editNumber = array.getInteger(R.styleable.VerificationCodeView_vcv_edit_number, 4);
        editWidth = array.getDimension(R.styleable.VerificationCodeView_vcv_edit_width, 100);
        colorId = array.getColor(R.styleable.VerificationCodeView_vcv_edit_text_color, Color.parseColor("#333333"));
        float defaultTextSize = editWidth / 5;
        textSize = array.getDimension(R.styleable.VerificationCodeView_vcv_edit_text_size, defaultTextSize);
        if (defaultTextSize < textSize) {
            textSize = defaultTextSize;
        }
        editBg = array.getDrawable(R.styleable.VerificationCodeView_vcv_edit_bg);
        autoInput = array.getBoolean(R.styleable.VerificationCodeView_vcv_edit_auto_input, true);
        autoComplete = array.getBoolean(R.styleable.VerificationCodeView_vcv_edit_auto_complete, true);
        array.recycle();
        initView(context);
    }

    private void initView(Context context) {
        addPlaceHolder(context);
        addFirstEdit(context);
        addTextViews(context);
    }

    public void setOnResultListener(CommonCallBack<String> mResultListener) {
        this.mResultListener = mResultListener;
    }

    private void addPlaceHolder(Context context) {
        TextView textView = new TextView(context);
        textView.setVisibility(INVISIBLE);
        LinearLayout.LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        addView(textView, params);
    }

    private void addFirstEdit(Context context) {
        editText = new EditText(context);
        editText.setBackground(editBg);
        editText.setGravity(Gravity.CENTER);
        editText.setCursorVisible(false);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setTextSize(textSize);
        editText.setPadding(0, 0, 0, 0);
        editText.setTextColor(colorId);
        editText.setSingleLine();
        editText.setFilters(new InputFilter[]{this, new InputFilter.LengthFilter(1)});
        editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        LinearLayout.LayoutParams params = new LayoutParams((int) editWidth, (int) editWidth);
        addView(editText, params);
        editText.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        editText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KEYCODE_DEL) {
                    if (event.getAction() == ACTION_DOWN || msg.isEmpty()) {
                        return true;
                    }
                    msg.remove(msg.size() - 1);
                    if (index == 0) {
                        editText.setText(null);
                        return true;
                    }
                    textViews[--index].setText(null);
                    return true;
                }
                return false;
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (mResultListener != null) {
                        mResultListener.onResult(getResult());
                    }
                }
                return false;
            }
        });
    }

    private void addTextViews(Context context) {
        addPlaceHolder(context);
        textViews = new TextView[editNumber - 1];
        for (int i = 0; i < editNumber - 1; i++) {
            TextView textView = new TextView(context);
            textView.setBackground(editBg);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(0, 0, 0, 0);
            textView.setTextSize(textSize);
            textView.setOnClickListener(this);
            textView.setTextColor(colorId);
            textView.setSingleLine();
            LinearLayout.LayoutParams params = new LayoutParams((int) editWidth, (int) editWidth);
            textViews[i] = textView;
            addView(textView, params);
            addPlaceHolder(context);
        }
        setOnClickListener(this);
        if (autoInput) {
            KeyboardUtil.showKeyboard(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public void onClick(View v) {
        KeyboardUtil.showKeyboard(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (dstart == dend) {
            if (TextUtils.isEmpty(source)) {
                return null;
            }
            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                msg.add(source);
                return source;
            }
            if (!TextUtils.isEmpty(textViews[editNumber - 2].getText().toString().trim())) {
                return null;
            }
            TextView textView = textViews[index++];
            if (TextUtils.isEmpty(textView.getText().toString().trim())) {
                msg.add(source);
                textView.setText(source);
            }
            if (index == (editNumber - 1) && autoComplete) {
                if (mResultListener != null) {
                    mResultListener.onResult(getResult());
                    KeyboardUtil.closeKeyBoard(editText);
                }
            }
            return null;
        }
        return null;
    }

    private String getResult() {
        StringBuffer sb = new StringBuffer();
        Iterator<CharSequence> iterator = msg.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
        }
        return sb.toString();
    }
}
