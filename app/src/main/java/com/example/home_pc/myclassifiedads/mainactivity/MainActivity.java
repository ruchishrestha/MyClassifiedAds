package com.example.home_pc.myclassifiedads.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import com.example.home_pc.myclassifiedads.user_login.LoginActivity;
import com.example.home_pc.myclassifiedads.user_login.UserChoice;


public class MainActivity extends ActionBarActivity {

    Button loginButton,newUserButton;
    TextView guestButton,guestButton2;
    Bitmap pic_image,pic_image2;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.login);
        newUserButton = (Button) findViewById(R.id.new_user);
        guestButton = (TextView) findViewById(R.id.guest);
        guestButton2 = (TextView) findViewById(R.id.guest2);
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
        guestButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guestButtonClick2();
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

        /*Intent guestIntent = new Intent(this,NavigationActivity.class);
        guestIntent.putExtra("Selection",2);
        guestIntent.putExtra("FullUserName","Welcome To Classified Ads");
        guestIntent.putExtra("userID","Guest");
        guestIntent.putExtra("PictureURL","-");
        startActivity(guestIntent);*/
        new AsyncLoadImage().execute("https://classifiedimagestorage.blob.core.windows.net/gallery/RealEstate7_0");

    }
    public void guestButtonClick2(){

        /*Intent guestIntent = new Intent(this,NavigationActivity.class);
        guestIntent.putExtra("Selection",2);
        guestIntent.putExtra("FullUserName","Welcome To Classified Ads");
        guestIntent.putExtra("userID","Guest");
        guestIntent.putExtra("PictureURL","-");
        startActivity(guestIntent);*/
        new AsyncLoadImage2().execute("https://classifiedimagestorage.blob.core.windows.net/gallery/RealEstate7_0");

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

    protected class AsyncLoadImage extends
            AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
               return ImageLoaderAPI.AzureImageDownloader(params[0]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadImage", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            pic_image=Bitmap.createScaledBitmap(result, dptopx(100), dptopx(100), true);
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.toast_layout));
            ImageView test = (ImageView) layout.findViewById(R.id.test);
            Toast toast=new Toast(context);
            test.setImageBitmap(pic_image);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }

    }

    protected class AsyncLoadImage2 extends
            AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                return ImageLoaderAPI.AzureImageDownloader2(params[0]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadImage", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            pic_image2=Bitmap.createScaledBitmap(result, dptopx(100), dptopx(100), true);
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.toast_layout));
            ImageView test = (ImageView) layout.findViewById(R.id.test);
            Toast toast=new Toast(context);
            test.setImageBitmap(pic_image);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }

    }

    public int dptopx(float dp){
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return ((int) (dp * scale + 0.5f));
    }


}
