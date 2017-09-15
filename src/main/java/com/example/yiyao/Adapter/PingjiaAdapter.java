package com.example.yiyao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.pojo.Evaluate;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/29.
 */
public class PingjiaAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Evaluate> data;
    private LayoutInflater inflater;

    public PingjiaAdapter(Context context, ArrayList<Evaluate> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pingjia_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader imageLoader = NetService.getInstance(context).getImageLoader();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.user_img,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get(data.get(position).getU_image_url(), listener);
        holder.user_name.setText(data.get(position).getU_nickname());
        holder.pingjia_content.setText(data.get(position).getEvaluate_content());
        holder.pingjia_date.setText(data.get(position).getEvaluate_date());

        return convertView;
    }

    private class ViewHolder {
        private TextView user_name;
        private TextView pingjia_content;
        private TextView pingjia_date;
        private ImageView user_img;

        public ViewHolder(View view) {
            user_img = (ImageView) view.findViewById(R.id.user_img);
            pingjia_content = (TextView) view.findViewById(R.id.pingjia_content);
            pingjia_date = (TextView) view.findViewById(R.id.pingjia_date);
            user_name = (TextView) view.findViewById(R.id.user_name);
        }
    }
}
