package com.example.home_pc.myclassifiedads.contacts;

import android.content.Context;
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
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;

import org.json.JSONObject;
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
    String tableCategory;

    public ContactAdsAdapter(Context context,ArrayList<ContactsAdObject> contactsAdObjects,String tableCategory) {
        this.context=context;
        this.contactsAdObjects=contactsAdObjects;
        this.tableCategory=tableCategory;
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

       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               intent = new Intent(v.getContext(), ContactViewDetail.class);
               Bundle bundle = new Bundle();
               bundle.putInt("adid", cao.adid);
               bundle.putString("viewerUsername", cao.username);
               bundle.putString("tableCategory", tableCategory);
               intent.putExtras(bundle);
               context.startActivity(intent);
           }
       });

        holder.popupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openpopupmenu(v,cao);
                final PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.overflow_popup_menu);
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.item_watchlist:
                                ContactsAdObject co=new ContactsAdObject(cao.adid,cao.tableCategory,cao.viewerUsername);
                                Toast.makeText(context,""+cao.tableCategory,Toast.LENGTH_LONG).show();
                               // new AsyncSavetoWatchlist().execute(co);
                        }

                        return true;
                    }
                });
            }
        });

    }


    protected class AsyncSavetoWatchlist extends
            AsyncTask<ContactsAdObject, Void, Integer> {

        Integer a=0;
        @Override
        protected Integer doInBackground(ContactsAdObject... params) {
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObject = api.PushtoWatchlist(params[0].adid, params[0].tableCategory, params[0].viewerUsername);
                JSONParser parser = new JSONParser();
              a= parser.parseReturnedValue(jsonObject);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncSavetoWatchlist", e.getMessage().toString());
            }
            return a;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if(result==1){
                Toast.makeText(context,"Already added",Toast.LENGTH_LONG).show();
        }
            else{
                Toast.makeText(context,"Added to watchlist",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public int getItemCount() {
        return contactsAdObjects.size();
    }



}