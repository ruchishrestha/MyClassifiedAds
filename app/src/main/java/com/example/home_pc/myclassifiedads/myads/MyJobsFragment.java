package com.example.home_pc.myclassifiedads.myads;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.jobs.JobAdsAdapter;
import com.example.home_pc.myclassifiedads.jobs.JobAdsObject;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ruchi on 2015-09-05.
 */
public class MyJobsFragment extends Fragment {
    RecyclerView jobList;
    ProgressDialog progressDialog;
    ArrayList<JobAdsObject> jobAdsObjects;
    Context context;
    JobAdsAdapter jobAdsAdapter;
    String tableCategory,userID,userCategory;
    SwipeRefreshLayout mswipeRefreshLayout;

    public MyJobsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ads_recycler_view, container,
                false);
        context = getActivity();
        userID=getArguments().getString("userID");
        tableCategory=getArguments().getString("tableCategory");
        userCategory=getArguments().getString("userCategory");
        jobList=(RecyclerView) view.findViewById(R.id.cardList);
        mswipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        jobList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        jobList.setLayoutManager(llm);
        new AsyncLoadJobAds().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncLoadJobAds().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                onItemLoadComplete();
            }
        });


        return view;
    }
    public void onItemLoadComplete(){
        mswipeRefreshLayout.setRefreshing(false);
    }

    protected class AsyncLoadJobAds extends
            AsyncTask<Void, Void, ArrayList<JobAdsObject>> {


        @Override
        protected ArrayList<JobAdsObject> doInBackground(Void... params) {

            RestAPI api = new RestAPI();
            try {
                jobAdsObjects=new ArrayList<>();
                JSONObject jsonObj = api.GetMyJobsList(userID);
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
                jobAdsAdapter=new JobAdsAdapter(context,result,userID,1);
                jobList.setAdapter(jobAdsAdapter);
            } else{
                Toast.makeText(getActivity(), "NO ADS FOUND :(", Toast.LENGTH_LONG).show();
            }
        }
    }
}
