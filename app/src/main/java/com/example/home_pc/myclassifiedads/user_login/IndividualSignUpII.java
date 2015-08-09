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


public class IndividualSignUpII extends ActionBarActivity {

    EditText individualAddress,contactNo,mobileNo,emailId,webSite;
    String firstName,middleName,lastName,userName,passWord;
    Button signUpButton;
    Bitmap profilePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_sign_up_ii);

        individualAddress = (EditText) findViewById(R.id.aDdress);
        contactNo = (EditText) findViewById(R.id.contact);
        mobileNo = (EditText) findViewById(R.id.mobileNo);
        emailId = (EditText) findViewById(R.id.email);
        webSite = (EditText) findViewById(R.id.website);
        firstName = getIntent().getExtras().getString("FirstName");
        middleName = getIntent().getExtras().getString("MiddleName");
        lastName = getIntent().getExtras().getString("LastName");
        userName = getIntent().getExtras().getString("UserName");
        passWord = getIntent().getExtras().getString("Password");
        profilePic = getIntent().getParcelableExtra("ProfilePic");
        signUpButton=(Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupclick();
            }
        });

    }


    public void signupclick(){
        if(individualAddress.getText().toString().equals("")){
            Toast.makeText(this,"Address Field is Empty!",Toast.LENGTH_SHORT).show();
        }
        else {
            /*Intent mainAct = new Intent(this, MainActivity.class);
            mainAct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainAct.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainAct);
            finish();*/
        }
    }

    protected class AsyncCreateIndividualAccount extends
            AsyncTask<Void, Void, IndividualUser> {

        IndividualUser userObject = null;

        @Override
        protected IndividualUser doInBackground(Void... params) {
            return userObject;
        }

        @Override
        protected void onPostExecute(IndividualUser result) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        SharedPreferences pref=getApplicationContext().getSharedPreferences("signupdata", 0);
        individualAddress.setText(pref.getString("address",""));
        contactNo.setText(pref.getString("contact",""));
        mobileNo.setText(pref.getString("mobile",""));
        emailId.setText(pref.getString("email",""));
        webSite.setText(pref.getString("website",""));
        SharedPreferences.Editor editor= pref.edit();
        editor.clear();
        editor.apply();
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        SharedPreferences pref=getApplicationContext().getSharedPreferences("signupdata", 0);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString("address",individualAddress.getText().toString());
        editor.putString("contact",contactNo.getText().toString());
        editor.putString("mobile",mobileNo.getText().toString());
        editor.putString("email",emailId.getText().toString());
        editor.putString("website",webSite.getText().toString());
        editor.apply();
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        SharedPreferences pref=getApplicationContext().getSharedPreferences("signupdata", 0);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString("address",individualAddress.getText().toString());
        editor.putString("contact",contactNo.getText().toString());
        editor.putString("mobile",mobileNo.getText().toString());
        editor.putString("email",emailId.getText().toString());
        editor.putString("website",webSite.getText().toString());
        editor.apply();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up2, menu);
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
