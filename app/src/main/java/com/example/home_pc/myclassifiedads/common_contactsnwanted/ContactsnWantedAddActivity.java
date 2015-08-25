package com.example.home_pc.myclassifiedads.common_contactsnwanted;

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


public class ContactsnWantedAddActivity extends ActionBarActivity {

    private final int RESULT_LOAD_IMAGE = 0;
    private final int RESULT_CAMERA_PIC = 1;
    private final int REQUEST_LATLONG = 2;
    private ImageView uploadedPic;
    private TextView uploadPic,locateOnMap,dialogOption1,dialogOption2;
    private EditText title,description,aDdress,contactNo,mobileNo,emailId;
    String userName,adTitle,adDescription,adAddress,adContactNo,adMobileNo,adEmailId,adCategory,adtype,adImageURL;
    private Spinner category;
    SpinnerAdapter categoryAdapter;
    Double _latitude,_longitude;
    private Button saveButton;
    Bitmap picture,temp;
    int photoCount;
    Boolean toggle;
    Dialog dialog;
    ContactsnWantedAdObject contactsnWantedAdObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contactsnwanted);

        photoCount=0;
        toggle = false;


        userName = getIntent().getStringExtra("userID");
        adtype = getIntent().getStringExtra("Category");
        uploadedPic=(ImageView) findViewById(R.id.adImage);
        uploadPic=(TextView) findViewById(R.id.uploadPics);
        title=(EditText) findViewById(R.id.title);
        description=(EditText) findViewById(R.id.description);
        category=(Spinner) findViewById(R.id.adCategory);
        aDdress=(EditText) findViewById(R.id.aDdress);
        contactNo=(EditText) findViewById(R.id.contactNo);
        mobileNo=(EditText) findViewById(R.id.mobileNo);
        emailId=(EditText) findViewById(R.id.emailId);
        locateOnMap=(TextView) findViewById(R.id.locateOnMap);
        saveButton= (Button) findViewById(R.id.saveButton);

        dialog=new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialogOption1=(TextView)dialog.findViewById(R.id.dialogOption1);
        dialogOption2=(TextView)dialog.findViewById(R.id.dialogOption2);

        uploadedPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPictureClick(v);
            }
        });

        uploadPic.setOnClickListener(new View.OnClickListener() {
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
        adTitle = title.getText().toString();
        adDescription = description.getText().toString();
        adAddress = aDdress.getText().toString();
        adContactNo = contactNo.getText().toString();
        adMobileNo = mobileNo.getText().toString();
        adEmailId = emailId.getText().toString();
        adCategory = "Organization";//category.getSelectedItem().toString();
        adImageURL = "-";
        contactsnWantedAdObject = new ContactsnWantedAdObject(userName,adTitle,adDescription,adAddress,adContactNo,adMobileNo,adEmailId,adCategory,_latitude,_longitude,adImageURL);
        switch(adtype){
            case "contacts":new AsyncAddContactsAds().execute(contactsnWantedAdObject);break;
            case "wanted":new AsyncAddWantedAds().execute(contactsnWantedAdObject);break;
        }

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
                        uploadedPic.setImageResource(R.drawable.camerapic);
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
            uploadedPic.setImageBitmap(temp);
            photoCount++;
        } else if (requestCode == RESULT_CAMERA_PIC && resultCode == RESULT_OK && data!=null) {
            picture = (Bitmap) data.getExtras().get("data");
            temp = Bitmap.createScaledBitmap(picture, dptopx(100), dptopx(100), true);
            uploadedPic.setImageBitmap(temp);
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

    protected class AsyncAddContactsAds extends AsyncTask<ContactsnWantedAdObject,Void,String>
    {

        String adID;
        String pictureURL;
        String result;

        @Override
        protected String doInBackground(ContactsnWantedAdObject... params) {


            RestAPI api = new RestAPI();

            try{
                JSONObject object = api.AddContactsAds(params[0].getUserName(), params[0].gettitle(), params[0].getDescription(), params[0].getCategory(), params[0].getaDdress(), params[0].getContactNo(), params[0].getMobileNo(), params[0].getemail(), params[0].getLatitude(), params[0].getLongitute(), params[0].getAdImage());
                JSONParser parser = new JSONParser();
                adID = parser.getId(object);

                pictureURL = ImageLoaderAPI.AzureImageUploader(picture, "Contacts" + adID);

                object = api.UpdateContactsAd(adID, pictureURL);
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


    protected class AsyncAddWantedAds extends AsyncTask <ContactsnWantedAdObject,Void,String>
    {

        String adID;
        String pictureURL;
        String result;

        @Override
        protected String doInBackground(ContactsnWantedAdObject... params) {


            RestAPI api = new RestAPI();

            try{
                JSONObject object = api.AddWantedAds(params[0].getUserName(),params[0].gettitle(),params[0].getDescription(),params[0].getCategory(),params[0].getaDdress(),params[0].getContactNo(),params[0].getMobileNo(),params[0].getemail(),params[0].getLatitude(),params[0].getLongitute(),params[0].getAdImage());
                JSONParser parser = new JSONParser();
                adID = parser.getId(object);

                pictureURL = ImageLoaderAPI.AzureImageUploader(picture, "Wanted" + adID);

                object = api.UpdateWantedAd(adID, pictureURL);
                result = parser.getResult(object);
            }
            catch (Exception e){
                result = ""+e;
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
        getMenuInflater().inflate(R.menu.menu_contactsnwanted_add, menu);
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
