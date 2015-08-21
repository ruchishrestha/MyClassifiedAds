package com.example.home_pc.myclassifiedads.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.ImageLoaderAPI;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

import com.example.home_pc.myclassifiedads.user_login.UserChoice;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


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

        new AsyncLoadImage().execute();
        /*Intent loginIntent = new Intent(this,LoginActivity.class);
        startActivity(loginIntent);*/
    }

    public void newUserButtonClick(){
        Intent signupIntent = new Intent(this,UserChoice.class);
        startActivity(signupIntent);
    }

    public void guestButtonClick(){
        Intent guestIntent = new Intent(this,NavigationActivity.class);
        guestIntent.putExtra("UserName","Guest");
        startActivity(guestIntent);
    }

    protected class AsyncLoadImage extends AsyncTask <Void,Void,Bitmap>{

        public static final String storageConnectionString =
                "DefaultEndpointsProtocol=https;"
                        + "AccountName=classifiedimagestorage;"
                        + "AccountKey=hTeFYaGi2hEmF3jF0vK860iIIq6IPUFe5k+aIk+H3vzdZnMQI0Ry19RQyOVUCxYGgUquTuChjvBDH1fQV9jQwg==";


        @Override
        protected Bitmap doInBackground(Void... params) {
            final String IMAGEURL="https://classifiedimagestorage.blob.core.windows.net/gallery/image1.png";

            return ImageLoaderAPI.AzureImageDownloader(IMAGEURL);
        }


        @Override
        protected void onPostExecute(Bitmap pic) {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_layout));
            ImageView test = (ImageView) layout.findViewById(R.id.test);
            Toast toast=new Toast(getApplicationContext());
            test.setImageBitmap(Bitmap.createScaledBitmap(pic,dptopx(100),dptopx(100),true));
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }
    }

    public int dptopx(float dp){
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return ((int) (dp * scale + 0.5f));
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
