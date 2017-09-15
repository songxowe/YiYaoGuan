package com.example.yiyao.OneFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yiyao.R;

/**
 * 会员权益
 */
public class VipFragment extends Fragment {


    public VipFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_vip, container, false);
        return view;
    }

}
