package com.example.home_pc.myclassifiedads.user_login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;


public class ShopSignUp extends ActionBarActivity {

    private final String USER_TYPE="SHOP";
    final Context context=this;
    EditText shopName,owner,panNo,aDdress,userName,passWord,rePassWord;
    ImageView nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_shop);

        shopName = (EditText) findViewById(R.id.shopName);
        owner=(EditText) findViewById(R.id.shopOwner);
        panNo = (EditText) findViewById(R.id.shopPanNo);
        aDdress = (EditText) findViewById(R.id.shopAddress);
        userName = (EditText) findViewById(R.id.shopUser);
        passWord = (EditText) findViewById(R.id.shopPassword);
        rePassWord = (EditText) findViewById(R.id.shopRePassword);
        nextButton = (ImageView) findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "All Fields have not been filled!!", Toast.LENGTH_SHORT).show();
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
    }

    public void nextButtonClick(){
        if((shopName.getText().toString().equals(""))||(userName.getText().toString().equals(""))||(panNo.getText().toString().equals(""))||(aDdress.getText().toString().equals("")))
        {
            Toast.makeText(context,"All Fields have not been filled!!",Toast.LENGTH_SHORT).show();
        }
        else {

            Bundle bundle = new Bundle();
            Intent nextPage = new Intent(context, ShopSignUpII.class);
            try {
                bundle.putString("ShopName", shopName.getText().toString());
                bundle.putString("ShopOwner",owner.getText().toString());
                bundle.putString("UserName", userName.getText().toString());
                bundle.putString("PanNo", panNo.getText().toString());
                bundle.putString("Address", aDdress.getText().toString());
                nextPage.putExtras(bundle);
                nextPage.putExtra("Password", PasswordEncryptionService.createHash(passWord.getText().toString()));
            }
            catch (Exception e){}

            startActivity(nextPage);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences pref;
        SharedPreferences.Editor editor;
        pref=getApplicationContext().getSharedPreferences(USER_TYPE, 0);
        editor= pref.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup_shop, menu);
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
