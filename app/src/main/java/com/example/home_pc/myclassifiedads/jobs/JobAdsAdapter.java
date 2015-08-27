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

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.ImageLoaderAPI;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.mainactivity.MainActivity;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Home-PC on 7/31/2015.
 */
public class JobAdsAdapter extends RecyclerView.Adapter<JobAdsAdapter.ViewHolder> {

    ArrayList<JobAdsObject> jobAdsObjects;
    Context context;
    View view;
    Intent intent;
    String userID;
    public Bitmap jobs_image;
    ViewHolder vh;

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
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jobs, parent, false);
        // set the view's size, margins, paddings and layout parameters
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final JobAdsObject jao=jobAdsObjects.get(position);
        holder.jobsTitle.setText(jao.title);
        holder.jobsCategory.setText(jao.jobCategory);
        holder.jobsVaccancies.setText(jao.vaccancyNo);
        holder.jobsSalary.setText("NPR."+jao.salary);
        holder.jobsUserID.setText(jao.userName);
        if(!jao.logoURL.equals("-")){
            new AsyncLoadImage(position,holder,jao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,jao.logoURL);
        }

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
                                    new AsyncSavetoWatchlist().execute(jao.jobID);
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
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    protected class AsyncSavetoWatchlist extends
            AsyncTask<Integer, Void, Boolean> {

        Boolean flag=false;
        @Override
        protected Boolean doInBackground(Integer... params) {
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObject = api.PushtoWatchlist(params[0],"job",userID);
                JSONParser parser = new JSONParser();
                flag= parser.parseReturnedValue(jsonObject);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncSavetoWatchlist", e.getMessage());
            }
            return flag;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            final AlertDialog alertDialog = new AlertDialog.Builder(
                    context).create();
            if (result) {
                alertDialog.setMessage("Added to watchlist");
                // Toast.makeText(context,"Added to watchlist",Toast.LENGTH_LONG).show();
            } else {
                alertDialog.setMessage("Already added");
            }
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {
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

        int pos;
        ViewHolder holder;
        JobAdsObject jobAdsObject;

        public AsyncLoadImage(int pos,ViewHolder holder,JobAdsObject jobAdsObject) {
            this.pos = pos;
            this.holder = holder;
            this.jobAdsObject = jobAdsObject;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                jobs_image = ImageLoaderAPI.AzureImageDownloader2(params[0]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadImage", e.getMessage());
            }
            return jobs_image;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            jobs_image=Bitmap.createScaledBitmap(result, dptopx(100), dptopx(100), true);
            holder.jobsImage.setImageBitmap(jobs_image);
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