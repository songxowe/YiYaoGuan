package com.example.yiyao;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

//注册页面
public class RegisterActivity extends AppCompatActivity {

    //密码输入框
    private EditText inputPassWord;
    private String password;
    private String mobile;

    private int i = 60;
    // 手机号输入框
    private EditText inputPhoneEt;
    // 验证码输入框
    private EditText inputCodeEt;
    // 获取验证码按钮
    private Button requestCodeBtn;
    // 注册按钮
    private Button commitBtn;

    //短信验证回调事件(由于此在非UI线程运行，常常将其发送到主线程操作)
    private EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            handler.sendMessage(msg);
        }
    };

    //处理事件
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                requestCodeBtn.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                requestCodeBtn.setText("获取验证码");
                requestCodeBtn.setClickable(true);
                i = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        Toast.makeText(getApplicationContext(), "提交验证码成功",
                                Toast.LENGTH_SHORT).show();
                        showCustomDialog();
                        Intent intent = new Intent(RegisterActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputPhoneEt = (EditText) findViewById(R.id.login_username);
        inputCodeEt = (EditText) findViewById(R.id.login_password);
        requestCodeBtn = (Button) findViewById(R.id.login_btn_wang);
        commitBtn = (Button) findViewById(R.id.login_btn_login);
        inputPassWord = (EditText) findViewById(R.id.login_password3);

        //初始化SDK
        SMSSDK.initSDK(this, "13e91b36b38d2", "3f7b9d1f9d1f297a92339c4222eef915");
        //注册回调事件
        SMSSDK.registerEventHandler(eh);
        //注册点击事件的方法
        initlistener();

    }


    private void initlistener() {
        requestCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNums = inputPhoneEt.getText().toString();
                // 1. 通过规则判断手机号
                if (!judgePhoneNums(phoneNums)) {
                    return;
                }
                // 2. 通过sdk发送短信验证
               SMSSDK.getVerificationCode("86", phoneNums);
                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                requestCodeBtn.setClickable(false);
                requestCodeBtn.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
            }
        });
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputPhoneEt.getText().toString().equals("") || inputPassWord.getText()
                        .toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入您的手机号码或者密码",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (inputCodeEt.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "请获取验证码并且输入到输入框中",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        String phoneNums = inputPhoneEt.getText().toString();
                        SMSSDK.submitVerificationCode("86", phoneNums, inputCodeEt.getText().toString());
                        createProgressBar();
                    }
                }
            }
        });
    }

    private void createProgressBar() {
        FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        ProgressBar mProBar = new ProgressBar(this);
        mProBar.setLayoutParams(layoutParams);
        mProBar.setVisibility(View.VISIBLE);
        layout.addView(mProBar);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }


    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11) && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 判断一个字符串的位数
     *
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }


    //5.注册事件
    private void showCustomDialog() {
        password = inputPassWord.getText().toString();
        mobile = inputPhoneEt.getText().toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                NetService.getAbsoluteUrl("RegisterServlet"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jo = new JSONObject(s);
                            if ("success".equals(jo.getString("flag"))) {
                                // 注册成功后跳转至登录
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                RegisterActivity.this.finish();
                            } else if ("error".equals(jo.getString("flag"))) {
                                // 注册失败
                                TmallUtils.showToast(getApplicationContext(), "注册失败");
                            } else if ("exist".equals(jo.getString("flag"))) {
                                // 用户名已存在
                                TmallUtils.showToast(getApplicationContext(), "用户名已存在");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", mobile);
                params.put("password", password);
                return params;
            }
        };
        NetService.getInstance(RegisterActivity.this).addToRequestQueue(stringRequest, "register");
    }


    @Override
    protected void onStop() {
        super.onStop();
        NetService.getInstance(this).cancelRequest("register");
    }



}
