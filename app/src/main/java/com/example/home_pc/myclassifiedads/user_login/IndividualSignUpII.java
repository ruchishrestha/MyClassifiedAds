package com.example.home_pc.myclassifiedads.user_login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.mainactivity.MainActivity;

import org.json.JSONObject;


public class IndividualSignUpII extends ActionBarActivity {

    final String USER_TYPE="INDIVIDUAL";
    EditText individualAddress,contactNo,mobileNo,emailId,webSite;
    String userName,passWord,firstName,middleName,lastName,aDdress,contactN,mobileN,emailI,webSit,profilePictureURL;
    Button signUpButton;
    IndividualUser individualUser;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_ii_individual);

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
            aDdress= individualAddress.getText().toString();
            contactN = contactNo.getText().toString();
            mobileN = mobileNo.getText().toString();
            emailI = emailId.getText().toString();
            webSit = webSite.getText().toString();
            profilePictureURL = "-";
            individualUser = new IndividualUser(userName,passWord,firstName,middleName,lastName,aDdress,contactN,mobileN,emailI,webSit,profilePictureURL);
            signUpButton.setEnabled(false);
            new AsyncCreateIndividualAccount().execute(individualUser);
        }

    }

    protected class AsyncCreateIndividualAccount extends
            AsyncTask<IndividualUser, Void, String> {

        String userChecker;

        @Override
        protected String doInBackground(IndividualUser... params) {

            RestAPI api= new RestAPI();


            try{
                JSONObject jsonObject = api.CreateIndividualProfile(params[0].getUserName(),params[0].getPassWord(),params[0].getFirstName(),params[0].getMiddleName(),params[0].getLastName(),params[0].getaDdress(),params[0].getContactNo(),params[0].getMobileNo(),params[0].getEmailId(),params[0].getWebSite(),params[0].getProfilePic());
                JSONParser jsonParser = new JSONParser();
                userChecker = jsonParser.checkUserName(jsonObject);
            }
            catch (Exception e){
                System.out.println("ERROR: "+e);
            }

            return userChecker;

        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            signUpButton.setEnabled(true);
            if(result.equals("Success")) {
                Intent mainAct = new Intent(getApplicationContext(), MainActivity.class);
                mainAct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mainAct.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainAct);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(),result+": In use.",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        // The activity has become visible (it is now "resumed").
        pref=getApplicationContext().getSharedPreferences(USER_TYPE, 0);
        editor= pref.edit();
        individualAddress.setText(pref.getString("address",""));
        contactNo.setText(pref.getString("contact", ""));
        mobileNo.setText(pref.getString("mobile", ""));
        emailId.setText(pref.getString("email", ""));
        webSite.setText(pref.getString("website", ""));
        editor.clear();
        editor.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pref=getApplicationContext().getSharedPreferences(USER_TYPE, 0);
        editor= pref.edit();
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
