package com.example.yiyao.OneActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.yiyao.LoginActivity;

import com.example.yiyao.OneFragment.ShowProductImagetextFragment;
import com.example.yiyao.OneFragment.ShowProductPingjiaFragment;

import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.pojo.Goods;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.TextHintView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowProductActivity extends AppCompatActivity {

    //商品id
    private int id;
    //用户id
    private int userID;
    //计算
    private int count;
    //定义商品是否被收藏
    private boolean collection = false;

    //初始化控件
    private RollPagerView mRollViewPager;
    private List<String> list;
    private TestNormalAdapter adapter;
    private Goods goods;


    private TextView tvName, tvDiscountPrice, tvDiscount,
            tvPrice, tvSuitPeople;
    private ImageView imageCollection;

    private Button buy, shoppingCar;

    //刘华智改
    private TextView xiala_more, xiala_show_pingjia, xiala_show_imagetext;
    private String show_imagetexturl = "";
    private boolean is_show = false;
    private int show_g_id = 0;
    private int show_evaluate_good = 0;
    private int show_evaluate_normal = 0;
    private int show_evaluate_bad = 0;
    private RelativeLayout xiala_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        initView();
        initDate();
        //查询商品的显示界面
        getDateFromService();
        //根据uid和gid来查询商品是否收藏
        collection();

    }

    /**
     * //根据uid和gid来查询商品是否收藏
     */
    private void collection() {
        userID = TmallUtils.getUser(this).getU_id();
        String url = NetService.getAbsoluteUrl("SelectCollectionByGidUidServlet");
        StringRequest stringQuest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jo = new JSONObject(s);
                    count = jo.getInt("count");
                    if (count == 0) {
                        collection = false;
                        imageCollection.setImageResource(R.drawable.conten_guanzhu);
                    } else {
                        collection = true;
                        imageCollection.setImageResource(R.drawable.star_full);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                TmallUtils.showToast(ShowProductActivity.this, "无网络,请连接网络");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("u_id", userID + "");
                map.put("g_id", id + "");
                return map;
            }
        };
        NetService.getInstance(this).addToRequestQueue(stringQuest, "co");
    }


    /**
     * 从服务器获取数据
     */
    private void getDateFromService() {
        id = this.getIntent().getIntExtra("id", 0);
        Log.v("MainActivity", id + "");
        if (id == 0) {
        } else {
            String url = NetService.getAbsoluteUrl("ShowGoodsServlet");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    //Log.v("MainActivity", s);
                    try {
                        //解析json对象
                        parseJson(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    TmallUtils.showToast(ShowProductActivity.this, "无网络,请连接网络");
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("g_id", id + "");
                    return map;
                }
            };
            NetService.getInstance(this).addToRequestQueue(stringRequest, "show");
        }
    }

    /**
     * 4.解析Json数据
     *
     * @param s
     * @throws JSONException
     */
    private void parseJson(String s) throws JSONException {
        JSONObject jo = new JSONObject(s);
        goods = new Goods();
        int g_id = jo.getInt("g_id");
        String g_name = jo.getString("g_name");
        Float g_price = Float.valueOf(jo.getString("g_price"));
        Float g_discount = Float.valueOf(jo.getString("g_discount"));
        String g_suit_people = jo.getString("g_suit_people");
        String g_imagetxt_url = NetService.BASE_URL + jo.getString("g_imagetxt_url");
        show_imagetexturl = g_imagetxt_url;

        Integer g_good_evaluate = jo.getInt("g_good_evaluate");
        show_evaluate_good = g_good_evaluate;
        Integer g_normal_evaluate = jo.getInt("g_normal_evaluate");
        show_evaluate_normal = g_normal_evaluate;
        Integer g_bad_evaluate = jo.getInt("g_bad_evaluate");
        show_evaluate_bad = g_bad_evaluate;

        String images = jo.getString("g_image_url");
        JSONArray ja = new JSONArray(images);
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo1 = ja.getJSONObject(i);
            String imageUrl1 = NetService.BASE_URL + jo1.getString("image");
            //Log.v("MainActivity", imageUrl1);
            list.add(imageUrl1);
        }

        goods.setG_id(g_id);
        goods.setG_name(g_name);
        goods.setG_price(g_price);
        goods.setG_discount(g_discount);
        goods.setG_suit_people(g_suit_people);
        goods.setG_good_evaluate(g_good_evaluate);
        goods.setG_bad_evaluate(g_bad_evaluate);
        goods.setG_normal_evaluate(g_normal_evaluate);
        //填充控件
        tvName.setText(g_name);
        tvSuitPeople.append(g_suit_people);
        tvDiscount.setText(g_discount * 10 + "折");
        tvPrice.setText("￥" + g_price);
        tvDiscountPrice.setText(String.format("￥%.2f", g_discount * g_price));
        //刘华智：改
        xiala_show_pingjia.setActivated(false);
        xiala_show_imagetext.setActivated(true);
        ShowProductImagetextFragment fragment = new ShowProductImagetextFragment();
        fragment.setImageurl(show_imagetexturl);
        ShowProductActivity.this.getFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        // Log.v("MainActivity", "000" + g_name + g_suit_people + g_price + g_discount);;
    }


    /**
     * 初始化视图
     */
    private void initView() {
        mRollViewPager = (RollPagerView) findViewById(R.id.show_roll_view_pager);
        tvName = (TextView) findViewById(R.id.show_name);
        tvDiscountPrice = (TextView) findViewById(R.id.show_discount_price);
        tvDiscount = (TextView) findViewById(R.id.show_discount);
        tvPrice = (TextView) findViewById(R.id.show_price);
        tvSuitPeople = (TextView) findViewById(R.id.show_suit_people);
        imageCollection = (ImageView) findViewById(R.id.show_collection);
        buy = (Button) findViewById(R.id.show_b_buy);
        shoppingCar = (Button) findViewById(R.id.show_b_shopping);
        //刘华智改
        xiala_show = (RelativeLayout) findViewById(R.id.xiala_show);
        xiala_show.setVisibility(View.GONE);
        is_show = false;
        xiala_more = (TextView) findViewById(R.id.xiala_more);
        xiala_show_pingjia = (TextView) findViewById(R.id.xiala_show_pingjia);
        xiala_show_imagetext = (TextView) findViewById(R.id.xiala_show_imagetext);
        //刘华智：一进来，先打开的为图文页面
        xiala_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_show) {
                    is_show = false;
                    xiala_show.setVisibility(View.GONE);
                    xiala_more.setText("展开更多");
                } else {
                    is_show = true;
                    xiala_show.setVisibility(View.VISIBLE);
                    xiala_more.setText("收起更多");
                }
            }
        });
        //刘华智：点击事件
        xiala_show_imagetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xiala_show_pingjia.setActivated(false);
                xiala_show_imagetext.setActivated(true);
                FragmentManager fm = ShowProductActivity.this.getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ShowProductImagetextFragment fragment = new ShowProductImagetextFragment();
                fragment.setImageurl(show_imagetexturl);
                ft.replace(R.id.layout_content, fragment);
                ft.commit();
            }
        });
        xiala_show_pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xiala_show_pingjia.setActivated(true);
                xiala_show_imagetext.setActivated(false);
                FragmentManager fm = ShowProductActivity.this.getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ShowProductPingjiaFragment fragment = new ShowProductPingjiaFragment();
                fragment.setG_id(show_g_id, show_evaluate_good, show_evaluate_normal, show_evaluate_bad);
                ft.replace(R.id.layout_content, fragment);
                ft.commit();
            }
        });

    }

    /**
     * 初始化数据
     */
    private void initDate() {
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(100000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(1000);
        //设置适配器
        list = new ArrayList<>();
        adapter = new TestNormalAdapter(list);
        mRollViewPager.setAdapter(adapter);
        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        // mRollViewPager.setHintView(new ColorPointHintView(this, Color.YELLOW, Color.WHITE));
        mRollViewPager.setHintView(new TextHintView(this));
        //mRollViewPager.setHintView(null);

        //收藏商品的点击事件
        imageCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collection) {
                    DeleteFromService();
                } else {
                    CollectionformService();
                }

            }
        });

        //添加到购物车的点击事件
        shoppingCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingFormService();
            }
        });

    }

    private void DeleteFromService() {
        userID = TmallUtils.getUser(this).getU_id();
        String url = NetService.getAbsoluteUrl("DeleteCollectionByGidUidServlet");
        StringRequest stringQuest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jo = new JSONObject(s);
                    String flag = jo.getString("flag");
                    if (flag.equals("success")) {
                        TmallUtils.showToast(ShowProductActivity.this, "取消收藏成功");
                        collection = false;
                        imageCollection.setImageResource(R.drawable.conten_guanzhu);
                    } else {
                        if (flag.equals("error")) {
                            TmallUtils.showToast(ShowProductActivity.this, "删除收藏失败");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                TmallUtils.showToast(ShowProductActivity.this, "无网络,请连接网络");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("u_id", userID + "");
                map.put("g_id", id + "");
                return map;
            }
        };
        NetService.getInstance(this).addToRequestQueue(stringQuest, "de");

    }

    /**
     * 向服务器提交收藏商品
     */
    private void CollectionformService() {
        userID = TmallUtils.getUser(this).getU_id();
        Log.v("MainActivity", userID + "");
        if (userID == 0) {
            startActivity(new Intent(ShowProductActivity.this, LoginActivity.class));
            TmallUtils.showToast(ShowProductActivity.this, "请登录哟");
            ShowProductActivity.this.finish();
        } else {
            String url = NetService.getAbsoluteUrl("AddCollectionServlet");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Log.v("MainActivity", s);
                    try {
                        JSONObject jo = new JSONObject(s);
                        String flag = jo.getString("flag");
                        if (flag.equals("success")) {
                            TmallUtils.showToast(ShowProductActivity.this, "恭喜你收藏成功");
                            collection = true;
                            imageCollection.setImageResource(R.drawable.star_full);
                        } else {
                            if (flag.equals("error")) {
                                TmallUtils.showToast(ShowProductActivity.this, "收藏失败");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    TmallUtils.showToast(ShowProductActivity.this, "无网络,请连接网络");
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("u_id", userID + "");
                    map.put("g_id", id + "");
                    return map;
                }
            };
            NetService.getInstance(this).addToRequestQueue(stringRequest, "collection");
        }
    }


    /**
     * 向服务器提交添加到购物车
     */
    private void ShoppingFormService() {
        userID = TmallUtils.getUser(this).getU_id();
        Log.v("MainActivity", userID + "");
        if (userID == 0) {
            startActivity(new Intent(ShowProductActivity.this, LoginActivity.class));
            TmallUtils.showToast(ShowProductActivity.this, "请登录哟");
            ShowProductActivity.this.finish();
        } else {
            String url = NetService.getAbsoluteUrl("AddShopCarServlet");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Log.v("MainActivity", s);
                    try {
                        JSONObject jo = new JSONObject(s);
                        String flag = jo.getString("flag");
                        if (flag.equals("success")) {
                            TmallUtils.showToast(ShowProductActivity.this, "恭喜你添加到购物车成功");
                        } else if (flag.equals("error")) {
                            TmallUtils.showToast(ShowProductActivity.this, "添加商品失败");
                        } else if (flag.equals("exist")) {
                            TmallUtils.showToast(ShowProductActivity.this, "亲,该商品已经添加到购物车中");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    TmallUtils.showToast(ShowProductActivity.this, "无网络,请链接网络");
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("u_id", userID + "");
                    map.put("g_id", id + "");
                    return map;
                }
            };
            NetService.getInstance(this).addToRequestQueue(stringRequest, "shoopingcar");
        }
    }


    /**
     * 加载广告的适配器
     */
    private class TestNormalAdapter extends StaticPagerAdapter {
        private List<String> data;

        public TestNormalAdapter(List<String> data) {
            this.data = data;
        }

       /* private int[] imgs = {
                R.drawable.jingdong_show_2,
                R.drawable.duanwu,
                R.drawable.huawei_show,
        };*/

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            /*view.setImageResource(imgs[position]);*/
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageLoader imageLoader = NetService.getInstance(ShowProductActivity.this).getImageLoader();
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(view,
                    R.drawable.signin_local_gallry, R.drawable.signin_local_gallry);
            imageLoader.get(data.get(position), listener);
            return view;
        }

        @Override
        public int getCount() {
            return data.size();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        NetService.getInstance(this).cancelRequest("show");
        NetService.getInstance(this).cancelRequest("collection");
        NetService.getInstance(this).cancelRequest("shoopingcar");
    }
}
