package com.example.home_pc.myclassifiedads.jobs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.ImageLoaderAPI;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.contacts.AllCommentsActivity;
import com.example.home_pc.myclassifiedads.contacts.CommentObject;
import com.example.home_pc.myclassifiedads.main.AsyncAdComment;
import com.example.home_pc.myclassifiedads.main.MainActivity;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class JobDetailActivity extends ActionBarActivity {
    ArrayList<JobAdsObject> jobAdsObjects=null;
    TextView title,jobCategory,salary,username,ad_postedDate,ad_description,responsibility,skills,jobTime,vaccancyNo,contact,
                website, comment,commentText,postedDate,commenterUsername,myComments,addres,email;
    Integer jobID;
    CardView commentJob;
    ProgressDialog progressDialog;
    String userID;
    ImageView read_comment,comment_cancel,comment_save,jobsImage;
    Bitmap jobs_image=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jobID=getIntent().getExtras().getInt("jobID");
        userID=getIntent().getExtras().getString("userID");
        title=(TextView)findViewById(R.id.title);
        jobCategory=(TextView)findViewById(R.id.jobCategory);
        salary=(TextView)findViewById(R.id.salary);
        username=(TextView)findViewById(R.id.username);
        ad_postedDate=(TextView)findViewById(R.id.ad_postedDate);
        ad_description=(TextView)findViewById(R.id.ad_description);
        responsibility=(TextView)findViewById(R.id.responsibility);
        skills=(TextView)findViewById(R.id.skills);
        jobTime=(TextView)findViewById(R.id.jobTime);
        contact=(TextView)findViewById(R.id.contact);
        website=(TextView)findViewById(R.id.website);
        vaccancyNo=(TextView)findViewById(R.id.vaccanyNo);
        addres=(TextView)findViewById(R.id.address);
        comment=(TextView)findViewById(R.id.comment);
        email=(TextView)findViewById(R.id.email);
        postedDate=(TextView)findViewById(R.id.postedDate);
        commenterUsername=(TextView)findViewById(R.id.comenterUsername);
        myComments=(TextView)findViewById(R.id.myComments);
        commentJob=(CardView)findViewById(R.id.commentJob);
        read_comment=(ImageView)findViewById(R.id.read_comment);
        jobsImage=(ImageView)findViewById(R.id.jobsImage);

        commentJob.setVisibility(View.GONE);

        new AsyncLoadJobsDetail().execute(jobID);

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userID.equals("Guest")) {
                   popupalert();
                } else {
                    initiatePopupWindow();
                }
            }
        });

        read_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allCommentsPopup(jobID, "Job");

            }
        });
    }

    public void popupalert(){
        final AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setMessage("Please Login or Sign Up");
        alertDialog.setButton2("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        alertDialog.setButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void initiatePopupWindow(){
        final PopupWindow pwindo;
        LayoutInflater inflater = (LayoutInflater) JobDetailActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.comment_popup,
                (ViewGroup) findViewById(R.id.popup_comment));
        pwindo = new PopupWindow(layout,dptopx(300),dptopx(220),true);
        pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
        comment_save=(ImageView)layout.findViewById(R.id.comment_save);
        comment_cancel=(ImageView)layout.findViewById(R.id.comment_cancel);
        commentText=(EditText)layout.findViewById(R.id.commentText);

        comment_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwindo.dismiss();
            }
        });

        comment_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((commentText.getText().toString()).equals("")) {
                    Toast.makeText(layout.getContext(), "Please comment first", Toast.LENGTH_LONG).show();
                } else {
                    save_comment(commentText.getText().toString());
                    pwindo.dismiss();
                }

            }
        });
    }

    protected class AsyncLoadJobsDetail extends
            AsyncTask<Integer, Void, ArrayList<JobAdsObject>> {

        @Override
        protected ArrayList<JobAdsObject> doInBackground(Integer ...params) {
            // TODO Auto-generated method stub
            jobAdsObjects=new ArrayList<>();
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObj = api.GetJobsDetail(params[0]);
                JSONParser parser = new JSONParser();
                jobAdsObjects= parser.parseJobDetails(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadDeptDetails", e.getMessage());
            }

            return jobAdsObjects;
        }


        @Override
        protected void onPreExecute(){
            progressDialog=new ProgressDialog(JobDetailActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            if(userID!="Guest"){
                loadMyComments(jobID, userID);
            }
        }

        @Override
        protected void onPostExecute(ArrayList<JobAdsObject> result) {
            // TODO Auto-generated method stub
            //  contact_photo.setImageBitmap(bitmap);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
           username.setText(result.get(0).username);
            title.setText(result.get(0).title);
           jobCategory.setText(result.get(0).jobCategory);
            salary.setText("NPR." + result.get(0).salary);
            ad_postedDate.setText(result.get(0).ad_postedDate);
            ad_description.setText(result.get(0).ad_description);
            responsibility.setText(result.get(0).responsibility);
            skills.setText(result.get(0).skills);
            jobTime.setText(result.get(0).jobTime);
            contact.setText(result.get(0).contactNo);
            email.setText(result.get(0).emailId);
            addres.setText(result.get(0).aDdress);
            vaccancyNo.setText(result.get(0).vaccancyNo);

              jobs_image =ImageLoaderAPI.AzureImageDownloader(result.get(0).logoURL);
//              jobs_image= Bitmap.createScaledBitmap(jobs_image, dptopx(150), dptopx(150), true);
                jobsImage.setImageBitmap(jobs_image);

            Toast.makeText(getApplicationContext(),""+result.get(0).logoURL,Toast.LENGTH_LONG).show();

        }
    }

    public void loadMyComments(int adid,String userID){
        CommentObject commentObject=new CommentObject(adid,userID,"Job");
        new AsyncLoadMyComments().execute(commentObject);
    }



    protected class AsyncLoadMyComments extends
            AsyncTask<CommentObject, Void, ArrayList<CommentObject>> {

        @Override
        protected ArrayList<CommentObject> doInBackground(CommentObject... params) {
            // TODO Auto-generated method stub
            ArrayList<CommentObject> myCommentObject = new ArrayList<>();
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObj = api.GetMyComment(params[0].adid, params[0].userID, params[0].tableCategory);
                JSONParser parser = new JSONParser();
                myCommentObject = parser.parseComment(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadMyComment", e.getMessage());
            }

            return myCommentObject;
        }

        @Override
        protected void onPostExecute(ArrayList<CommentObject> result) {
            // TODO Auto-generated method stub
            if(result==null){
                //Toast.makeText(getApplicationContext(),""+result.get(0).userID,Toast.LENGTH_LONG).show();
                commentJob.setVisibility(View.GONE);
            }
            else{

                commentJob.setVisibility(View.VISIBLE);
                postedDate.setText("Posted on: "+result.get(0).commentDate);
                commenterUsername.setText("Posted by: "+result.get(0).username);
                myComments.setText(result.get(0).commentText);
            }
        }
    }


    public void allCommentsPopup(int adid,String tableCategory) {
        Intent intent=new Intent(getApplicationContext(),AllCommentsActivity.class);
        intent.putExtra("adid",adid);
        intent.putExtra("category", tableCategory);
        startActivity(intent);

    }

    public void save_comment(String commentText){
        CommentObject commentObject=new CommentObject("Job",userID,jobID,commentText);
        new AsyncSaveComment().execute(commentObject);
    }


    protected class AsyncSaveComment extends
            AsyncTask<CommentObject, Void,Void > {

        @Override
        protected Void doInBackground(CommentObject ...params) {
            // TODO Auto-generated method stub

            RestAPI api = new RestAPI();
            try {
                api.PushComments(params[0].tableCategory,params[0].userID,params[0].adid,params[0].commentText);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncSaveComment", e.getMessage());
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            loadMyComments(jobID,userID);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_detail, menu);
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
