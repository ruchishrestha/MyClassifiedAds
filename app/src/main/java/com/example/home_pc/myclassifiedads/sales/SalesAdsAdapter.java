package com.example.home_pc.myclassifiedads.sales;

import android.annotation.SuppressLint;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.ImageLoaderAPI;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.mainactivity.MainActivity;
import com.example.home_pc.myclassifiedads.myads.MySalesEditActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home-PC on 7/31/2015.
 */
public class SalesAdsAdapter extends RecyclerView.Adapter<SalesAdsAdapter.ViewHolder> {

    String userID,salesCategory;
    Context context;
    private ArrayList<SalesAdsObject> salesAdsObject;
    View view;
    Intent intent;
    int flag;
    Double averageRate;

    public SalesAdsAdapter(Context context,ArrayList<SalesAdsObject> salesAdsObject,String userID,String salesCategory,int flag) {
        this.context=context;
        this.salesAdsObject=salesAdsObject;
        this.salesCategory=salesCategory;
        this.userID=userID;
        this.flag=flag;
    }

    public SalesAdsAdapter(Context context,ArrayList<SalesAdsObject> salesAdsObject,String userID,int flag) {
        this.context=context;
        this.salesAdsObject=salesAdsObject;
        this.userID=userID;
        this.flag=flag;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView salesTitle,salesPrice,salesStatus,salesCondition,salesBrand,salesModel,salesUserID;
        protected ImageView salesImage,salesPopupMenu;
        protected RatingBar averageRating;

        public ViewHolder(View itemView) {
            super(itemView);
            salesTitle=(TextView)itemView.findViewById(R.id.salesTitle);
            salesPrice=(TextView)itemView.findViewById(R.id.salesPrice);
            salesStatus=(TextView)itemView.findViewById(R.id.salesStatus);
            salesCondition=(TextView)itemView.findViewById(R.id.salesCondition);
            salesBrand=(TextView)itemView.findViewById(R.id.salesBrand);
            salesModel=(TextView)itemView.findViewById(R.id.salesModel);
            salesUserID=(TextView)itemView.findViewById(R.id.salesUserId);
            salesImage=(ImageView)itemView.findViewById(R.id.salesImage);
            salesPopupMenu=(ImageView)itemView.findViewById(R.id.salesPopupMenu);
            averageRating=(RatingBar)itemView.findViewById(R.id.averageRating);
        }
    }


    @Override
    public SalesAdsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sales, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public void remove(SalesAdsObject sao) {
        int position=salesAdsObject.indexOf(sao);
        salesAdsObject.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SalesAdsObject sao=salesAdsObject.get(position);

        holder.salesTitle.setText(sao.title);
        holder.salesUserID.setText(sao.userName);
        holder.salesModel.setText(sao.modelNo);
        holder.salesBrand.setText(sao.brand);
        holder.salesCondition.setText(sao.condition);
        holder.salesStatus.setText(sao.status);
        holder.salesPrice.setText("NPR."+sao.price);
        holder.averageRating.setRating(sao.rating1.floatValue());
        new AsyncLoadSalesPic(position,holder).execute(sao.salesID);

        switch (flag){
            case 0:
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(v.getContext(), SalesDetailView.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("salesID", sao.salesID);
                        bundle.putString("userID", userID);
                        bundle.putString("salesCategory",salesCategory);
                        bundle.putString("userName", sao.userName);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
                break;
            case 1:
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(v.getContext(), SalesDetailView.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("salesID", sao.salesID);
                        bundle.putString("userID", userID);
                        bundle.putString("salesCategory",sao.category);
                        bundle.putString("userName", sao.userName);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
                break;
            default:
                break;
        }

        switch (flag){
            case 0:
                holder.salesPopupMenu.setOnClickListener(new View.OnClickListener() {
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

                                            new AsyncSavetoWatchlist().execute(sao.salesID);
                                        }
                                }
                                return true;
                            }
                        });
                    }
                });
                break;
            case 1:
                holder.salesPopupMenu.setOnClickListener(new View.OnClickListener() {
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
                                        int ad_id=sao.salesID;
                                        navigatetoEditActivity(ad_id);
                                        break;
                                    case R.id.item_delete:
                                        final AlertDialog alertDialog = new AlertDialog.Builder(
                                                context).create();
                                        alertDialog.setMessage("Are you sure?");
                                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                deleteFromDb(sao.salesID,sao.category);
                                                remove(sao);
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
        intent =new Intent(context, MySalesEditActivity.class);
        intent.putExtra("userID", userID);
        intent.putExtra("adid", ad_id);
        context.startActivity(intent);

    }

    public void navigatetohome(){
        final AlertDialog alertDialog = new AlertDialog.Builder(
                context).create();
        alertDialog.setMessage("Please create your account first or Login");
        alertDialog.setIcon(R.drawable.backward);
        alertDialog.setTitle("The Classified Ads App");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
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
                JSONObject jsonObject = api.PushtoWatchlist(params[0],salesCategory,userID);
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
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    protected class AsyncLoadSalesPic extends
            AsyncTask<Integer, Void, Bitmap> {
        int pos;
        ViewHolder holder;

        Bitmap salesPic;
        String picURL;

        public AsyncLoadSalesPic(int pos,ViewHolder holder) {
            this.pos = pos;
            this.holder = holder;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected Bitmap doInBackground(Integer... params) {
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObject=api.GetSalesPictureURL(params[0]);
                JSONParser parser=new JSONParser();
                picURL= parser.parseReturnedURL(jsonObject);
                String sub1 = picURL.substring(0, 61);
                String sub2 = "temp_"+picURL.substring(61);
                if(picURL!=null){
                    salesPic= ImageLoaderAPI.AzureImageDownloader(sub1+sub2);
                }
                else{
                    salesPic=null;
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadSalesPic", e.getMessage());
            }
            return salesPic;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            if(result!=null){
                //salesPic=Bitmap.createScaledBitmap(result, dptopx(100), dptopx(100), true);
                holder.salesImage.setImageBitmap(result);
            }

        }

    }


    public void deleteFromDb(int adid,String salesCategory) {
        Toast.makeText(context, "" + adid, Toast.LENGTH_LONG).show();
        new AsyncDeleteSalesAd(adid,salesCategory).execute();
    }

    protected class AsyncDeleteSalesAd extends
            AsyncTask<Integer, Void, Void> {
        int adid;
        String salesCategory;

        public AsyncDeleteSalesAd(int adid,String salesCategory){
            this.adid=adid;
            this.salesCategory=salesCategory;
        }

        @Override
        protected Void doInBackground(Integer ...params) {
            // TODO Auto-generated method stub

            RestAPI api = new RestAPI();
            try {
                api.DeleteSalesAd(adid,salesCategory);
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
        return salesAdsObject.size();
    }

    public int dptopx(float dp){
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return ((int) (dp * scale + 0.5f));
    }

}