package com.example.yiyao.OneFragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yiyao.OneActivity.FindFuKeActivity;
import com.example.yiyao.OneActivity.FindGanmaoActivity;
import com.example.yiyao.OneActivity.FindHuXiActivity;
import com.example.yiyao.OneActivity.FindWaikeActivity;
import com.example.yiyao.OneActivity.FindXiaoHuaActivity;
import com.example.yiyao.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.jude.rollviewpager.hintview.IconHintView;

/**
 * 今日特卖
 */
public class NowSaleFragment extends Fragment {
    //初始化控件
    private RollPagerView mRollViewPager;
    private ImageView imageGanMao,imageXiaoHua,imageWaike,imageFuke,
            imageHuxi,imageMore,imageYikaTong,imageJuan,imageJinaKang,imageDaoJIa;



    public NowSaleFragment() {
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_sale, container, false);
        initView(view);
        initData(view);
        initOnClick(view);
        return view;
    }

    /**
     * 点击事件
     * @param view
     */
    private void initOnClick(View view) {
        //感冒的点击事件
        imageGanMao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getActivity(), FindGanmaoActivity.class));
            }
        });

        //消化的点击事件
        imageXiaoHua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FindXiaoHuaActivity.class));
            }
        });

        //外科的点击事件
        imageWaike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FindWaikeActivity.class));
            }
        });

        //呼吸的点击事件
        imageHuxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FindHuXiActivity.class));
            }
        });
        //妇科的点击事件
        imageFuke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FindFuKeActivity.class));
            }
        });

        //更多的点击事件
        imageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"更多,敬请期待",Toast.LENGTH_SHORT).show();
            }
        });

        //医卡通的点击事件
        imageYikaTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FindGanmaoActivity.class));
            }
        });

        //优惠劵的点击事件
        imageJuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FindGanmaoActivity.class));
            }
        });

        //健康的点击事件
        imageJinaKang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FindGanmaoActivity.class));
            }
        });

        //到家的点击事件
        imageDaoJIa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * 初始化数据
     * @param view
     */
    private void initData(View view) {
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(1000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new TestNormalAdapter());
        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        mRollViewPager.setHintView(new IconHintView(getActivity(), R.drawable.point_focus, R.drawable.point_normal));
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.YELLOW, Color.WHITE));
        //mRollViewPager.setHintView(new TextHintView(getActivity()));
        //mRollViewPager.setHintView(null);

    }
    /**
     * 初始化视图
     * @param view
     */
    private void initView(View view) {
        mRollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        imageGanMao=(ImageView)view.findViewById(R.id.now_sale_ganmao);
        imageXiaoHua=(ImageView)view.findViewById(R.id.now_sale_xiaohua);
        imageWaike=(ImageView)view.findViewById(R.id.now_sale_waike);
        imageHuxi=(ImageView)view.findViewById(R.id.now_sale_huxi);
        imageFuke=(ImageView)view.findViewById(R.id.now_sale_fuke);
        imageMore=(ImageView)view.findViewById(R.id.now_sale_more);
        imageYikaTong=(ImageView)view.findViewById(R.id.now_sale_yikatong);
        imageJuan=(ImageView)view.findViewById(R.id.now_sale_juan);
        imageJinaKang=(ImageView)view.findViewById(R.id.now_sale_jiankang);
        imageDaoJIa=(ImageView)view.findViewById(R.id.now_sale_daojia);
    }





    /**
     * 加载广告的适配器
     */
    private class TestNormalAdapter extends StaticPagerAdapter {
        private int[] imgs = {
                R.drawable.jingdong_show_2,
                R.drawable.duanwu,
                R.drawable.huawei_show,
        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }


}
