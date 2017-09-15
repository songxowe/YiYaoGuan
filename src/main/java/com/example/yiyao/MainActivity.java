package com.example.yiyao;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.yiyao.Fragment.FourFragment;
import com.example.yiyao.Fragment.OneFragment;
import com.example.yiyao.Fragment.ThreeFragment;
import com.example.yiyao.Fragment.TwoFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FourFragment fourFragment;
    int hideItem = 0;

    /**
     * 需隐藏fragment的标识
     */
    boolean isExit = false;
    /**
     * 按两次退出的标识
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            isExit = false;
        }
    };
    /**
     * fragment的TAG
     */
    String[] tags = {"a", "b", "c", "d"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottomNavi();
        initFragment();
    }


    /**
     * 初始化底部导航
     */
    private void initBottomNavi() {
        BottomNavigationItem bottomNavigationItem1 =
                new BottomNavigationItem(R.drawable.zhuyedianji, "首页");
        BottomNavigationItem bottomNavigationItem2 =
                new BottomNavigationItem(R.drawable.yao_homedianji, "分类");
        BottomNavigationItem bottomNavigationItem3 =
                new BottomNavigationItem(R.drawable.gouwuchedianjiyao, "购物车");
        BottomNavigationItem bottomNavigationItem4 =
                new BottomNavigationItem(R.drawable.wodedianjiyao, "我的");

        bottomNavigationItem1.setInactiveIconResource(R.drawable.zhuye);
        bottomNavigationItem2.setInactiveIconResource(R.drawable.yao_fenlei);
        bottomNavigationItem3.setInactiveIconResource(R.drawable.gouwucheyao);
        bottomNavigationItem4.setInactiveIconResource(R.drawable.wodeyao);

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.addItem(bottomNavigationItem1)
                .addItem(bottomNavigationItem2)
                .addItem(bottomNavigationItem3)
                .addItem(bottomNavigationItem4)
                .initialise();
        //底部导航栏的监听事件
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                FragmentManager fm = getSupportFragmentManager();

                Fragment show = fm.findFragmentByTag(tags[position]);
                Fragment hide = fm.findFragmentByTag(tags[hideItem]);

                // 更改显示的内容 点击首页 便隐藏其他几个导航栏
                getSupportFragmentManager().beginTransaction()
                        .hide(hide)
                        .show(show)
                        .commit();

                hideItem = position;

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });


    }

    /**
     * 初始化4个Fragment
     */
    private void initFragment() {
        oneFragment=new OneFragment();
        twoFragment=new TwoFragment();
        threeFragment=new ThreeFragment();
        fourFragment=new FourFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame, oneFragment, "a")
                .add(R.id.frame, twoFragment, "b")
                .add(R.id.frame, threeFragment, "c")
                .add(R.id.frame, fourFragment, "d")
                .hide(twoFragment)
                .hide(threeFragment)
                .hide(fourFragment)
                .commit();
    }


    /**
     * 连按两次返回键，退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 连按两次退出
     */
    private void exit() {
        if (isExit) {
            //退出app
            finish();
            System.exit(0);
        } else {
            isExit = true;
            Toast.makeText(this, "在按一次退出医药", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(100, 2000);
        }
    }


}
