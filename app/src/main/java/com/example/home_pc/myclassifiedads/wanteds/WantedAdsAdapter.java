package com.example.home_pc.myclassifiedads.wanteds;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Home-PC on 7/26/2015.
 */
public class WantedAdsAdapter extends RecyclerView.Adapter<WantedAdsAdapter.ViewHolder> {

    Context context;
    private List<WantedAdObject> wantedAdObject;

    public WantedAdsAdapter(Context context,List<WantedAdObject> wantedAdObject) {
        this.context=context;
        this.wantedAdObject=wantedAdObject;
    }

    @Override
    public WantedAdsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return wantedAdObject.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
