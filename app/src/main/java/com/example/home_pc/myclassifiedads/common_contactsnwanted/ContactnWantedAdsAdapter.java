package com.example.home_pc.myclassifiedads.common_contactsnwanted;

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
import com.example.home_pc.myclassifiedads.myads.MyContactsWantedEditActivity;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Home-PC on 7/25/2015.
 */
public class ContactnWantedAdsAdapter extends RecyclerView.Adapter<ContactnWantedAdsAdapter.ViewHolder> {
    Bitmap bitmap;
    Context context;
    private ArrayList<ContactsnWantedAdObject> contactsAdObjects;
    View view;
    Intent intent;
    String tableCategory,userID;
    public Bitmap contactswanted_image;
    int flag;

    public ContactnWantedAdsAdapter(Context context, ArrayList<ContactsnWantedAdObject> contactsAdObjects, String tableCategory, String userID,int flag) {
        this.context=context;
        this.contactsAdObjects=contactsAdObjects;
        this.tableCategory=tableCategory;
        this.userID=userID;
        this.flag=flag;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected ImageView adImage;
        protected TextView adTitle,aDdress,adContact,username,mobileNo;
        public ImageView popupMenu;

        public ViewHolder(View v) {
            super(v);
            adImage=(ImageView)v.findViewById(R.id.adImage);
            adTitle=(TextView)v.findViewById(R.id.adTitle);
            adContact=(TextView)v.findViewById(R.id.adContact);
            aDdress=(TextView)v.findViewById(R.id.aDdress);
            username=(TextView)v.findViewById(R.id.userId);
            mobileNo=(TextView)v.findViewById(R.id.mobileNo);
            popupMenu=(ImageView)v.findViewById(R.id.popupMenu);
        }
    }

    @Override
    public ContactnWantedAdsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public void remove(ContactsnWantedAdObject cao) {
        int position=contactsAdObjects.indexOf(cao);
        contactsAdObjects.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ContactsnWantedAdObject cao=contactsAdObjects.get(position);

       // holder.adImage.setImageBitmap(bitmap);
        holder.adTitle.setText(cao.title);
        holder.adContact.setText(cao.contactNo);
        holder.aDdress.setText(cao.aDdress);
        holder.username.setText(cao.userName);
        if(!cao.adImageURL.equals("-")){
            String sub1 = cao.adImageURL.substring(0,61);
            String sub2 = "temp_"+cao.adImageURL.substring(61);
            System.out.println(sub1+sub2);
            new AsyncLoadImage(position,holder,cao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,sub1+sub2);
        }

       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               intent = new Intent(v.getContext(), ContactsnWantedViewDetail.class);
               Bundle bundle = new Bundle();
               bundle.putInt("adid", cao.adid);
               bundle.putString("userID", userID);
               bundle.putString("tableCategory", tableCategory);
               intent.putExtras(bundle);
               context.startActivity(intent);
           }
       });

        switch (flag){
            case 0:
                holder.popupMenu.setOnClickListener(new View.OnClickListener() {
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
                                            ContactsnWantedAdObject co = new ContactsnWantedAdObject(cao.adid, tableCategory, userID);
                                            new AsyncSavetoWatchlist().execute(co);
                                        }
                                }
                                return true;
                            }
                        });
                    }
                });
                break;
            case 1:
                holder.popupMenu.setOnClickListener(new View.OnClickListener() {
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
                                        int ad_id=cao.adid;
                                        navigatetoEditActivity(ad_id);
                                        break;
                                    case R.id.item_delete:
                                        final AlertDialog alertDialog = new AlertDialog.Builder(
                                                context).create();
                                        alertDialog.setMessage("Are you sure?");
                                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                deleteFromDb(cao.adid);
                                                remove(cao);
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
        intent =new Intent(context, MyContactsWantedEditActivity.class);
        intent.putExtra("userID", userID);
        intent.putExtra("tableCategory", tableCategory);
        intent.putExtra("adid", ad_id);
        context.startActivity(intent);

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
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    protected class AsyncSavetoWatchlist extends
            AsyncTask<ContactsnWantedAdObject, Void, Boolean> {

        Boolean flag=false;
        @Override
        protected Boolean doInBackground(ContactsnWantedAdObject... params) {
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObject = api.PushtoWatchlist(params[0].adid, params[0].tableCategory, params[0].userID);
                JSONParser parser = new JSONParser();
              flag= parser.parseReturnedValue(jsonObject);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncSavetoWatchlist", ""+e);
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
        ContactsnWantedAdObject contactsnWantedAdObject;

        public AsyncLoadImage(int pos,ViewHolder holder,ContactsnWantedAdObject contactsnWantedAdObject) {
            this.pos = pos;
            this.holder = holder;
            this.contactsnWantedAdObject = contactsnWantedAdObject;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                contactswanted_image = ImageLoaderAPI.AzureImageDownloader(params[0]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadImage", e.getMessage());
            }
            return contactswanted_image;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            contactswanted_image=Bitmap.createScaledBitmap(result, dptopx(110), dptopx(110), true);
            holder.adImage.setImageBitmap(contactswanted_image);
        }

    }



    public void deleteFromDb(int adid) {
        Toast.makeText(context, "" + adid, Toast.LENGTH_LONG).show();
        new AsyncDeleteContactsWantedAd().execute(adid);
    }

    protected class AsyncDeleteContactsWantedAd extends
            AsyncTask<Integer, Void, Void> {


        @Override
        protected Void doInBackground(Integer ...params) {
            // TODO Auto-generated method stub

            RestAPI api = new RestAPI();
            try {
                api.DeleteContactsWantedAd(params[0],tableCategory);
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
        return contactsAdObjects.size();
    }
    public int dptopx(float dp){
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return ((int) (dp * scale + 0.5f));
    }

}