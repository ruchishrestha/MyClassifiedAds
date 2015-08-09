package com.example.home_pc.myclassifiedads.jobs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Home-PC on 7/31/2015.
 */
public class JobAdsAdapter extends RecyclerView.Adapter<JobAdsAdapter.ViewHolder> {

    Context context;
    private List<String> jobAdsObject;

    public JobAdsAdapter(Context context,List<String> jobAdsObject) {
        this.context=context;
        this.jobAdsObject=jobAdsObject;
    }

    @Override
    public JobAdsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return jobAdsObject.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}