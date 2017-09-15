package com.example.yiyao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.yiyao.OneActivity.ShowProductActivity;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.pojo.Goods;

import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 */
public class TwoFragmentRecycleViewAdapter extends RecyclerView.Adapter<TwoFragmentRecycleViewAdapter.ViewHolder> {
    private List<Goods> data;
    private Context context;
    private LayoutInflater inflater;

    public TwoFragmentRecycleViewAdapter(Context context, List<Goods> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.two_recycleview_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Goods good = data.get(position);
        //加载网络图片
        ImageLoader imageLoader = NetService.getInstance(context).getImageLoader();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.two_image_url,
                R.drawable.signin_local_gallry, R.drawable.signin_local_gallry);
        imageLoader.get(good.getG_show_image_url(), listener);

        holder.two_goods_name.setText(good.getG_name());
        holder.two_goods_symptom.setText("针对症状：" + good.getG_cure_symptom());
        holder.two_goods_price.setText(String.format("金额：￥%.2f", good.getG_price() * good.getG_discount()));

        //页面的点击事件
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ShowProductActivity.class);
                intent.putExtra("id",good.getG_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView two_image_url;
        TextView two_goods_name;
        TextView two_goods_symptom;
        TextView two_goods_price;
        View view;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            two_image_url = (ImageView) view.findViewById(R.id.two_image_url);
            two_goods_name = (TextView) view.findViewById(R.id.two_goods_name);
            two_goods_symptom = (TextView) view.findViewById(R.id.two_goods_symptom);
            two_goods_price = (TextView) view.findViewById(R.id.two_goods_price);
        }
    }
}
