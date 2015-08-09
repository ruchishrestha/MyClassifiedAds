package com.example.home_pc.myclassifiedads.contacts;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;


public class ContactsAddActivity extends ActionBarActivity {

    private ImageView uploadedPic;
    private TextView uploadPic,locateOnMap;
    private EditText title,description,aDdress,contactNo,mobileNo,emailId;
    private Spinner category;
    SpinnerAdapter categoryAdapter;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contacts);

        uploadedPic=(ImageView) findViewById(R.id.contactImage);
        uploadPic=(TextView) findViewById(R.id.uploadPics);
        title=(EditText) findViewById(R.id.title);
        description=(EditText) findViewById(R.id.description);
        category=(Spinner) findViewById(R.id.contactCategory);
        aDdress=(EditText) findViewById(R.id.aDdress);
        contactNo=(EditText) findViewById(R.id.contactNo);
        mobileNo=(EditText) findViewById(R.id.mobileNo);
        emailId=(EditText) findViewById(R.id.emailId);
        locateOnMap=(TextView) findViewById(R.id.locateOnMap);
        saveButton= (Button) findViewById(R.id.saveButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts_add, menu);
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
        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
