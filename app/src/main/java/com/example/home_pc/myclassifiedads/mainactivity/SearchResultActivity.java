package com.example.home_pc.myclassifiedads.mainactivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;

import org.json.JSONObject;

import java.util.ArrayList;


public class SearchResultActivity extends ActionBarActivity {

    ListView queryResult;
    ArrayAdapter<String> myAdapter;
    ArrayList<String> myQueryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        myQueryList = new ArrayList<String>();
        queryResult = (ListView) findViewById(R.id.queryResult);
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

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("ExtraSearch",0);
            category = pref.getString("AdCategory","");
            System.out.println("Query: "+category+";"+query);

            new AsyncSearchMyQuery().execute(query);
        }
    }

    protected class AsyncSearchMyQuery extends AsyncTask<String,Void,ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            RestAPI api = new RestAPI();
            ArrayList<String> queryList = new ArrayList<String>();
            try{
                JSONObject jsonObject = api.MyFullTextSearcher(params[0]);
                JSONParser parser = new JSONParser();
                queryList = parser.getMyQueryResult(jsonObject);
            }
            catch(Exception e){
                System.out.println("ERROR in Searching: " + e);
            }
            return queryList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            myQueryList = result;
            System.out.println(result.get(0));
            myAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,myQueryList);
            queryResult.setAdapter(myAdapter);
        }
    }
}
