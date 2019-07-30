package com.demo.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Stack;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/7/29
 */
public class VerificationCodeLayout extends LinearLayout implements TextWatcher {

    private Drawable editBg;
    private float editWidth;
    private int editNumber;
    private EditText[] editTexts;
    private Stack<CharSequence> msg = new Stack<>();
    private int index = 0;

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
        editBg = array.getDrawable(R.styleable.VerificationCodeView_vcv_edit_bg);
        array.recycle();
        initView(context);
    }

    private void initView(Context context) {
        editTexts = new EditText[editNumber];
        addPlaceHolder(context);
        for (int i = 0; i < editNumber; i++) {
            EditText editText = new EditText(context);
            editText.setBackground(editBg);
            editText.setGravity(Gravity.CENTER);
            editText.setKeyListener(null);
            editText.setSingleLine();
            editText.addTextChangedListener(this);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            LinearLayout.LayoutParams params = new LayoutParams((int) editWidth, (int) editWidth);
            addView(editText, params);
            editTexts[i] = editText;
            addPlaceHolder(context);
        }
    }

    private void addPlaceHolder(Context context) {
        TextView textView = new TextView(context);
        textView.setVisibility(INVISIBLE);
        LinearLayout.LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        addView(textView, params);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
