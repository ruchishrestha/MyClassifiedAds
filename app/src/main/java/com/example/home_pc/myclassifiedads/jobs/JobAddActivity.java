package com.example.home_pc.myclassifiedads.jobs;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.ImageLoaderAPI;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.mainactivity.LocateOnMapActivity;

import org.json.JSONObject;


public class JobAddActivity extends ActionBarActivity {

    private final int RESULT_LOAD_IMAGE = 0;
    private final int RESULT_CAMERA_PIC = 1;
    private final int REQUEST_LATLONG = 2;
    ImageView organizationLogo;
    TextView uploadLogo,locateOnMap,dialogOption1,dialogOption2;
    EditText title,description,responsibility,skills,jobCategoryOthers,vacancies,salary,aDdress,contactNo,emailId,webSite;
    String userName,jtitle,jdescription,jresponsibility,jskills,jjobCategory,jjobTime,jvacancies,jsalary,jaDdress,jcontactNo,jemailId,jwebSite,jpictureURL;
    private Spinner jobCategory,jobTiming;
    SpinnerAdapter jobCategoryAdapter;
    Double _latitude,_longitude;
    Button saveButton;
    Bitmap picture,temp;
    int photoCount;
    Boolean toggle;
    Dialog dialog;
    JobAdsObject jobAdsObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_jobs);

        photoCount=0;
        toggle = false;

        userName = getIntent().getStringExtra("userID");
        organizationLogo = (ImageView) findViewById(R.id.jobsImage);
        uploadLogo = (TextView) findViewById(R.id.uploadPic);
        title=(EditText) findViewById(R.id.title);
        description=(EditText) findViewById(R.id.description);
        responsibility=(EditText) findViewById(R.id.responsibility);
        skills=(EditText) findViewById(R.id.requirement);
        jobCategoryOthers=(EditText) findViewById(R.id.others);
        vacancies=(EditText) findViewById(R.id.noOfVacancy);
        salary=(EditText) findViewById(R.id.salary);
        aDdress=(EditText) findViewById(R.id.organizationAddress);
        contactNo=(EditText) findViewById(R.id.organizationContact);
        emailId=(EditText) findViewById(R.id.organizationEmail);
        webSite=(EditText) findViewById(R.id.organizationWebsite);
        jobCategory=(Spinner) findViewById(R.id.jobCategory);
        jobTiming=(Spinner) findViewById(R.id.jobTime);
        locateOnMap = (TextView) findViewById(R.id.locateOnMap);
        saveButton =(Button) findViewById(R.id.saveButton);

        dialog=new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialogOption1=(TextView)dialog.findViewById(R.id.dialogOption1);
        dialogOption2=(TextView)dialog.findViewById(R.id.dialogOption2);

        organizationLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPictureClick(v);
            }
        });

        uploadLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPictureClick(v);
            }
        });

        locateOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locateOnMap= new Intent(getApplicationContext(), LocateOnMapActivity.class);
                startActivityForResult(locateOnMap, REQUEST_LATLONG);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButtonClick();
            }
        });

    }

    public void saveButtonClick(){
        jtitle = title.getText().toString();
        jdescription = description.getText().toString();
        jresponsibility = responsibility.getText().toString();
        jskills = skills.getText().toString();
        jjobCategory = "Computer Engineer";//jobCategory.getSelectedItem().toString();
        jjobTime = jobTiming.getSelectedItem().toString();
        jvacancies = vacancies.getText().toString();
        jsalary = salary.getText().toString();
        jaDdress = aDdress.getText().toString();
        jcontactNo = contactNo.getText().toString();
        jemailId = emailId.getText().toString();
        jwebSite = webSite.getText().toString();
        jpictureURL = "-";
        jobAdsObject = new JobAdsObject(userName,jpictureURL,jtitle,jdescription,jresponsibility,jskills,jjobCategory,jjobTime,jvacancies,jsalary,jaDdress,jcontactNo,jemailId,jwebSite,_latitude,_longitude);
        new AsyncAddJobAds().execute(jobAdsObject);
    }

    public void uploadPictureClick(View view){

        if(!toggle) {
            if (photoCount == 0) {

                dialog.setTitle("Choose option to upload photo:");
                dialogOption1.setText("From Device");
                dialogOption2.setText("From Camera");
                dialog.show();

                dialogOption1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent gallery = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, RESULT_LOAD_IMAGE);
                    }
                });

                dialogOption2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent camera = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivityForResult(camera, RESULT_CAMERA_PIC);
                    }
                });
            }
            toggle=true;
        }

        else {

            if (photoCount > 0) {

                dialog.setTitle("Do you want to remove this photo?");
                dialogOption1.setText("Yes");
                dialogOption2.setText("No");
                dialog.show();

                dialogOption1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        organizationLogo.setImageResource(R.drawable.camerapic);
                        photoCount--;
                        dialog.dismiss();
                    }
                });

                dialogOption2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
            toggle=false;
        }
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
                Toast.makeText(this, "" + _latitude + " " + _longitude, Toast.LENGTH_LONG).show();
            }
        }
        else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            picture = BitmapFactory.decodeFile(picturePath);
            temp = Bitmap.createScaledBitmap(picture, dptopx(100), dptopx(100), true);
            organizationLogo.setImageBitmap(temp);
            photoCount++;
        } else if (requestCode == RESULT_CAMERA_PIC && resultCode == RESULT_OK && data!=null) {
            picture = (Bitmap) data.getExtras().get("data");
            temp = Bitmap.createScaledBitmap(picture, dptopx(100), dptopx(100), true);
            organizationLogo.setImageBitmap(temp);
            photoCount++;
        }

        dialog.dismiss();
    }

    public int dptopx(float dp){
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return ((int) (dp * scale + 0.5f));
    }

    protected class AsyncAddJobAds extends AsyncTask <JobAdsObject,Void,String>
    {

        String adID;
        String pictureURL;
        String result;

        @Override
        protected String doInBackground(JobAdsObject... params) {


            RestAPI api = new RestAPI();

            try{
                JSONObject object = api.AddJobAds(params[0].getUserName(), params[0].gettitle(), params[0].getDescription(), params[0].getResponsibility(), params[0].getSkills(), params[0].getJobCategory(), params[0].getJobTime(), params[0].getVaccancyNo(), params[0].getSalary(), params[0].getaDdress(), params[0].getContactNo(), params[0].getEmailId(), params[0].getWebSite(), params[0].getLatitude(), params[0].getLongitude(), params[0].getLogoURL());
                JSONParser parser = new JSONParser();
                adID = parser.getId(object);

                pictureURL = ImageLoaderAPI.AzureImageUploader(picture, "Jobs" + adID);

                object = api.UpdateJobAd(adID, pictureURL);
                result = parser.getResult(object);
            }
            catch (Exception e){

            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            System.out.println(result);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_add, menu);
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
