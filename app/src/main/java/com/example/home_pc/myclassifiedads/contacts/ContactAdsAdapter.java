package com.example.home_pc.myclassifiedads.contacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home-PC on 7/25/2015.
 */
public class ContactAdsAdapter extends RecyclerView.Adapter<ContactAdsAdapter.ViewHolder> {
    Bitmap bitmap;
    Context context;
    private ArrayList<ContactsAdObject> contactsAdObjects;
    View view;
    Intent intent;

    public ContactAdsAdapter(Context context,ArrayList<ContactsAdObject> contactsAdObjects) {
        this.context=context;
        this.contactsAdObjects=contactsAdObjects;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected ImageView adImage;
        protected TextView adTitle,aDdress,adContact,userId;
        public ImageView popupMenu;

        public ViewHolder(View v) {
            super(v);
            adImage=(ImageView)v.findViewById(R.id.adImage);
            adTitle=(TextView)v.findViewById(R.id.adTitle);
            adContact=(TextView)v.findViewById(R.id.adContact);
            aDdress=(TextView)v.findViewById(R.id.aDdress);
            userId=(TextView)v.findViewById(R.id.userId);
            popupMenu=(ImageView)v.findViewById(R.id.popupMenu);
        }
    }

    @Override
    public ContactAdsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ContactsAdObject cao=contactsAdObjects.get(position);

       // holder.adImage.setImageBitmap(bitmap);
        holder.adTitle.setText(cao.title);
        holder.adContact.setText(cao.contactNo);
        holder.aDdress.setText(cao.address);
        holder.userId.setText(cao.username);



     /*   view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), ContactViewDetail.class);
                Bundle bundle=new Bundle();
                bundle.putInt("contactID",contactID);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return contactsAdObjects.size();
    }



}