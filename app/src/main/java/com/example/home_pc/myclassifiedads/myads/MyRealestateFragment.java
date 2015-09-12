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
import com.example.home_pc.myclassifiedads.realestates.RealEstateAdsAdapter;
import com.example.home_pc.myclassifiedads.realestates.RealEstatesAdObject;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ruchi on 2015-09-05.
 */
public class MyRealestateFragment extends Fragment {
    RecyclerView realestateList;
    ProgressDialog progressDialog;
    ArrayList<RealEstatesAdObject> realEstatesAdObjects;
    Context context;
    String userID;
    SwipeRefreshLayout mswipeRefreshLayout;
    public MyRealestateFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ads_recycler_view, container,
                false);
        userID = getArguments().getString("userID");
        realestateList = (RecyclerView) view.findViewById(R.id.cardList);
        mswipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        context = getActivity();
        setHasOptionsMenu(true);
        realestateList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        realestateList.setLayoutManager(llm);
        new AsyncLoadRealestateAds().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncLoadRealestateAds().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                onItemLoadComplete();
            }
        });
        return view;
    }

    public void onItemLoadComplete(){
        mswipeRefreshLayout.setRefreshing(false);
    }

    protected class AsyncLoadRealestateAds extends
            AsyncTask<Void, Void, ArrayList<RealEstatesAdObject>> {


        @Override
        protected ArrayList<RealEstatesAdObject> doInBackground(Void... params) {

            RestAPI api = new RestAPI();
            try {
                realEstatesAdObjects=new ArrayList<RealEstatesAdObject>();
                JSONObject jsonObj = api.GetMyRealestateList(userID);
                JSONParser parser = new JSONParser();
                realEstatesAdObjects = parser.parseRealestateList(jsonObj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadRealEstateList", e.getMessage());
            }
            return realEstatesAdObjects;
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
        protected void onPostExecute(ArrayList<RealEstatesAdObject> result) {
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            if(result!=null){
                RealEstateAdsAdapter realEstateAdsAdapter=new RealEstateAdsAdapter(context,result,userID,1);
                realestateList.setAdapter(realEstateAdsAdapter);
            } else{
                Toast.makeText(getActivity(), "NO ADS FOUND :(", Toast.LENGTH_LONG).show();
            }
        }
    }
}
