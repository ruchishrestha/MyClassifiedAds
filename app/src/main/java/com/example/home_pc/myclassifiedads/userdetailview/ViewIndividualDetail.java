package com.example.home_pc.myclassifiedads.userdetailview;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.ImageLoaderAPI;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.user_login.IndividualUser;

import org.json.JSONObject;

import java.util.concurrent.ThreadPoolExecutor;

public class ViewIndividualDetail extends ActionBarActivity {
    String userName;
    ImageView profile_pic;
    TextView fullname,addres,contact,mobile,email,website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_individual_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userName=getIntent().getExtras().getString("username");
        new AsyncGetDetail().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    protected class AsyncGetDetail extends AsyncTask<Void,Void,IndividualUser> {

        IndividualUser individualUser;

        @Override
        protected IndividualUser doInBackground(Void... params) {

            RestAPI api = new RestAPI();

            try{
                JSONObject jsonObject=api.GetIndividualDetail(userName);
                JSONParser parser = new JSONParser();
                individualUser = parser.getIndividualDetail(jsonObject);
            }catch (Exception e){
                System.out.println("AsyncError: "+e);
            }

            return individualUser;
        }

        @Override
        protected void onPreExecute(){
            ProgressDialog progressDialog=new ProgressDialog(ViewIndividualDetail.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(IndividualUser result) {

            fullname = (TextView)findViewById(R.id.fullname);
            addres = (TextView)findViewById(R.id.addres);
            contact = (TextView)findViewById(R.id.contact);
            mobile = (TextView)findViewById(R.id.mobile);
            email = (TextView) findViewById(R.id.email);
            website = (TextView) findViewById(R.id.website);


            fullname.setText(result.getFirstName()+" "+result.getMiddleName()+" "+result.getLastName());
            addres.setText(result.getaDdress());
            contact.setText("Contact me:"+result.getContactNo());
            mobile.setText("Mobile No.:"+result.getMobileNo());
            email.setText("Email address:"+result.getEmailId());
            website.setText("Website URL:"+result.getWebSite());
            if(!result.getProfilePic().equals("-")){
                // new AsyncLoadImage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, result.getProfilePic());
            }
        }
    }
    protected class AsyncLoadImage extends
            AsyncTask<String, Void, Bitmap> {
        Bitmap individual_image;
        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                individual_image = ImageLoaderAPI.AzureImageDownloader2(params[0]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadImage", e.getMessage());
            }
            return individual_image;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            profile_pic=(ImageView)findViewById(R.id.profile_pic);
            individual_image=Bitmap.createScaledBitmap(result, dptopx(100), dptopx(100), true);
            profile_pic.setImageBitmap(individual_image);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_individual_detail, menu);
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
        if(id==android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int dptopx(float dp){
        // Get the screen's density scale
        final float scale = this.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return ((int) (dp * scale + 0.5f));
    }
}
