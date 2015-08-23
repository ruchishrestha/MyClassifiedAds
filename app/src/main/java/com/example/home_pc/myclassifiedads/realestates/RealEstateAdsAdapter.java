package com.example.home_pc.myclassifiedads.realestates;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.mainactivity.MainActivity;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Home-PC on 7/31/2015.
 */
public class RealEstateAdsAdapter extends RecyclerView.Adapter<RealEstateAdsAdapter.ViewHolder> {
    String userID;
    Context context;
    private ArrayList<RealEstatesAdObject> realEstatesAdObjects;
    View view;
    Intent intent;

    public RealEstateAdsAdapter(Context context,ArrayList<RealEstatesAdObject> realEstatesAdObjects,String userID) {
        this.context=context;
        this.realEstatesAdObjects=realEstatesAdObjects;
        this.userID=userID;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView realEstateImage;
        protected TextView realEstateTitle,realEstateAddress,realEstateContact,username,realEstatePrice,saleType;
        public ImageView realEstatepopupMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            realEstateImage=(ImageView)itemView.findViewById(R.id.realEstateImage);
            realEstateTitle=(TextView)itemView.findViewById(R.id.realEstateTitle);
            realEstateAddress=(TextView)itemView.findViewById(R.id.realEstateAddress);
            realEstateContact=(TextView)itemView.findViewById(R.id.realEstateContact);
            username=(TextView)itemView.findViewById(R.id.username);
            realEstatePrice=(TextView)itemView.findViewById(R.id.realEstatePrice);
            saleType=(TextView)itemView.findViewById(R.id.saleType);
            realEstatepopupMenu=(ImageView)itemView.findViewById(R.id.realEstatePopupMenu);
        }
    }


    @Override
    public RealEstateAdsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_realestate, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RealEstatesAdObject reo=realEstatesAdObjects.get(position);

        holder.realEstateTitle.setText(reo.title);
        holder.realEstatePrice.setText("NPR."+reo.price);
        holder.saleType.setText(reo.saleType);
        holder.realEstateAddress.setText(reo.aDdress);
        holder.realEstateContact.setText(reo.contactNo);
        holder.username.setText(reo.userName);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), RealEstateViewDetail.class);
                Bundle bundle = new Bundle();
                bundle.putInt("realestateID", reo.realestateID);
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.realEstatepopupMenu.setOnClickListener(new View.OnClickListener() {
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
                                    RealEstatesAdObject co = new RealEstatesAdObject(reo.realestateID, "RealEstate", userID);
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
            AsyncTask<RealEstatesAdObject, Void, Boolean> {

        Boolean flag=false;
        @Override
        protected Boolean doInBackground(RealEstatesAdObject... params) {
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObject = api.PushtoWatchlist(params[0].realestateID, params[0].tableCategory, params[0].userID);
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
            if(result){
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

    @Override
    public int getItemCount() {
        return realEstatesAdObjects.size();
    }


}