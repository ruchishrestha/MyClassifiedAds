package com.example.home_pc.myclassifiedads.contacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
import android.widget.EditText;
import android.widget.ImageView;
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
    ImageView contact_photo,comment_cancel,comment_save;
    TextView category,username,ad_description,ad_title,contactNo,address,email,comment,myComments,commentText,commenterUsername;
    Bitmap bitmap;
    Integer contactID;
    CardView commentContacts;
    CommentObject commentObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);

        contactID=getIntent().getExtras().getInt("contactID");
        contact_photo=(ImageView)findViewById(R.id.contact_photo);
        category=(TextView)findViewById(R.id.category);
        username=(TextView)findViewById(R.id.username);
        contactNo=(TextView)findViewById(R.id.contactNo);
        ad_description=(TextView)findViewById(R.id.ad_description);
        ad_title=(TextView)findViewById(R.id.ad_title);
        address=(TextView)findViewById(R.id.address);
        email=(TextView)findViewById(R.id.email);
        comment=(TextView)findViewById(R.id.comment);
        commentContacts=(CardView)findViewById(R.id.commentContacts);

        commentContacts.setVisibility(View.GONE);

        Drawable drawable=getResources().getDrawable(R.drawable.ic_pictures);
        bitmap=((BitmapDrawable)drawable).getBitmap();
        bitmap= Bitmap.createScaledBitmap(bitmap, dptopx(150), dptopx(150), true);

        new AsyncLoadContactDetail().execute(contactID);

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindow();
            }
        });
    }

    protected class AsyncLoadContactDetail extends
            AsyncTask<Integer, Void, ArrayList<ContactsAdObject>> {

        @Override
        protected ArrayList<ContactsAdObject> doInBackground(Integer ...params) {
            // TODO Auto-generated method stub
            contactsAdObject=new ArrayList<ContactsAdObject>();
            RestAPI api = new RestAPI();
            try {
            /*    api.GetContactsDetail(params[0]);
                JSONObject jsonObj = api.GetContactsDetail(contactID);
                JSONParser parser = new JSONParser();*/
               // contactsAdObject = parser.parseContactsDetail(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadDeptDetails", e.getMessage());
            }

          //  contactsAdObject.add(new ContactsAdObject(1,"ddgfdg","sasz1973","Windows 10 installation","hurry up fpr jfjfdbfjdbfjdbjgfdjgbjfdbgjdfbgjdbgdjgjbdgbdjjg","repair","jwagal","987654","hfhurh@ksdfdfk",0.0,0.0));

            return contactsAdObject;
        }


        @Override
        protected void onPostExecute(ArrayList<ContactsAdObject> result) {
            // TODO Auto-generated method stub
            contact_photo.setImageBitmap(bitmap);
            username.setText(result.get(0).username);
            ad_title.setText(result.get(0).title);
            ad_description.setText(result.get(0).ad_description);
            category.setText(result.get(0).contacts_category);
            address.setText(result.get(0).address);
            contactNo.setText(result.get(0).contactNo);
            email.setText(result.get(0).email);

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
        myComments=(TextView)layout.findViewById(R.id.myComments);
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
                }
                else{
                   save_comment(commentText.getText().toString());
                    pwindo.dismiss();
                }

            }
        });
    }

    public void save_comment(String commentText){
        commentObject=new CommentObject("contacts",username.getText().toString(),contactID,commentText);
       // new AsyncSaveComment().execute(commentObject);
    }

/*
    protected class AsyncSaveComment extends
            AsyncTask<CommentObject, Void,Void > {

        @Override
        protected Void doInBackground(CommentObject ...params) {
            // TODO Auto-generated method stub

  RestAPI api = new RestAPI();
            try {
                api.SaveComment(params[0]);
                JSONObject jsonObj = api.GetContactsDetail(contactID);
                JSONParser parser = new JSONParser();
                contactsAdObject = parser.parseContactsDetail(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadDeptDetails", e.getMessage());
            }




            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            commentContacts.setVisibility(View.VISIBLE);
            commenterUsername=(TextView)findViewById(R.id.comenterUsername);
            myComments=(TextView)findViewById(R.id.myComments);
            commenterUsername.setText(commentObject.username);
            myComments.setText(commentObject.commentText);

        }
    }
*/

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
