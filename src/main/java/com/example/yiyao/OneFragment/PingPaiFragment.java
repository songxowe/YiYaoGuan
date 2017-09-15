package com.example.yiyao.OneFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yiyao.PingPaiActivity.SearchAjiaoActivity;
import com.example.yiyao.PingPaiActivity.SearchShenBaoActivity;
import com.example.yiyao.PingPaiActivity.SearchTongrentangActivity;
import com.example.yiyao.PingPaiActivity.SerarchGanMaoActivity;
import com.example.yiyao.R;

/**
 * 品牌特惠
 */
public class PingPaiFragment extends Fragment {

    private ImageView imageGanmao,imageShenbao,imageAjiao,imagetongrenTang;
    public PingPaiFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_ping_pai, container, false);
        initView(view);
        initDate(view);
        return view;
    }



    /**
     * 初始化视图
     * @param view
     */
    private void initView(View view) {
        imageGanmao=(ImageView)view.findViewById(R.id.pingpai_ganmao);
        imageShenbao=(ImageView)view.findViewById(R.id.pingpai_shenbao);
        imageAjiao=(ImageView)view.findViewById(R.id.pingpai_a_jiao);
        imagetongrenTang=(ImageView)view.findViewById(R.id.pingpai_tongrentang);
    }


    /**
     * 初始化数据
     * @param view
     */
    private void initDate(View view) {
        //搜索感冒品牌的点击事件
        imageGanmao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SerarchGanMaoActivity.class));
            }
        });

        //搜索肾宝品牌的点击事件
        imageShenbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchShenBaoActivity.class));
            }
        });

        //搜索阿胶品牌的点击事件
        imageAjiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchAjiaoActivity.class));
            }
        });

        //搜索同仁堂品牌的点击事件

        imagetongrenTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchTongrentangActivity.class));
            }
        });

    }

}
