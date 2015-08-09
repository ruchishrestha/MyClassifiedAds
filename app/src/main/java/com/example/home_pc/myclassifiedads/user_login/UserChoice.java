package com.example.home_pc.myclassifiedads.user_login;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;


public class UserChoice extends ActionBarActivity {

    TextView ind,shop,org;
    ImageView imgind,imgshop,imgorg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_choice);
        ind = (TextView) findViewById(R.id.txt_ind);
        shop = (TextView) findViewById(R.id.txt_shop);
        org = (TextView) findViewById(R.id.txt_org);
        imgind = (ImageView) findViewById(R.id.individual);
        imgshop = (ImageView) findViewById(R.id.shops);
        imgorg = (ImageView) findViewById(R.id.organization);

        ind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indclick();
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopclick();
            }
        });

        org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orgclick();
            }
        });

        imgind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indclick();
            }
        });

        imgshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopclick();
            }
        });

        imgorg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orgclick();
            }
        });
    }

    public void indclick(){
        Intent nextpage=new Intent(this,IndividualSignUp.class);
        nextpage.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextpage);
    }

    public void shopclick(){
        Intent nextpage=new Intent(this,ShopSignUp.class);
        nextpage.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextpage);
    }

    public void orgclick(){
        Intent nextpage=new Intent(this,OrganizationSignUp.class);
        nextpage.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextpage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_choice, menu);
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
