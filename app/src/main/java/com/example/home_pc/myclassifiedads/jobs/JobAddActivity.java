package com.example.home_pc.myclassifiedads.jobs;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;


public class JobAddActivity extends ActionBarActivity {

    ImageView organizationLogo;
    TextView uploadLogo,locateOnMap;
    EditText title,description,responsibility,skills,jobCategoryOthers,vacancies,salary,aDdress,contactNo,emailId,webSite;
    private Spinner jobCategory,jobTiming;
    SpinnerAdapter jobCategoryAdapter;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_jobs);

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
