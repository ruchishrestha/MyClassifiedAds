package com.example.home_pc.myclassifiedads.sales;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.comments.SalesAllComments;
import com.example.home_pc.myclassifiedads.classified_api.ImageLoaderAPI;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.comments.CommentObject;
import com.example.home_pc.myclassifiedads.mainactivity.MainActivity;

import org.json.JSONObject;

import java.util.ArrayList;

public class SalesDetailView extends ActionBarActivity {
    TextView title,price,status,condition,username,ad_postedDate,ratenumber,description,brand,model,timeuse,contact ,
    comment,commentText,postedDate,commenterUsername,myComments;
    ImageView comment_cancel,comment_save,read_comment;
    Integer salesID;
    CardView commentSales;
    ProgressDialog progressDialog;
    String userID,salesCategory,userName;
    ArrayList<ImageView> img;
    HorizontalScrollView horizontalScrollView;
    ArrayList<SalesAdsObject> salesAdsObjects=null;
    RatingBar averageRating,MyRating,MyRatingMain;
    Double myrating=0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_sales_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        salesID=getIntent().getExtras().getInt("salesID");
        userID=getIntent().getExtras().getString("userID");
        salesCategory=getIntent().getExtras().getString("salesCategory");
        userName=getIntent().getExtras().getString("userName");
        Toast.makeText(getApplicationContext(),salesCategory,Toast.LENGTH_LONG).show();
        img=new ArrayList<>();
        img.add((ImageView) findViewById(R.id.img1));
        img.add((ImageView) findViewById(R.id.img2));
        img.add((ImageView) findViewById(R.id.img3));
        img.add((ImageView)findViewById(R.id.img4));
        img.add((ImageView) findViewById(R.id.img5));
        img.add((ImageView) findViewById(R.id.img6));
        title=(TextView)findViewById(R.id.title);
        price=(TextView)findViewById(R.id.price);
        status=(TextView)findViewById(R.id.status);
        condition=(TextView)findViewById(R.id.condition);
        username=(TextView)findViewById(R.id.username);
        ad_postedDate=(TextView)findViewById(R.id.ad_postedDate);
        condition=(TextView)findViewById(R.id.condition);
        ratenumber=(TextView)findViewById(R.id.ratenumber);
        description=(TextView)findViewById(R.id.description);
        brand=(TextView)findViewById(R.id.brand);
        model=(TextView)findViewById(R.id.model);
        timeuse=(TextView)findViewById(R.id.timeuse);
        contact=(TextView)findViewById(R.id.contact);
        averageRating=(RatingBar)findViewById(R.id.averageRating);
        comment=(TextView)findViewById(R.id.comment);
        horizontalScrollView=(HorizontalScrollView)findViewById(R.id.horizontalScroll);
        postedDate=(TextView)findViewById(R.id.postedDate);
        commenterUsername=(TextView)findViewById(R.id.comenterUsername);
        myComments=(TextView)findViewById(R.id.myComments);
        commentSales=(CardView)findViewById(R.id.commentSales);
        read_comment=(ImageView)findViewById(R.id.read_comment);
        MyRatingMain=(RatingBar)findViewById(R.id.MyRatingMain);

        commentSales.setVisibility(View.GONE);

        new AsyncLoadSalesDetail().execute(salesID);

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userID.equals("Guest")) {
                    popupalert();
                } else {
                    initiatePopupWindow();
                }
            }
        });

        read_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allCommentsPopup(salesID,salesCategory);

            }
        });
    }

    public void popupalert(){
        final AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setMessage("Please Login or Sign Up");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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

    protected class AsyncLoadSalesDetail extends
            AsyncTask<Integer, Void, ArrayList<SalesAdsObject>> {

        @SuppressLint("LongLogTag")
        @Override
        protected ArrayList<SalesAdsObject> doInBackground(Integer... params) {
            // TODO Auto-generated method stub
            salesAdsObjects = new ArrayList<>();
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObj = api.GetSalesDetail(params[0]);
                JSONParser parser = new JSONParser();
                salesAdsObjects = parser.parseSalesDetails(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadRealEstateDetails", e.getMessage());
            }

            return salesAdsObjects;
        }


        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(SalesDetailView.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            if (!userID.equals("Guest")) {
                loadMyComments();
            }

        }

        @Override
        protected void onPostExecute(ArrayList<SalesAdsObject> result) {
            // TODO Auto-generated method stub
            //  contact_photo.setImageBitmap(bitmap);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            username.setText(Html.fromHtml("<u>" + result.get(0).userName + "</u>"));
            title.setText(result.get(0).title);
            price.setText(result.get(0).price);
            brand.setText(result.get(0).brand);
            model.setText(result.get(0).modelNo);
            contact.setText(result.get(0).contactNo);
            condition.setText(result.get(0).condition);
            ad_postedDate.setText(result.get(0).ad_postedDate);
            description.setText(result.get(0).description);
            timeuse.setText(result.get(0).usedTime);
            ratenumber.setText(result.get(0).rating1.toString());
            averageRating.setRating((result.get(0).rating1).floatValue());
            new AsyncLoadImages().execute(salesID);
        }
    }

    public void initiatePopupWindow(){
        final PopupWindow pwindo;
        LayoutInflater inflater = (LayoutInflater)SalesDetailView.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.sales_comment_popup,
                (ViewGroup) findViewById(R.id.popup_comment));
        pwindo = new PopupWindow(layout,dptopx(300),dptopx(220),true);
        pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
        comment_save=(ImageView)layout.findViewById(R.id.comment_save);
        comment_cancel=(ImageView)layout.findViewById(R.id.comment_cancel);
        commentText=(EditText)layout.findViewById(R.id.commentText);
        MyRating=(RatingBar)layout.findViewById(R.id.MyRating);
        if (userName.equals(userID)) {
            MyRating.setEnabled(false);
        }
      MyRating.setRating(myrating.floatValue());
        comment_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwindo.dismiss();
            }
        });


        comment_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((commentText.getText().toString()).equals("")) {
                    Toast.makeText(layout.getContext(), "Please comment first", Toast.LENGTH_LONG).show();
                } else {
                    myrating= Double.parseDouble(Float.toString(MyRating.getRating()));
                    Toast.makeText(getApplicationContext(),""+myrating,Toast.LENGTH_LONG).show();
                    save_comment(commentText.getText().toString());

                            pwindo.dismiss();
                }

            }
        });
    }

    public void allCommentsPopup(int adid,String tableCategory) {
        Intent intent=new Intent(getApplicationContext(), SalesAllComments.class);
        intent.putExtra("adid",adid);
        intent.putExtra("category", tableCategory);
        startActivity(intent);

    }

    public void save_comment(String commentText){
        new AsyncSaveComment().execute(commentText);
    }


    protected class AsyncSaveComment extends
            AsyncTask<String, Void,Void > {

        @Override
        protected Void doInBackground(String  ...params) {
            // TODO Auto-generated method stub

            RestAPI api = new RestAPI();
            try {
                api.PushComments(salesCategory, userID, salesID, params[0]);
                if(myrating!=0.0){
                    api.PushRateValue(salesID, userID, salesCategory, myrating);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncSaveComment", ""+e);
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            loadMyComments();
        }
    }

    public void loadMyComments(){

        new AsyncLoadMyComments().execute();
    }

    protected class AsyncLoadMyComments extends
            AsyncTask<Void, Void, ArrayList<CommentObject>> {

        JSONObject jsonObj;
        JSONParser parser;
        @Override
        protected ArrayList<CommentObject> doInBackground(Void... params) {
            // TODO Auto-generated method stub
            ArrayList<CommentObject> myCommentObject = new ArrayList<CommentObject>();
            RestAPI api = new RestAPI();
            try {
                myrating=0.0;
              jsonObj = api.GetMyComment(salesID,userID,salesCategory);
                parser = new JSONParser();
                myCommentObject = parser.parseComment(jsonObj);
               jsonObj=api.GetMyRating(salesID, userID, salesCategory);
                parser=new JSONParser();
                myrating=parser.parseMyRating(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadMyComment", ""+e);
            }

            return myCommentObject;
        }

        @Override
        protected void onPostExecute(ArrayList<CommentObject> result) {
            // TODO Auto-generated method stub
            if(result==null){
                //Toast.makeText(getApplicationContext(),""+result.get(0).userID,Toast.LENGTH_LONG).show();
                commentSales.setVisibility(View.GONE);
            }
            else{

                commentSales.setVisibility(View.VISIBLE);
                postedDate.setText("Posted on: " + result.get(0).commentDate);
                commenterUsername.setText("Posted by: "+result.get(0).username);
                myComments.setText(result.get(0).commentText);
                MyRatingMain.setRating(myrating.floatValue());

              //  Toast.makeText(getApplicationContext(),""+myrating,Toast.LENGTH_LONG).show();
            }
        }
    }

    protected class AsyncLoadImages extends
            AsyncTask<Integer, Void, ArrayList<Bitmap>> {
        ArrayList<Bitmap> sales_pics=null;
        ArrayList<String> sales_pictures=null;

        @Override
        protected ArrayList<Bitmap> doInBackground(Integer... params) {
            RestAPI api=new RestAPI();
            sales_pictures=new ArrayList<>();
            sales_pics=new ArrayList<>();
            try{
                JSONObject jsonObj = api.GetSalesImages(params[0]);
                JSONParser parser = new JSONParser();
                sales_pictures = parser.parseReturnedURLs(jsonObj);
                if(sales_pictures==null){
                    sales_pics=null;
                }else{
                    sales_pics= ImageLoaderAPI.AzureImageDownloader(sales_pictures);
                }


            }
            catch (Exception e){
                Log.d("AsyncLoadAllImages=>",e.getMessage());

            }
            return sales_pics;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> result){
            if(result!=null){
                for(int i=0;i<result.size();i++){
                    img.get(i).setVisibility(View.VISIBLE);
                    img.get(i).setImageBitmap(Bitmap.createScaledBitmap(result.get(i),dptopx(150),dptopx(150),true));
                }
            }else{
                horizontalScrollView.setVisibility(View.GONE);
            }

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sales_detail_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public int dptopx(float dp){
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return ((int) (dp * scale + 0.5f));
    }
}
