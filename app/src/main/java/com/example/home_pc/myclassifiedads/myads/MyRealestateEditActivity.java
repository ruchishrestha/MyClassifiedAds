package com.example.home_pc.myclassifiedads.myads;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.ImageLoaderAPI;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.mainactivity.LocateOnMapActivity;
import com.example.home_pc.myclassifiedads.realestates.RealEstatesAdObject;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyRealestateEditActivity extends ActionBarActivity {
    private final int REQUEST_LATLONG=2;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_CAMERA_IMAGE = 0;
    private final Context context=this;
    private ImageView[] uploadedImages;
    private TextView uploadPics,dialogOptionOne,dialogOptionTwo,locateOnMap;
    private EditText title,description,houseNo,price,aDdress,contactNo,mobileNo;
    String userName,rtitle,rdescription,rhouseNo,rpropertyType,rsaleType,rprice,raDdress,rcontactNo,rmobileNo,userID;
    private Spinner propertyType,saleType;
    ArrayAdapter<String> propertyTypeAdapter;
    private ArrayList<Bitmap> photosToUpload,tempPhotoView;
    Double _latitude,_longitude;
    private int i,realestateID;
    private Dialog dialog;
    private Button saveButton;
    RealEstatesAdObject realEstatesAdObject;
    ArrayList<RealEstatesAdObject> realEstatesAdObjects;
    ArrayList<String> propertyList;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_real_estates);

        realestateID=getIntent().getExtras().getInt("realestateID");
        userID=getIntent().getExtras().getString("userID");

        uploadedImages=new ImageView[10];

        uploadedImages[0]=(ImageView) findViewById(R.id.img1);
        uploadedImages[1]=(ImageView) findViewById(R.id.img2);
        uploadedImages[2]=(ImageView) findViewById(R.id.img3);
        uploadedImages[3]=(ImageView) findViewById(R.id.img4);
        uploadedImages[4]=(ImageView) findViewById(R.id.img5);
        uploadedImages[5]=(ImageView) findViewById(R.id.img6);
        uploadedImages[6]=(ImageView) findViewById(R.id.img7);
        uploadedImages[7]=(ImageView) findViewById(R.id.img8);
        uploadedImages[8]=(ImageView) findViewById(R.id.img9);
        uploadedImages[9]=(ImageView) findViewById(R.id.img10);

        photosToUpload = new ArrayList<Bitmap>();
        tempPhotoView =new ArrayList<Bitmap>();

        dialog=new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        dialogOptionOne=(TextView)dialog.findViewById(R.id.dialogOption1);
        dialogOptionTwo=(TextView)dialog.findViewById(R.id.dialogOption2);

        uploadPics=(TextView) findViewById(R.id.uploadPics);
        title=(EditText) findViewById(R.id.title);
        description=(EditText) findViewById(R.id.description);
        houseNo = (EditText) findViewById(R.id.houseBuildingNo);
        propertyType = (Spinner) findViewById(R.id.propertyTypeList);
        new AsyncLoadPropertyList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        saleType=(Spinner) findViewById(R.id.saleTypeList);
        contactNo=(EditText) findViewById(R.id.realEstateContact);
        mobileNo=(EditText) findViewById(R.id.mobileNo);
        aDdress=(EditText) findViewById(R.id.aDdress);
        price = (EditText) findViewById(R.id.realEstatePrice);
        locateOnMap=(TextView) findViewById(R.id.locateOnMap);
        saveButton = (Button) findViewById(R.id.saveButton);
        new AsyncLoadRealestateDetail().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, realestateID);
        new AsyncLoadImages().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, realestateID);
        uploadPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPicsclick();
            }
        });

        locateOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locateOnMap= new Intent(getApplicationContext(), LocateOnMapActivity.class);
                locateOnMap.putExtra("Latitude",_latitude);
                locateOnMap.putExtra("Longitude",_longitude);
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
        rtitle = title.getText().toString();
        rdescription = description.getText().toString();
        rhouseNo = houseNo.getText().toString();
        rpropertyType = propertyType.getSelectedItem().toString();
        rsaleType = saleType.getSelectedItem().toString();
        rprice = price.getText().toString();
        raDdress = aDdress.getText().toString();
        rcontactNo = contactNo.getText().toString();
        rmobileNo = mobileNo.getText().toString();
        realEstatesAdObject = new RealEstatesAdObject(userID,rtitle,rdescription,rhouseNo,rpropertyType,rsaleType,rprice,raDdress,rcontactNo,rmobileNo,_latitude,_longitude);
        saveButton.setEnabled(false);
        new AsyncUpdateRealEstateAds().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,realEstatesAdObject);
    }

    public void uploadPicsclick(){
        flag=1;
        if(i<9) {
            dialog.setTitle("Choose option to upload photo:");
            dialogOptionOne.setText("From Device");
            dialogOptionTwo.setText("From Camera");
            dialog.show();

            dialogOptionOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RESULT_LOAD_IMAGE);
                }
            });

            dialogOptionTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, RESULT_CAMERA_IMAGE);
                }
            });
        }
    }

    public void imageclick(View v){
        if(i<9 && v.getId()== R.id.img10){return;}
        if(i>-1) {
            dialog.setTitle("Do you want to remove this photo?");
            dialogOptionOne.setText("Yes");
            dialogOptionTwo.setText("No");
            dialog.show();

            final int j = v.getId() % (R.id.img1);

            dialogOptionOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetimg(j);
                    i--;
                }
            });

            dialogOptionTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }

    public void resetimg(int j){
        photosToUpload.remove(j);
        tempPhotoView.remove(j);
        for(int k=j;k<i;k++) {
            uploadedImages[k].setImageBitmap(tempPhotoView.get(k));
        }
        uploadedImages[i].setImageBitmap(null);
        dialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            i++;
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            photosToUpload.add(BitmapFactory.decodeFile(picturePath));
            tempPhotoView.add(Bitmap.createScaledBitmap(photosToUpload.get(i), dptopx(100), dptopx(100), true));
            uploadedImages[i].setImageBitmap(tempPhotoView.get(i));
        }
        else if(requestCode == RESULT_CAMERA_IMAGE && resultCode == RESULT_OK && data != null){
            i++;
            photosToUpload.add((Bitmap) data.getExtras().get("data"));
            tempPhotoView.add(Bitmap.createScaledBitmap(photosToUpload.get(i),dptopx(100),dptopx(100),true));
            uploadedImages[i].setImageBitmap(tempPhotoView.get(i));
        }
        else if (requestCode == REQUEST_LATLONG){
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
        dialog.dismiss();
    }


    protected class AsyncLoadPropertyList extends AsyncTask<Void,Void,ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            System.out.println("LOADLIST");
            ArrayList<String> categoryLst = new ArrayList<String>();
            RestAPI api = new RestAPI();
            try{
                JSONObject object = api.GetContactsCategory();
                JSONParser parser = new JSONParser();
                categoryLst = parser.getList(object);
            }
            catch(Exception e){}

            return categoryLst;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            propertyList = new ArrayList<String>();
            if(result.size() != 0 && result !=null) {
                for (int i = 0; i < result.size(); i++) {
                    propertyList.add(result.get(i));
                }
            }
            else{
                propertyList.add("Not Available");
            }
            propertyTypeAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,propertyList);
            propertyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            propertyType.setAdapter(propertyTypeAdapter);
        }
    }

    protected class AsyncLoadRealestateDetail extends
            AsyncTask<Integer, Void, ArrayList<RealEstatesAdObject>> {
        ProgressDialog progressDialog;
        @SuppressLint("LongLogTag")
        @Override
        protected ArrayList<RealEstatesAdObject> doInBackground(Integer ...params) {
            // TODO Auto-generated method stub
            realEstatesAdObjects=new ArrayList<>();
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObj = api.GetRealestateDetails(params[0]);
                JSONParser parser = new JSONParser();
                realEstatesAdObjects= parser.parseRealestateDetails(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadRealEstateDetails", e.getMessage());
            }

            return realEstatesAdObjects;
        }


        @Override
        protected void onPreExecute(){
            progressDialog=new ProgressDialog(MyRealestateEditActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<RealEstatesAdObject> result) {
            // TODO Auto-generated method stub
            //  contact_photo.setImageBitmap(bitmap);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            title.setText(result.get(0).gettitle());
            price.setText((result.get(0).getPrice()).toString());
            description.setText(result.get(0).getDescription());
            houseNo.setText(result.get(0).getHouseNo());
            aDdress.setText(result.get(0).getADdress());
            contactNo.setText(result.get(0).getContactNo());
            mobileNo.setText(result.get(0).getMobileNo());
            _latitude=result.get(0).getLatitude();
            _longitude=result.get(0).getLongitude();
        }
    }

    protected class AsyncLoadImages extends
            AsyncTask<Integer, Void, ArrayList<Bitmap>> {
        ArrayList<String> realestate_pictures=null;

        @Override
        protected ArrayList<Bitmap> doInBackground(Integer... params) {
            RestAPI api=new RestAPI();
            realestate_pictures=new ArrayList<>();
            try{
                JSONObject jsonObj = api.GetAllImages(params[0]);
                JSONParser parser = new JSONParser();
                realestate_pictures = parser.parseReturnedURLs(jsonObj);
                if(realestate_pictures==null){
                    photosToUpload=null;
                }else{
                    photosToUpload = ImageLoaderAPI.AzureImageDownloader(realestate_pictures);
                }


            }
            catch (Exception e){
                Log.d("AsyncLoadAllImages=>",e.getMessage());

            }
            return photosToUpload;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> result){
            if(result!=null){
                for(int i=0;i<result.size();i++){
                    tempPhotoView.add(Bitmap.createScaledBitmap(result.get(i), dptopx(100), dptopx(100), true));
                    uploadedImages[i].setImageBitmap(Bitmap.createScaledBitmap(result.get(i), dptopx(100), dptopx(100), true));
                }
                i=result.size()-1;
            }else{
                i=-1;
            }
        }
    }

    protected class AsyncUpdateRealEstateAds extends AsyncTask<RealEstatesAdObject,Void,String>{

        ArrayList<String> pictureURLs = new ArrayList<String>();
        String result;

        @Override
        protected String doInBackground(RealEstatesAdObject... params) {

            RestAPI api = new RestAPI();

            try{
                api.UpdateRealEstateAds(realestateID, params[0].gettitle(), params[0].getDescription(), params[0].getHouseNo(), params[0].getPropertyType(), params[0].getSaleType(), params[0].getPrice(), params[0].getADdress(), params[0].getContactNo(), params[0].getMobileNo(), params[0].getLatitude(), params[0].getLongitude());

                pictureURLs = ImageLoaderAPI.AzureImageUploader(photosToUpload,tempPhotoView,"RealEstate"+realestateID);

                if (flag == 1) {
                    api.DeleteRealestateURL(realestateID);
                    api.AddtoRealEstateGallery("" + realestateID, pictureURLs);
                }
            }
            catch(Exception e){
                System.out.println("ERORUPDATE: "+e);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println("Real: "+result);
            saveButton.setEnabled(true);
            onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_realestate_edit, menu);
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
        public int dptopx(float dp){
            // Get the screen's density scale
            final float scale = getResources().getDisplayMetrics().density;
            // Convert the dps to pixels, based on density scale
            return ((int) (dp * scale + 0.5f));
        }
}
