package com.example.yiyao.fourFragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.example.yiyao.Adapter.ZhiFuAdapter;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.pojo.Orderform;
import com.example.yiyao.pojo.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//待支付，待收货，待评论，我的售后4个界面共用此Activity
public class DaiZhiFuActivity extends AppCompatActivity {

    private ImageView shezhireturn;

    //数据，适配器，控件
    private RecyclerView recyclerDingDan;
    private ZhiFuAdapter adapter;
    private List<Orderform> data;

    private User user;
    private String userId;
    private int page = 1;
    private int intTotalPage = 1;
    private String state;
    private String state1;
    private TextView tvState;

    private String st;
    private int oId;
    //支持下拉刷新的ViewGroup
    private PtrClassicFrameLayout mPtrFrame;
    //添加Header和Footer的封装类
    private RecyclerAdapterWithHF mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dai_zhi_fu);
        initView();
        initDate();
        //获取总页数
        getTotal();
    }

    private void getTotal() {
        String url = NetService.getAbsoluteUrl("SelectOrderformByUidTotalServlet");
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject jo = new JSONObject(s);
                    //获取网络中的总页数
                    intTotalPage = jo.getInt("totalpage");
                    Log.v("MainActivity", intTotalPage + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                TmallUtils.showToast(DaiZhiFuActivity.this, "无网络,请连接网络");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("u_id", userId);
                map.put("o_state", state);
                return map;
            }
        };
        NetService.getInstance(DaiZhiFuActivity.this).addToRequestQueue(request, "getTotal");
    }

    /**
     * 初始化视图
     */
    private void initView() {
        shezhireturn = (ImageView) findViewById(R.id.shezhireturn);
        tvState = (TextView) findViewById(R.id.tv_dingdan_state);
        recyclerDingDan = (RecyclerView) findViewById(R.id.recycler_dingdan);

        //返回键的点击事件
        shezhireturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaiZhiFuActivity.this.finish();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        //获得选项存储中的用户信息
        user = TmallUtils.getUser(this);
        userId = String.format("%d", user.getU_id());
        //获得Intent的state的值
        Intent intent = this.getIntent();
        state = intent.getStringExtra("o_state");
        if (state.equals("1")) {
            // state1 = "订单待支付";
        } else if (state.equals("2")) {
            //设置标题头为  待收货订单  btn--->确定收货
            state1 = "待收货订单";
            tvState.setText(state1);
        } else if (state.equals("3")) {
            state1 = "待评论订单";
            tvState.setText(state1);
        } else if (state.equals("4")) {
            state1 = "我的售后";
            tvState.setText(state1);
        }
        //================================
        data = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerDingDan.setLayoutManager(layoutManager);
        adapter = new ZhiFuAdapter(this, data);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        recyclerDingDan.setAdapter(mAdapter);

        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
        //下拉刷新支持时间
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        //下拉刷新一些设置 详情参考文档
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);

        //1.刷新加载的方法
        shuAXinAndJiaZai();
    }

    /**
     * 刷新加载的方法
     */
    private void shuAXinAndJiaZai() {
        mPtrFrame.post(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        });
        //下拉刷新
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                data.clear();
                page = 1;
                //访问网络
                getDateFormService();
                //模拟联网 延迟更新列表
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        mPtrFrame.refreshComplete();
                        mPtrFrame.setLoadMoreEnable(true);
                    }
                }, 1000);
            }
        });
        //上拉加载
        mPtrFrame.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                page++;
                if (page <= intTotalPage) {
                    getDateFormService();
                    mAdapter.notifyDataSetChanged();
                } else {
                    TmallUtils.showToast(DaiZhiFuActivity.this, "没有更多的数据");
                }
                mPtrFrame.loadMoreComplete(true);
                mPtrFrame.setLoadMoreEnable(true);
            }
        });

    }

    /**
     * 从服务器中获取数据
     */
    private void getDateFormService() {
        StringRequest request = new StringRequest(Request.Method.POST,
                NetService.getAbsoluteUrl("SelectOrderformByStateServlet"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONArray ja = new JSONArray(s);
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                Orderform of = new Orderform();
                                of.setO_id(Integer.valueOf(jo.getString("o_id")));
                                of.setU_id(Integer.valueOf(jo.getString("u_id")));
                                of.setG_ids(jo.getString("g_ids"));
                                of.setO_date(jo.getString("o_date"));
                                of.setO_state(Integer.parseInt(jo.getString("o_state")));
                                of.setO_goods_value(Float.valueOf(jo.getString("o_goods_value")));
                                of.setO_total_value(Float.valueOf(jo.getString("o_total_value")));
                                of.setShow_image_url(jo.getString("show_image_url"));
                                data.add(of);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                TmallUtils.showToast(DaiZhiFuActivity.this, "无网络,请连接网络");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("u_id", userId);
                map.put("pageno", page + "");
                map.put("o_state", state);
                return map;
            }
        };
        NetService.getInstance(this).addToRequestQueue(request, "dingdan");
    }

    @Override
    protected void onStop() {
        super.onStop();
        NetService.getInstance(this).cancelRequest("dingdan");
        NetService.getInstance(this).cancelRequest("getTotal");
    }

    //待跳转至此Activity则重新刷新数据
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        shuAXinAndJiaZai();
    }
}
