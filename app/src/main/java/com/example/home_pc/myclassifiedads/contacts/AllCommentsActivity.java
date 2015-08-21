package com.example.home_pc.myclassifiedads.contacts;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;

import org.json.JSONObject;

import java.util.ArrayList;

public class AllCommentsActivity extends ActionBarActivity {
    RecyclerView allcomments;
    SwipeRefreshLayout mswipeRefreshLayout;
    int adid;
    String tableCategory;
    LinearLayout ll;
    TextView no_comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_comments);

        ll=(LinearLayout)findViewById(R.id.ll);
        no_comments=(TextView)findViewById(R.id.no_comments);
        allcomments=(RecyclerView)findViewById(R.id.cardList);
        allcomments.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        allcomments.setLayoutManager(llm);

        mswipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        adid=getIntent().getExtras().getInt("adid");
        tableCategory= getIntent().getExtras().getString("category");


        loadallcomments(adid, tableCategory);

        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadallcomments(adid, tableCategory);
                onItemLoadComplete();
            }
        });

    }

    public void onItemLoadComplete(){
        mswipeRefreshLayout.setRefreshing(false);
    }


    public void loadallcomments(int adid,String tableCategory){
        CommentObject commentObject=new CommentObject(adid,tableCategory);
        new AsyncLoadAllComments().execute(commentObject);
    }

    protected class AsyncLoadAllComments extends
            AsyncTask<CommentObject,Void,ArrayList<CommentObject>> {

        @Override
        protected ArrayList<CommentObject> doInBackground(CommentObject... params) {
            ArrayList<CommentObject> allcomments=new ArrayList<CommentObject>();
            RestAPI api=new RestAPI();
            try{
                JSONObject jsonObj = api.GetAllComments(params[0].adid,params[0].tableCategory);
                JSONParser parser = new JSONParser();
                allcomments = parser.parseComment(jsonObj);

            }
            catch (Exception e){
                Log.d("AsyncLoadAllComments", e.getMessage());
            }
            return allcomments;
        }

        @Override
        protected void onPostExecute(ArrayList<CommentObject> result) {
            if(result==null){
                Toast.makeText(getApplicationContext(),"NO COMMENTS FOUND",Toast.LENGTH_LONG).show();
                no_comments.setVisibility(View.VISIBLE);
            }
            else{
                CommentAdsAdapter cad=new CommentAdsAdapter(result);
                allcomments.setAdapter(cad);
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_comments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
