package com.example.yiyao.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.pojo.Goods;
import java.util.List;

/**
 * Created by licae on 2016/6/23
 * 订单详情Adapter
 */
public class DDXiangQingAdapter extends RecyclerView.Adapter<DDXiangQingAdapter.ViewHold> {
    private List<Goods> data;
    private LayoutInflater inflater;
    private Context context;

    public DDXiangQingAdapter(Context context, List<Goods> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_spxx_item, parent, false);
        ViewHold viewHold = new ViewHold(view);
        return viewHold;
    }

    @Override
    public void onBindViewHolder(ViewHold holder, int position) {
        Goods g=data.get(position);
        holder.tvSPName.setText(g.getG_name());
        holder.tvJiaGe.setText(String.format("￥%.2f",g.getG_price()));
        holder.tvPingPai.setText(g.getG_trademark());
        //加载网络图片
        ImageLoader imageLoader= NetService.getInstance(context).getImageLoader();
        ImageLoader.ImageListener listener=ImageLoader.getImageListener(holder.ivSPUrl,
                R.drawable.signin_local_gallry,R.drawable.signin_local_gallry);
        imageLoader.get(NetService.BASE_URL+g.getG_show_image_url(),listener);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        TextView tvSPName;
        TextView tvPingPai;
        TextView tvJiaGe;
        ImageView ivSPUrl;
        View view;

        public ViewHold(View view) {
            super(view);
            this.view = view;
            tvSPName = (TextView) view.findViewById(R.id.tv_xpxx_name);
            tvPingPai = (TextView) view.findViewById(R.id.tv_xpxx_pp);
            tvJiaGe = (TextView) view.findViewById(R.id.tv_xpxx_jiage);
            ivSPUrl = (ImageView) view.findViewById(R.id.iv_xpxx_image);
        }
    }
}
