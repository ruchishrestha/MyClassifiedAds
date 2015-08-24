package com.example.home_pc.myclassifiedads.sales;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.home_pc.myclassifiedads.R;

import java.util.ArrayList;

/**
 * Created by Ruchi on 2015-08-24.
 */
public class NewAdsFragment extends Fragment {

    RecyclerView salesList;
    SwipeRefreshLayout mswipeRefreshLayout;
    ProgressDialog progressDialog;
    ArrayList<SalesAdsObject> salesAdsObjects;
    Context context;
    SalesAdsAdapter salesAdsAdapter;
    String userID;
    ImageView popupMenu;

    public NewAdsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ads_recycler_view, container,
                false);
        userID = getArguments().getString("userID");
        salesList = (RecyclerView) view.findViewById(R.id.cardList);
        mswipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        context = getActivity();
        setHasOptionsMenu(true);
        salesList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        salesList.setLayoutManager(llm);
        // loadRealestateAds();
        return view;
    }

}


