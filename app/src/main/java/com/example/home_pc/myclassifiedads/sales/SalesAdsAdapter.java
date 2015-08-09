package com.example.home_pc.myclassifiedads.sales;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Home-PC on 7/31/2015.
 */
public class SalesAdsAdapter extends RecyclerView.Adapter<SalesAdsAdapter.ViewHolder> {

    Context context;
    private List<String> salesAdsObject;

    public SalesAdsAdapter(Context context,List<String> salesAdsObject) {
        this.context=context;
        this.salesAdsObject=salesAdsObject;
    }

    @Override
    public SalesAdsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return salesAdsObject.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}