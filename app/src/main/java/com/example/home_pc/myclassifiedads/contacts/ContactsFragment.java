package com.example.home_pc.myclassifiedads.contacts;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
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

import com.astuetz.PagerSlidingTabStrip;
import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Home-PC on 7/23/2015.
 */
public class ContactsFragment extends Fragment {


    private PagerSlidingTabStrip fragmentTabs;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private android.support.v4.app.Fragment selectFragment;
    private ViewPager viewPager;
   public ArrayList<ContactsAdObject> contactsAdObject;
    Context context;
    public Bundle args;


    public ContactsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.tab_fragments, container, false);
        fragmentTabs= (PagerSlidingTabStrip) v.findViewById(R.id.fragment_tabs);
        viewPager = (ViewPager) v.findViewById(R.id.fragmentPager);
        args=new Bundle();
        contactsAdObject=new ArrayList<ContactsAdObject>();
        loadContactAds();
        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(0,false);
        fragmentTabs.setViewPager(viewPager);

        return v;

    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentSelection(position);
        }

        public Fragment fragmentSelection(int fragmentPosition){


            switch (fragmentPosition) {
                case 0:
                    selectFragment= new ContactsListFragment();
                    args.putParcelableArrayList("contactList",contactsAdObject);
                    selectFragment.setArguments(args);
                    break;
                case 1:
                    selectFragment= new ContactsMapFragment();
                    break;
                default:
                    break;
            }

            return selectFragment;

        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_fragment_contactsinlist);
                case 1:
                    return getString(R.string.title_fragment_contactsinmap);
            }
            return null;
        }
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
*/
            contactsAdObject=result;
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

    public int dptopx(float dp){
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return ((int) (dp * scale + 0.5f));
    }
}