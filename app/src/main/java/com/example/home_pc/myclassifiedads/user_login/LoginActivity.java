package com.example.home_pc.myclassifiedads.user_login;

import android.content.Intent;
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
import com.example.home_pc.myclassifiedads.mainactivity.NavigationActivity;

import org.json.JSONObject;


public class LoginActivity extends ActionBarActivity {

    Button logInButton;
    EditText userName,passWord;
    String usrName,pasWord;
    TextView forgotPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName=(EditText) findViewById(R.id.userName);
        passWord=(EditText) findViewById(R.id.passWord);
        forgotPassWord=(TextView) findViewById(R.id.forgotPassWord);
        logInButton =(Button) findViewById(R.id.log_in);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_inclick();
            }
        });
    }

    public void log_inclick(){
        if(userName.getText().toString().equals("") || passWord.getText().toString().equals("")){
            Toast.makeText(this,"Missing Username or Password!!",Toast.LENGTH_SHORT).show();
        }
        else {
            usrName=userName.getText().toString();
            pasWord=passWord.getText().toString();
            logInButton.setEnabled(false);
            new AsyncAuthenticateUser().execute();
        }
    }

    protected class AsyncAuthenticateUser extends
            AsyncTask<Void, Void, String[]> {

        String[] userCategory={"","",""};

        @Override
        protected String[] doInBackground(Void... params) {
            RestAPI api = new RestAPI();
            try{
                JSONObject jsonObject=api.UserAuthentication(usrName, pasWord);
                JSONParser jsonParser=new JSONParser();
                userCategory[0] = jsonParser.authenticateUser(jsonObject)[0];
                userCategory[1] = jsonParser.authenticateUser(jsonObject)[1];
                userCategory[2] = jsonParser.authenticateUser(jsonObject)[2];
            }
            catch(Exception e){
                System.out.println("NOUSER: "+e);
            }
            return userCategory;
        }

        @Override
        protected void onPostExecute(String[] result) {
            System.out.println("RESULT: "+result[0]+" "+result[1]+" "+result[2]);
            logInButton.setEnabled(true);
            if(result[0].equals("False")){
                System.out.println("WRONG: "+result[0]);
            }
            else {
                Intent loginIntent = new Intent(getApplicationContext(), NavigationActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                loginIntent.putExtra("Selection", 2);
                loginIntent.putExtra("userID", usrName);
                loginIntent.putExtra("userCategory", result[0]);
                loginIntent.putExtra("FullUserName", result[1]);
                loginIntent.putExtra("PictureURL", result[2]);
                startActivity(loginIntent);
                finish();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
}
