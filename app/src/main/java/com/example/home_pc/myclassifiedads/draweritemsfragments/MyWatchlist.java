package com.example.home_pc.myclassifiedads.draweritemsfragments;

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

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ruchi on 2015-09-14.
 */
public class MyWatchlist extends Fragment {
    RecyclerView mywatchlist;
    SwipeRefreshLayout mswipeRefreshLayout;
    ProgressDialog progressDialog;
    Context context;
    String userID;
    WatchlistAdapter watchlistAdapter;
    public MyWatchlist(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ads_recycler_view, container,
                false);
        setHasOptionsMenu(true);
        userID=getArguments().getString("userID");
        mywatchlist=(RecyclerView) view.findViewById(R.id.cardList);
        mswipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        context=getActivity();
        setHasOptionsMenu(true);
        mywatchlist.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mywatchlist.setLayoutManager(llm);
        new AsyncLoadMyWatchlist().execute();

        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncLoadMyWatchlist().execute();
                onItemLoadComplete();
            }
        });
        return view;
    }

    public void onItemLoadComplete(){
        mswipeRefreshLayout.setRefreshing(false);
    }

    protected class AsyncLoadMyWatchlist extends
            AsyncTask<Void, Void, ArrayList<WatchlistObject>> {

ArrayList<WatchlistObject> watchlistObject;
        @Override
        protected ArrayList<WatchlistObject> doInBackground(Void... params) {

            RestAPI api = new RestAPI();
            try {
                watchlistObject=new ArrayList<>();
                JSONObject jsonObj = api.GetWatchlist(userID);
                JSONParser parser = new JSONParser();
                watchlistObject = parser.parseWatchlist(jsonObj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadWatchlist", e.getMessage());
            }
            return watchlistObject;
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
        protected void onPostExecute(ArrayList<WatchlistObject> result) {
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            if(result!=null){
                watchlistAdapter=new WatchlistAdapter(context,result,userID);
                mywatchlist.setAdapter(watchlistAdapter);
            } else{
                Toast.makeText(getActivity(), "NO ADS FOUND :(", Toast.LENGTH_LONG).show();
            }
        }
    }
}
