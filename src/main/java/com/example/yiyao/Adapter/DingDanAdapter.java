package com.example.yiyao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.fourFragment.DaiPingLunActivity;
import com.example.yiyao.fourFragment.DaiShouHuoActivity;
import com.example.yiyao.fourFragment.DingDanXiangQingActivity;
import com.example.yiyao.fourFragment.ShouHouActivity;
import com.example.yiyao.pojo.Orderform;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by licae on 2016/6/22.
 */
public class DingDanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Orderform> orderforms;
    private LayoutInflater inflater;
    private Context context;


    public DingDanAdapter(Context context, List<Orderform> orderforms) {
        this.context = context;
        this.orderforms = orderforms;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.four_dingdain_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        final Orderform of = orderforms.get(position);
        holder1.tvBianHao.setText("订单编号   " + of.getO_id());
        holder1.tvDate.setText("下单时间   " + of.getO_date());
        holder1.tvJinE.setText("订单总额   ￥" + String.format("%.2f", of.getO_total_value()));
        String s = String.format("%d", of.getO_state());
        String state = null;
        if (s.equals("1")) {
            state = "订单待支付";
            holder1.ivTiaoZhuan.setVisibility(View.VISIBLE);
            //点击跳到订单详情页
            holder1.linearDingDan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,DingDanXiangQingActivity.class);
                    intent.putExtra("of",of);
                    context.startActivity(intent);
                }
            });
        } else if (s.equals("2")) {
            state = "订单待收货";
            holder1.ivTiaoZhuan.setVisibility(View.VISIBLE);
            //点击跳到订单详情页
            holder1.linearDingDan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,DaiShouHuoActivity.class);
                    intent.putExtra("of",of);
                    context.startActivity(intent);
                }
            });
        } else if (s.equals("3")) {
            state = "订单待评论";
            holder1.ivTiaoZhuan.setVisibility(View.VISIBLE);
            //点击跳到订单详情页
            holder1.linearDingDan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,DaiPingLunActivity.class);
                    intent.putExtra("of",of);
                    context.startActivity(intent);
                }
            });
        }else if (s.equals("4")) {
            state = "已完成订单";
            holder1.ivTiaoZhuan.setVisibility(View.GONE);
            //点击跳到订单详情页
            holder1.linearDingDan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ShouHouActivity.class);
                    intent.putExtra("of",of);
                    context.startActivity(intent);
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

    @Override
    public int getItemCount() {
        return orderforms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGoods;
        TextView tvBianHao;
        TextView tvJinE;
        TextView tvDate;
        TextView tvState;
        LinearLayout linearDingDan;
         ImageView ivTiaoZhuan;
        View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ivGoods = (ImageView) view.findViewById(R.id.four_dingdan_iv_goods_image);
            tvBianHao = (TextView) view.findViewById(R.id.four_dingdan_tv_goods_bianhao);
            tvJinE = (TextView) view.findViewById(R.id.four_dingdan_tv_goods_value);
            tvDate = (TextView) view.findViewById(R.id.four_dingdan_tv_goods_date);
            tvState = (TextView) view.findViewById(R.id.four_dingdan_tv_goods_state);
            linearDingDan= (LinearLayout) view.findViewById(R.id.linear_dingdan);
            ivTiaoZhuan= (ImageView) view.findViewById(R.id.iv_tiaozhuan);
        }
    }
}
