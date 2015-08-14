package com.example.home_pc.myclassifiedads.contacts;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.List;

/**
 * Created by Ruchi on 2015-08-12.
 */
public class ContactsListFragment extends Fragment {
    RecyclerView contactsList;
    ArrayList<ContactsAdObject> contactsAdObjects;

    Context context;
    ContactAdsAdapter contactAdsAdapter;

    public ContactsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ads_recycler_view, container,
                false);
        contactsAdObjects = new ArrayList<ContactsAdObject>();

        contactsAdObjects=getArguments().getParcelableArrayList("contactList");
        contactsList=(RecyclerView) view.findViewById(R.id.cardList);
        context=getActivity();
        setHasOptionsMenu(true);
        contactsList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        contactsList.setLayoutManager(llm);
        loadContactAds();

        return view;
    }

    public void loadContactAds(){
        new AsyncLoadContactAds().execute();
    }


    protected class AsyncLoadContactAds extends
            AsyncTask<Void, Void, ArrayList<ContactsAdObject>> {
        ArrayList<ContactsAdObject> cObject;

        @Override
        protected ArrayList<ContactsAdObject> doInBackground(Void... params) {

            RestAPI api = new RestAPI();
            try {
                cObject=new ArrayList<ContactsAdObject>();
                JSONObject jsonObj = api.GetContactsList();
                JSONParser parser = new JSONParser();
                cObject = parser.parseContactsList(jsonObj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadContactList", e.getMessage());
            }
            //   contactsAdObject.add(new ContactsAdObject(1,bitmap,"sasz1973","Windows 10 installation","jwagal","987654"));*//*
            return cObject;
        }

        @Override
        protected void onPostExecute(ArrayList<ContactsAdObject> result) {
          /* contactsAdObject.add(new ContactsAdObject(1,result.get(0).ad_insertdate,
                   result.get(0).contactImage,result.get(0).username,result.get(0).title,result.get(0).ad_description,result.get(0).contacts_category,
                   result.get(0).address,result.get(0).contactNo,result.get(0).email,result.get(0).latitude,result.get(0).longitute);
*/          contactAdsAdapter =new ContactAdsAdapter(context,result);
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
