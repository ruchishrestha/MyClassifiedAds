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
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.ImageLoaderAPI;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.sales.SalesAdsObject;

import org.json.JSONObject;

import java.util.ArrayList;

public class MySalesEditActivity extends ActionBarActivity {
    private final Context context=this;
    private ImageView[] uploadedImages;
    private TextView uploadPics,dialogOptionOne,dialogOptionTwo;
    private EditText title,description,brand,modelNo,price,contactNo,usedTime;
    String userName,stitle,sdescription,scategory,sbrand,smodelNo,sprice,sstatus,scondition,scontactNo,susedTime,srating,userID;
    private Spinner status,condition;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_CAMERA_IMAGE = 0;
    private ArrayList<Bitmap> photosToUpload,tempPhotoView;
    private int i;
    private Dialog dialog;
    private Button saveButton;
    SalesAdsObject salesAdsObject;
    ArrayList<SalesAdsObject> salesAdsObjects;
    int adid,flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_salesitems);

        i=-1;
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

        photosToUpload= new ArrayList<Bitmap>();
        tempPhotoView =new ArrayList<Bitmap>();

        userID = getIntent().getStringExtra("userID");
        adid=getIntent().getExtras().getInt("adid");

        dialog=new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        dialogOptionOne=(TextView)dialog.findViewById(R.id.dialogOption1);
        dialogOptionTwo=(TextView)dialog.findViewById(R.id.dialogOption2);

        uploadPics=(TextView) findViewById(R.id.uploadPics);
        title=(EditText) findViewById(R.id.title);
        description=(EditText) findViewById(R.id.description);
        brand = (EditText) findViewById(R.id.salesBrand);
        modelNo = (EditText) findViewById(R.id.modelNo);
        contactNo=(EditText) findViewById(R.id.salesContact);
        usedTime=(EditText) findViewById(R.id.usedTime);
        price = (EditText) findViewById(R.id.salesPrice);
        status= (Spinner) findViewById(R.id.salesStatus);
        condition=(Spinner) findViewById(R.id.conditionList);
        saveButton = (Button) findViewById(R.id.saveButton);
        new AsyncLoadSalesDetail().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,adid);
        new AsyncLoadImages().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,adid);

        uploadPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPicsclick();
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
        stitle = title.getText().toString();
        sdescription = description.getText().toString();
        sbrand = brand.getText().toString();
        smodelNo = modelNo.getText().toString();
        sprice = price.getText().toString();
        sstatus = status.getSelectedItem().toString();
        scondition = condition.getSelectedItem().toString();
        scontactNo = contactNo.getText().toString();
        susedTime = usedTime.getText().toString();
        salesAdsObject = new SalesAdsObject(adid,stitle,sdescription,scategory,sbrand,smodelNo,sprice,sstatus,scontactNo,scondition,susedTime);
        saveButton.setEnabled(false);
        new AsyncUpdateSalesAds().execute(salesAdsObject);

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
        flag=1;
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
        for(int k=j;k<i;k++){
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
        dialog.dismiss();
    }


    protected class AsyncLoadSalesDetail extends
            AsyncTask<Integer, Void, ArrayList<SalesAdsObject>> {
ProgressDialog progressDialog;
        @SuppressLint("LongLogTag")
        @Override
        protected ArrayList<SalesAdsObject> doInBackground(Integer... params) {
            // TODO Auto-generated method stub
            salesAdsObjects = new ArrayList<>();
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObj = api.GetSalesDetail(params[0]);
                JSONParser parser = new JSONParser();
                salesAdsObjects = parser.parseSalesDetails(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadRealEstateDetails", e.getMessage());
            }

            return salesAdsObjects;
        }


        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MySalesEditActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(ArrayList<SalesAdsObject> result) {
            // TODO Auto-generated method stub
            //  contact_photo.setImageBitmap(bitmap);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            title.setText(result.get(0).gettitle());
            price.setText("NPR."+result.get(0).getPrice());
            brand.setText(result.get(0).getBrand());
            modelNo.setText(result.get(0).getModelNo());
            contactNo.setText(result.get(0).getContactNo());
            description.setText(result.get(0).getDescription());
            usedTime.setText(result.get(0).getUsedTime());
            scategory=result.get(0).getCategory();

        }
    }

    protected class AsyncLoadImages extends
            AsyncTask<Integer, Void, ArrayList<Bitmap>> {
        ArrayList<String> sales_pictures=null;

        @Override
        protected ArrayList<Bitmap> doInBackground(Integer... params) {
            RestAPI api=new RestAPI();
            sales_pictures=new ArrayList<>();
            try{
                JSONObject jsonObj = api.GetSalesImages(params[0]);
                JSONParser parser = new JSONParser();
                sales_pictures = parser.parseReturnedURLs(jsonObj);
                if(sales_pictures==null){
                    photosToUpload=null;
                }else{
                    photosToUpload= ImageLoaderAPI.AzureImageDownloader(sales_pictures);
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
                    uploadedImages[i].setImageBitmap(Bitmap.createScaledBitmap(result.get(i),dptopx(150),dptopx(150),true));
                }
                i=result.size()-1;
                }else{
                i=-1;
            }
        }
    }

    protected class AsyncUpdateSalesAds extends AsyncTask<SalesAdsObject,Void,String>{

        String adID;
        ArrayList<String> pictureURLs = new ArrayList<String>();
        String result;
        String alter;

        @Override
        protected String doInBackground(SalesAdsObject... params) {
            RestAPI api = new RestAPI();

            try {
                api.UpdateSalesAds(params[0].getSalesID(), params[0].gettitle(), params[0].getDescription(), params[0].getBrand(), params[0].getModelNo(), params[0].getPrice(), params[0].getStatus(), params[0].getCondition(), params[0].getUsedTime(), params[0].getContactNo());
                alter = scategory.replace(" ", "_");
                pictureURLs = ImageLoaderAPI.AzureImageUploader(photosToUpload,tempPhotoView,"Sales" + alter + ""+adid);
                if(flag==1){
                    api.DeleteSalesURL(adid);
                    api.AddtoSalesGallery(""+adid, scategory, pictureURLs);
                }

            }
            catch (Exception e){

            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println("Sales: "+result);
            saveButton.setEnabled(true);
            onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_sales_edit, menu);
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
