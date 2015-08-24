package com.example.home_pc.myclassifiedads.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.user_login.LoginActivity;
import com.example.home_pc.myclassifiedads.user_login.UserChoice;


public class MainActivity extends ActionBarActivity {

    Button loginButton,newUserButton;
    TextView guestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.login);
        newUserButton = (Button) findViewById(R.id.new_user);
        guestButton = (TextView) findViewById(R.id.guest);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonClick();
            }
        });
        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUserButtonClick();
            }
        });
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guestButtonClick();
            }
        });

    }


    public void loginButtonClick(){

        Intent loginIntent = new Intent(this,LoginActivity.class);
        startActivity(loginIntent);

    }

    public void newUserButtonClick(){

        Intent signupIntent = new Intent(this,UserChoice.class);
        startActivity(signupIntent);

    }

    public void guestButtonClick(){

        Intent guestIntent = new Intent(this,NavigationActivity.class);
        guestIntent.putExtra("Selection",2);
        guestIntent.putExtra("FullUserName","Welcome To Classified Ads");
        guestIntent.putExtra("userID","Guest");
        guestIntent.putExtra("PictureURL","-");
        startActivity(guestIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
