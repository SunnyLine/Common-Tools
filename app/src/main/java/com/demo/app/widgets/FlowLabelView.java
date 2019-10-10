package com.demo.app.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.demo.app.R;
import com.pullein.common.utils.CollectionUtil;

import java.util.List;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/10/10
 */
public class FlowLabelView extends View {
    private float paddingLeft;
    private float paddingTop;
    private float paddingRight;
    private float paddingBottom;
    private float paddingInnerLeft;
    private float paddingInnerTop;
    private float paddingInnerRight;
    private float paddingInnerBottom;
    private float textSize;
    private int textColor;
    private Drawable textBg;

    private List<String> mData;

    public FlowLabelView(Context context) {
        this(context, null);
    }

    public FlowLabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParameter(context, attrs, defStyleAttr);
    }

    private void initParameter(Context context, AttributeSet attributeSet, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.FlowLabelView);
        textColor = array.getColor(R.styleable.FlowLabelView_label_text_color, Color.parseColor("#999999"));
        textSize = array.getDimension(R.styleable.FlowLabelView_label_text_size, 12);
        textBg = array.getDrawable(R.styleable.FlowLabelView_label_text_bg);
        paddingLeft = array.getDimension(R.styleable.FlowLabelView_label_padding, 10);
        paddingTop = array.getDimension(R.styleable.FlowLabelView_label_padding, 10);
        paddingRight = array.getDimension(R.styleable.FlowLabelView_label_padding, 10);
        paddingBottom = array.getDimension(R.styleable.FlowLabelView_label_padding, 10);
        paddingLeft = array.getDimension(R.styleable.FlowLabelView_label_padding_left, 10);
        paddingTop = array.getDimension(R.styleable.FlowLabelView_label_padding_top, 10);
        paddingRight = array.getDimension(R.styleable.FlowLabelView_label_padding_right, 10);
        paddingBottom = array.getDimension(R.styleable.FlowLabelView_label_padding_bottom, 10);
        paddingInnerLeft = array.getDimension(R.styleable.FlowLabelView_label_inner_padding, 5);
        paddingInnerTop = array.getDimension(R.styleable.FlowLabelView_label_inner_padding, 5);
        paddingInnerRight = array.getDimension(R.styleable.FlowLabelView_label_inner_padding, 5);
        paddingInnerBottom = array.getDimension(R.styleable.FlowLabelView_label_inner_padding, 5);
        paddingInnerLeft = array.getDimension(R.styleable.FlowLabelView_label_inner_padding_left, 5);
        paddingInnerTop = array.getDimension(R.styleable.FlowLabelView_label_inner_padding_top, 5);
        paddingInnerRight = array.getDimension(R.styleable.FlowLabelView_label_inner_padding_right, 5);
        paddingInnerBottom = array.getDimension(R.styleable.FlowLabelView_label_inner_padding_bottom, 5);
        array.recycle();
    }

    public void setData(List<String> mData) {
        this.mData = mData;
        if (CollectionUtil.isEmpty(mData)) {
            return;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
