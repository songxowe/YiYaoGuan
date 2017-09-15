package com.example.yiyao.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.yiyao.OneActivity.ShowProductActivity;
import com.example.yiyao.R;
import com.example.yiyao.ThreeActivity.CheckOutActivity;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.pojo.ShopCar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//购物车
public class ThreeFragment extends Fragment implements View.OnClickListener {
    private List<ShopCar> shopCars = new ArrayList<>();
    //存g_id的List集合
    private List<ShopCar> gid = new ArrayList<>();

    private static final int INITIALIZE = 0;

    private ListView mListView;// 列表

    private ListAdapter mListAdapter;// adapter

    private List<ShopCar> mListData = new ArrayList<ShopCar>();// 数据

    private boolean isBatchModel;// 是否可删除模式

    private RelativeLayout mBottonLayout;
    private CheckBox mCheckAll; // 全选 全不选

    private TextView mEdit; // 切换到删除模式

    private TextView mPriceAll; // 商品总价

    private TextView mSelectNum; // 选中数量

    private TextView mFavorite; // 移到收藏夹

    private TextView mDelete; // 删除 结算

    private float totalPrice = 0; // 商品总价
    /**
     * 批量模式下，用来记录当前选中状态
     */
    private SparseArray<Boolean> mSelectState = new SparseArray<Boolean>();


    public ThreeFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mListData.clear();
        getData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        mBottonLayout = (RelativeLayout) view.findViewById(R.id.cart_rl_allprie_total);
        mCheckAll = (CheckBox) view.findViewById(R.id.check_box);
        mEdit = (TextView) view.findViewById(R.id.three_subtitle);
        mPriceAll = (TextView) view.findViewById(R.id.tv_cart_total);
        mSelectNum = (TextView) view.findViewById(R.id.tv_cart_select_num);
        mFavorite = (TextView) view.findViewById(R.id.tv_cart_move_favorite);
        mDelete = (TextView) view.findViewById(R.id.tv_cart_buy_or_del);
        mListView = (ListView) view.findViewById(R.id.three_listview);
        mListView.setSelector(R.drawable.list_selector);
        mEdit.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mCheckAll.setOnClickListener(this);
        //异步任务获取数据
        new LoadDataTask().execute(new Params(INITIALIZE));
        return view;
    }

    private void doDelete(List<Integer> ids) {
        for (int i = 0; i < mListData.size(); i++) {
            long dataId = mListData.get(i).getS_id();
            for (int j = 0; j < ids.size(); j++) {
                int deleteId = ids.get(j);
                if (dataId == deleteId) {
                    mListData.remove(i);
                    i--;
                    ids.remove(j);
                    j--;
                }
            }
        }

        refreshListView();
        mSelectState.clear();
        totalPrice = 0;
        mSelectNum.setText("已选" + 0 + "件商品");
        mPriceAll.setText(0.00 + "");
        mCheckAll.setChecked(false);

    }

    private final List<Integer> getSelectedIds() {
        ArrayList<Integer> selectedIds = new ArrayList<Integer>();
        for (int index = 0; index < mSelectState.size(); index++) {
            if (mSelectState.valueAt(index)) {
                selectedIds.add(mSelectState.keyAt(index));
            }
        }
        return selectedIds;
    }

    /**
     * 刷新视图
     */
    private void refreshListView() {
        if (mListAdapter == null) {
            mListAdapter = new ListAdapter(getActivity());
            mListView.setAdapter(mListAdapter);
            mListView.setOnItemClickListener(mListAdapter);

        } else {
            mListAdapter.notifyDataSetChanged();

        }
    }

    /**
     * 从网络获取数据
     *
     * @return
     */
    private List<ShopCar> getData() {
        final String id = TmallUtils.getUser(getActivity()).getU_id() + "";
        StringRequest request = new StringRequest(Request.Method.POST, NetService.getAbsoluteUrl("SelectAllShopCarServlet"), new Response.Listener<String>() {
            @Override
            public void onResponse(String json) {
                try {
                    JSONArray ja = new JSONArray(json);
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        ShopCar shopCar = new ShopCar();
                        ShopCar shopCar1 = new ShopCar();
                        shopCar.setS_id(jo.getInt("s_id"));
                        shopCar.setU_id(jo.getInt("u_id"));
                        shopCar.setG_id(jo.getInt("g_id"));
                        //图片路径
                        String url = NetService.BASE_URL + jo.getString("g_image_url");
                        shopCar.setG_image_url(url);
                        shopCar.setG_name(jo.getString("g_name"));
                        shopCar.setG_price((float) jo.getDouble("g_price") * (float) jo.getDouble("g_discount"));
                        shopCar.setG_discount((float) jo.getDouble("g_discount"));
                        shopCar.setShopNumber(1);
                        shopCar.setShopName("111医药网");
                        shopCars.add(shopCar);
                        shopCar1.setG_id(jo.getInt("g_id"));
                        gid.add(shopCar1);

                    }
                    //adapter.notifyDataSetChanged();

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
                params.put("u_id", id);
                return params;
            }
        };
        NetService.getInstance(getActivity()).addToRequestQueue(request, "jiexi");
        return shopCars;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NetService.getInstance(getActivity()).cancelRequest("jiexi");
        NetService.getInstance(getActivity()).cancelRequest("DeleteShopCar");
    }

    class Params {
        int op;

        public Params(int op) {
            this.op = op;
        }

    }

    class Result {
        int op;
        List<ShopCar> list;
    }

    private class LoadDataTask extends AsyncTask<Params, Void, Result> {
        @Override
        protected Result doInBackground(Params... params) {
            Params p = params[0];
            Result result = new Result();
            result.op = p.op;
            try {// 模拟耗时
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.list = getData();
            return result;
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            if (result.op == INITIALIZE) {
                mListData = result.list;
            } else {
                mListData.addAll(result.list);
                Toast.makeText(getActivity(), "添加成功！", Toast.LENGTH_SHORT).show();
            }

            refreshListView();
        }

    }

    /**
     * 购物车的适配器
     */
    private class ListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
        private Context context;


        public ListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.cart_list_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            final ShopCar data = mListData.get(position);
            bindListItem(holder, data);
            holder.add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int _id = (int) mListData.get(position).getS_id();

                    boolean selected = mSelectState.get(_id, false);

                    mListData.get(position).setShopNumber(mListData.get(position).getShopNumber() + 1);

                    notifyDataSetChanged();

                    if (selected) {
                        totalPrice += mListData.get(position).getG_price();
                        mPriceAll.setText(String.format("%.2f", totalPrice));

                    }

                }
            });

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowProductActivity.class);
                    intent.putExtra("id", data.getG_id());
                    context.startActivity(intent);
                }
            });

            //增加的点击事件
            holder.red.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListData.get(position).getShopNumber() == 1)
                        return;

                    int _id = (int) mListData.get(position).getS_id();

                    boolean selected = mSelectState.get(_id, false);
                    mListData.get(position).setShopNumber(mListData.get(position).getShopNumber() - 1);
                    notifyDataSetChanged();

                    if (selected) {
                        totalPrice -= mListData.get(position).getG_price();
                        mPriceAll.setText(String.format("%.2f", totalPrice));

                    }

                }
            });
            return view;
        }

        private void bindListItem(ViewHolder holder, ShopCar data) {
            //加载网络图片
            ImageLoader imageLoader = NetService.getInstance(context).getImageLoader();
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.image,
                    R.drawable.signin_local_gallry, R.drawable.signin_local_gallry);
            imageLoader.get(data.getG_image_url(), listener);
            holder.shopName.setText(data.getShopName());
            holder.content.setText(data.getG_name());
            holder.price.setText(String.format("%.2f", data.getG_price()));
            holder.carNum.setText(data.getShopNumber() + "");
            int _id = data.getS_id();

            boolean selected = mSelectState.get(_id, false);
            holder.checkBox.setChecked(selected);

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ShopCar bean = shopCars.get(position);

            ViewHolder holder = (ViewHolder) view.getTag();
            int _id = (int) bean.getS_id();
            boolean selected = !mSelectState.get(_id, false);
            holder.checkBox.toggle();
            if (selected) {
                mSelectState.put(_id, true);
                totalPrice += bean.getShopNumber() * bean.getG_price();
            } else {
                mSelectState.delete(_id);
                totalPrice -= bean.getShopNumber() * bean.getG_price();
            }
            mSelectNum.setText("已选" + mSelectState.size() + "件商品");
            mPriceAll.setText(String.format("%.2f", totalPrice));
            if (mSelectState.size() == mListData.size()) {
                mCheckAll.setChecked(true);
            } else {
                mCheckAll.setChecked(false);
            }

        }

    }

    class ViewHolder {
        CheckBox checkBox;
        LinearLayout linearLayout;
        ImageView image;
        TextView shopName;
        TextView content;
        TextView carNum;
        TextView price;
        TextView add;
        TextView red;

        public ViewHolder(View view) {
            checkBox = (CheckBox) view.findViewById(R.id.check_box);
            shopName = (TextView) view.findViewById(R.id.tv_source_name);
            image = (ImageView) view.findViewById(R.id.iv_adapter_list_pic);
            content = (TextView) view.findViewById(R.id.tv_intro);
            carNum = (TextView) view.findViewById(R.id.tv_num);
            price = (TextView) view.findViewById(R.id.tv_price);
            add = (TextView) view.findViewById(R.id.tv_add);
            red = (TextView) view.findViewById(R.id.tv_reduce);
            linearLayout=(LinearLayout)view.findViewById(R.id.cart_goto_shop);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.three_subtitle:
                isBatchModel = !isBatchModel;
                if (isBatchModel) {
                    mEdit.setText(getResources().getString(R.string.menu_enter));
                    mDelete.setText(getResources().getString(R.string.menu_del));
                    mBottonLayout.setVisibility(View.GONE);
                    mFavorite.setVisibility(View.VISIBLE);

                } else {
                    mEdit.setText(getResources().getString(R.string.menu_edit));

                    mFavorite.setVisibility(View.GONE);
                    mBottonLayout.setVisibility(View.VISIBLE);
                    mDelete.setText(getResources().getString(R.string.menu_sett));
                }

                break;

            case R.id.check_box:
                if (mCheckAll.isChecked()) {

                    totalPrice = 0;
                    if (mListData != null) {
                        mSelectState.clear();
                        int size = mListData.size();
                        if (size == 0) {
                            return;
                        }
                        for (int i = 0; i < size; i++) {
                            int _id = (int) shopCars.get(i).getS_id();
                            mSelectState.put(_id, true);
                            totalPrice += mListData.get(i).getShopNumber() * mListData.get(i).getG_price();
                        }
                        refreshListView();
                        mPriceAll.setText(String.format("%.2f", totalPrice));
                        mSelectNum.setText("已选" + mSelectState.size() + "件商品");

                    }
                } else {
                    if (mListAdapter != null) {
                        totalPrice = 0;
                        mSelectState.clear();
                        refreshListView();
                        mPriceAll.setText(0.00 + "");
                        mSelectNum.setText("已选" + 0 + "件商品");

                    }
                }
                break;

            case R.id.tv_cart_buy_or_del:
                if (isBatchModel) {
                    List<Integer> ids = getSelectedIds();
                    for (int i = 0; i <= ids.size() - 1; i++) {
                        int aaa = ids.get(i);
                        DeleteShopCar(aaa);
                    }

                    doDelete(ids);
                } else {
                    String mPriceAllMoney = mPriceAll.getText().toString();
                    String cc = "";
                    int key = 0;
                    for (int i = 0; i < mSelectState.size(); i++) {
                        key = mSelectState.keyAt(i);
                        for (int j = 0; j < shopCars.size(); j++) {
                            ShopCar aa = shopCars.get(j);
                            if (key == aa.getS_id()) {
                                int bb = aa.getG_id();
                                cc += bb + "*";
                            }
                        }

                    }

                    if (mSelectState.size() <= 0) {
                        Toast.makeText(getActivity(), "请勾选您要购买的商品", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getActivity(), CheckOutActivity.class);
                        intent.putExtra("mPriceAllMoney", mPriceAllMoney);
                        intent.putExtra("gid", cc.substring(0, cc.length() - 1));
                        TmallUtils.savePrice(getContext(),mPriceAllMoney);
                        startActivity(intent);
                    }
                }

                break;

            default:
                break;
        }
    }

    public void DeleteShopCar(final int s_id) {
        StringRequest request = new StringRequest(Request.Method.POST, NetService.getAbsoluteUrl("DeleteShopCarServlet"), new Response.Listener<String>() {
            @Override
            public void onResponse(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    if ("success".equals(jo.getString("flag"))) {
                        // 删除成功
                        TmallUtils.showToast(getActivity(), "删除成功");
                    } else if ("error".equals(jo.getString("flag"))) {
                        // 删除失败
                        TmallUtils.showToast(getActivity(), "删除失败");
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
                params.put("s_id", s_id + "");
                return params;
            }
        };
        NetService.getInstance(getActivity()).addToRequestQueue(request, "DeleteShopCar");
    }
}
