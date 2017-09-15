package com.example.yiyao.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.fourFragment.DaiPingLunActivity;
import com.example.yiyao.fourFragment.DaiShouHuoActivity;
import com.example.yiyao.fourFragment.DingDanXiangQingActivity;
import com.example.yiyao.pojo.Orderform;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by licae on 2016/6/23.
 */
public class ZhiFuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Orderform> orderforms;
    private LayoutInflater inflater;
    private Context context;
    private int oId;
    private String st;//状态

    public ZhiFuAdapter(Context context, List<Orderform> orderforms) {
        this.context = context;
        this.orderforms = orderforms;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.dai_zhi_fu_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder holder1 = (ViewHolder) holder;
        final Orderform of = orderforms.get(position);
        holder1.tvDate.setText("下单时间   " + of.getO_date());
        holder1.tvJinE.setText("订单总额   ￥" + String.format("%.2f", of.getO_total_value()));
        String s = String.format("%d", of.getO_state());
        String state = null;
        if (s.equals("1")) {
            state = "订单待支付";
            //点击跳转到订单详情页
            holder1.btnZhiFu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DingDanXiangQingActivity.class);
                    intent.putExtra("of", of);
                    context.startActivity(intent);
                }
            });

        } else if (s.equals("2")) {
            state = "订单待收货";
            holder1.btnZhiFu.setText("待收货");
            //点击跳转到订单详情页
            holder1.btnZhiFu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DaiShouHuoActivity.class);
                    intent.putExtra("of", of);
                    context.startActivity(intent);
                }

            });


        } else if (s.equals("3")) {
            state = "订单待评论";
            holder1.btnZhiFu.setText("去评论");
            //点击跳转到订单详情页
            holder1.btnZhiFu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DaiPingLunActivity.class);
                    intent.putExtra("of", of);
                    context.startActivity(intent);
                }

            });

        } else if (s.equals("4")) {
            state = "已完成订单";
            holder1.btnZhiFu.setText("联系商家");
            holder1.btnZhiFu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View view = inflater.inflate(R.layout.shezhi_tishi_dialog, null);
                    builder.setView(view);
                    builder.setPositiveButton(R.string.four_tishi_btn_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String phoneNumber = "18229971814";
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_CALL);
                            //url统一资源定位符
                            intent.setData(Uri.parse("tel:" + phoneNumber));
                            //开启系统拨号器
                            context.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton(R.string.four_tishi_btn_canl, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();

                }
            });


        }
        holder1.tvState.setText("订单状态   " + state);
        //加载网络图片
        ImageLoader imageLoader = NetService.getInstance(context).getImageLoader();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder1.ivGoods,
                R.drawable.signin_local_gallry, R.drawable.signin_local_gallry);
        imageLoader.get(NetService.BASE_URL + of.getShow_image_url(), listener);

    }

    //当确定收货后，服务器更新o_state为待评论状态
    private void updateState() {
        StringRequest request = new StringRequest(Request.Method.POST, NetService.getAbsoluteUrl("UpdateOrderformServlet"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jo = new JSONObject(s);
                            if (jo.getString("flag").equals("success")) {
                                Log.v("MainActivity", "更新成功！");
                                Log.v("MainActivity", "已收货。。");

                            } else {
                                Log.v("MainActivity", "更新失败！");
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
                map.put("o_id", oId + "");
                Log.v("MainActivity", oId + "-=-=-=-=-=");
                map.put("o_state", st);
                Log.v("MainActivity", st + "-=-=-=-=-=");

                return map;
            }
        };
        NetService.getInstance(context).addToRequestQueue(request, "updateState");
    }

    @Override
    public int getItemCount() {
        return orderforms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGoods;
        TextView tvJinE;
        TextView tvDate;
        TextView tvState;
        Button btnZhiFu;
        View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ivGoods = (ImageView) view.findViewById(R.id.four_dingdan_iv_goods_image);
            tvJinE = (TextView) view.findViewById(R.id.four_dingdan_tv_goods_value);
            tvDate = (TextView) view.findViewById(R.id.four_dingdan_tv_goods_date);
            tvState = (TextView) view.findViewById(R.id.four_dingdan_tv_goods_state);
            btnZhiFu = (Button) view.findViewById(R.id.four_btn_zhi_fu);
        }
    }
}
