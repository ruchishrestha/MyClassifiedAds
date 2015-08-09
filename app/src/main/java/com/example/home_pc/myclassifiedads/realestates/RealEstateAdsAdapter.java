package com.example.home_pc.myclassifiedads.realestates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Home-PC on 7/31/2015.
 */
public class RealEstateAdsAdapter extends RecyclerView.Adapter<RealEstateAdsAdapter.ViewHolder> {

    Context context;
    private List<RealEstatesAdObject> realEstatesAdObjects;

    public RealEstateAdsAdapter(Context context,List<RealEstatesAdObject> realEstatesAdObjects) {
        this.context=context;
        this.realEstatesAdObjects=realEstatesAdObjects;
    }

    @Override
    public RealEstateAdsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return realEstatesAdObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}