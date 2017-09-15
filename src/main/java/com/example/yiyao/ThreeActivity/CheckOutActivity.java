package com.example.yiyao.ThreeActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.yiyao.MainActivity;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//结算页面
public class CheckOutActivity extends AppCompatActivity {

    private TextView shoppingCarMoney, txtShopMoney, txtShopMoney1, u_name, u_phone, u_address;
    private RelativeLayout relative2;
    private Button btnjiesuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        initView();
        initDate();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        shoppingCarMoney = (TextView) findViewById(R.id.txt_shop_orMoney);
        txtShopMoney = (TextView) findViewById(R.id.text_shop_money1);
        txtShopMoney1 = (TextView) findViewById(R.id.text_shop_money);
        relative2 = (RelativeLayout) findViewById(R.id.relative2);
        u_name = (TextView) findViewById(R.id.u_name);
        u_address = (TextView) findViewById(R.id.u_dizi);
        u_phone = (TextView) findViewById(R.id.u_phone);
        btnjiesuan = (Button) findViewById(R.id.btn_jiesuan);
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        //Intent intent = getIntent();
        //String mPriceAllMoney = intent.getStringExtra("mPriceAllMoney");
        //商品价格
        String mPriceAllMoney = TmallUtils.getPrice(this);
        Float pars = Float.parseFloat(mPriceAllMoney);
        //商品价格加邮费
        Float parst = Float.parseFloat(mPriceAllMoney) + 15;

        shoppingCarMoney.setText(String.format("%.2f", parst));
        //商品金额1
        txtShopMoney.setText(String.format("￥%.2f", pars));
        //商品金额2
        txtShopMoney1.setText(String.format("￥%.2f", pars));
        u_name.setText(TmallUtils.getReceiveName(this));
        u_address.setText(TmallUtils.getReceiveAdress(this));
        u_phone.setText(TmallUtils.getReceiveMobile(this));

        //选择地址的点击事件
        relative2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckOutActivity.this, AddressActivity.class));
            }
        });

        //结算的点击事件
        btnjiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddOrderFormService();
                startActivity(new Intent(CheckOutActivity.this, MainActivity.class));
            }
        });

    }

    /**
     * 付款的时候提交订单详情到服务器中 结算是提交订单.
     */
    private void AddOrderFormService() {
        //需要向服务器提交的参数
        //用户id
        final int id = TmallUtils.getUser(CheckOutActivity.this).getU_id();
        Intent intent = getIntent();
        //购物车订单id.
        final String gid = intent.getStringExtra("gid");
        Date currDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        //获取系统当前时间 也就是订单生产时间
        final String strCurrDate = sdf.format(currDate);
        String mPriceAllMoney = intent.getStringExtra("mPriceAllMoney");
        //总金额 就是没有加邮费的
        final Float pars = Float.parseFloat(mPriceAllMoney);
        final Float parst = Float.parseFloat(mPriceAllMoney) + 15;

        String url = NetService.getAbsoluteUrl("AddOrderformServlet");
        StringRequest stringQuest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jo = new JSONObject(s);
                    String flag = jo.getString("flag");
                    if (flag.equals("success")) {
                        TmallUtils.showToast(CheckOutActivity.this, "添加订单成功");
                    } else if (flag.equals("error")) {
                        TmallUtils.showToast(CheckOutActivity.this, "添加订单失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                TmallUtils.showToast(CheckOutActivity.this, "无网络,请连接网络");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("u_id", id + "");
                params.put("g_ids", gid);
                params.put("o_date", strCurrDate);
                params.put("o_goods_value", pars + "");
                params.put("o_transport_value", "15");
                params.put("o_total_value", parst + "");
                params.put("o_receive_people", u_name.getText().toString());
                params.put("o_receive_address", u_address.getText().toString());
                params.put("o_receive_phone", u_phone.getText().toString());
                return params;
            }
        };
        NetService.getInstance(this).addToRequestQueue(stringQuest, "addOrder");

    }

    @Override
    protected void onStop() {
        super.onStop();
        NetService.getInstance(this).cancelRequest("addOrder");
    }
}
