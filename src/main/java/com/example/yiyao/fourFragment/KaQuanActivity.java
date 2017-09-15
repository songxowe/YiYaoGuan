package com.example.yiyao.fourFragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.yiyao.Adapter.KaQuanAdapter;
import com.example.yiyao.R;
import com.example.yiyao.pojo.KaQuan;

import java.util.ArrayList;
import java.util.List;

public class KaQuanActivity extends AppCompatActivity {
    private RecyclerView kaQuanRecycler;
    private KaQuanAdapter adapter;
    private List<KaQuan> kaQuans;
    private ImageView kaquan_return;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ka_quan);
        initDate();
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        kaquan_return=(ImageView)findViewById(R.id.kaquan_return);

    }

    /**
     * 初始化数据
     */
    private void initDate() {
        kaQuanRecycler = (RecyclerView) findViewById(R.id.four_recycler_kaquan);
        kaQuans = new ArrayList<>();
        //***临时数据*************************************
        kaQuans.add(new KaQuan(1, "新会员专享-39减5", "2016-06-13", "2016-06-20"));
        kaQuans.add(new KaQuan(2, "新会员专享-99减10", "2016-06-13", "2016-06-20"));
        kaQuans.add(new KaQuan(3, "新会员专享-329减30", "2016-06-13", "2016-06-20"));
        kaQuans.add(new KaQuan(4, "100元新会员壹券", "2016-06-13", "2016-06-20"));
        kaQuans.add(new KaQuan(5, "100元-积分壹券", "2016-06-13", "2016-06-20"));
        kaQuans.add(new KaQuan(6, "100元券-壹券专区专用", "2016-06-13", "2016-06-20"));
        //**********************************************

        adapter = new KaQuanAdapter(this, kaQuans);
        kaQuanRecycler.setLayoutManager(new LinearLayoutManager(this));
        kaQuanRecycler.setAdapter(adapter);
    }
    //btn使用说明事件
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.kaquan_return:
                KaQuanActivity.this.finish();
                break;
            case R.id.shiyongshuoming:
                startActivity(new Intent(this, YiJian_ShiYongActivity.class));
                break;
        }
    }
}
