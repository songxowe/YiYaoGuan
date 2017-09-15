package com.example.yiyao.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yiyao.R;
import com.example.yiyao.pojo.KaQuan;

import java.util.List;

/**
 * Created by licae on 2016/6/18.
 */
public class KaQuanAdapter extends RecyclerView.Adapter<KaQuanAdapter.ViewHold> {
    private List<KaQuan> kaQuans;
    private LayoutInflater inflater;
    private Context context;

    public KaQuanAdapter(Context context, List<KaQuan> kaQuans) {
        this.context = context;
        this.kaQuans = kaQuans;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.four_kaquan_item,parent,false);
        ViewHold viewHold=new ViewHold(view);
        return viewHold;
    }

    @Override
    public void onBindViewHolder(ViewHold holder, int position) {
        KaQuan kaQuan=kaQuans.get(position);
        holder.tvKaQuan.setText(kaQuan.getName());
        holder.tvKaQuanTime.setText("有效期："+kaQuan.getStartTime()+" - "+kaQuan.getEndTime());
    }

    @Override
    public int getItemCount() {
        return kaQuans.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder{
        TextView tvKaQuan;
        TextView tvKaQuanTime;
        View view;
        public ViewHold(View view) {
            super(view);
            this.view=view;
            tvKaQuan= (TextView) view.findViewById(R.id.tv_kaquan);
            tvKaQuanTime= (TextView) view.findViewById(R.id.tv_kaquan_time);
        }
    }

}
