package com.demo.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.app.R;
import com.demo.app.widgets.FlowLabelView;

import java.util.ArrayList;
import java.util.List;

public class LabelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);

        List<String> list = new ArrayList<>();
        list.add("aaaaaa");
        list.add("下班再送");
        list.add("啦啦啦");
        list.add("二恶烷二无");

        FlowLabelView flowLabelView = findViewById(R.id.flowLabelView);
        flowLabelView.setData(list);
    }
}
