package com.example.yiyao.fourFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.yiyao.LoginActivity;
import com.example.yiyao.R;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.pojo.User;

public class SheZhiActivity extends AppCompatActivity {
    private LinearLayout zhangHao, qingChu, keFu, guanYu, exit;
    private ImageView shezhireturn;
    private User user;
    private Button btnExit;
    private boolean isLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_she_zhi);
        initView();
        initDate();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        shezhireturn=(ImageView)findViewById(R.id.shezhireturn);
        zhangHao = (LinearLayout) findViewById(R.id.shezhi_zhanghao);
        qingChu = (LinearLayout) findViewById(R.id.shezhi_qingchu);
        keFu = (LinearLayout) findViewById(R.id.shezhi_kefu);
        guanYu = (LinearLayout) findViewById(R.id.shezhi_guanyu);
        exit = (LinearLayout) findViewById(R.id.shezhi_exit);
        btnExit = (Button) findViewById(R.id.btn_zhuxiao_exit);
        //判断选项存储 中是否 有账号

        user = TmallUtils.getUser(this);
        if (user.getU_id() > 0) {
            btnExit.setText("退出登录");
            isLogin=true;
        } else {
            btnExit.setText("前去登陆");
            isLogin=false;
        }

    }

    /**
     * 初始化数据
     */
    private void initDate() {
        //关于
        guanYu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SheZhiActivity.this, GuanYuActivity.class));
            }
        });
        //清除缓存
        qingChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              TmallUtils.showToast(SheZhiActivity.this,"缓存已经清楚");
            }
        });
        //联系客服
        keFu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SheZhiActivity.this);
                View view = getLayoutInflater().inflate(R.layout.shezhi_tishi_dialog, null);
                builder.setView(view);
                builder.setPositiveButton(R.string.four_tishi_btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String phoneNumber = "4006063111";
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        //url统一资源定位符
                        intent.setData(Uri.parse("tel:" + phoneNumber));
                        //开启系统拨号器
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton(R.string.four_tishi_btn_canl, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });

        //我的账号
        zhangHao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLogin){
                    startActivity(new Intent(SheZhiActivity.this, WoDeZhangHaoActivity.class));
                }else{
                    QingdengLu();
                }
            }
        });
        //退出账号
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    //如果是已登录状态，则注销去登录页面
                    User user = new User();
                    user.setU_id(0);
                    user.setU_nickname("");
                    user.setU_mobile("");
                    user.setU_password("");
                    user.setU_imageurl("");
                    user.setU_backgroundimageurl("");
                    TmallUtils.saveUser(SheZhiActivity.this, user);
                    isLogin=false;
                } else {
                    //如果是未登录状态，则还是去登录页面
                    isLogin=true;
                }
                startActivity(new Intent(SheZhiActivity.this, LoginActivity.class));
                SheZhiActivity.this.finish();
            }
        });

        shezhireturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SheZhiActivity.this.finish();
            }
        });

    }



    //封装未登录跳转方法
    private void QingdengLu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("亲,查看此项须先登录哟");
        builder.setMessage("前去登陆...");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    startActivity(new Intent(SheZhiActivity.this, LoginActivity.class));
                }
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {

                }
            }
        });
        builder.create().show();
    }

}
