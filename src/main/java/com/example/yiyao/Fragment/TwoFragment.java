package com.example.yiyao.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
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
import com.example.yiyao.Adapter.ClassifyAdapter;
import com.example.yiyao.Adapter.TwoFragmentRecycleViewAdapter;
import com.example.yiyao.OneActivity.FindDrugActivity;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.WebViewActivity;
import com.example.yiyao.pojo.Goods;
import com.example.yiyao.zxing.activity.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//分类页面
public class TwoFragment extends Fragment {
    //分类列表 数据与适配器
    private String[] classify_one_list;
    private Map<String, String[]> classify_two_list;
    private ClassifyAdapter adapter;
    //商品类适配器与数据
    private List<Goods> data;
    private RecyclerView.Adapter recycleadapter;
    //总页数，当前页数
    private int totalpage = 1;
    private int page = 1;

    private ImageView imageViewSao;
    private Button button;

    //控件
    private ExpandableListView two_classify_name_list;
    private RecyclerView two_recyclerView_list;
    private TextView two_classify_name;

    //支持下拉刷新的ViewGroup
    private PtrClassicFrameLayout mPtrFrame;
    //添加Header和Footer的封装类
    private RecyclerAdapterWithHF mAdapter;

    public TwoFragment() {

    }


    //创建时，初始化数据与控件
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化数据
        data = new ArrayList<>();
        /**
        classify_one_list = new String[]{"中西成药", "中药饮品", "母婴用品", "美容护理", "保健品"};

        classify_two_list = new HashMap<>();
        classify_two_list.put("中西成药", new String[]{"健脾益肾药品", "美容养颜类", "补血类", "养心安神类"});
        classify_two_list.put("中药饮品", new String[]{"健脾益肾药品", "美容养颜类", "补血类", "养心安神类"});
        classify_two_list.put("母婴用品", new String[]{"健脾益肾药品", "美容养颜类", "补血类", "养心安神类"});
        classify_two_list.put("美容护理", new String[]{"健脾益肾药品", "美容养颜类", "补血类", "养心安神类"});
        classify_two_list.put("保健品", new String[]{"健脾益肾药品", "美容养颜类", "补血类", "养心安神类"});
         **/
        classify_one_list = new String[]{"滋补调养", "风湿骨科", "呼吸道疾病", "男科疾病", "妇科疾病",
                "皮肤病", "五官科疾病", "消化系统疾病", "心脑血管", "肿瘤科", "肝病科", "神经系统", "内分泌", "中西药品维生素钙剂"};
        classify_two_list = new HashMap<>();
        classify_two_list.put("滋补调养", new String[]{"健脾益肾", "美容养颜", "补血补气", "安神助眠", "增强抵抗力",
                "减肥瘦身"});
        classify_two_list.put("风湿骨科", new String[]{"跌打骨伤类", "风湿类风湿", "关节炎", "外用贴膏", "骨质疏松",
                "疼痛用药", "骨质增生", "痛风", "颈椎病", "腰椎病", "器官移植", "脊椎炎", "股骨头坏死", "坐骨神经痛", "强直性脊柱炎"});
        classify_two_list.put("呼吸道疾病", new String[]{"感冒发烧", "咳嗽", "上火", "哮喘", "小儿感冒", "慢性阻塞性肺疾病", "支气管炎"});
        classify_two_list.put("男科疾病", new String[]{"性功能障碍", "前列腺炎", "泌尿系统疾病", "肾病", "男性不育症"});
        classify_two_list.put("妇科疾病", new String[]{"子宫疾病用药", "痛经", "盆腔炎", "宫颈炎", "产后用药", "白带异常", "乳腺疾病",
                "更年期", "月经不调", "阴道炎", "避孕", "保胎促孕"});
        classify_two_list.put("皮肤病", new String[]{"皮炎癣症", "疱疹", "烧烫创伤", "痤疮(青春痘)", "银屑病(牛皮癣)", "过敏", "白癜风",
                "脱发", "蚊虫叮咬", "荨麻疹", "皮肤其他类", "尖锐湿疣", "黄褐斑", "灰指甲", "疤痕", "系统性红斑狼疮", "艾滋病", "色斑"});
        classify_two_list.put("五官科疾病", new String[]{"咽喉炎", "鼻炎", "眼部炎症", "白内障", "眩晕", "牙周病痛", "牙周病痛", "视网膜病变",
                "耳聋耳鸣", "青光眼", "近视弱视", "眼部麻醉", "眼干眼涩", "口腔溃疡"});
        classify_two_list.put("消化系统疾病", new String[]{"胃炎", "便秘", "腹泻", "胃肠溃疡", "消化不良", "痔疮", "小儿胃肠病", "消化系统其他类",
                "蛔虫病", "营养剂", "克罗恩病", "结肠炎"});
        classify_two_list.put("心脑血管", new String[]{"高血压", "冠心病", "高血脂", "心绞痛", "动脉硬化", "心律失常", "心力衰竭", "心肌炎",
                "血液疾病", "肺动脉高压", "低血压", "心血管疾病", "眩晕症", "脑血管病", "静脉曲张"});
        classify_two_list.put("肿瘤科", new String[]{"肝癌", "肺癌", "白血病", "淋巴癌", "胃癌", "乳腺癌", "升白升血小板", "癌症止痛止呕",
                "结直肠癌", "宫颈癌", "甲状腺瘤", "前列腺瘤", "食道癌", "脑癌", "鼻咽癌", "骨癌", "卵巢癌", "睾丸肿瘤", "膀胱癌", "肾癌", "胰腺癌", "化疗提高营养"});
        classify_two_list.put("肝病科", new String[]{"乙肝", "肝纤维化肝硬化", "胆道疾病", "免疫调节", "胆结石", "脂肪肝", "酒精肝", "肝性脑病",
                "肝纤维化", "保肝护肝"});
        classify_two_list.put("神经系统", new String[]{"中风", "癫痫", "腹泻", "抑郁症", "头痛偏头痛", "失眠", "老年性痴呆", "神经损伤",
                "多动症", "帕金森", "神经衰弱", "神经性疼痛", "焦虑症", "运动神经元病", "周围神经病变", "面瘫", "戒烟", "小儿脑瘫", "躁狂症", "神经分裂症"});
        classify_two_list.put("内分泌", new String[]{"抗病毒药", "糖尿病", "甲状腺疾病"});
        classify_two_list.put("中西药品维生素钙剂", new String[]{"中西药品维生素", "中西药品钙剂", "中西药品矿物质", "中西药品叶酸"});

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        initView(view);
        page = 1;
        getTotal("健脾益肾");
        selectFromInternet("健脾益肾");
        return view;
    }

    //获得详情列表
    private void selectFromInternet(final String classifyname) {
        String url = NetService.getAbsoluteUrl("SelectGoodsServlet");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    //解析Json数据
                    JSONArray ja = new JSONArray(s);
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        Goods goods = new Goods();
                        //加载主图
                        String g_show_image_url = NetService.BASE_URL + jo.getString("g_show_image_url");
                        Float price = Float.valueOf(jo.getString("g_price"));
                        Float discount = Float.valueOf(jo.getString("g_discount"));
                        //加载显示图
                        String g_cure_symptom = jo.getString("g_cure_symptom");
                        goods.setG_id(jo.getInt("g_id"));
                        goods.setG_show_image_url(g_show_image_url);
                        goods.setG_name(jo.getString("g_name"));
                        goods.setG_price(price);
                        goods.setG_discount(discount);
                        goods.setG_cure_symptom(g_cure_symptom);
                        data.add(goods);
                    }
                    mAdapter.notifyDataSetChanged();
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
                map.put("order", "price");
                map.put("g_classify_two", classifyname);
                return map;
            }
        };
        NetService.getInstance(getActivity()).addToRequestQueue(stringRequest, "classify");
    }

    /**
     * 获取总页数的方法
     */
    private void getTotal(final String classifyname) {
        String url = NetService.getAbsoluteUrl("SelectGoodsTotalServlet");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jo = new JSONObject(s);
                    //获取网络中的总页数
                    totalpage = jo.getInt("totalpage");
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
                map.put("g_classify_two", classifyname);
                return map;
            }
        };
        NetService.getInstance(getActivity()).addToRequestQueue(stringRequest, "classifygetTotal");
    }

    private void initView(View view) {
        two_classify_name_list = (ExpandableListView) view.findViewById(R.id.two_classify_name_list);
        two_recyclerView_list = (RecyclerView) view.findViewById(R.id.two_recyclerView_list);
        two_classify_name = (TextView) view.findViewById(R.id.two_classify_name);
        imageViewSao = (ImageView) view.findViewById(R.id.two_fragment_sao);
        button = (Button) view.findViewById(R.id.btn_job_find);

        //搜索药品的点击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FindDrugActivity.class));
            }
        });

        //扫描的点击事件
        imageViewSao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });


        /*********************************** 分类布局 ***************************************/
        adapter = new ClassifyAdapter(getActivity(), classify_one_list, classify_two_list);
        two_classify_name_list.setAdapter(adapter);
        //设置展开事件
        two_classify_name_list.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < classify_one_list.length; i++) {
                    if (groupPosition != i) {
                        two_classify_name_list.collapseGroup(i);
                    }
                }
            }
        });
        //设置点击事件  从服务器获取收据
        two_classify_name_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String classifyname = classify_two_list.get(classify_one_list[groupPosition])[childPosition];
                two_classify_name.setText(classifyname);
                data.clear();
                page = 1;
                //分类名字
                getTotal(classifyname);
                selectFromInternet(classifyname);
                return true;
            }
        });

        /***********************************  商品详情布局  ***************************************/
        //将 recycleview设置为网格布局
        two_recyclerView_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleadapter = new TwoFragmentRecycleViewAdapter(getActivity(), data);
        mAdapter = new RecyclerAdapterWithHF(recycleadapter);
        two_recyclerView_list.setAdapter(mAdapter);

        //加载刷新控件的初始化
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.two_rotate_header_list_view_frame);
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
        //下拉刷新
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                data.clear();
                page = 1;
                String classify = two_classify_name.getText().toString();
                getTotal(classify);
                selectFromInternet(classify);
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
                String classify = two_classify_name.getText().toString();
                page++;
                if (page <= totalpage) {
                    selectFromInternet(classify);
                    mAdapter.notifyDataSetChanged();
                } else {
                    TmallUtils.showToast(getActivity(), "没有更多的数据");
                }
                mPtrFrame.loadMoreComplete(true);
                mPtrFrame.setLoadMoreEnable(true);
            }
        });
    }


    /**
     * 打开二维码扫描
     */
    private void open() {
        config();
        startActivityForResult(new Intent(getActivity(),
                CaptureActivity.class), 0);
    }


    /**
     * 提高屏幕亮度
     */
    private void config() {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.screenBrightness = 1.0f;
        getActivity().getWindow().setAttributes(lp);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            Log.v("MainActivity", result);
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("webView", result);
            startActivity(intent);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        NetService.getInstance(getActivity()).cancelRequest("classifygetTotal");
    }
}
