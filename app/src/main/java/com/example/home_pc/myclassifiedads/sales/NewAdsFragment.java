package com.example.home_pc.myclassifiedads.sales;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ruchi on 2015-08-24.
 */
public class NewAdsFragment extends Fragment {

    RecyclerView salesList;
    SwipeRefreshLayout mswipeRefreshLayout;
    ProgressDialog progressDialog;
    ArrayList<SalesAdsObject> salesAdsObjects;
    Context context;
    String userID,salesCategory;

    public NewAdsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ads_recycler_view, container,
                false);
        userID = getArguments().getString("userID");
        salesCategory=getArguments().getString("salesCategory");
        salesList = (RecyclerView) view.findViewById(R.id.cardList);
        mswipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        context = getActivity();
        setHasOptionsMenu(true);
        salesList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        salesList.setLayoutManager(llm);
        loadSalesList();

        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadSalesList();
                onItemLoadComplete();
            }
        });

        return view;
    }

    public void onItemLoadComplete(){
        mswipeRefreshLayout.setRefreshing(false);
    }

    public void loadSalesList(){
        new AsyncLoadSalesList().execute();
    }

    protected class AsyncLoadSalesList extends
            AsyncTask<Void, Void, ArrayList<SalesAdsObject>> {


        @Override
        protected ArrayList<SalesAdsObject> doInBackground(Void... params) {

            RestAPI api = new RestAPI();
            try {
                salesAdsObjects=new ArrayList<>();
                JSONObject jsonObj = api.GetSalesList(salesCategory,"newads");
                JSONParser parser = new JSONParser();
                salesAdsObjects = parser.parseSalesList(jsonObj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadSalesList",""+e);
            }
            return salesAdsObjects;
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
        protected void onPostExecute(ArrayList<SalesAdsObject> result) {
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            if(result!=null){
                SalesAdsAdapter salesAdsAdapter=new SalesAdsAdapter(context,result,userID,salesCategory);
                salesList.setAdapter(salesAdsAdapter);
            }
            else{
                Toast.makeText(context,"NO ADS FOUND :(",Toast.LENGTH_LONG).show();
            }
        }
    }

}


