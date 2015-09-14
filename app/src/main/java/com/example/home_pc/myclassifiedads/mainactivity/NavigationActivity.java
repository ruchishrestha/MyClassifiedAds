package com.example.home_pc.myclassifiedads.mainactivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.common_contactsnwanted.ContactsFragment;
import com.example.home_pc.myclassifiedads.draweritemsfragments.AboutFragment;
import com.example.home_pc.myclassifiedads.draweritemsfragments.HelpFragment;
import com.example.home_pc.myclassifiedads.draweritemsfragments.MyAdsFragment;
import com.example.home_pc.myclassifiedads.draweritemsfragments.MyIndividualAccount;
import com.example.home_pc.myclassifiedads.draweritemsfragments.MyOrganizationAccount;
import com.example.home_pc.myclassifiedads.draweritemsfragments.MyShopAccount;
import com.example.home_pc.myclassifiedads.draweritemsfragments.SettingsFragment;
import com.example.home_pc.myclassifiedads.jobs.JobFragment;
import com.example.home_pc.myclassifiedads.mainactivity.CustomDrawerAdapter;
import com.example.home_pc.myclassifiedads.mainactivity.DrawerItem;
import com.example.home_pc.myclassifiedads.realestates.RealEstateFragment;
import com.example.home_pc.myclassifiedads.recommendation.RecommendedAdsFragment;
import com.example.home_pc.myclassifiedads.sales.SalesFragment;
import com.example.home_pc.myclassifiedads.common_contactsnwanted.WantedFragment;

import java.util.ArrayList;
import java.util.List;


public class NavigationActivity extends ActionBarActivity {

    private DrawerLayout navigationDrawerLayout;
    private ListView navDrawerUpperItemListView,salesItemListView,navDrawerLowerItemListView;
    private ActionBarDrawerToggle navDrawerToggle;
    private CharSequence navDrawerTitle,selectedItemTitle;
    private ImageView profilePicImageView,salesIconImageView;
    private TextView userNameTextView,userIdTextView,salesTitleTextView;
    private ToggleButton navDrawerListToggleButton,salesListToggleButton;
    private Boolean navDrawerListToggle;

    private FrameLayout salesListLayout;
    private LinearLayout drawerLinearLayout,salesMainLayout;

    private CustomDrawerAdapter upperDrawerListAdapter,salesListAdapter,lowerDrawerListAdapter;
    private List<DrawerItem> upperDrawerListItems,salesListItems,lowerDrawerListItems;
    private  String userID, userCategory, fullUserName, pictureURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout);

        // Initializing all view items
        selectedItemTitle = navDrawerTitle = getTitle();

        SharedPreferences pref = this.getSharedPreferences("UserProfile", 0);
        userCategory = pref.getString("userCategory","Guest");
        fullUserName = pref.getString("FullUserName","Welcome To Classified Ads");
        userID = pref.getString("userID","Guest");
        pictureURL = pref.getString("PictureURL","-");
        int selection = pref.getInt("Selection",2);

        navigationDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLinearLayout= (LinearLayout) findViewById(R.id.navigationDrawer);
        profilePicImageView= (ImageView) findViewById(R.id.profilePicture);
        userNameTextView=(TextView) findViewById(R.id.userName);
        userIdTextView=(TextView) findViewById(R.id.userId);
        navDrawerListToggleButton=(ToggleButton) findViewById(R.id.navigationToggle);
        navDrawerUpperItemListView = (ListView) findViewById(R.id.upperDrawerList);
        salesMainLayout= (LinearLayout) findViewById(R.id.salesLayout);
        salesListLayout = (FrameLayout) findViewById(R.id.salesItem);
        salesIconImageView= (ImageView) findViewById(R.id.salesImage);
        salesTitleTextView=(TextView) findViewById(R.id.sales);
        salesListToggleButton=(ToggleButton) findViewById(R.id.salesToggle);
        salesItemListView = (ListView) findViewById(R.id.salesList);
        navDrawerLowerItemListView = (ListView) findViewById(R.id.lowerDrawerList);

        navigationDrawerLayout.setDrawerShadow(R.mipmap.drawer_shadow_9, GravityCompat.START); //NavigationDrawer Shadow

        navDrawerListToggle = false;

        //INPUT from user database
        if(!pictureURL.equals("-")){System.out.println(pictureURL);}
        profilePicImageView.setImageResource(R.drawable.default_pic);
        userNameTextView.setText(fullUserName);
        userIdTextView.setText(userID);

        // Add Drawer Items to dataList and set the drawer
        addDataToList(navDrawerListToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navDrawerToggle = new ActionBarDrawerToggle(this, navigationDrawerLayout, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(selectedItemTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(navDrawerTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };

        navigationDrawerLayout.setDrawerListener(navDrawerToggle);

        navDrawerListToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navDrawerListToggleButtonclick(v);
            }
        });

        if(savedInstanceState==null) {
            selectNavDrawerItem(selection, selection, upperDrawerListItems);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        // Associate searchable configuration with the SearchView
        /*SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("ExtraSearch",0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("AdCategory",selectedItemTitle.toString());
                editor.apply();
            }
        });*/
        return true;

    }

    public void navDrawerListToggleButtonclick(View v){

        navDrawerListToggle = ((ToggleButton) v).isChecked();
        addDataToList(navDrawerListToggle);

    }

    public void addDataToList(Boolean toggle){

        upperDrawerListItems = new ArrayList<DrawerItem>();
        salesListItems = new ArrayList<DrawerItem>();
        lowerDrawerListItems = new ArrayList<DrawerItem>();

        if(!toggle){

            upperDrawerListItems.add(new DrawerItem("Recommended Ads", R.mipmap.ic_action_good,3));
            upperDrawerListItems.add(new DrawerItem("Categories")); // adding a header to the list
            upperDrawerListItems.add(new DrawerItem("Contacts", R.mipmap.ic_action_email,3));
            upperDrawerListItems.add(new DrawerItem("Wanted", R.mipmap.ic_action_good,3));

            salesIconImageView.setImageResource(R.mipmap.ic_action_import_export);
            salesTitleTextView.setText("Sales");

            salesListItems.add(new DrawerItem("PCs & Laptop", R.mipmap.ic_action_email, 2));
            salesListItems.add(new DrawerItem("Mobiles", R.mipmap.ic_action_good,2));
            salesListItems.add(new DrawerItem("Accessories", R.mipmap.ic_action_gamepad,2));
            salesListItems.add(new DrawerItem("Vehicles", R.mipmap.ic_action_gamepad,2));

            lowerDrawerListItems.add(new DrawerItem("Real Estate", R.mipmap.ic_action_search,3));
            lowerDrawerListItems.add(new DrawerItem("Job", R.mipmap.ic_action_cloud,3));
            lowerDrawerListItems.add(new DrawerItem("Others")); // adding a header to the list
            lowerDrawerListItems.add(new DrawerItem("About", R.mipmap.ic_action_about,1));
            lowerDrawerListItems.add(new DrawerItem("Help", R.mipmap.ic_action_help,1));

            upperDrawerListAdapter = new CustomDrawerAdapter(this, R.layout.drawer_item_layout, upperDrawerListItems);
            navDrawerUpperItemListView.setAdapter(upperDrawerListAdapter);
            navDrawerUpperItemListView.setOnItemClickListener(new DrawerItemClickListener(0));

            lowerDrawerListAdapter = new CustomDrawerAdapter(this, R.layout.drawer_item_layout, lowerDrawerListItems);
            navDrawerLowerItemListView.setAdapter(lowerDrawerListAdapter);
            navDrawerLowerItemListView.setOnItemClickListener(new DrawerItemClickListener(2));

            salesListToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    salesListToggleButtonclick(v);
                }
            });

            navDrawerUpperItemListView.setVisibility(View.VISIBLE);
            salesMainLayout.setVisibility(View.VISIBLE);
            salesListLayout.setVisibility(View.VISIBLE);
            salesListToggleButton.setVisibility(View.VISIBLE);
        }
        else{
            lowerDrawerListItems.add(new DrawerItem("Profile"));
            lowerDrawerListItems.add(new DrawerItem("My Account", R.mipmap.ic_action_group, 3));
            lowerDrawerListItems.add(new DrawerItem("My Ads", R.mipmap.ic_action_email, 3));
            lowerDrawerListItems.add(new DrawerItem("Settings", R.mipmap.ic_action_settings, 3));


            navDrawerUpperItemListView.setVisibility(View.GONE);
            salesMainLayout.setVisibility(View.GONE);
            salesListLayout.setVisibility(View.INVISIBLE);
            salesListToggleButton.setVisibility(View.GONE);

            lowerDrawerListAdapter = new CustomDrawerAdapter(this, R.layout.drawer_item_layout, lowerDrawerListItems);
            navDrawerLowerItemListView.setAdapter(lowerDrawerListAdapter);
            navDrawerLowerItemListView.setOnItemClickListener(new DrawerItemClickListener(3));
        }

    }

    public void salesListToggleButtonclick(View v){
        Boolean salesListToggle = ((ToggleButton) v).isChecked();
        if(salesListToggle){
            salesListAdapter = new CustomDrawerAdapter(this, R.layout.drawer_item_layout,
                    salesListItems);
            salesItemListView.setAdapter(salesListAdapter);
            salesItemListView.setOnItemClickListener(new DrawerItemClickListener(1));
        }
        else{
            salesItemListView.setAdapter(null);
        }
    }

    public void selectNavDrawerItem(int navDrawerItemPosition,int listItemPosition,List<DrawerItem> datalist){
        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (navDrawerItemPosition) {

            case 0:
                fragment = new RecommendedAdsFragment();
                break;
            case 2:
                fragment = new ContactsFragment();
                break;
            case 3:
                fragment = new WantedFragment();
                break;
            case 4:
                fragment = new SalesFragment();
                args.putString("SalesCategory",datalist.get(listItemPosition).getItemName());
                break;
            case 5:
                fragment = new RealEstateFragment();
                break;
            case 6:
                fragment = new JobFragment();
                break;
            case 8:
                fragment = new AboutFragment();
                break;
            case 9:
                fragment = new HelpFragment();
                break;
            case 11:
                switch (userCategory){
                    case "individual":fragment = new MyIndividualAccount();
                        break;
                    case "shop": fragment = new MyShopAccount();
                        break;
                    case "organization": fragment = new MyOrganizationAccount();
                        break;
                }

                break;
            case 12:
                fragment = new MyAdsFragment();
                break;
            case 13:
                fragment = new SettingsFragment();
                break;

            default:
                break;
        }

        args.putString("userID",userID);
        fragment.setArguments(args);
        FragmentManager frgManager = getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        navDrawerUpperItemListView.setItemChecked(listItemPosition, true);
        setTitle(datalist.get(listItemPosition).getItemName());
        navigationDrawerLayout.closeDrawer(drawerLinearLayout);
    }


    @Override
    public void setTitle(CharSequence title) {
        selectedItemTitle = title;
        getSupportActionBar().setTitle(selectedItemTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        navDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        navDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (navDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        int selectMethod;

        public DrawerItemClickListener(int selectMethod){
            this.selectMethod=selectMethod;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch(selectMethod){
                case 0:
                    if (upperDrawerListItems.get(position).getTitle() == null) {
                        selectNavDrawerItem(position, position, upperDrawerListItems);
                    }
                    break;
                case 1:
                    {
                        selectNavDrawerItem(4, position, salesListItems);
                    }
                    break;
                case 2:
                    if (lowerDrawerListItems.get(position).getTitle() == null) {
                        selectNavDrawerItem(position+5,position,lowerDrawerListItems);
                    }
                    break;
                case 3:
                    if (lowerDrawerListItems.get(position).getTitle() == null) {
                        selectNavDrawerItem(position+10,position,lowerDrawerListItems);
                    }
                    break;
                default:break;

            }
        }
    }
}
