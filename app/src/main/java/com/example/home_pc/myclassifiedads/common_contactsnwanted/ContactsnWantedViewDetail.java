package com.example.home_pc.myclassifiedads.common_contactsnwanted;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Html;
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
import com.example.home_pc.myclassifiedads.classified_api.ImageLoaderAPI;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.comments.AllCommentsActivity;
import com.example.home_pc.myclassifiedads.comments.CommentObject;
import com.example.home_pc.myclassifiedads.mainactivity.MainActivity;
import com.example.home_pc.myclassifiedads.mainactivity.ViewOnMap;
import com.example.home_pc.myclassifiedads.userdetailview.ViewCompanyDetail;
import com.example.home_pc.myclassifiedads.userdetailview.ViewIndividualDetail;
import com.example.home_pc.myclassifiedads.userdetailview.ViewShopDetail;

import org.json.JSONObject;

import java.util.ArrayList;

public class ContactsnWantedViewDetail extends ActionBarActivity {
    public ContactsnWantedAdObject contactsAdObject=null;
    ImageView contact_photo,comment_cancel,comment_save,read_comment;
    TextView category,username,ad_description,ad_title,contactNo,address,email,comment,commentText,postedDate,commenterUsername,myComments,
    ad_postedDate,mobileNo,viewOnMap;
    Bundle args;
    Integer adid;
    CardView commentContacts;
    ProgressDialog progressDialog;
    String userID,tableCategory;
    Bitmap contactswanted_image;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contactnwanted_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adid=getIntent().getExtras().getInt("adid");
        userID=getIntent().getExtras().getString("userID");
        tableCategory=getIntent().getExtras().getString("tableCategory");
        contact_photo=(ImageView)findViewById(R.id.contact_photo);
        category=(TextView)findViewById(R.id.category);
        username=(TextView)findViewById(R.id.username);
        contactNo=(TextView)findViewById(R.id.contactNo);
        mobileNo=(TextView)findViewById(R.id.mobileNo);
        ad_description=(TextView)findViewById(R.id.ad_description);
        ad_title=(TextView)findViewById(R.id.ad_title);
        address=(TextView)findViewById(R.id.address);
        email=(TextView)findViewById(R.id.email);
        comment=(TextView)findViewById(R.id.comment);
        postedDate=(TextView)findViewById(R.id.postedDate);
        ad_postedDate=(TextView)findViewById(R.id.ad_postedDate);
        commenterUsername=(TextView)findViewById(R.id.comenterUsername);
        myComments=(TextView)findViewById(R.id.myComments);
        commentContacts=(CardView)findViewById(R.id.commentContacts);
        read_comment=(ImageView)findViewById(R.id.read_comment);
        viewOnMap=(TextView)findViewById(R.id.viewOnMap);

        commentContacts.setVisibility(View.GONE);

        new AsyncLoadContactDetail().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new ContactsnWantedAdObject(adid, tableCategory));

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
                allCommentsPopup(adid, tableCategory);

            }
        });
        viewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ViewOnMap.class);
                intent.putExtra("longitute", contactsAdObject.longitute);
                intent.putExtra("addres", contactsAdObject.aDdress);
                intent.putExtra("latitute", contactsAdObject.latitude);
                startActivity(intent);
            }
        });
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),""+username.getText().toString(),Toast.LENGTH_LONG).show();
                new AsyncLoadUserCategory().execute(username.getText().toString());
            }
        });
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        contactNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + contactNo.getText().toString()));
                startActivity(callIntent);
            }
        });

        mobileNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent("android.intent.action.VIEW");
                Uri data = Uri.parse("sms:"+mobileNo.getText().toString());
                intent.setData(data);
                startActivity(intent);
            }
        });
    }

    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
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

    protected class AsyncLoadContactDetail extends
            AsyncTask<ContactsnWantedAdObject, Void, ContactsnWantedAdObject> {

        @Override
        protected ContactsnWantedAdObject doInBackground(ContactsnWantedAdObject...params) {
            // TODO Auto-generated method stub

            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObj = api.GetContactDetails(params[0].adid,params[0].tableCategory);
                JSONParser parser = new JSONParser();
                contactsAdObject = parser.parseContactDetails(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadContactDetails", e.getMessage());
            }

            return contactsAdObject;
        }

        @Override
        protected void onPreExecute(){
            progressDialog=new ProgressDialog(ContactsnWantedViewDetail.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            if(!userID.equals("Guest")){
                loadMyComments();
            }

        }



        @Override
        protected void onPostExecute(ContactsnWantedAdObject result) {
            // TODO Auto-generated method stub
          //  contact_photo.setImageBitmap(bitmap);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            username.setText(Html.fromHtml("<u>"+result.userName+"</u>"));
            ad_title.setText(result.title);
            ad_description.setText(result.description);
            category.setText(result.category);
            address.setText(result.aDdress);
            contactNo.setText(Html.fromHtml("<u>"+result.contactNo+"</u>"));
            email.setText(result.emailId);
            ad_postedDate.setText(result.ad_insertdate);
            mobileNo.setText(Html.fromHtml("<u>"+result.mobileNo+"</u>"));
            if(!result.adImageURL.equals("-")){
                new AsyncLoadImage().execute(result.getAdImage());
            }



        }
    }

    public void loadMyComments(){
        new AsyncLoadMyComments().execute();
    }



    protected class AsyncLoadMyComments extends
            AsyncTask<Void, Void, ArrayList<CommentObject>> {

        @Override
        protected ArrayList<CommentObject> doInBackground(Void... params) {
            // TODO Auto-generated method stub
            ArrayList<CommentObject> myCommentObject = new ArrayList<CommentObject>();
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObj = api.GetMyComment(adid,userID,tableCategory);
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
                commentContacts.setVisibility(View.GONE);
            }
            else{

                commentContacts.setVisibility(View.VISIBLE);
                postedDate.setText("Posted on: "+result.get(0).commentDate);
                commenterUsername.setText("Posted by: "+result.get(0).username);
                myComments.setText(result.get(0).commentText);
            }
        }
    }

    public void initiatePopupWindow(){
        final PopupWindow pwindo;
        LayoutInflater inflater = (LayoutInflater) ContactsnWantedViewDetail.this
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
                if((commentText.getText().toString()).equals("")){
                    Toast.makeText(layout.getContext(),"Please comment first",Toast.LENGTH_LONG).show();
                } else{
                   save_comment(commentText.getText().toString());
                    pwindo.dismiss();
                }

            }
        });
    }

    public void allCommentsPopup(int adid,String tableCategory) {
       Intent intent=new Intent(getApplicationContext(),AllCommentsActivity.class);
        intent.putExtra("adid",adid);
        intent.putExtra("category",tableCategory);
        startActivity(intent);

    }

    public void save_comment(String commentText){
        new AsyncSaveComment().execute(commentText);
    }


    protected class AsyncSaveComment extends
            AsyncTask<String, Void,Void > {

        @Override
        protected Void doInBackground(String ...params) {
            // TODO Auto-generated method stub

            RestAPI api = new RestAPI();
            try {
                api.PushComments(tableCategory,userID,adid,params[0]);
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

    protected class AsyncLoadImage extends
            AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                contactswanted_image = ImageLoaderAPI.AzureImageDownloader(params[0]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadImage", ""+e);
            }

            return contactswanted_image;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            contactswanted_image=Bitmap.createScaledBitmap(result,dptopx(140),dptopx(140),true);
            contact_photo.setImageBitmap(contactswanted_image);
        }
    }

    protected class AsyncLoadUserCategory extends
            AsyncTask<String, Void,String > {
        String userCategory;

        @Override
        protected String doInBackground(String ...params) {
            // TODO Auto-generated method stub

            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObj = api.GetUserCategory(params[0]);
                JSONParser parser = new JSONParser();
                userCategory = parser.parseUserCategory(jsonObj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncSaveComment", ""+e);
            }
            return userCategory;
        }


        @Override
        protected void onPostExecute(String result) {
            System.out.println("here=>"+result);
            switch (result){
                case "individual":
                    intent=new Intent(getApplicationContext(), ViewIndividualDetail.class);
                    break;
                case "organization":
                    intent=new Intent(getApplicationContext(), ViewCompanyDetail.class);
                    break;
                case "shop":
                    intent=new Intent(getApplicationContext(), ViewShopDetail.class);
                    break;
                default:
                    break;
            }
            intent.putExtra("username",username.getText().toString());
            startActivity(intent);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contactsnwanted_view_detail, menu);
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
