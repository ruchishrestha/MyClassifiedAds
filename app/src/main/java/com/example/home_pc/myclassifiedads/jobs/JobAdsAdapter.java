package com.example.home_pc.myclassifiedads.jobs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home-PC on 7/31/2015.
 */
public class JobAdsAdapter extends RecyclerView.Adapter<JobAdsAdapter.ViewHolder> {

    ArrayList<JobAdsObject> jobAdsObjects;
    Context context;
    View view;
    Intent intent;
    String userID;
    public JobAdsAdapter(Context context,ArrayList<JobAdsObject> jobAdsObjects,String userID) {
        this.context=context;
        this.jobAdsObjects=jobAdsObjects;
        this.userID=userID;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView jobsTitle,jobsCategory,jobsVaccancies,jobsSalary,jobsUserID;
        ImageView jobsImage,jobsPopupMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            jobsTitle=(TextView)itemView.findViewById(R.id.jobsTitle);
            jobsCategory=(TextView)itemView.findViewById(R.id.jobsCategory);
            jobsVaccancies=(TextView)itemView.findViewById(R.id.jobVacancies);
            jobsSalary=(TextView)itemView.findViewById(R.id.jobsSalary);
            jobsUserID=(TextView)itemView.findViewById(R.id.jobsUserId);
            jobsImage=(ImageView)itemView.findViewById(R.id.jobsImage);
            jobsPopupMenu=(ImageView)itemView.findViewById(R.id.jobsPopupMenu);
        }
    }

    @Override
    public JobAdsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final JobAdsObject jao=jobAdsObjects.get(position);

        holder.jobsTitle.setText(jao.title);
        holder.jobsCategory.setText(jao.jobCategory);
        holder.jobsVaccancies.setText(jao.vaccancyNo);
        holder.jobsSalary.setText("NPR."+jao.salary);
        holder.jobsUserID.setText(jao.username);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), JobDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("jobID", jao.jobID);
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobAdsObjects.size();
    }



}