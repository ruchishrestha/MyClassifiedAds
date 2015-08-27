package com.example.home_pc.myclassifiedads.common_contactsnwanted;

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
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Ruchi on 2015-08-12.
 */
public class ContactsnWantedListFragment extends Fragment {
    RecyclerView contactsList;
    SwipeRefreshLayout mswipeRefreshLayout;
    ProgressDialog progressDialog;
    ArrayList<ContactsnWantedAdObject> cObject;
    Context context;
    ContactnWantedAdsAdapter contactAdsAdapter;
    String tableCategory,userID,adtype;

    public ContactsnWantedListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ads_recycler_view, container,
                false);
        tableCategory=getArguments().getString("tableCategory");
        userID=getArguments().getString("userID");
        contactsList=(RecyclerView) view.findViewById(R.id.cardList);
        mswipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        context=getActivity();
        //setHasOptionsMenu(true);
        contactsList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        contactsList.setLayoutManager(llm);
        loadContactAds();

        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadContactAds();
                onItemLoadComplete();
            }
        });

        return view;
    }

    public void onItemLoadComplete(){
        mswipeRefreshLayout.setRefreshing(false);
    }

    public void loadContactAds(){
        new AsyncLoadContactAds().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    protected class AsyncLoadContactAds extends
            AsyncTask<Void, Void, ArrayList<ContactsnWantedAdObject>> {


        @Override
        protected ArrayList<ContactsnWantedAdObject> doInBackground(Void... params) {

            RestAPI api = new RestAPI();
            try {
                cObject=new ArrayList<ContactsnWantedAdObject>();
                JSONObject jsonObj = api.GetContactsList(tableCategory);
                JSONParser parser = new JSONParser();
                cObject = parser.parseContactsList(jsonObj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadContactList", e.getMessage());
            }
            return cObject;
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
        protected void onPostExecute(ArrayList<ContactsnWantedAdObject> result) {
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            if(result!=null){
                contactAdsAdapter =new ContactnWantedAdsAdapter(context,result,tableCategory,userID);
                contactsList.setAdapter(contactAdsAdapter);
            } else{
                Toast.makeText(getActivity(), "NO ADS FOUND :(", Toast.LENGTH_LONG).show();
            }
        }
    }



}
