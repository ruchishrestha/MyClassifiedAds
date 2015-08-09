package com.example.home_pc.myclassifiedads.user_login;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.main.NavigationActivity;
import com.example.home_pc.myclassifiedads.R;


public class LoginActivity extends ActionBarActivity {

    Button logInButton;
    EditText userName,passWord;
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
        if(userName.getText().equals("") || passWord.getText().equals("")){
            Toast.makeText(this,"Enter Both Username and Password!!",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent loginintent = new Intent(this, NavigationActivity.class);
            loginintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            loginintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(loginintent);
            finish();
        }
    }

    protected class AsyncGetUserDetails extends
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
