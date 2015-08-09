package com.example.home_pc.myclassifiedads.user_login;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;


public class IndividualSignUp extends ActionBarActivity {

    final Context context=this;
    EditText firstName,middleName,lastName,userName,passWord,rePassWord;
    ImageView profilePicture,nextButton;
    TextView uploadPicture,dialogOption1,dialogOption2;
    Dialog dialog;
    Bitmap profilePic,tempShow;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_CAMERA_PIC = 0;
    int photoCount;
    boolean toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_sign_up);

        photoCount=0;
        toggle=false;

        profilePicture= (ImageView) findViewById(R.id.profilePicture);
        uploadPicture= (TextView) findViewById(R.id.uploadPicture);
        firstName = (EditText) findViewById(R.id.firstName);
        middleName= (EditText) findViewById(R.id.middleName);
        lastName = (EditText) findViewById(R.id.lastName);
        userName = (EditText) findViewById(R.id.userName);
        passWord = (EditText) findViewById(R.id.passWord);
        rePassWord = (EditText) findViewById(R.id.rePassword);
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        dialogOption1=(TextView)dialog.findViewById(R.id.dialogOption1);
        dialogOption2=(TextView)dialog.findViewById(R.id.dialogOption2);
        nextButton = (ImageView) findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"All Fields have not been filled!!",Toast.LENGTH_SHORT).show();
            }
        });

        rePassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!rePassWord.getText().toString().equals(passWord.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Passwords Donot Match!", Toast.LENGTH_SHORT).show();
                    nextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "Passwords Donot Match!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {

                    Toast.makeText(getApplicationContext(), "Passwords Have Matched!", Toast.LENGTH_SHORT).show();
                    nextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nextButtonClick();
                        }
                    });
                }
            }
        });
        uploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPictureClick();
            }
        });
    }

    public void nextButtonClick(){
        if((firstName.getText().toString().equals(""))||(lastName.getText().toString().equals(""))||(userName.getText().toString().equals("")))
        {
            Toast.makeText(context,"All Fields have not been filled!!",Toast.LENGTH_SHORT).show();
        }
        else {

            Bundle bundle = new Bundle();
            Intent nextPage = new Intent(context, IndividualSignUpII.class);
            try {
            bundle.putString("FirstName", firstName.getText().toString());
            bundle.putString("MiddleName", middleName.getText().toString());
            bundle.putString("LastName", lastName.getText().toString());
            bundle.putString("UserName", userName.getText().toString());
            bundle.putString("Password", PasswordEncryptionService.createHash(passWord.getText().toString()));
            nextPage.putExtras(bundle);
            nextPage.putExtra("ProfilePic", profilePic);
            }
            catch (Exception e){}

            startActivity(nextPage);
        }
    }

    public void uploadPictureClick(){
        if(photoCount==0) {

            dialog.setTitle("Choose option to upload photo:");
            dialogOption1.setText("From Device");
            dialogOption2.setText("From Camera");
            dialog.show();

            dialogOption1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gallery = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
    }

    public void profilePictureClick(View view){

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
                               android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                       profilePicture.setImageResource(R.drawable.camerapic);
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
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                profilePic = BitmapFactory.decodeFile(picturePath);
                tempShow = Bitmap.createScaledBitmap(profilePic, dptopx(100), dptopx(100), true);
                profilePicture.setImageBitmap(tempShow);
                photoCount++;
            } else if (requestCode == RESULT_CAMERA_PIC && resultCode == RESULT_OK && data!=null) {
                profilePic = (Bitmap) data.getExtras().get("data");
                tempShow = Bitmap.createScaledBitmap(profilePic, dptopx(100), dptopx(100), true);
                profilePicture.setImageBitmap(tempShow);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
