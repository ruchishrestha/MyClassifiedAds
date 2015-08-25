package com.example.home_pc.myclassifiedads.realestates;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ruchi on 2015-08-20.
 */
public class RealEstateListFragment extends Fragment {

    RecyclerView realestateList;
    SwipeRefreshLayout mswipeRefreshLayout;
    ProgressDialog progressDialog;
    ArrayList<RealEstatesAdObject> realEstatesAdObjects;
    Context context;
    RealEstateAdsAdapter realEstateAdsAdapter;
    String userID;
    ImageView popupMenu;

    public RealEstateListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

              View view = inflater.inflate(R.layout.ads_recycler_view, container,
                false);
        //setHasOptionsMenu(true);
        userID=getArguments().getString("userID");
        realestateList=(RecyclerView) view.findViewById(R.id.cardList);
        mswipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        context=getActivity();
        setHasOptionsMenu(true);
        realestateList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        realestateList.setLayoutManager(llm);
        loadRealestateAds();

        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRealestateAds();
                onItemLoadComplete();
            }
        });

        return view;
    }

    public void onItemLoadComplete(){
        mswipeRefreshLayout.setRefreshing(false);
    }

    public void loadRealestateAds(){
        new AsyncLoadRealestateAds().execute();
    }

    protected class AsyncLoadRealestateAds extends
            AsyncTask<Void, Void, ArrayList<RealEstatesAdObject>> {


        @Override
        protected ArrayList<RealEstatesAdObject> doInBackground(Void... params) {

            RestAPI api = new RestAPI();
            try {
                realEstatesAdObjects=new ArrayList<RealEstatesAdObject>();
                JSONObject jsonObj = api.GetRealestateList();
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
                RealEstateAdsAdapter realEstateAdsAdapter=new RealEstateAdsAdapter(context,result,userID);
                realestateList.setAdapter(realEstateAdsAdapter);
            }
        }
    }

 /*   @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        inflater.inflate(R.menu.menu_add_ads, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.addads){
            Intent intent = new Intent(getActivity(),RealEstateAddActivity.class);
            intent.putExtra("userID",userID);
            startActivity(intent);
        }
        return false;
    }*/
}




