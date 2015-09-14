package com.example.home_pc.myclassifiedads.draweritemsfragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.common_contactsnwanted.ContactsnWantedViewDetail;
import com.example.home_pc.myclassifiedads.jobs.JobDetailActivity;
import com.example.home_pc.myclassifiedads.realestates.RealEstateViewDetail;
import com.example.home_pc.myclassifiedads.sales.SalesDetailView;

import java.util.ArrayList;

/**
 * Created by Ruchi on 2015-09-14.
 */
public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.ViewHolder> {

    Context context;
    ArrayList<WatchlistObject> watchlistObjects;
    String userID;
    View view;
    ViewHolder vh;
    Intent intent;
    public WatchlistAdapter(Context context,ArrayList<WatchlistObject> watchlistObjects,String userID) {
        this.context=context;
        this.watchlistObjects=watchlistObjects;
        this.userID=userID;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView title,category;
        protected Button delete_btn;
        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            category=(TextView)itemView.findViewById(R.id.category);
            delete_btn=(Button)itemView.findViewById(R.id.delete_btn);
        }

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_watchlist, parent, false);
        // set the view's size, margins, paddings and layout parameters
        vh = new ViewHolder(view);
        return vh;
    }

    public void remove(WatchlistObject wo) {
        int position=watchlistObjects.indexOf(wo);
        watchlistObjects.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WatchlistObject wo=watchlistObjects.get(position);
        holder.title.setText(wo.title);
        holder.category.setText(wo.category);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                switch (wo.category) {
                    case "contacts":
                        intent = new Intent(v.getContext(), ContactsnWantedViewDetail.class);
                        bundle.putInt("adid", wo.adid);
                        bundle.putString("userID", userID);
                        bundle.putString("tableCategory", wo.category);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        break;
                    case "wanted":
                        intent = new Intent(v.getContext(), ContactsnWantedViewDetail.class);
                        bundle.putInt("adid", wo.adid);
                        bundle.putString("userID", userID);
                        bundle.putString("tableCategory", wo.category);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        break;
                    case "RealEstate":
                        intent = new Intent(v.getContext(), RealEstateViewDetail.class);
                        bundle.putInt("realestateID", wo.adid);
                        bundle.putString("userID", userID);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        break;
                    case "job":
                        intent = new Intent(v.getContext(), JobDetailActivity.class);
                        bundle.putInt("jobID", wo.adid);
                        bundle.putString("userID", userID);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        break;
                    default:
                        intent = new Intent(v.getContext(), SalesDetailView.class);
                        bundle.putInt("salesID", wo.adid);
                        bundle.putString("userID", userID);
                        bundle.putString("salesCategory", wo.category);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        break;
                }

            }
        });

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(
                        context).create();
                alertDialog.setMessage("Are you sure?");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AsyncDeleteWatchlist().execute(wo);
                        remove(wo);
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
        });

    }

    protected class AsyncDeleteWatchlist extends
            AsyncTask<WatchlistObject, Void, Void> {


        @Override
        protected Void doInBackground(WatchlistObject ...params) {
            // TODO Auto-generated method stub

            RestAPI api = new RestAPI();
            try {
                api.DeleteWatchlist(params[0].adid, params[0].category);
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
        return watchlistObjects.size();
    }
}


