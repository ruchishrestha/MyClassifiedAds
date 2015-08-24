package com.example.home_pc.myclassifiedads.jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.jobs.JobAddActivity;
import com.example.home_pc.myclassifiedads.R;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Home-PC on 7/23/2015.
 */
public class JobFragment extends Fragment {
    RecyclerView jobList;
    SwipeRefreshLayout mswipeRefreshLayout;
    ProgressDialog progressDialog;
    ArrayList<JobAdsObject> jobAdsObjects;
    Context context;
    JobAdsAdapter jobAdsAdapter;
    String userID;
    ImageView popupMenu;


    public JobFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ads_recycler_view, container,
                false);
        setHasOptionsMenu(true);
        userID=getArguments().getString("userID");
        jobList=(RecyclerView) view.findViewById(R.id.cardList);
        mswipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        context=getActivity();
        setHasOptionsMenu(true);
        jobList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        jobList.setLayoutManager(llm);
        loadJobAds();

        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJobAds();
                onItemLoadComplete();
            }
        });
        return view;
    }

    public void onItemLoadComplete(){
        mswipeRefreshLayout.setRefreshing(false);
    }

    public void loadJobAds(){
        new AsyncLoadJobAds().execute();
    }

    protected class AsyncLoadJobAds extends
            AsyncTask<Void, Void, ArrayList<JobAdsObject>> {


        @Override
        protected ArrayList<JobAdsObject> doInBackground(Void... params) {

            RestAPI api = new RestAPI();
            try {
                jobAdsObjects=new ArrayList<>();
                JSONObject jsonObj = api.GetJobsList();
                JSONParser parser = new JSONParser();
                jobAdsObjects = parser.parseJobsList(jsonObj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadContactList", e.getMessage());
            }
            return jobAdsObjects;
        }

        @Override
        protected void onPreExecute(){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(ArrayList<JobAdsObject> result) {
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            if(result!=null){
                jobAdsAdapter=new JobAdsAdapter(context,result,userID);
                jobList.setAdapter(jobAdsAdapter);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        inflater.inflate(R.menu.menu_add_ads, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.addads){
            Intent intent = new Intent(getActivity(),JobAddActivity.class);
            intent.putExtra("userID",userID);
            startActivity(intent);
        }
        return false;
    }
}
