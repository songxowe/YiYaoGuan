package com.example.yiyao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.yiyao.R;
import com.example.yiyao.ThreeActivity.CheckOutActivity;
import com.example.yiyao.ThreeActivity.UpdateAddressActivity;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.pojo.UserAddress;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 2016/6/24.
 */
public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<UserAddress> persons;
    private LayoutInflater inflater;
    private OnDeleteLister lister;

    public interface OnDeleteLister {
        public void deleteAddress(int position);

    }

    public void setOnDeleteLister(OnDeleteLister lister) {
        this.lister = lister;
    }

    public AddressAdapter(Context context, List<UserAddress> persons) {
        this.context = context;
        this.persons = persons;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.address_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //这里和平常的适配器最大的区别之处在于。
        final ViewHolder viewHolder = (ViewHolder) holder;
        final UserAddress address = persons.get(position);
        viewHolder.u_address.setText(address.getA_receive_user_address());
        viewHolder.u_phone.setText(address.getA_receive_user_phone());
        viewHolder.u_id.setText(address.getA_receive_user_name());

        viewHolder.imgxiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aid = address.getA_id() + "";
                TmallUtils.saveAid(v.getContext(), aid);
                Intent intent = new Intent(v.getContext(), UpdateAddressActivity.class);
                intent.putExtra("address", address);
                context.startActivity(intent);
            }
        });


        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CheckOutActivity.class);
                String uname = viewHolder.u_id.getText().toString();
                String uphone = viewHolder.u_phone.getText().toString();
                String uaddress = viewHolder.u_address.getText().toString();
                TmallUtils.saveAddress(context, uname, uaddress, uphone);
                context.startActivity(intent);
            }
        });

        viewHolder.deleteaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aid = address.getA_id() + "";
                deleteAddress(aid);
                lister.deleteAddress(position);

            }
        });


    }

    @Override
    public int getItemCount() {
        return persons == null ? 0 : persons.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //修改地址图标
        ImageView imgxiu;
        //用户姓名
        TextView u_id;
        //用户电话
        TextView u_phone;
        //用户地址
        TextView u_address;
        RelativeLayout relativeLayout;
        //删除按钮
        Button deleteaddress;


        View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            imgxiu = (ImageView) view.findViewById(R.id.img_xiu);
            u_id = (TextView) view.findViewById(R.id.u_name);
            u_phone = (TextView) view.findViewById(R.id.u_phone);
            u_address = (TextView) view.findViewById(R.id.u_address);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.addres_relative);
            deleteaddress = (Button) view.findViewById(R.id.delete_address);
        }
    }

    public void deleteAddress(String aaid) {
        final String aid = aaid;
        StringRequest request = new StringRequest(Request.Method.POST, NetService.getAbsoluteUrl("DeleteReceiveAddressServlet"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String json) {
                        try {
                            JSONObject jo = new JSONObject(json);
                            if ("success".equals(jo.getString("flag"))) {
                                // 添加成功
                                TmallUtils.showToast(context, "删除成功");
                            } else if ("error".equals(jo.getString("flag"))) {
                                // 添加失败
                                TmallUtils.showToast(context, "删除失败");
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("a_id", aid);
                return params;
            }
        };
        NetService.getInstance(context).addToRequestQueue(request, "deleteAddress");
    }

}
