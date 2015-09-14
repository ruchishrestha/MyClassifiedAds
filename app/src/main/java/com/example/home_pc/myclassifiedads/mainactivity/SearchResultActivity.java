package com.example.home_pc.myclassifiedads.mainactivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.common_contactsnwanted.ContactnWantedAdsAdapter;
import com.example.home_pc.myclassifiedads.common_contactsnwanted.ContactsnWantedAdObject;
import com.example.home_pc.myclassifiedads.jobs.JobAdsAdapter;
import com.example.home_pc.myclassifiedads.jobs.JobAdsObject;
import com.example.home_pc.myclassifiedads.realestates.RealEstateAdsAdapter;
import com.example.home_pc.myclassifiedads.realestates.RealEstatesAdObject;
import com.example.home_pc.myclassifiedads.sales.SalesAdsAdapter;
import com.example.home_pc.myclassifiedads.sales.SalesAdsObject;

import org.json.JSONObject;

import java.util.ArrayList;


public class SearchResultActivity extends ActionBarActivity {

    RecyclerView queryResult;
    ContactnWantedAdsAdapter contactAdsAdapter;
    SalesAdsAdapter salesAdsAdapter;
    RealEstateAdsAdapter realEstateAdsAdapter;
    JobAdsAdapter jobAdsAdapter;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        queryResult = (RecyclerView) findViewById(R.id.resultList);
        queryResult.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        queryResult.setLayoutManager(llm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_result, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String category="";
        String userID="";

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("ExtraSearch",0);
            category = pref.getString("AdCategory","");
            userID = pref.getString("userID","");
            System.out.println("Query: "+category+" "+userID+";"+query);

            new AsyncSearchMyQuery(category,userID).execute(query);
        }
    }

    protected class AsyncSearchMyQuery extends AsyncTask<String,Void,Void>{

        String category;
        String userID;
        ArrayList<ContactsnWantedAdObject> contactsnWantedAdObjects;
        ArrayList<SalesAdsObject> salesAdsObjects;
        ArrayList<RealEstatesAdObject> realEstatesAdObjects;
        ArrayList<JobAdsObject> jobAdsObjects;

        public AsyncSearchMyQuery(String category,String userID) {
            this.category=category;
            this.userID = userID;
        }

        @Override
        protected Void doInBackground(String... params) {
            RestAPI api = new RestAPI();
           try{
                JSONObject jsonObject=new JSONObject();
                JSONParser parser = new JSONParser();
                switch(category){
                    case "contacts":
                        contactsnWantedAdObjects = new ArrayList<>();
                        jsonObject = api.contactsSearcher(params[0]);
                        contactsnWantedAdObjects = parser.parseContactsList(jsonObject);
                        break;
                    case "wanted":
                        contactsnWantedAdObjects = new ArrayList<>();
                        jsonObject = api.wantedSearcher(params[0]);
                        contactsnWantedAdObjects = parser.parseContactsList(jsonObject);
                        break;
                    case "RealEstates":
                        realEstatesAdObjects = new ArrayList<>();
                        jsonObject = api.realEstateSearcher(params[0]);
                        realEstatesAdObjects = parser.parseRealestateList(jsonObject);
                        break;
                    case "Jobs":
                        jobAdsObjects = new ArrayList<>();
                        jsonObject = api.jobsSearcher(params[0]);
                        jobAdsObjects = parser.parseJobsList(jsonObject);
                        break;
                    default:
                        salesAdsObjects = new ArrayList<>();
                        jsonObject = api.salesSearcher(params[0]);
                        salesAdsObjects = parser.parseSalesList(jsonObject);
                        break;
                }
            }
            catch(Exception e){
                System.out.println("ERROR in Searching: " + e);
            }
            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            switch(category){
                case "contacts":
                    contactAdsAdapter = new ContactnWantedAdsAdapter(getApplicationContext(),contactsnWantedAdObjects,category,userID,0);
                    queryResult.setAdapter(contactAdsAdapter);
                    break;
                case "wanted":
                    contactAdsAdapter =new ContactnWantedAdsAdapter(getApplicationContext(),contactsnWantedAdObjects,category,userID,0);
                    queryResult.setAdapter(contactAdsAdapter);
                    break;
                case "RealEstates":
                    realEstateAdsAdapter=new RealEstateAdsAdapter(getApplicationContext(),realEstatesAdObjects,userID,0);
                    queryResult.setAdapter(realEstateAdsAdapter);
                    break;
                case "Jobs":
                    jobAdsAdapter=new JobAdsAdapter(getApplicationContext(),jobAdsObjects,userID,0);
                    queryResult.setAdapter(jobAdsAdapter);
                    break;
                default:
                    SalesAdsAdapter salesAdsAdapter=new SalesAdsAdapter(getApplicationContext(),salesAdsObjects,category,userID,0);
                    queryResult.setAdapter(salesAdsAdapter);
                    break;
            }

        }
    }
}
