package com.example.home_pc.myclassifiedads.comments;

import android.annotation.SuppressLint;
import android.media.Rating;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ruchi on 2015-08-24.
 */
public class SalesCommentAdsAdapter extends RecyclerView.Adapter<SalesCommentAdsAdapter.ViewHolder> {
    ArrayList<CommentObject> commentObjects;
    int adid;

    public SalesCommentAdsAdapter(ArrayList<CommentObject> commentObjects,int adid){
        this.commentObjects=commentObjects;
        this.adid=adid;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView commentText,postedDate,username;
        protected RatingBar MyRatingMain;

        public ViewHolder(View v) {
            super(v);
            postedDate=(TextView)v.findViewById(R.id.postedDate);
            username=(TextView)v.findViewById(R.id.comenterUsername);
            commentText=(TextView)v.findViewById(R.id.myComments);
            MyRatingMain=(RatingBar)v.findViewById(R.id.MyRatingMain);

        }
    }

    @Override
    public SalesCommentAdsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_sales_comment, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CommentObject co=commentObjects.get(position);

        holder.postedDate.setText("Posted on "+co.commentDate);
        holder.username.setText("By " + co.username);
        holder.commentText.setText(co.commentText);

        new AsyncLoadAllRatings(position,holder).execute(co.username);
    }

    protected class AsyncLoadAllRatings extends
            AsyncTask<String, Void, Double> {
        int pos;
        ViewHolder holder;
        Double rating;

        public AsyncLoadAllRatings(int pos,ViewHolder holder) {
            this.pos = pos;
            this.holder = holder;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected Double doInBackground(String... params) {
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObject=api.GetCommentRating(adid,params[0]);
                JSONParser parser=new JSONParser();
                rating= parser.parseMyRating(jsonObject);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadSalesPic", e.getMessage());
            }
            return rating;
        }

        @Override
        protected void onPostExecute(Double result){
           holder.MyRatingMain.setRating(rating.floatValue());
        }

    }

    @Override
    public int getItemCount() {
        return commentObjects.size();
    }
}
