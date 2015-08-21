package com.example.home_pc.myclassifiedads.contacts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;

import java.util.ArrayList;

/**
 * Created by Ruchi on 2015-08-16.
 */
public class CommentAdsAdapter extends RecyclerView.Adapter<CommentAdsAdapter.ViewHolder> {
    ArrayList<CommentObject> commentObjects;

    public CommentAdsAdapter(ArrayList<CommentObject> commentObjects){
       this.commentObjects=commentObjects;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView commentText,postedDate,username;

        public ViewHolder(View v) {
            super(v);
            postedDate=(TextView)v.findViewById(R.id.postedDate);
            username=(TextView)v.findViewById(R.id.username);
            commentText=(TextView)v.findViewById(R.id.commentText);
        }
    }

    @Override
    public CommentAdsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_comments_content, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CommentObject co=commentObjects.get(position);

        // holder.adImage.setImageBitmap(bitmap);
      holder.postedDate.setText("Posted on "+co.commentDate);
        holder.username.setText("By "+co.username);
        holder.commentText.setText(co.commentText);
    }

    @Override
    public int getItemCount() {
        return commentObjects.size();
    }
}
