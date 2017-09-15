package com.example.yiyao.fourFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.yiyao.R;
import com.example.yiyao.ThreeActivity.AddressActivity;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.Utils.ToastUtil;
import com.example.yiyao.pojo.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WoDeZhangHaoActivity extends AppCompatActivity implements View.OnClickListener {

    //初始化控件
    private TextView tvnickname, tvname, tvsex, tvage, tvaddress, tvmobile;
    private LinearLayout lnnickname, lnname, lnsex, lnage, lnaddress, lnmobile,lnManagerAddress;
    private ImageView wodezhanghaoreturn;
    //存放在选项存储中的user
    private User user;
    //用于储存 从服务器中获取的数据
    private User userfrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_zhang_hao);
        initview();
        initdata();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wodezhanghaoreturn:
                WoDeZhangHaoActivity.this.finish();
                break;
            case R.id.linear_nicheng:
                UpdateEdit(1);
                break;
            case R.id.linear_name:
                UpdateEdit(2);
                break;
            case R.id.linear_sex:
                UpdateChoose();
                break;
            case R.id.linear_age:
                UpdateEdit(4);
                break;
            case R.id.linear_address:
                UpdateEdit(5);
                break;
            case R.id.linear_mobile:
                UpdateEdit(6);
                break;
        }
    }

    private void UpdateChoose() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.choosedialog, null);
        builder.setTitle("性别:");
        final RadioButton male = (RadioButton) view.findViewById(R.id.male);
        final RadioButton female = (RadioButton) view.findViewById(R.id.female);
        if (userfrom.getU_sex().equals("男")) {
            male.setChecked(true);
            female.setChecked(false);
        } else if (userfrom.getU_sex().equals("女")) {
            male.setChecked(false);
            female.setChecked(true);
        }
        builder.setView(view);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    if (male.isChecked()) {
                        saveToService(3, "男");
                    } else if (female.isChecked()) {
                        saveToService(3, "女");
                    }
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {

                }
            }
        });
        builder.create().show();
    }


    private void UpdateEdit(final int code) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = LayoutInflater.from(this).inflate(R.layout.editdialog, null);
        final EditText edit = (EditText) view.findViewById(R.id.edit);
        switch (code) {
            case 1:
                builder.setTitle("我的昵称:");
                edit.setText(userfrom.getU_nickname());
                builder.setView(view);
                break;
            case 2:
                builder.setTitle("我的姓名:");
                edit.setText(userfrom.getU_username());
                builder.setView(view);
                break;
            case 4:
                builder.setTitle("我的年龄:");
                edit.setText(userfrom.getU_sex());
                edit.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                builder.setView(view);
                break;
            case 5:
                builder.setTitle("我的地址:");
                edit.setText(userfrom.getU_address());
                builder.setView(view);
                break;
            case 6:
                builder.setTitle("我的手机:");
                edit.setText(userfrom.getU_mobile());
                builder.setView(view);
                break;
        }
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    saveToService(code, edit.getText().toString());
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {

                }
            }
        });
        builder.create().show();
    }


    private void initview() {
        wodezhanghaoreturn = (ImageView) findViewById(R.id.wodezhanghaoreturn);
        wodezhanghaoreturn.setOnClickListener(this);

        tvnickname = (TextView) findViewById(R.id.shezhi_zhanghao_nicheng);
        lnnickname = (LinearLayout) findViewById(R.id.linear_nicheng);
        lnnickname.setOnClickListener(this);

        tvname = (TextView) findViewById(R.id.shezhi_zhanghao_name);
        lnname = (LinearLayout) findViewById(R.id.linear_name);
        lnname.setOnClickListener(this);

        tvsex = (TextView) findViewById(R.id.shezhi_zhanghao_sex);
        lnsex = (LinearLayout) findViewById(R.id.linear_sex);
        lnsex.setOnClickListener(this);

        tvage = (TextView) findViewById(R.id.shezhi_zhanghao_age);
        lnage = (LinearLayout) findViewById(R.id.linear_age);
        lnage.setOnClickListener(this);

        tvaddress = (TextView) findViewById(R.id.shezhi_zhanghao_address);
        lnaddress = (LinearLayout) findViewById(R.id.linear_address);
        lnaddress.setOnClickListener(this);

        tvmobile = (TextView) findViewById(R.id.shezhi_zhanghao_mobile);
        lnmobile = (LinearLayout) findViewById(R.id.linear_mobile);
        lnmobile.setOnClickListener(this);
        lnManagerAddress=(LinearLayout)findViewById(R.id.linear_manageraddress);

        //管理地址
        lnManagerAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WoDeZhangHaoActivity.this, AddressActivity.class));
            }
        });
    }

    //从选项存储中获取id，并从网络加载数据
    private void initdata() {
        user = TmallUtils.getUser(this);
        userfrom = new User();
        StringRequest request = new StringRequest(Request.Method.POST, NetService.getAbsoluteUrl("LoginServlet"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jo = new JSONObject(s);
                            //昵称
                            if (!jo.isNull("u_nickname")) {
                                tvnickname.setText(jo.getString("u_nickname"));
                                userfrom.setU_nickname(jo.getString("u_nickname"));
                                Log.v("Mainactivity", jo.getString("u_nickname"));
                            } else {
                                tvnickname.setText("");
                                userfrom.setU_nickname("");
                            }
                            //姓名
                            if (!jo.isNull("u_username")) {
                                tvname.setText(jo.getString("u_username"));
                                Log.v("Mainactivity", jo.getString("u_username"));
                                userfrom.setU_username(jo.getString("u_username"));
                            } else {
                                tvname.setText("");
                                userfrom.setU_username("");
                            }
                            //u_sex
                            if (!jo.isNull("u_sex")) {
                                tvsex.setText(jo.getString("u_sex"));
                                Log.v("Mainactivity", jo.getString("u_sex"));
                                userfrom.setU_sex(jo.getString("u_sex"));
                            } else {
                                tvsex.setText("");
                                userfrom.setU_sex("");
                            }
                            //u_age
                            if (!jo.isNull("u_age")) {
                                tvage.setText(jo.getInt("u_age") + "");
                                Log.v("Mainactivity", jo.getInt("u_age") + "");
                                userfrom.setU_age(jo.getInt("u_age"));
                            } else {
                                tvage.setText("");
                                userfrom.setU_age(0);
                            }
                            //u_address
                            if (!jo.isNull("u_address")) {
                                tvaddress.setText(jo.getString("u_address"));
                                Log.v("Mainactivity", jo.getString("u_address"));
                                user.setU_address(jo.getString("u_address"));
                            } else {
                                tvaddress.setText("");
                                user.setU_address("");
                            }
                            //u_mobile
                            if (!jo.isNull("u_mobile")) {
                                tvmobile.setText(jo.getString("u_mobile"));
                                Log.v("Mainactivity", jo.getString("u_mobile"));
                                userfrom.setU_mobile(jo.getString("u_mobile"));
                            } else {
                                tvmobile.setText("");
                                userfrom.setU_mobile("");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("mobile", user.getU_mobile());
                map.put("password", user.getU_password());
                return map;
            }
        };
        NetService.getInstance(this).addToRequestQueue(request, "user");
    }


    private void saveToService(final int code, final String content) {
        //将数据上传至数据库
        final StringRequest request = new StringRequest(Request.Method.POST, NetService.getAbsoluteUrl("UpdateUserServlet"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jo = new JSONObject(s);
                            if (jo.getString("flag").equals("success")) {
                                ToastUtil.show(WoDeZhangHaoActivity.this, "更新成功");
                                switch (code) {
                                    case 1:
                                        tvnickname.setText(content);
                                        user.setU_nickname(content);
                                        TmallUtils.saveUser(WoDeZhangHaoActivity.this, user);
                                        break;
                                    case 2:
                                        tvname.setText(content);
                                        break;
                                    case 3:
                                        tvsex.setText(content);
                                        user.setU_sex(content);
                                        TmallUtils.saveUser(WoDeZhangHaoActivity.this, user);
                                        break;
                                    case 4:
                                        tvage.setText(content);
                                        break;
                                    case 5:
                                        tvaddress.setText(content);
                                        break;
                                    case 6:
                                        tvmobile.setText(content);
                                        user.setU_mobile(content);
                                        TmallUtils.saveUser(WoDeZhangHaoActivity.this, user);
                                        break;
                                }
                            } else if (jo.getString("flag").equals("error")) {
                                ToastUtil.show(WoDeZhangHaoActivity.this, "更新失败");
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("u_id", user.getU_id() + "");
                switch (code) {
                    case 1:
                        map.put("u_nickname", content);
                        break;
                    case 2:
                        map.put("u_username", content);
                        break;
                    case 3:
                        map.put("u_sex", content);
                        break;
                    case 4:
                        map.put("u_age", content);
                        break;
                    case 5:
                        map.put("u_address", content);
                        break;
                    case 6:
                        map.put("u_mobile", content);
                        break;
                }
                return map;
            }
        };
        NetService.getInstance(this).addToRequestQueue(request, "update");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetService.getInstance(this).cancelRequest("update");
        NetService.getInstance(this).cancelRequest("user");
    }
}
