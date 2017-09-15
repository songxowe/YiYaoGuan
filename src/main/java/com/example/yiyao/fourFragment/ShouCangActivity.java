package com.example.yiyao.fourFragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.yiyao.Adapter.ShouCangAdapter;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.pojo.Goods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShouCangActivity extends AppCompatActivity {
    private ImageView shezhireturn;

    private RecyclerView recyclerView;
    private ShouCangAdapter adapter;
    private List<Goods> list;
    private int page = 1;
    private int intTotalPage = 1;
    private int uId;
    private ImageView ivWuPingLun;
    private TextView tvWuPingLun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_cang);
        initView();
        initDate();
    }


    /**
     * 从服务器中获取数据
     */
    private void getDateFromService() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        String url = NetService.getAbsoluteUrl("SelectAllCollectionServlet");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray ja = new JSONArray(s);
                    if (ja.length() == 0) {
                        ivWuPingLun.setVisibility(View.VISIBLE);
                        tvWuPingLun.setVisibility(View.VISIBLE);
                    } else {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            Goods g = new Goods();
                            g.setG_id(jo.getInt("g_id"));
                            g.setG_image_url(jo.getString("g_image_url"));
                            g.setG_name(jo.getString("g_name"));
                            g.setG_price(Float.parseFloat(jo.getString("g_price")));
                            Float discount = Float.valueOf(jo.getString("g_discount"));
                            g.setG_discount(discount);
                            list.add(g);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                TmallUtils.showToast(ShouCangActivity.this, "无网络,请连接网络");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("u_id", uId + "");
                return map;
            }
        };
        NetService.getInstance(this).addToRequestQueue(stringRequest, "shenbao");
    }


    /**
     * 初始化视图
     */
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.shenbao_recyclerView);
        //从选项存储中获取uId
        uId = TmallUtils.getUser(this).getU_id();
        ivWuPingLun = (ImageView) findViewById(R.id.iv_wupinglun);
        tvWuPingLun = (TextView) findViewById(R.id.tv_wupinglun);
        shezhireturn = (ImageView) findViewById(R.id.shezhireturn);

    }

    /**
     * 初始化数据
     */
    private void initDate() {
        shezhireturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShouCangActivity.this.finish();
            }
        });

        //初始化
        list = new ArrayList<>();
        //网格布局
        //下拉刷新 上拉加载
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ShouCangAdapter(list, this);
        recyclerView.setAdapter(adapter);
        getDateFromService();
    }


    @Override
    public void onStop() {
        super.onStop();
        NetService.getInstance(this).cancelRequest("shenbao");
        NetService.getInstance(this).cancelRequest("getTotal");
        NetService.getInstance(this).cancelRequest("delAllDate");
    }

    //点击清空，删除所有收藏宝贝
    public void onClick(View view) {
        //访问服务
        delServiceDate();
        recyclerView.setVisibility(View.GONE);
        ivWuPingLun.setVisibility(View.VISIBLE);
        tvWuPingLun.setVisibility(View.VISIBLE);
    }

    private void delServiceDate() {
        StringRequest request = new StringRequest(Request.Method.POST, NetService.getAbsoluteUrl("DeleteAllCollectionServlet"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.v("MainActivity", s);
                        try {
                            JSONObject jo = new JSONObject(s);
                            if (jo.getString("flag").equals("success")) {
                                ivWuPingLun.setVisibility(View.VISIBLE);
                                tvWuPingLun.setVisibility(View.VISIBLE);
                                TmallUtils.showToast(ShouCangActivity.this, "成功清除！");
                            } else {
                                TmallUtils.showToast(ShouCangActivity.this, "清空失败！");
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
                map.put("u_id", uId + "");
                return map;
            }
        };

        NetService.getInstance(this).addToRequestQueue(request, "delAllDate");
    }

}
