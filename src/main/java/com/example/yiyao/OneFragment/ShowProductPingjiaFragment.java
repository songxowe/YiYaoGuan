package com.example.yiyao.OneFragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.yiyao.Adapter.PingjiaAdapter;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.pojo.Evaluate;
import com.example.yiyao.widget.MyListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowProductPingjiaFragment extends Fragment implements View.OnClickListener {
    private int g_id;
    private int good_evaluate;
    private int normal_evaluate;
    private int bad_evaluate;
    private int id;

    private int evaluate_level = 2;

    private ArrayList<Evaluate> data;
    private MyListView listView;
    private PingjiaAdapter adapter;

    private TextView good_pingjia, normal_pingjia, bad_pingjia;

    public void setG_id(int g_id, int good_evaluate, int normal_evaluate, int bad_evaluate) {
        this.g_id = g_id;
        this.good_evaluate = good_evaluate;
        this.normal_evaluate = normal_evaluate;
        this.bad_evaluate = bad_evaluate;
    }


    public ShowProductPingjiaFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_product_pingjia, container, false);
        id = getActivity().getIntent().getIntExtra("id", 0);
        good_pingjia = (TextView) view.findViewById(R.id.tv_good_pingjia);
        good_pingjia.setText("好评（" + good_evaluate + "）");
        good_pingjia.setOnClickListener(this);
        normal_pingjia = (TextView) view.findViewById(R.id.tv_normal_pingjia);
        normal_pingjia.setText("中评（" + normal_evaluate + "）");
        normal_pingjia.setOnClickListener(this);
        bad_pingjia = (TextView) view.findViewById(R.id.tv_bad_pingjia);
        bad_pingjia.setText("差评（" + bad_evaluate + "）");
        bad_pingjia.setOnClickListener(this);
        listView = (MyListView) view.findViewById(R.id.pingjia_list);
        adapter = new PingjiaAdapter(getActivity(), data);
        listView.setAdapter(adapter);
        normal_pingjia.setActivated(true);
        getdatafromInternet();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_good_pingjia:
                data.clear();
                evaluate_level = 1;
                good_pingjia.setActivated(true);
                normal_pingjia.setActivated(false);
                bad_pingjia.setActivated(false);
                getdatafromInternet();
                break;
            case R.id.tv_normal_pingjia:
                data.clear();
                evaluate_level = 2;
                good_pingjia.setActivated(false);
                normal_pingjia.setActivated(true);
                bad_pingjia.setActivated(false);
                getdatafromInternet();
                break;
            case R.id.tv_bad_pingjia:
                data.clear();
                evaluate_level = 3;
                good_pingjia.setActivated(false);
                normal_pingjia.setActivated(false);
                bad_pingjia.setActivated(true);
                getdatafromInternet();
                break;
        }

    }


    public void getdatafromInternet() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                NetService.getAbsoluteUrl("SelectEvaluateByEvaluteLevelServlet"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            Log.v("MainActivity", s + "");
                            JSONArray ja = new JSONArray(s);
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                Evaluate evaluate = new Evaluate();
                                //昵称;如果未设置，则为编号
                                if (!jo.isNull("u_nickname")) {
                                    evaluate.setU_nickname(jo.getString("u_nickname"));
                                } else {
                                    evaluate.setU_nickname(jo.getString("u_id"));
                                }
                                //头像
                                if (!jo.isNull("u_imageurl")) {
                                    evaluate.setU_image_url(NetService.BASE_URL + jo.getString("u_imageurl"));
                                } else {
                                    evaluate.setU_image_url("");
                                }
                                //评价内容
                                evaluate.setEvaluate_content(jo.getString("evaluate_content"));
                                //评价时间
                                evaluate.setEvaluate_date(jo.getString("evaluate_date"));
                                data.add(evaluate);
                                Log.v("MainActivity", jo.getString("evaluate_content") + "");
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("g_id", id + "");
                params.put("evaluate_level", evaluate_level + "");
                Log.v("MainActivity", g_id + "9999" + evaluate_level + "000000");
                return params;
            }
        };
        NetService.getInstance(getActivity()).addToRequestQueue(stringRequest, "getpingjia");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NetService.getInstance(getActivity()).cancelRequest("getpingjia");
    }
}
