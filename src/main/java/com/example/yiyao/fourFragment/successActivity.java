package com.example.yiyao.fourFragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.yiyao.Adapter.SuccessAdapter;
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

public class successActivity extends AppCompatActivity {

    private List<Goods> list;
    private SuccessAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tvState;
    private ImageView imageView;

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        initView();
        initDate();
        getDateFromService();
    }


    /**
     * 3.从服务器中获取数据
     */
    private void getDateFromService() {
        String url = NetService.getAbsoluteUrl("SelectGoodsServlet");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
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
                TmallUtils.showToast(successActivity.this, "无网络,请连接网络");
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
        NetService.getInstance(successActivity.this).addToRequestQueue(stringRequest, "zhekou");
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
            list.add(goods);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        tvState = (TextView) findViewById(R.id.tv_success);
        imageView = (ImageView) findViewById(R.id.success_iamge);
        recyclerView = (RecyclerView) findViewById(R.id.success_recyclerView);

    }

    /**
     * 初始化数据
     */
    private void initDate() {
        Intent intent = this.getIntent();
        String zhuangtai = intent.getStringExtra("zhuangtai");
        if (zhuangtai.equals("1")) {
            tvState.setText("支付成功");
            imageView.setImageResource(R.drawable.goumaicheng);
        } else if (zhuangtai.equals("2")) {
            tvState.setText("交易成功");
            imageView.setImageResource(R.drawable.shouhuochengg);
        }

        list = new ArrayList<>();
        adapter = new SuccessAdapter(list, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        NetService.getInstance(successActivity.this).cancelRequest("zhekou");
    }
}
