package com.example.home_pc.myclassifiedads.contacts;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home-PC on 7/23/2015.
 */
public class ContactsFragment extends Fragment {
    Context context;
    ArrayList<ContactsAdObject> contactsAdObjects = null;
    RecyclerView contactsList;
    ArrayList<ContactsAdObject> cList;

    public ContactsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ads_recycler_view, container,
                false);
        setHasOptionsMenu(true);
        context=getActivity();
        contactsList = (RecyclerView) view.findViewById(R.id.cardList);

        contactsList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        contactsList.setLayoutManager(llm);
        Toast.makeText(context, "Loading Please Wait..", Toast.LENGTH_SHORT).show();
       // new AsyncLoadContactAds().execute();
        return view;

    }


   /* protected class AsyncLoadContactAds extends
            AsyncTask<Void, Void, ArrayList<ContactsAdObject>> {

        @Override
        protected ArrayList<ContactsAdObject> doInBackground(Void... params) {
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObj = api.GetContactsDetail();
                JSONParser parser = new JSONParser();
                contactsAdObjects = parser.parseContactsDetail(jsonObj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadDeptDetails", e.getMessage());
            }

            return contactsAdObjects;
        }

        @Override
        protected void onPostExecute(ArrayList<ContactsAdObject> result) {
            ContactAdsAdapter contactAdsAdapter = new ContactAdsAdapter(context,result);
            contactsList.setAdapter(contactAdsAdapter);
        }
    }*/

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