package com.example.yiyao.ThreeActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.example.yiyao.Adapter.AddressAdapter;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.pojo.UserAddress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressActivity extends AppCompatActivity {
    private RelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private List<UserAddress> addresses = new ArrayList<>();
    private AddressAdapter adapter;

    //支持下拉刷新的ViewGroup
    private PtrClassicFrameLayout mPtrFrame;
    //添加Header和Footer的封装类
    private RecyclerAdapterWithHF mAdapter;

    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initView();
        initDate();
    }


    /**
     * 初始化视图
     */
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.address_recycler_view);
        relativeLayout = (RelativeLayout) findViewById(R.id.address_bottom_bar);

    }

    /**
     * 初始化数据
     */
    private void initDate() {
        //跳转到新增地址。
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this, AddaddressActivity.class));
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(AddressActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AddressAdapter(AddressActivity.this, addresses);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        recyclerView.setAdapter(mAdapter);
        adapter.setOnDeleteLister(new AddressAdapter.OnDeleteLister() {
            @Override
            public void deleteAddress(int position) {
                addresses.remove(position);
                mAdapter.notifyDataSetChanged();

            }
        });

        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.address_rotate_header_list_view_frame);

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
        //进入Activity就进行自动下拉刷新
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
                //刷新服务端
                addresses.clear();
                page = 1;
                GetDateFromService();
                adapter.notifyDataSetChanged();
                mPtrFrame.refreshComplete();
                mPtrFrame.setLoadMoreEnable(true);
            }
        });

        //上拉加载
        mPtrFrame.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                adapter.notifyDataSetChanged();
                mPtrFrame.loadMoreComplete(true);
                Toast.makeText(AddressActivity.this, "没有更多数据了", Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }

    /**
     * 从服务器获取数据
     */
    public void GetDateFromService() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo in = cm.getActiveNetworkInfo();
        if (in != null && in.isConnectedOrConnecting()) {


            final String id = TmallUtils.getUser(this).getU_id() + "";
            StringRequest request = new StringRequest(Request.Method.POST, NetService.getAbsoluteUrl("SelectAllReceiveAddressServlet")
                    , new Response.Listener<String>() {
                @Override
                public void onResponse(String json) {
                    try {
                        //解析Json数据
                        parseJson(json);
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
                    params.put("u_id", id);
                    return params;
                }
            };
            NetService.getInstance(this).addToRequestQueue(request, "address");
        } else {
            TmallUtils.showToast(this, "无网络\n亲，请查看下你的网络连接");
            Log.v("MainActivity", "无网络/不符合更新条件");
            Cache cache = NetService.getInstance(this).getQueue().getCache();
            Cache.Entry entry = cache.get(NetService.getAbsoluteUrl("SelectGoodsServlet"));
            if (entry != null) {
                try {
                    String data = new String(entry.data, "utf-8");
                    parseJson(data);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void parseJson(String s) throws JSONException {
        JSONArray ja = new JSONArray(s);
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.getJSONObject(i);
            UserAddress address = new UserAddress();
            address.setA_id(jo.getInt("a_id"));
            address.setA_receive_user_address(jo.getString("a_receive_user_address"));
            address.setA_receive_user_name(jo.getString("a_receive_user_name"));
            address.setA_receive_user_phone(jo.getString("a_receive_user_phone"));
            address.setU_id(jo.getInt("u_id"));
            addresses.add(address);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        NetService.getInstance(this).cancelRequest("address");
        NetService.getInstance(this).cancelRequest("deleteAddress");
    }
}
