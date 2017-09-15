package com.example.yiyao.OneFragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.example.yiyao.R;
import com.example.yiyao.Utils.NetService;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowProductImagetextFragment extends Fragment {
    private ImageLoader imageLoader;
    private ImageLoader.ImageListener listener;
    private String imageurl;
    private ImageView show_imgtxt;

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }


    public ShowProductImagetextFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_product_imagetext, container, false);
        show_imgtxt = (ImageView) view.findViewById(R.id.show_imgtxt);
        imageLoader = NetService.getInstance(getActivity()).getImageLoader();
        listener = ImageLoader.getImageListener(show_imgtxt,
                R.drawable.signin_local_gallry, R.drawable.signin_local_gallry);
        imageLoader.get(imageurl, listener);
        return view;
    }
}
