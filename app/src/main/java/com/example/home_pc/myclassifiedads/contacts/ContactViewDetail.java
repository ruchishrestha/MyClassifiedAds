package com.example.home_pc.myclassifiedads.contacts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;

import org.json.JSONObject;

import java.util.ArrayList;

public class ContactViewDetail extends ActionBarActivity {
    ArrayList<ContactsAdObject> contactsAdObject=null;
    ImageView contact_photo,comment_cancel,comment_save,read_comment;
    TextView category,username,ad_description,ad_title,contactNo,address,email,comment,commentText,postedDate,commenterUsername,myComments;
    Bitmap bitmap;
    Integer adid;
    CardView commentContacts;
    ProgressDialog progressDialog;
    String viewerUsername,tableCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);

        adid=getIntent().getExtras().getInt("adid");
        viewerUsername=getIntent().getExtras().getString("viewerUsername");
        tableCategory=getIntent().getExtras().getString("tableCategory");
        contact_photo=(ImageView)findViewById(R.id.contact_photo);
        category=(TextView)findViewById(R.id.category);
        username=(TextView)findViewById(R.id.username);
        contactNo=(TextView)findViewById(R.id.contactNo);
        ad_description=(TextView)findViewById(R.id.ad_description);
        ad_title=(TextView)findViewById(R.id.ad_title);
        address=(TextView)findViewById(R.id.address);
        email=(TextView)findViewById(R.id.email);
        comment=(TextView)findViewById(R.id.comment);
        postedDate=(TextView)findViewById(R.id.postedDate);
        commenterUsername=(TextView)findViewById(R.id.comenterUsername);
        myComments=(TextView)findViewById(R.id.myComments);
        commentContacts=(CardView)findViewById(R.id.commentContacts);
        read_comment=(ImageView)findViewById(R.id.read_comment);

        commentContacts.setVisibility(View.GONE);

        new AsyncLoadContactDetail().execute(new ContactsAdObject(adid,tableCategory));

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindow();
            }
        });

        read_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allCommentsPopup(adid,tableCategory);

            }
        });
    }

    protected class AsyncLoadContactDetail extends
            AsyncTask<ContactsAdObject, Void, ArrayList<ContactsAdObject>> {

        @Override
        protected ArrayList<ContactsAdObject> doInBackground(ContactsAdObject ...params) {
            // TODO Auto-generated method stub
            contactsAdObject=new ArrayList<ContactsAdObject>();
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObj = api.GetContactDetails(params[0].adid,params[0].tableCategory);
                JSONParser parser = new JSONParser();
                contactsAdObject = parser.parseContactDetails(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadDeptDetails", e.getMessage());
            }

            return contactsAdObject;
        }

        @Override
        protected void onPreExecute(){
            progressDialog=new ProgressDialog(ContactViewDetail.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            loadMyComments(adid, viewerUsername);
        }



        @Override
        protected void onPostExecute(ArrayList<ContactsAdObject> result) {
            // TODO Auto-generated method stub
          //  contact_photo.setImageBitmap(bitmap);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            username.setText(result.get(0).username);
            ad_title.setText(result.get(0).title);
            ad_description.setText(result.get(0).ad_description);
            category.setText(result.get(0).Category);
            address.setText(result.get(0).address);
            contactNo.setText(result.get(0).contactNo);
            email.setText(result.get(0).email);

        }
    }

    public void loadMyComments(int adid,String Username){
       CommentObject commentObject=new CommentObject(adid,Username,tableCategory);
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
                JSONObject jsonObj = api.GetMyComment(params[0].adid, params[0].username, params[0].tableCategory);
                JSONParser parser = new JSONParser();
                myCommentObject = parser.parseComment(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadMyComment", e.getMessage());
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
        LayoutInflater inflater = (LayoutInflater) ContactViewDetail.this
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
        CommentObject commentObject=new CommentObject(tableCategory,viewerUsername,adid,commentText);
        new AsyncSaveComment().execute(commentObject);
    }


    protected class AsyncSaveComment extends
            AsyncTask<CommentObject, Void,Void > {

        @Override
        protected Void doInBackground(CommentObject ...params) {
            // TODO Auto-generated method stub

            RestAPI api = new RestAPI();
            try {
                api.PushComments(params[0].tableCategory,params[0].username,params[0].adid,params[0].commentText);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncSaveComment", e.getMessage());
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            loadMyComments(adid,username.getText().toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_view_detail, menu);
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
