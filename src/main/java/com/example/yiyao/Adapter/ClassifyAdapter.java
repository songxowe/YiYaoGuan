package com.example.yiyao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yiyao.R;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/24.
 */
public class ClassifyAdapter extends BaseExpandableListAdapter {
    private String[] classify_one_list;
    private Map<String, String[]> classify_two_list;
    private Context context;
    private LayoutInflater inflater;
    private ViewHolder holder;

    public ClassifyAdapter(Context context, String[] classify_one_list, Map<String, String[]> classify_two_list) {
        this.context = context;
        this.classify_one_list = classify_one_list;
        this.classify_two_list = classify_two_list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return classify_one_list.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return classify_two_list.get(classify_one_list[groupPosition]).length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return classify_one_list[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return classify_two_list.get(classify_one_list[groupPosition])[childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.classify_item_one, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (isExpanded) {
            holder.imageView.setImageResource(R.drawable.icon_search_filter_sel);
        } else {
            holder.imageView.setImageResource(R.drawable.icon_search_filter_nor);
        }
        holder.textView.setText(classify_one_list[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.classify_item_one, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setVisibility(View.GONE);
        holder.textView.setText(classify_two_list.get(classify_one_list[groupPosition])[childPosition]);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private View view;

        public ViewHolder(View view) {
            this.view = view;
            this.imageView = (ImageView) view.findViewById(R.id.classify_choose);
            this.textView = (TextView) view.findViewById(R.id.classify_name);
        }
    }
}
