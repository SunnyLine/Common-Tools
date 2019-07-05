package com.pullein.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.pullein.common.utils.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView3)
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        textView.setText(TextUtil.fromHtml("Java", "#FF60EE"));
        textView2.setText(TextUtil.fromHtml(TextUtil.getHtml("Today " + TextUtil.getHtml("is ", "#FF60EE") + TextUtil.getHtml("nice ", "#FF6022") + "day !!!")));
        textView3.setText(TextUtil.fromMixedHtml("Today ", TextUtil.getHtml("is ", "#FF60EE"), TextUtil.getHtml("nice ", "#FF6022"), "day !!!"));
    }

}
