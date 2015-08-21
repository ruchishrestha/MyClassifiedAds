package com.example.home_pc.myclassifiedads.jobs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.ImageLoaderAPI;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.main.MainActivity;

import org.json.JSONObject;

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
    public Bitmap jobs_image=null;
    ViewHolder holder;

    public JobAdsAdapter(Context context,ArrayList<JobAdsObject> jobAdsObjects,String userID) {
        this.context=context;
        this.jobAdsObjects=jobAdsObjects;
        this.userID=userID;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView jobsTitle,jobsCategory,jobsVaccancies,jobsSalary,jobsUserID;
        public ImageView jobsImage,jobsPopupMenu;

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
         new AsyncLoadImage().doInBackground(jao.logoURL);
        holder.jobsImage.setImageBitmap(jobs_image);
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

        holder.jobsPopupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.overflow_popup_menu);
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_watchlist:
                                if (userID.equals("Guest")) {
                                    navigatetohome();
                                } else {
                                   JobAdsObject co = new JobAdsObject(jao.jobID, "job", userID);
                                    new AsyncSavetoWatchlist().execute(co);
                                }
                        }
                        return true;
                    }
                });
            }
        });
    }

    public void navigatetohome(){
        final AlertDialog alertDialog = new AlertDialog.Builder(
                context).create();
        alertDialog.setMessage("Please create your account first or Login");
        alertDialog.setIcon(R.drawable.backward);
        alertDialog.setTitle("The Classified Ads App");
        alertDialog.setButton2("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        alertDialog.setButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    protected class AsyncSavetoWatchlist extends
            AsyncTask<JobAdsObject, Void, Boolean> {

        Boolean flag=false;
        @Override
        protected Boolean doInBackground(JobAdsObject... params) {
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObject = api.PushtoWatchlist(params[0].jobID, params[0].tableCategory, params[0].userID);
                JSONParser parser = new JSONParser();
                flag= parser.parseReturnedValue(jsonObject);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncSavetoWatchlist", e.getMessage().toString());
            }
            return flag;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            final AlertDialog alertDialog = new AlertDialog.Builder(
                    context).create();
            if(result==true){
                alertDialog.setMessage("Added to watchlist");
                // Toast.makeText(context,"Added to watchlist",Toast.LENGTH_LONG).show();
            }
            else{
                alertDialog.setMessage("Already added");
            }
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    protected class AsyncLoadImage extends
            AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                jobs_image = ImageLoaderAPI.AzureImageDownloader(params[0]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadImage", e.getMessage());
            }
            return jobs_image;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            jobs_image=Bitmap.createScaledBitmap(jobs_image, dptopx(100), dptopx(100), true);
        }

        }

    @Override
    public int getItemCount() {
        return jobAdsObjects.size();
    }
    public int dptopx(float dp){
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return ((int) (dp * scale + 0.5f));
    }


}