package com.example.home_pc.myclassifiedads.draweritemsfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.home_pc.myclassifiedads.R;

/**
 * Created by Home-PC on 7/23/2015.
 */
public class MyAdsFragment extends Fragment {



    public MyAdsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_layouts, container,
                false);


        return view;
    }
}
