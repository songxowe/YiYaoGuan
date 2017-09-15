package com.example.yiyao.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.pojo.Goods;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单详情评价Adapter
 */
public class DDXiangQingPjAdapter extends RecyclerView.Adapter<DDXiangQingPjAdapter.ViewHold> {
    private List<Goods> data;
    private LayoutInflater inflater;
    private Context context;
    private int uId;
    private int gId;
    private String systemDate;
    private String pj;
    private int level = 2;
    private int count = 0;

    public DDXiangQingPjAdapter(Context context, List<Goods> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return count;
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_sppj_item, parent, false);
        ViewHold viewHold = new ViewHold(view);
        return viewHold;
    }

    @Override
    public void onBindViewHolder(final ViewHold holder, int position) {
        final Goods g = data.get(position);

        holder.tvSPName.setText(g.getG_name());
        holder.tvJiaGe.setText(String.format("￥%.2f", g.getG_price()));
        holder.tvPingPai.setText(g.getG_trademark());
        gId = g.getG_id();

        //加载网络图片
        ImageLoader imageLoader = NetService.getInstance(context).getImageLoader();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.ivSPUrl,
                R.drawable.signin_local_gallry, R.drawable.signin_local_gallry);
        imageLoader.get(NetService.BASE_URL + g.getG_show_image_url(), listener);


        //评价的点击事件。
        holder.btnPj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(context).inflate(R.layout.ping_jia_dialog, null);
                final RadioButton rbHao = (RadioButton) view.findViewById(R.id.rb_hao);
                final RadioButton rbZhong = (RadioButton) view.findViewById(R.id.rb_zhong);
                final RadioButton rbCha = (RadioButton) view.findViewById(R.id.rb_cha);

                final EditText pingJia = (EditText) view.findViewById(R.id.et_pj);


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(view);


                builder.setPositiveButton(R.string.four_queding, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //商品id

                        //用户id
                        uId = TmallUtils.getUser(context).getU_id();
                        //评价值

                        if (rbHao.isChecked()) {
                            level = 1;
                        } else if (rbZhong.isChecked()) {
                            level = 2;
                        } else if (rbCha.isChecked()) {
                            level = 3;
                        }
                        pj = pingJia.getText().toString();
                        //评价时间
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        systemDate = sdf.format(date);
                        //将评价提交到服务器以对话框显示
                        //    dataToService();
                        StringRequest request = new StringRequest(Request.Method.POST, NetService.getAbsoluteUrl("AddEvaluateServlet"),
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        JSONObject jo = null;
                                        try {
                                            jo = new JSONObject(s);
                                            if (jo.getString("flag").equals("success")) {
                                                TmallUtils.showToast(context, "成功评价！");
                                                holder.btnPj.setText("已评价");
                                                holder.btnPj.setClickable(false);
                                                count++;
                                            } else {
                                                Log.v("MainActivity", "评价失败");
                                            }
                                        } catch (JSONException e) {
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
                                map.put("g_id", gId + "");
                                map.put("u_id", uId + "");
                                map.put("evaluate_date", systemDate);
                                map.put("evaluate_content", pj);
                                map.put("evaluate_level", level + "");
                                return map;
                            }
                        };
                        NetService.getInstance(context).addToRequestQueue(request, "evaluate");

                    }
                });
                builder.setNeutralButton(R.string.four_tishi_btn_canl, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
            }
        });


    }

    //将评价提交到服务器
   /* private void dataToService() {
        StringRequest request = new StringRequest(Request.Method.POST, VolleyUtil.getAbsoluteUrl("AddEvaluateServlet"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        JSONObject jo = null;
                        try {
                            jo = new JSONObject(s);
                            if (jo.getString("flag").equals("success")) {
                                TmallUtils.showToast(context, "成功评价！");
                                Log.v("MainActivity", "评价完成");

                            } else {
                                Log.v("MainActivity", "评价失败");
                            }
                        } catch (JSONException e) {
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
                map.put("g_id", gId + "");
                map.put("u_id", uId + "");
                map.put("evaluate_date", systemDate);
                map.put("evaluate_content", pj);
                map.put("evaluate_level", level + "");
                return map;
            }
        };
        VolleyUtil.getInstance(context).addToRequestQueue(request, "evaluate");

    }*/

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        TextView tvSPName;
        TextView tvPingPai;
        TextView tvJiaGe;
        ImageView ivSPUrl;
        Button btnPj;
        View view;

        public ViewHold(View view) {
            super(view);
            this.view = view;
            tvSPName = (TextView) view.findViewById(R.id.tv_xpxx_name);
            tvPingPai = (TextView) view.findViewById(R.id.tv_xpxx_pp);
            tvJiaGe = (TextView) view.findViewById(R.id.tv_xpxx_jiage);
            ivSPUrl = (ImageView) view.findViewById(R.id.iv_xpxx_image);
            btnPj = (Button) view.findViewById(R.id.btn_pj);
        }
    }
}
