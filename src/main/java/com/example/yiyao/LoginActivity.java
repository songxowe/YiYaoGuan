package com.example.yiyao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.QQUtil;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.pojo.User;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    //--再见的app_id替换222222
    public static String mAppid = "222222";
    //--腾讯对象
    public static Tencent mTencent;

    private EditText txtMobile;
    private EditText txtPassword;
    private ImageView imagePassWord;
    private User user;
    private boolean isCheckBoxChecked = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (mTencent == null) {
            //--第一步 根据app_id和上下文创建实例.
            mTencent = Tencent.createInstance(mAppid, this);
        }

        //初始化数据
        txtMobile = (EditText) findViewById(R.id.login_mobile);
        txtPassword = (EditText) findViewById(R.id.login_password);
        imagePassWord = (ImageView) findViewById(R.id.login_image_password2);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.btn_image_qq)
    public void btnqq(View view) {
        // --2.QQ登录按钮动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
        onClickLogin();
        view.startAnimation(animation);
    }

    // 3.--QQ 登录
    private void onClickLogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
        }
    }

    //5.--登录监听事件
    IUiListener loginListener = new BaseUiListener(){
        @Override
        protected void doComplete(JSONObject values) {
            Log.v("MainActivity",">>>>>>>>"+values.toString());
            //6 初始化OpenId/Token
            initOpenidAndToken(values);
        }
    };


    /**
     * 6--.解析腾讯返回的jsonObject对象数据
     * @param jsonObject
     */
    private void initOpenidAndToken(JSONObject jsonObject) {

        try {
            String token=jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires=jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId=jsonObject.getString(Constants.PARAM_OPEN_ID);
            if(!TextUtils.isEmpty(token) &&!TextUtils.isEmpty(expires)
                    &&!TextUtils.isEmpty(openId)){
                mTencent.setAccessToken(token,expires);
                mTencent.setOpenId(openId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //--第四部监听类
    private class BaseUiListener implements IUiListener {


        //完成
        @Override
        public void onComplete(Object response) {
            if (response == null) {
                QQUtil.showResultDialog(LoginActivity.this, "返回为空", "登录失败");
                return;
            }
            //如果返回的json对象为空并且对象的长度为0 也是登录失败
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                QQUtil.showResultDialog(LoginActivity.this, "返回为空", "登录失败");
                return;
            }
            //QQUtil.showResultDialog(LoginActivity.this, response.toString(), "登录成功");
            //登录成功后 跳转至MainActivity
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        /**
         * --自定义完成的方法
         */
        protected void doComplete(JSONObject values){

        }

        //--错误
        @Override
        public void onError(UiError uiError) {
            QQUtil.toastMessage(LoginActivity.this, "onError:" + uiError.errorDetail);

            //登录框的消失
            QQUtil.dismissDialog();
        }

        //--取消
        @Override
        public void onCancel() {
            QQUtil.toastMessage(LoginActivity.this, "取消");
            //登录框的消失
            QQUtil.dismissDialog();
        }
    }


    //登陆的点击事件
    @OnClick(R.id.login_btn_login)
    public void btnLogin() {
        final String mobile = txtMobile.getText().toString();
        final String password = txtPassword.getText().toString();

        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(password)) {
            TmallUtils.showToast(LoginActivity.this, "用户名或者密码不能为空");
        }

        StringRequest request = new StringRequest(Request.Method.POST, NetService.getAbsoluteUrl("LoginServlet"), new Response.Listener<String>() {
            @Override
            public void onResponse(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    if (jo.getInt("u_id") == 0) {
                        TmallUtils.showToast(LoginActivity.this, "错误的用户名或密码!");
                    } else {
                        //存储用户编号
                        User user=new User();
                        Integer id = jo.getInt("u_id");
                        user.setU_id(id);
                        //存储头像地址
                        String user_image_url="";
                        if (!jo.isNull("u_imageurl")) {
                            user_image_url = jo.getString("u_imageurl");
                        }
                        user.setU_imageurl(user_image_url);
                        //存储昵称
                        String u_nickname="";
                        if (!jo.isNull("u_nickname")) {
                            u_nickname = jo.getString("u_nickname");
                        }
                        user.setU_nickname(u_nickname);
                        //存储性别
                        String u_sex="";
                        if (!jo.isNull("u_sex")) {
                            u_sex = jo.getString("u_sex");
                        }
                        user.setU_sex(u_sex);
                        //存储背景图片
                        String u_backgroundimageurl="";
                        if (!jo.isNull("u_backgroundimageurl")) {
                            u_backgroundimageurl = jo.getString("u_backgroundimageurl");
                        }
                        user.setU_backgroundimageurl(u_backgroundimageurl);
                        //存储 手机号 和 密码
                        String mobile = jo.getString("u_mobile");
                        user.setU_mobile(mobile);
                        String password = jo.getString("u_password");
                        user.setU_password(password);
                        //
                        TmallUtils.saveUser(LoginActivity.this, user);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        LoginActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", mobile);
                params.put("password", password);
                return params;
            }
        };
        NetService.getInstance(this).addToRequestQueue(request, "login");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetService.getInstance(this).cancelRequest("login");
    }

    /*图片的监听事件
       */
    @OnClick(R.id.login_image_password2)
    public void btnImageClick() {
        if (isCheckBoxChecked) {
            txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imagePassWord.setImageResource(R.drawable.zhengyan);
            isCheckBoxChecked = false;
        } else {
            txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imagePassWord.setImageResource(R.drawable.biyan);
            isCheckBoxChecked = true;
        }
    }

    //随便逛逛点击事件
    @OnClick(R.id.login_image)
    public void btnLoginImage() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        LoginActivity.this.finish();
    }

    //注册111点击事件
    @OnClick(R.id.login_btn_password)
    public void btnRegister() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

}
