package com.example.yiyao.fourFragment;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

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
import com.example.yiyao.Adapter.DingDanAdapter;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.pojo.Orderform;
import com.example.yiyao.pojo.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DingDanActivity extends AppCompatActivity {

    private ImageView shezhireturn;

    //数据，适配器，控件
    private RecyclerView recyclerDingDan;
    private DingDanAdapter adapter;
    private List<Orderform> data;

    private User user;
    private String userId;
    private int page = 1;
    private int totalPage = 1;
    //支持下拉刷新的ViewGroup
    private PtrClassicFrameLayout mPtrFrame;
    //添加Header和Footer的封装类
    private RecyclerAdapterWithHF mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_dan);
        initView();
        initDate();
        getTotal();
    }

    /**
     * 获取总页数的方法
     */
    private void getTotal() {
        StringRequest request = new StringRequest(Request.Method.POST, NetService.getAbsoluteUrl("SelectOrderformByUidTotalServlet"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jo = new JSONObject(s);
                            totalPage = Integer.parseInt(jo.getString("totalpage"));
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
                map.put("u_id", userId);
                return map;
            }
        };
        NetService.getInstance(this).addToRequestQueue(request, "totalPage");
    }

    /**
     * 初始化视图
     */
    private void initView() {
        shezhireturn = (ImageView) findViewById(R.id.shezhireturn);
        recyclerDingDan = (RecyclerView) findViewById(R.id.recycler_dingdan);
        shezhireturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DingDanActivity.this.finish();
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
        data = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerDingDan.setLayoutManager(layoutManager);
        adapter = new DingDanAdapter(this, data);
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
                if (page <= totalPage) {
                    getDateFormService();
                    mAdapter.notifyDataSetChanged();
                } else {
                    TmallUtils.showToast(DingDanActivity.this, "没有更多的数据");
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
        StringRequest request = new StringRequest(Request.Method.POST, NetService.getAbsoluteUrl("SelectOrderformByUIdServlet"),
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

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("u_id", userId);
                map.put("pageno", page + "");

                return map;
            }
        };
        NetService.getInstance(this).addToRequestQueue(request, "dingdan");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetService.getInstance(this).cancelRequest("dingdan");
        NetService.getInstance(this).cancelRequest("totalPage");
    }

    //待跳转至此Activity则重新刷新数据
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        shuAXinAndJiaZai();

    }
}
