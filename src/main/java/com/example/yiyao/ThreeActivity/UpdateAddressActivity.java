package com.example.yiyao.ThreeActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.pojo.UserAddress;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateAddressActivity extends AppCompatActivity {
    private EditText txtName, txtPhone, txtAddress;
    private Button update;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        initView();
        initDate();
    }


    /**
     * 初始化视图
     */
    private void initView() {
        txtName = (EditText) findViewById(R.id.txt_name);
        txtPhone = (EditText) findViewById(R.id.txt_phone);
        txtAddress = (EditText) findViewById(R.id.txt_address);
        cancel = (Button) findViewById(R.id.update_quxiao);
        update = (Button) findViewById(R.id.upate_xiugai);
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        //接收适配器传过来的值.
        Intent intent = this.getIntent();
        UserAddress address = (UserAddress) intent.getSerializableExtra("address");
        txtName.setText(address.getA_receive_user_name());
        txtPhone.setText(address.getA_receive_user_phone());
        txtAddress.setText(address.getA_receive_user_address());

        //取消的点击事件
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateAddressActivity.this, AddressActivity.class));
            }
        });
        //修改的点击事件
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtName.getText().toString().equals("") && txtPhone.getText().toString().equals("") && txtAddress.getText().toString().equals("")) {
                    Toast.makeText(UpdateAddressActivity.this, "请输入您的姓名电话还有详细地址", Toast.LENGTH_SHORT).show();
                } else {
                    if (!judgePhoneNums(txtPhone.getText().toString())) {
                        return;
                    }
                    addAddressFromService();
                    startActivity(new Intent(UpdateAddressActivity.this, AddressActivity.class));
                }
            }
        });
    }


    /**
     * 提交新的地址到服务器
     */
    private void addAddressFromService() {

        String url = NetService.getAbsoluteUrl("UpdateReceiveAddressServlet");
        StringRequest stringQuest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jo = new JSONObject(s);
                    if ("success".equals(jo.getString("flag"))) {
                        // 添加成功
                        TmallUtils.showToast(UpdateAddressActivity.this, "添加成功");
                    } else if ("error".equals(jo.getString("flag"))) {
                        // 添加失败
                        TmallUtils.showToast(UpdateAddressActivity.this, "添加失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                TmallUtils.showToast(UpdateAddressActivity.this, "无网络,请连接网络");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("a_id", TmallUtils.getAid(UpdateAddressActivity.this));
                params.put("u_id", TmallUtils.getUser(UpdateAddressActivity.this).getU_id() + "");
                params.put("a_receive_user_name", txtName.getText().toString());
                params.put("a_receive_user_address", txtAddress.getText().toString());
                params.put("a_receive_user_phone", txtPhone.getText().toString());
                return params;
            }
        };
        NetService.getInstance(this).addToRequestQueue(stringQuest, "update");
    }

    @Override
    protected void onStop() {
        super.onStop();
        NetService.getInstance(this).cancelRequest("update");
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
        TmallUtils.showToast(this, "输入手机号码有误,请重新输入哟");
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
}
