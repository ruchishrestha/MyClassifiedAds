package com.example.home_pc.myclassifiedads.userdetailview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.ImageLoaderAPI;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.mainactivity.ViewOnMap;
import com.example.home_pc.myclassifiedads.user_login.OrganizationUser;

import org.json.JSONObject;

public class ViewCompanyDetail extends ActionBarActivity {
    String userName;
    ImageView profile_pic;
    TextView organizationName,addres,registrationNo,contact,mobile,email,website,viewOnMap;
    Double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_company_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userName=getIntent().getExtras().getString("username");
        new AsyncGetDetail().execute();
        viewOnMap=(TextView)findViewById(R.id.viewOnMap);
        viewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ViewOnMap.class);
                intent.putExtra("latitute",latitude);
                intent.putExtra("longitute",longitude);
                startActivity(intent);
            }
        });
    }

    protected class AsyncGetDetail extends AsyncTask<Void,Void,OrganizationUser> {

        OrganizationUser organizationUser;

        @Override
        protected OrganizationUser doInBackground(Void... params) {

            RestAPI api = new RestAPI();

            try{
                JSONObject jsonObject=api.GetOrganizationDetail(userName);
                JSONParser parser = new JSONParser();
                organizationUser = parser.getOrganizationDetail(jsonObject);
            }catch (Exception e){
                System.out.println("AsyncError: "+e);
            }

            return organizationUser;
        }

        @Override
        protected void onPreExecute(){
           ProgressDialog progressDialog=new ProgressDialog(ViewCompanyDetail.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(OrganizationUser result) {

            organizationName = (TextView)findViewById(R.id.organizationName);
            registrationNo = (TextView)findViewById(R.id.registrationNo);
            addres = (TextView)findViewById(R.id.addres);
            contact = (TextView)findViewById(R.id.contact);
            mobile = (TextView)findViewById(R.id.mobile);
            email = (TextView) findViewById(R.id.email);
            website = (TextView) findViewById(R.id.website);


            organizationName.setText(result.getOrganizationName());
            registrationNo.setText("Registration No.:"+result.getRegistrationNo());
            addres.setText(result.getaDdress());
            contact.setText("Contact us:"+result.getContactNo());
            mobile.setText("Mobile No.:"+result.getMobileNo());
            email.setText("Email address:"+result.getEmailId());
            website.setText("Website URL:"+result.getWebSite());
            latitude = result.getLatitude();
            longitude = result.getLongitude();
            if(!result.getOrganizationPic().equals("-")){
                //  new AsyncLoadImage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, result.getOrganizationPic());
            }
        }
    }

    protected class AsyncLoadImage extends
            AsyncTask<String, Void, Bitmap> {
        Bitmap org_image;
        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                org_image = ImageLoaderAPI.AzureImageDownloader2(params[0]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadImage", e.getMessage());
            }
            return org_image;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            profile_pic=(ImageView)findViewById(R.id.profile_pic);
            org_image=Bitmap.createScaledBitmap(result, dptopx(100), dptopx(100), true);
            profile_pic.setImageBitmap(org_image);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_company_detail, menu);
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
