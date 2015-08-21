package com.example.home_pc.myclassifiedads.contacts;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.home_pc.myclassifiedads.main.MainActivity;

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
    private Fragment selectFragment;
    private ViewPager viewPager;
   public ArrayList<ContactsAdObject> contactsAdObject;
    String userID;
    public Bundle args;


    public ContactsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        args=new Bundle();
        contactsAdObject=new ArrayList<ContactsAdObject>();

        View v = inflater.inflate(R.layout.tab_fragments, container, false);
        fragmentTabs= (PagerSlidingTabStrip) v.findViewById(R.id.fragment_tabs);
        userID=getArguments().getString("userID");
        viewPager = (ViewPager) v.findViewById(R.id.fragmentPager);
        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(0, false);
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

            Bundle args=new Bundle();
            args.putString("tableCategory","contacts");
            args.putString("userID",userID);
            switch (fragmentPosition) {
                case 0:
                    selectFragment= new ContactsListFragment();
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


    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        inflater.inflate(R.menu.menu_add_ads, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.addads){
            if(userID.equals("Guest")){
                popupalert();
            }else{
                Intent intent = new Intent(getActivity(), ContactsAddActivity.class);
                startActivity(intent);
            }
        }
        return false;
    }

    public void popupalert(){
        final AlertDialog alertDialog = new AlertDialog.Builder(
                getActivity()).create();
        alertDialog.setMessage("Please Login or Sign Up");
        alertDialog.setIcon(R.drawable.backward);
        alertDialog.setButton2("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        alertDialog.setButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public int dptopx(float dp){
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return ((int) (dp * scale + 0.5f));
    }
}