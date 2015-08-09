package com.example.home_pc.myclassifiedads.sales;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;

import java.util.ArrayList;


public class SalesAddActivity extends ActionBarActivity {

    private final Context context=this;
    private ImageView[] uploadedImages;
    private TextView uploadPics,dialogOptionOne,dialogOptionTwo;
    private EditText title,description,brand,modelNo,price,contactNo,usedTime;
    private Spinner status,condition;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_CAMERA_IMAGE = 0;
    private Bitmap[] tempPhotoView;
    private ArrayList<Bitmap> photosToUpload;
    private int i;
    private Dialog dialog;
    private Button saveButton;

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
        tempPhotoView =new Bitmap[10];

        dialog=new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        dialogOptionOne=(TextView)dialog.findViewById(R.id.dialogOption1);
        dialogOptionTwo=(TextView)dialog.findViewById(R.id.dialogOption2);

        uploadPics=(TextView) findViewById(R.id.uploadPics);
        title=(EditText) findViewById(R.id.title);
        description=(EditText) findViewById(R.id.description);
        brand = (EditText) findViewById(R.id.salesBrand);
        modelNo = (EditText) findViewById(R.id.modelNo);
        contactNo=(EditText) findViewById(R.id.realEstateContact);
        usedTime=(EditText) findViewById(R.id.usedTime);
        price = (EditText) findViewById(R.id.salesPrice);
        status= (Spinner) findViewById(R.id.salesStatus);
        condition=(Spinner) findViewById(R.id.conditionList);
        saveButton = (Button) findViewById(R.id.saveButton);

        uploadPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPicsclick();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void uploadPicsclick(){

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
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
        if(i<9 && v.getId()==R.id.img10){return;}
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
        for(int k=j;k<i;k++){
            photosToUpload.add(k, photosToUpload.get(k + 1));
            tempPhotoView[k]= tempPhotoView[k+1];
            uploadedImages[k].setImageBitmap(tempPhotoView[k]);
        }
        photosToUpload.add(i,null);
        tempPhotoView[i]=null;
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
            tempPhotoView[i]=Bitmap.createScaledBitmap(photosToUpload.get(i), dptopx(100), dptopx(100), true);
            uploadedImages[i].setImageBitmap(tempPhotoView[i]);
        }
        else if(requestCode == RESULT_CAMERA_IMAGE && resultCode == RESULT_OK && data != null){
            i++;
            photosToUpload.add((Bitmap) data.getExtras().get("data"));
            tempPhotoView[i]=Bitmap.createScaledBitmap(photosToUpload.get(i),dptopx(100),dptopx(100),true);
            uploadedImages[i].setImageBitmap(tempPhotoView[i]);
        }
        dialog.dismiss();
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
        getMenuInflater().inflate(R.menu.menu_sales_add, menu);
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
