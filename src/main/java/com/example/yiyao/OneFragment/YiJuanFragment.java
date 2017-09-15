package com.example.yiyao.OneFragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.example.yiyao.Adapter.ZheKouAdapter;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.pojo.Goods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 壹劵专区
 */
public class YiJuanFragment extends Fragment {

    private RecyclerView recyclerView;
    private ZheKouAdapter adapter;
    private List<Goods> list;
    private int page = 1;
    private int intTotalPage = 1;

    //支持下拉刷新的ViewGroup
    private PtrClassicFrameLayout mPtrFrame;
    //添加Header和Footer的封装类
    private RecyclerAdapterWithHF mAdapter;

    private ProgressDialog dialog;

    public YiJuanFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yi_juan, container, false);
        initView(view);
        initDate(view);
        //获取总数
        getTotal();
        //对话框的方法。
        //showProgressDialog();
        return view;
    }

    private void showProgressDialog() {
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("加载中,请稍后....");
        dialog.show();
    }

    private void getTotal() {
        String url = NetService.getAbsoluteUrl("SelectGoodsTotalServlet");
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
                TmallUtils.showToast(getActivity(), "无网络,请连接网络");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("pageno", page + "");
                map.put("order", "discount");
                return map;
            }
        };
        NetService.getInstance(getActivity()).addToRequestQueue(request, "getTotal");

    }

    /**
     * 3.从服务器中获取数据
     */
    private void getDateFromService() {
        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting()) {
            String url = NetService.getAbsoluteUrl("SelectGoodsServlet");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    // Log.v("MainActivity", s);
                    try {
                        //解析Json数据
                        parseJson(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    TmallUtils.showToast(getActivity(), "无网络,请连接网络");
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("pageno", page + "");
                    map.put("order", "discount");
                    return map;
                }
            };
            NetService.getInstance(getActivity()).addToRequestQueue(stringRequest, "zhekou");
        } else {
            TmallUtils.showToast(getActivity(), "无网络\n亲，请查看下你的网络连接");
            Log.v("MainActivity", "无网络/不符合更新条件");
            Cache cache = NetService.getInstance(getActivity()).getQueue().getCache();
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


    /**
     * 4.解析Json数据
     *
     * @param s
     * @throws JSONException
     */
    private void parseJson(String s) throws JSONException {
        JSONArray ja = new JSONArray(s);
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.getJSONObject(i);
            Goods goods = new Goods();
            goods.setG_id(jo.getInt("g_id"));
            //加载主图
            String g_show_image_url = NetService.BASE_URL + jo.getString("g_show_image_url");
            goods.setG_show_image_url(g_show_image_url);
            goods.setG_name(jo.getString("g_name"));
            Float price = Float.valueOf(jo.getString("g_price"));
            Float discount = Float.valueOf(jo.getString("g_discount"));

            //加载显示图
            String imagetxt_url = NetService.BASE_URL + jo.getString("g_imagetxt_url");
            String g_cure_symptom = jo.getString("g_cure_symptom");
            String g_classify_one = jo.getString("g_classify_one");
            String g_classify_two = jo.getString("g_classify_two");
            String g_suit_people = jo.getString("g_suit_people");
            String g_trademark = jo.getString("g_trademark");
            String g_product_company = jo.getString("g_product_company");
            String g_special_sell_day = jo.getString("g_special_sell_day");
            Integer g_good_evaluate = jo.getInt("g_good_evaluate");
            Integer g_normal_evaluate = jo.getInt("g_normal_evaluate");
            Integer g_bad_evaluate = jo.getInt("g_bad_evaluate");
            if (jo.isNull("condition")) {
                String condition = "";
                goods.setCondition(condition);
            } else {
                String condition = jo.getString("condition");
                goods.setCondition(condition);
            }
            //图文详情
            if (jo.isNull("g_image_url")) {
                String g_image_url = "";
                goods.setG_image_url(g_image_url);
            } else {
                String g_image_url = NetService.BASE_URL + jo.getString("g_image_url");
                goods.setG_image_url(g_image_url);
            }

            //Log.v("MainActivity", "999" + g_show_image_url + price + discount + imagetxt_url + g_bad_evaluate + g_good_evaluate + g_normal_evaluate + g_classify_one + g_classify_two + g_cure_symptom + g_product_company + g_special_sell_day + g_suit_people + g_trademark);
            goods.setG_price(price);
            goods.setG_discount(discount);
            goods.setG_imagetxt_url(imagetxt_url);
            goods.setG_cure_symptom(g_cure_symptom);
            goods.setG_classify_one(g_classify_one);
            goods.setG_classify_two(g_classify_two);
            goods.setG_suit_people(g_suit_people);
            goods.setG_trademark(g_trademark);
            goods.setG_product_company(g_product_company);
            goods.setG_special_sell_day(g_special_sell_day);
            goods.setG_good_evaluate(g_good_evaluate);
            goods.setG_bad_evaluate(g_bad_evaluate);
            goods.setG_normal_evaluate(g_normal_evaluate);
            if (discount < 1 && discount > 0) {
                list.add(goods);
            }
            adapter.notifyDataSetChanged();
        }

    }


    /**
     * 1.初始化视图
     *
     * @param view
     */
    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.yi_juan_recyclerView);

    }

    /**
     * 2.初始化数据
     *
     * @param view
     */
    private void initDate(View view) {
        //初始化
        list = new ArrayList<>();
        //网格布局
        //下拉刷新 上拉加载
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ZheKouAdapter(list, getActivity());
        mAdapter = new RecyclerAdapterWithHF(adapter);
        recyclerView.setAdapter(mAdapter);

        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.yi_juan_rotate_header_list_view_frame);
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

        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);


        //下拉刷新
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                list.clear();
                page = 1;
                //下拉刷新从新加载服务器
                getDateFromService();
                //通知适配器发生改变
                mAdapter.notifyDataSetChanged();
                //刷新完成
                mPtrFrame.refreshComplete();
                mPtrFrame.setLoadMoreEnable(true);
            }
        });

        //上拉加载
        mPtrFrame.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                //4.加载更多
                page++;
                if (page <= intTotalPage) {
                    getDateFromService();
                    mAdapter.notifyDataSetChanged();
                } else {
                    TmallUtils.showToast(getActivity(), "没有更多的数据");
                }
                mPtrFrame.loadMoreComplete(true);
                mPtrFrame.setLoadMoreEnable(true);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        NetService.getInstance(getActivity()).cancelRequest("zhekou");
        NetService.getInstance(getActivity()).cancelRequest("getTotal");
    }
}
