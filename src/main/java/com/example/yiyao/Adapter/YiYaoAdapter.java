package com.example.yiyao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.yiyao.OneActivity.ShowProductActivity;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.pojo.Goods;

import java.util.List;

/**
 * Created by MBENBEN on 2016-6-20.
 */
public class YiYaoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Goods> list;
    private Context context;
    private LayoutInflater inflater;


    public YiYaoAdapter(List<Goods> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.yiyao_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final Goods goods = list.get(position);
        ImageLoader imageLoader = NetService.getInstance(context).getImageLoader();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(viewHolder.imgDrugUrl,
                R.drawable.signin_local_gallry, R.drawable.signin_local_gallry);
        imageLoader.get(goods.getG_show_image_url(), listener);
        viewHolder.tvDrugName.setText(goods.getG_name());
        viewHolder.tvDrugPrice.setText(goods.getG_price()+"");
        viewHolder.tvDrugPeople.setText("适用人群：" + goods.getG_suit_people());
        viewHolder.tvDrugZheKouPrice.setText(String.format("%.2f", goods.getG_discount() * goods.getG_price()));
        //点击事件
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ShowProductActivity.class);
                intent.putExtra("id",goods.getG_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDrugUrl;
        TextView tvDrugName;
        TextView tvDrugZheKouPrice;
        TextView tvDrugPrice;
        TextView tvDrugPeople;
        ImageView imageBuy;
        RelativeLayout relativeLayout;
        View view;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            imgDrugUrl = (ImageView) view.findViewById(R.id.yiyao_image);
            imageBuy = (ImageView) view.findViewById(R.id.yi_yao_buy);
            tvDrugName = (TextView) view.findViewById(R.id.yi_yao_name);
            tvDrugZheKouPrice = (TextView) view.findViewById(R.id.yi_yao_discountPrice);
            tvDrugPrice = (TextView) view.findViewById(R.id.yi_yao_drugPrice);
            tvDrugPeople = (TextView) view.findViewById(R.id.yi_yao_renqun);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.yi_yao_relat);
        }
    }

}
