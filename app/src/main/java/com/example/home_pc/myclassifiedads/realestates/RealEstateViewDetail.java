package com.example.home_pc.myclassifiedads.realestates;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.comments.AllCommentsActivity;
import com.example.home_pc.myclassifiedads.comments.CommentObject;
import com.example.home_pc.myclassifiedads.mainactivity.MainActivity;

import org.json.JSONObject;

import java.util.ArrayList;

public class RealEstateViewDetail extends ActionBarActivity {
    ArrayList<RealEstatesAdObject> realEstatesAdObjects=null;
    ImageView realestate_picture,comment_cancel,comment_save,read_comment,img1,img2,img3,img4,img5,img6;
    TextView username,ad_description,ad_title,saleType,price,contactNo,aDdress,
            comment,commentText,postedDate,houseNo,propertyType,mobileNo,commenterUsername,myComments,
            ad_postedDate;
    Integer realestateID;
    CardView commentRealestate;
    ProgressDialog progressDialog;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_realestate_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        realestateID=getIntent().getExtras().getInt("realestateID");
        userID=getIntent().getExtras().getString("userID");
        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);
        img3=(ImageView)findViewById(R.id.img3);
        img4=(ImageView)findViewById(R.id.img4);
        img5=(ImageView)findViewById(R.id.img5);
        img6=(ImageView)findViewById(R.id.img6);
        ad_title=(TextView)findViewById(R.id.adTitle);
        ad_description=(TextView)findViewById(R.id.ad_description);
        saleType=(TextView)findViewById(R.id.saleType);
        price=(TextView)findViewById(R.id.price);
        username=(TextView)findViewById(R.id.username);
       ad_postedDate=(TextView)findViewById(R.id.ad_postedDate);
        houseNo=(TextView)findViewById(R.id.houseNo);
        propertyType=(TextView)findViewById(R.id.propertyType);
        contactNo=(TextView)findViewById(R.id.contactNo);
        mobileNo=(TextView)findViewById(R.id.mobileNo);
        aDdress=(TextView)findViewById(R.id.address);
        comment=(TextView)findViewById(R.id.comment);
        postedDate=(TextView)findViewById(R.id.postedDate);
        commenterUsername=(TextView)findViewById(R.id.comenterUsername);
        myComments=(TextView)findViewById(R.id.myComments);
        commentRealestate=(CardView)findViewById(R.id.commentRealestate);
        read_comment=(ImageView)findViewById(R.id.read_comment);

        commentRealestate.setVisibility(View.GONE);

        new AsyncLoadRealestateDetail().execute(realestateID);

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
                allCommentsPopup(realestateID, "RealEstate");

            }
        });
    }

    public void popupalert(){
        final AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setMessage("Please Login or Sign Up");
        alertDialog.setButton2("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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

    protected class AsyncLoadRealestateDetail extends
            AsyncTask<Integer, Void, ArrayList<RealEstatesAdObject>> {

        @Override
        protected ArrayList<RealEstatesAdObject> doInBackground(Integer ...params) {
            // TODO Auto-generated method stub
            realEstatesAdObjects=new ArrayList<>();
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObj = api.GetRealestateDetails(params[0]);
                JSONParser parser = new JSONParser();
                 realEstatesAdObjects= parser.parseRealestateDetails(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadDeptDetails", e.getMessage());
            }

            return realEstatesAdObjects;
        }


        @Override
        protected void onPreExecute(){
            progressDialog=new ProgressDialog(RealEstateViewDetail.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            if(!userID.equals("Guest")){
                loadMyComments(realestateID, userID);
            }

        }

        @Override
        protected void onPostExecute(ArrayList<RealEstatesAdObject> result) {
            // TODO Auto-generated method stub
            //  contact_photo.setImageBitmap(bitmap);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            username.setText(result.get(0).userName);
            ad_title.setText(result.get(0).title);
            saleType.setText(result.get(0).saleType);
            price.setText("NPR." + (result.get(0).price).toString());
            ad_postedDate.setText(result.get(0).ad_insertdate);
            ad_description.setText(result.get(0).description);
            houseNo.setText(result.get(0).houseNo);
            propertyType.setText(result.get(0).propertyType);
            aDdress.setText(result.get(0).aDdress);
            contactNo.setText(result.get(0).contactNo);
            mobileNo.setText(result.get(0).mobileNo);
            new AsyncLoadImages().execute(result.get(0).realestateID);

        }
    }

    public void initiatePopupWindow(){
        final PopupWindow pwindo;
        LayoutInflater inflater = (LayoutInflater) RealEstateViewDetail.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.comment_popup,
                (ViewGroup) findViewById(R.id.popup_comment));
        pwindo = new PopupWindow(layout,dptopx(300),dptopx(220),true);
        pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
        comment_save=(ImageView)layout.findViewById(R.id.comment_save);
        comment_cancel=(ImageView)layout.findViewById(R.id.comment_cancel);
        commentText=(EditText)layout.findViewById(R.id.commentText);

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
                    save_comment(commentText.getText().toString());
                    pwindo.dismiss();
                }

            }
        });
    }

    public void allCommentsPopup(int adid,String tableCategory) {
        Intent intent=new Intent(getApplicationContext(),AllCommentsActivity.class);
        intent.putExtra("adid",adid);
        intent.putExtra("category", tableCategory);
        startActivity(intent);

    }

    public void save_comment(String commentText){
        CommentObject commentObject=new CommentObject("RealEstate",userID,realestateID,commentText);
        new AsyncSaveComment().execute(commentObject);
    }


    protected class AsyncSaveComment extends
            AsyncTask<CommentObject, Void,Void > {

        @Override
        protected Void doInBackground(CommentObject ...params) {
            // TODO Auto-generated method stub

            RestAPI api = new RestAPI();
            try {
                api.PushComments(params[0].tableCategory,params[0].userID,params[0].adid,params[0].commentText);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncSaveComment", ""+e);
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
           loadMyComments(realestateID,userID);
        }
    }

    public void loadMyComments(int adid,String userID){
        CommentObject commentObject=new CommentObject(adid,userID,"RealEstate");
        new AsyncLoadMyComments().execute(commentObject);
    }

    protected class AsyncLoadMyComments extends
            AsyncTask<CommentObject, Void, ArrayList<CommentObject>> {

        @Override
        protected ArrayList<CommentObject> doInBackground(CommentObject... params) {
            // TODO Auto-generated method stub
            ArrayList<CommentObject> myCommentObject = new ArrayList<CommentObject>();
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObj = api.GetMyComment(params[0].adid, params[0].userID,params[0].tableCategory);
                JSONParser parser = new JSONParser();
                myCommentObject = parser.parseComment(jsonObj);

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
                commentRealestate.setVisibility(View.GONE);
            }
            else{

                commentRealestate.setVisibility(View.VISIBLE);
                postedDate.setText("Posted on: "+result.get(0).commentDate);
                commenterUsername.setText("Posted by: "+result.get(0).username);
                myComments.setText(result.get(0).commentText);
            }
        }
    }

    protected class AsyncLoadImages extends
            AsyncTask<Integer, Void, ArrayList<RealEstatesAdObject>> {
ArrayList<RealEstatesAdObject> realestate_pictures=new ArrayList<>();
        @Override
        protected ArrayList<RealEstatesAdObject> doInBackground(Integer... params) {
            RestAPI api=new RestAPI();
            try{
                /*JSONObject jsonObj = api.GetImages(params[0].realestateID);
                JSONParser parser = new JSONParser();
                realestate_pictures = parser.parseImages(jsonObj);*/
            }
            catch (Exception e){
                Log.d("AsyncLoadImaged=>",e.getMessage());

            }
            return realestate_pictures;
        }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_real_estate_view_detail, menu);
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
        if(id==android.R.id.home){
            onBackPressed();
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
