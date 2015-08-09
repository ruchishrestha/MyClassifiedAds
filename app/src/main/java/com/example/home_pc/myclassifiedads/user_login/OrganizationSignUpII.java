package com.example.home_pc.myclassifiedads.user_login;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;


public class OrganizationSignUpII extends ActionBarActivity {

    EditText contactNo,mobileNo,emailId,webSite;
    String organizationName, registrationNo,aDdress,userName,passWord;
    Button signUpButton;
    Bitmap organizationPic;
    Double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organization_sign_up_ii);

        latitude=null;
        longitude=null;
        contactNo = (EditText) findViewById(R.id.organizationContact);
        mobileNo = (EditText) findViewById(R.id.organizationMobile);
        emailId = (EditText) findViewById(R.id.organizationEmail);
        webSite = (EditText) findViewById(R.id.organizationWebsite);
        organizationName = getIntent().getExtras().getString("CompanyName");
        registrationNo = getIntent().getExtras().getString("RegistrationNo");
        aDdress = getIntent().getExtras().getString("Address");
        userName = getIntent().getExtras().getString("UserName");
        passWord = getIntent().getExtras().getString("Password");
        organizationPic = getIntent().getParcelableExtra("CompanyPic");
        signUpButton =(Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupclick();
            }
        });
    }


    public void signupclick(){
        if(latitude==null || longitude==null){
            Toast.makeText(this, "Select your location in Map!", Toast.LENGTH_SHORT).show();
        }
        else {
           /* Intent mainAct=new Intent(this,MainActivity.class);
            mainAct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainAct.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainAct);
            finish();*/
        }
    }

    protected class AsyncCreateOrganizationAccount extends
            AsyncTask<Void, Void, OrganizationUser> {

        OrganizationUser userObject = null;

        @Override
        protected OrganizationUser doInBackground(Void... params) {
            return userObject;
        }

        @Override
        protected void onPostExecute(OrganizationUser result) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        SharedPreferences pref=getApplicationContext().getSharedPreferences("orgsignupdata", 0);
        contactNo.setText(pref.getString("contact",""));
        mobileNo.setText(pref.getString("mobile",""));
        emailId.setText(pref.getString("email",""));
        webSite.setText(pref.getString("website", ""));
        latitude = Double.parseDouble(pref.getString("latitude","0.0"));
        longitude = Double.parseDouble(pref.getString("longitude","0.0"));
        if(latitude==0.0 && longitude ==0.0){
            latitude=null;
            longitude=null;
        }
        SharedPreferences.Editor editor= pref.edit();
        editor.clear();
        editor.apply();
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        SharedPreferences pref=getApplicationContext().getSharedPreferences("orgsignupdata", 0);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString("contact",contactNo.getText().toString());
        editor.putString("mobile", mobileNo.getText().toString());
        editor.putString("email", emailId.getText().toString());
        editor.putString("website",webSite.getText().toString());
        if(latitude==null && longitude ==null){
            latitude=0.0;
            longitude=0.0;
        }
        editor.putString("latitude",latitude.toString());
        editor.putString("longitude",longitude.toString());
        editor.apply();
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        SharedPreferences pref=getApplicationContext().getSharedPreferences("orgsignupdata", 0);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString("contact",contactNo.getText().toString());
        editor.putString("mobile", mobileNo.getText().toString());
        editor.putString("email", emailId.getText().toString());
        editor.putString("website", webSite.getText().toString());
        if(latitude==null && longitude ==null){
            latitude=0.0;
            longitude=0.0;
        }
        editor.putString("latitude",latitude.toString());
        editor.putString("longitude",longitude.toString());
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_org_signup2, menu);
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
