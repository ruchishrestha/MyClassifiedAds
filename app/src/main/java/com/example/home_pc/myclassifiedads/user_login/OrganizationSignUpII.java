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
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.mainactivity.LocateOnMapActivity;
import com.example.home_pc.myclassifiedads.mainactivity.MainActivity;

import org.json.JSONObject;


public class OrganizationSignUpII extends ActionBarActivity {

    private final int REQUEST_LATLONG=2;
    private final String USER_TYPE="ORGANIZATION";
    private EditText contactNo,mobileNo,emailId,webSite;
    private String organizationName, registrationNo,aDdress,userName,passWord,contactN,mobileN,emailI,websit,organizationPictureURL;
    private TextView locateOnMap;
    private Button signUpButton;
    private Double _latitude,_longitude;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    OrganizationUser organizationUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_ii_organization);

        _latitude=null;
        _longitude=null;
        contactNo = (EditText) findViewById(R.id.organizationContact);
        mobileNo = (EditText) findViewById(R.id.organizationMobile);
        emailId = (EditText) findViewById(R.id.organizationEmail);
        webSite = (EditText) findViewById(R.id.organizationWebsite);
        organizationName = getIntent().getExtras().getString("CompanyName");
        registrationNo = getIntent().getExtras().getString("RegistrationNo");
        aDdress = getIntent().getExtras().getString("Address");
        userName = getIntent().getExtras().getString("UserName");
        passWord = getIntent().getExtras().getString("Password");
        locateOnMap = (TextView) findViewById(R.id.locateOnMap);
        signUpButton =(Button) findViewById(R.id.signUpButton);

        locateOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref=getApplicationContext().getSharedPreferences(USER_TYPE, 0);
                editor= pref.edit();
                editor.putString("contact",contactNo.getText().toString());
                editor.putString("mobile", mobileNo.getText().toString());
                editor.putString("email", emailId.getText().toString());
                editor.putString("website",webSite.getText().toString());
                if(_latitude==null && _longitude ==null){
                    _latitude=0.0;
                    _longitude=0.0;
                }
                editor.putString("latitude", _latitude.toString());
                editor.putString("longitude",_longitude.toString());
                editor.apply();
                Intent locateOnMap= new Intent(getApplicationContext(), LocateOnMapActivity.class);
                startActivityForResult(locateOnMap,REQUEST_LATLONG);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupclick();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_LATLONG){
            if(resultCode== LocateOnMapActivity.RESULT_LATLONG){
                _latitude=data.getDoubleExtra("Latitude",0.0);
                _longitude=data.getDoubleExtra("Longitude",0.0);
                if(_latitude==0.0 && _longitude==0.0){
                    _latitude=null;
                    _longitude=null;
                }
                pref = getApplicationContext().getSharedPreferences(USER_TYPE, 0);
                editor= pref.edit();
                editor.putString("latitude", _latitude.toString());
                editor.putString("longitude",_longitude.toString());
                editor.apply();
                Toast.makeText(this,""+_latitude+" "+_longitude,Toast.LENGTH_LONG).show();
            }
        }
    }

    public void signupclick(){
        if(_latitude==null || _longitude==null){
            Toast.makeText(this, "Select your location in Map!", Toast.LENGTH_SHORT).show();
        }
        else {
            contactN = contactNo.getText().toString();
            mobileN = mobileNo.getText().toString();
            emailI = emailId.getText().toString();
            websit = webSite.getText().toString();
            organizationPictureURL = "-";
            organizationUser = new OrganizationUser(userName,passWord,organizationName,registrationNo,aDdress,contactN,mobileN,emailI,websit,_latitude,_longitude,organizationPictureURL);
            signUpButton.setEnabled(false);
            new AsyncCreateOrganizationAccount().execute(organizationUser);
        }
    }

    protected class AsyncCreateOrganizationAccount extends
            AsyncTask<OrganizationUser, Void, String> {

        String userChecker;

        @Override
        protected String doInBackground(OrganizationUser... params) {
            RestAPI api= new RestAPI();


            try{
                JSONObject jsonObject = api.CreateOrganizationProfile(params[0].getUserName(),params[0].getPassWord(),params[0].getOrganizationName(),params[0].getRegistrationNo(),params[0].getaDdress(),params[0].getContactNo(),params[0].getMobileNo(),params[0].getEmailId(),params[0].getWebSite(),params[0].getLatitude(),params[0].getLongitude(),params[0].getOrganizationPic());
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
        contactNo.setText(pref.getString("contact",""));
        mobileNo.setText(pref.getString("mobile",""));
        emailId.setText(pref.getString("email",""));
        webSite.setText(pref.getString("website", ""));
        _latitude = Double.parseDouble(pref.getString("latitude","0.0"));
        _longitude = Double.parseDouble(pref.getString("longitude","0.0"));
        if(_latitude==0.0 && _longitude ==0.0){
            _latitude=null;
            _longitude=null;
        }
        editor= pref.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pref=getApplicationContext().getSharedPreferences(USER_TYPE, 0);
        editor= pref.edit();
        editor.putString("contact",contactNo.getText().toString());
        editor.putString("mobile", mobileNo.getText().toString());
        editor.putString("email", emailId.getText().toString());
        editor.putString("website",webSite.getText().toString());
        if(_latitude==null && _longitude ==null){
            _latitude=0.0;
            _longitude=0.0;
        }
        editor.putString("latitude", _latitude.toString());
        editor.putString("longitude",_longitude.toString());
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup2_org, menu);
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
