package com.example.home_pc.myclassifiedads.contacts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruchi on 2015-08-12.
 */
public class ContactsListFragment extends Fragment {
    RecyclerView contactsList;
    SwipeRefreshLayout mswipeRefreshLayout;
    ProgressDialog progressDialog;
    ArrayList<ContactsAdObject> cObject;
    Context context;
    ContactAdsAdapter contactAdsAdapter;
    String tableCategory;
    ImageView popupMenu;

    public ContactsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ads_recycler_view, container,
                false);
        tableCategory=getArguments().getString("tableCategory");
        contactsList=(RecyclerView) view.findViewById(R.id.cardList);
        mswipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        context=getActivity();
        setHasOptionsMenu(true);
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
        new AsyncLoadContactAds().execute(tableCategory);
    }


    protected class AsyncLoadContactAds extends
            AsyncTask<String, Void, ArrayList<ContactsAdObject>> {


        @Override
        protected ArrayList<ContactsAdObject> doInBackground(String... params) {

            RestAPI api = new RestAPI();
            try {
                cObject=new ArrayList<ContactsAdObject>();
                JSONObject jsonObj = api.GetContactsList(params[0]);
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
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(true);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(ArrayList<ContactsAdObject> result) {
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            contactAdsAdapter =new ContactAdsAdapter(context,result,tableCategory);
            contactsList.setAdapter(contactAdsAdapter);

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
            Intent intent = new Intent(getActivity(), ContactsAddActivity.class);
            startActivity(intent);
        }
        return false;
    }
}
