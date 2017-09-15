package com.example.yiyao.fourFragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.yiyao.Adapter.DDXiangQingAdapter;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.pojo.Goods;
import com.example.yiyao.pojo.Orderform;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DingDanXiangQingActivity extends AppCompatActivity {

    private ImageView shezhireturn;
    private int oId;
    private String st;//订单状态
    private TextView bianHao, time, state, name, mobile, address, zJinE, yunFei, yfJinE;
    private RecyclerView recyleSPXX;
    private ImageView ivSPUrl;
    private DDXiangQingAdapter adapter;
    private List<Goods> data;
    private Orderform of;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_dan_xiang_qing);
        initView();
        initDate();
        getDateFromService();
    }


    /**
     * 初始化视图
     */
    private void initView() {
        shezhireturn = (ImageView) findViewById(R.id.shezhireturn);
        bianHao = (TextView) findViewById(R.id.tv_dingdan_bh);
        time = (TextView) findViewById(R.id.tv_dingdan_time);
        state = (TextView) findViewById(R.id.tv_dingdan_state);
        name = (TextView) findViewById(R.id.tv_dingdan_name);
        mobile = (TextView) findViewById(R.id.tv_dingdan_mobile);
        address = (TextView) findViewById(R.id.tv_dingdan_address);
        zJinE = (TextView) findViewById(R.id.tv_dingdan_zong_jin_e);
        yunFei = (TextView) findViewById(R.id.tv_dingdan_yf);
        yfJinE = (TextView) findViewById(R.id.tv_dingdan_ying_fu);

    }

    /**
     * 初始化数据
     */
    private void initDate() {
        //接收Intent传过来的o_id
        Intent intent = this.getIntent();
        of = (Orderform) intent.getSerializableExtra("of");
        oId = of.getO_id();
        //适配器，控件，数据
        recyleSPXX = (RecyclerView) findViewById(R.id.recycler_spxx);
        data = new ArrayList<>();
        adapter = new DDXiangQingAdapter(this, data);
        recyleSPXX.setLayoutManager(new LinearLayoutManager(this));
        recyleSPXX.setAdapter(adapter);

        //返回键的点击事件
        shezhireturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DingDanXiangQingActivity.this.finish();
            }
        });
    }

    /**
     * 从服务器中获取数据
     */
    private void getDateFromService() {
        StringRequest request = new StringRequest(Request.Method.POST, NetService.getAbsoluteUrl("ShowOrderformServlet"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jo = new JSONObject(s);
                            bianHao.setText(jo.getString("o_id"));
                            time.setText(jo.getString("o_date"));
                            String n = jo.getString("o_state");
                            String ss = null;
                            if (n.equals("1")) {
                                ss = "订单待支付";
                            } else if (s.equals("2")) {
                                ss = "订单待收货";
                            } else if (s.equals("3")) {
                                ss = "订单待评论";
                            }
                            state.setText(ss);
                            name.setText(jo.getString("o_receive_people"));
                            mobile.setText(jo.getString("o_receive_phone"));
                            address.setText(jo.getString("o_receive_address"));
                            zJinE.setText("￥" + jo.getString("o_total_value"));
                            yunFei.setText("￥" + jo.getString("o_transport_value"));
                            yfJinE.setText("￥" + jo.getString("o_total_value"));
                            String sa = jo.getString("orderfrom_good");
                            JSONArray ja = new JSONArray(sa);
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo1 = ja.getJSONObject(i);
                                Goods goods = new Goods();
                                goods.setG_id(jo1.getInt("g_id"));
                                goods.setG_name(jo1.getString("g_name"));
                                goods.setG_show_image_url(jo1.getString("g_show_image_url"));
                                goods.setG_trademark(jo1.getString("g_trademark"));
                                goods.setG_price(Float.parseFloat(jo1.getString("g_price")));
                                data.add(goods);
                                adapter.notifyDataSetChanged();
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
                map.put("o_id", oId + "");
                return map;
            }
        };
        NetService.getInstance(this).addToRequestQueue(request, "dingdan");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetService.getInstance(this).cancelRequest("dingdan");
        NetService.getInstance(this).cancelRequest("del");
    }

    /**
     * 取消订单、去支付点击事件
     *
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ddxq_canle:
                //取消订单
                //传o_id到服务器进行删除
                delData();
                break;
            case R.id.btn_ddxq_pay:
                //去支付
                TmallUtils.showToast(this, "成功支付！");
                st = "2";
                //成功支付以后，更新o_state为待收货状态
                updateState();
                Intent intent=new Intent(this,successActivity.class);
                intent.putExtra("zhuangtai","1");
                startActivity(intent);
                DingDanXiangQingActivity.this.finish();
                break;
        }
    }

    //当确定收货后，服务器更新o_state为待评论状态
    private void updateState() {
        StringRequest request = new StringRequest(Request.Method.POST, NetService.getAbsoluteUrl("UpdateOrderformServlet"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jo = new JSONObject(s);

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
                map.put("o_id", oId + "");
                map.put("o_state", st);
                return map;
            }
        };
        NetService.getInstance(this).addToRequestQueue(request, "updateState");
    }

    //访问网络，删除数据库订单的方法
    private void delData() {
        StringRequest request = new StringRequest(Request.Method.POST, NetService.getAbsoluteUrl("DeleteOrderformServlet"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {

                            JSONObject jo = new JSONObject(s);
                            String result = jo.getString("flag");
                            if (result.equals("success")) {
                                TmallUtils.showToast(DingDanXiangQingActivity.this, "删除成功！");
                                DingDanXiangQingActivity.this.finish();
                            } else {
                                TmallUtils.showToast(DingDanXiangQingActivity.this, "删除失败！");
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                TmallUtils.showToast(DingDanXiangQingActivity.this, "网络君失联喽！");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("o_id", oId + "");
                return map;
            }
        };
        NetService.getInstance(this).addToRequestQueue(request, "del");
    }

}
