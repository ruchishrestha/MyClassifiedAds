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
import com.example.home_pc.myclassifiedads.mainactivity.MainActivity;
import com.example.home_pc.myclassifiedads.myads.MyJobsEditActivity;

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
    int flag;

    public JobAdsAdapter(Context context,ArrayList<JobAdsObject> jobAdsObjects,String userID,int flag) {
        this.context=context;
        this.jobAdsObjects=jobAdsObjects;
        this.userID=userID;
        this.flag=flag;
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

    public void remove(JobAdsObject jao) {
        int position=jobAdsObjects.indexOf(jao);
        jobAdsObjects.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final JobAdsObject jao=jobAdsObjects.get(position);
        holder.jobsTitle.setText(jao.title);
        holder.jobsCategory.setText(jao.jobCategory);
        holder.jobsVaccancies.setText(jao.vaccancyNo);
        holder.jobsSalary.setText("NPR." + jao.salary);
        holder.jobsUserID.setText(jao.userName);

        if(!jao.logoURL.equals("-")){
            String sub1 = jao.getLogoURL().substring(0,61);
            String sub2 = "temp_"+jao.getLogoURL().substring(61);
            new AsyncLoadImage(position,holder,jao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,sub1+sub2);
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
        switch (flag) {
            case 0:
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
                                            JobAdsObject jo=new JobAdsObject(jao.jobID,jao.title);
                                            new AsyncSavetoWatchlist().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, jo);
                                        }
                                }
                                return true;
                            }
                        });
                    }
                });
                break;
            case 1:
                holder.jobsPopupMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final PopupMenu popup = new PopupMenu(v.getContext(), v);
                        popup.inflate(R.menu.myoverflow_popup_menu);
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()) {
                                    case R.id.item_edit:
                                        int ad_id=jao.jobID;
                                        navigatetoEditActivity(ad_id);
                                        break;
                                    case R.id.item_delete:
                                        final AlertDialog alertDialog = new AlertDialog.Builder(
                                                context).create();
                                        alertDialog.setMessage("Are you sure?");
                                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                deleteFromDb(jao.jobID);
                                                remove(jao);
                                            }
                                        });
                                        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                alertDialog.dismiss();
                                            }
                                        });
                                        alertDialog.show();
                                        break;
                                }
                                return true;
                            }
                        });
                    }
                });
                break;
            default:
                break;
        }

        }

    public void navigatetoEditActivity(int ad_id){
        intent =new Intent(context, MyJobsEditActivity.class);
        intent.putExtra("userID", userID);
        intent.putExtra("adid", ad_id);
        context.startActivity(intent);

    }
    public void navigatetohome(){
        final AlertDialog alertDialog = new AlertDialog.Builder(
                context).create();
        alertDialog.setMessage("Please create your account first or Login");
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
            AsyncTask<JobAdsObject, Void, Boolean> {

        Boolean flag=false;
        @Override
        protected Boolean doInBackground(JobAdsObject... params) {
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObject = api.PushtoWatchlist(params[0].jobID,"job",userID,params[0].title);
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

                jobs_image = ImageLoaderAPI.AzureImageDownloader(params[0]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadImage", e.getMessage());
            }
            return jobs_image;
        }

        @Override
        protected void onPostExecute(Bitmap bitresult){
            //jobs_image=Bitmap.createScaledBitmap(result, dptopx(100), dptopx(100), true);
            holder.jobsImage.setImageBitmap(bitresult);
        }

    }

    public void deleteFromDb(int adid) {
        Toast.makeText(context, "" + adid, Toast.LENGTH_LONG).show();
        new AsyncDeleteJobsAd().execute(adid);
    }

    protected class AsyncDeleteJobsAd extends
            AsyncTask<Integer, Void, Void> {


        @Override
        protected Void doInBackground(Integer ...params) {
            // TODO Auto-generated method stub

            RestAPI api = new RestAPI();
            try {
                api.DeleteJobsAd(params[0],"jobs");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadResult", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub

            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();

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