package com.example.home_pc.myclassifiedads.draweritemsfragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;

/**
 * Created by Home-PC on 5/21/2015.
 */
public class AboutFragment extends Fragment {


    public AboutFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.item_layouts,container, false);


        return view;
    }

}
