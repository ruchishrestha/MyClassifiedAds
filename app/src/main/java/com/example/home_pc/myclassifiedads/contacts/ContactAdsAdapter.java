package com.example.home_pc.myclassifiedads.contacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Home-PC on 7/25/2015.
 */
public class ContactAdsAdapter extends RecyclerView.Adapter<ContactAdsAdapter.ViewHolder> {

    Context context;
    private List<ContactsAdObject> contactsAdObjects;

    public ContactAdsAdapter(Context context,List<ContactsAdObject> contactsAdObjects) {
        this.context=context;
        this.contactsAdObjects=contactsAdObjects;
    }

    @Override
    public ContactAdsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return contactsAdObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}