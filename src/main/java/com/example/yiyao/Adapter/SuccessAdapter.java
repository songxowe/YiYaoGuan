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
 * Created by MBENBEN on 2016-7-4.
 */
public class SuccessAdapter extends RecyclerView.Adapter<SuccessAdapter.ViewHolder> {
    private List<Goods> list;
    private Context context;
    private LayoutInflater inflater;

    public SuccessAdapter(List<Goods> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.zhekou_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Goods goods = list.get(position);
        //加载网络图片
        ImageLoader imageLoader = NetService.getInstance(context).getImageLoader();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.imgDrugUrl,
                R.drawable.signin_local_gallry, R.drawable.signin_local_gallry);
        imageLoader.get(goods.getG_show_image_url(), listener);

        holder.tvDrugName.setText(goods.getG_name());
        holder.tvDrugPrice.setText("￥" + goods.getG_price());
        holder.tvDrugZheKouPrice.setText(String.format("￥%.2f", goods.getG_discount() * goods.getG_price()));
        //商品图片的点击事件
        holder.imgDrugUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowProductActivity.class);
                intent.putExtra("id", goods.getG_id());
                context.startActivity(intent);
            }
        });

        holder.imageBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowProductActivity.class);
                intent.putExtra("id", goods.getG_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgDrugUrl;
        TextView tvDrugName;
        TextView tvDrugZheKouPrice;
        TextView tvDrugPrice;
        ImageView imageBuy;
        View view;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            imgDrugUrl = (ImageView) view.findViewById(R.id.image_url);
            tvDrugName = (TextView) view.findViewById(R.id.tv_drugName);
            tvDrugZheKouPrice = (TextView) view.findViewById(R.id.tv_zhekouDrugPrice);
            tvDrugPrice = (TextView) view.findViewById(R.id.tv_drugPrice);
            imageBuy = (ImageView) view.findViewById(R.id.image_buy);
        }
    }
}
