package com.demo.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.app.R;
import com.pullein.common.android.recyclerview.CommonDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private BaseQuickAdapter<ItemBean, BaseViewHolder> mAdapter;
    private List<ItemBean> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initRecyclerView();
    }

    private void initData() {
        mData.clear();
        mData.add(new ItemBean(R.string.edit_view, EditViewActivity.class));
        mData.add(new ItemBean(R.string.fragment_web, WebViewActivity.class));
        mData.add(new ItemBean(R.string.verification_code, VerificationCodeActivity.class));
        mData.add(new ItemBean(R.string.float_button, FloatButtonActivity.class));
        mData.add(new ItemBean(R.string.event_transmit, EventActivity.class));
        mData.add(new ItemBean(R.string.tool_bar_style, ToolbarActivity.class));
        mData.add(new ItemBean(R.string.status_bar_style, StatusBarActivity.class));
        mData.add(new ItemBean(R.string.high_light, HighLightActivity.class));
    }

    private void initRecyclerView() {
        mAdapter = new BaseQuickAdapter<ItemBean, BaseViewHolder>(R.layout.item_main_navigation, mData) {
            @Override
            protected void convert(BaseViewHolder helper, ItemBean item) {
                helper.setText(R.id.tv_item_navigation, item.itemId);
            }
        };
        mAdapter.setOnItemClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new CommonDecoration(this, Color.parseColor("#E6E6E6")));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ItemBean itemBean = (ItemBean) adapter.getItem(position);
        if (itemBean != null) {
            startActivity(new Intent(this, itemBean.aClass));
        }
    }

    class ItemBean {
        int itemId;
        Class<? extends Activity> aClass;

        public ItemBean(int itemId, Class<? extends Activity> aClass) {
            this.itemId = itemId;
            this.aClass = aClass;
        }
    }
}
