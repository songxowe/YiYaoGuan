package com.example.yiyao.Fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yiyao.Adapter.OneAdapter;
import com.example.yiyao.OneActivity.FindDrugActivity;
import com.example.yiyao.OneFragment.NowSaleFragment;
import com.example.yiyao.OneFragment.PingPaiFragment;
import com.example.yiyao.OneFragment.VipFragment;
import com.example.yiyao.OneFragment.YiJuanFragment;
import com.example.yiyao.OneFragment.YiYaoFragment;
import com.example.yiyao.R;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.WebViewActivity;
import com.example.yiyao.zxing.activity.CaptureActivity;
import com.viewpagerindicator.TabPageIndicator;

//首页
public class OneFragment extends Fragment {
    //底部导航栏
    private TabPageIndicator indicator;
    private ViewPager pager;
    private OneAdapter adapter;
    //---------
    private Button button;

    private ImageView imageViewSao;



    public OneFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_one, container, false);
         indicator=(TabPageIndicator)view.findViewById(R.id.one_indicator);
         pager=(ViewPager)view.findViewById(R.id.one_pager);

        /**设置顶部导航栏*/
        setTopNavigation(view);
        //=======================
        button=(Button)view.findViewById(R.id.btn_job_find);
        imageViewSao=(ImageView)view.findViewById(R.id.one_fragment_sao);

        //搜索药品的点击事件

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FindDrugActivity.class));
            }
        });


        //二维码扫描事件
        imageViewSao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });

        return view;
    }

    /**
     * 打开二维码扫描
     */
    private void open() {
        config();
        startActivityForResult(new Intent(getActivity(),
                CaptureActivity.class), 0);
    }


    /**
     * 提高屏幕亮度
     */
    private void config() {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.screenBrightness = 1.0f;
        getActivity().getWindow().setAttributes(lp);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            Log.v("MainActivity",result);
            Intent intent=new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("webView",result);
            startActivity(intent);
        }
    }



    /**
     * 设置顶部导航栏
     * @param view
     */
    private void setTopNavigation(View view) {
        indicator.setBackgroundColor(Color.parseColor("#46a8c6"));
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        adapter = new OneAdapter(getChildFragmentManager());
        adapter.addHomeFragment("今日特卖",new NowSaleFragment());
        adapter.addHomeFragment("壹劵专区",new YiJuanFragment());
        adapter.addHomeFragment("品牌特惠",new PingPaiFragment());
        adapter.addHomeFragment("医药卡通",new YiYaoFragment());
        adapter.addHomeFragment("会员权益",new VipFragment());
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
    }

}
