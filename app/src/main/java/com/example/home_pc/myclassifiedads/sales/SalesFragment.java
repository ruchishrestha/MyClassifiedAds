package com.example.home_pc.myclassifiedads.sales;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.astuetz.PagerSlidingTabStrip;
import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.sales.SalesAddActivity;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Home-PC on 7/23/2015.
 */
public class SalesFragment extends Fragment {
    private PagerSlidingTabStrip fragmentTabs;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private Fragment selectFragment;
    private ViewPager viewPager;
    public ArrayList<SalesAdsObject> salesAdsObjects;
    String userID;
    public Bundle args;

    String salesCategory;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public SalesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragments, container,
                false);
        setHasOptionsMenu(true);

        userID = getArguments().getString("userID");
        salesCategory = getArguments().getString("salesCategory");
        fragmentTabs = (PagerSlidingTabStrip) view.findViewById(R.id.fragment_tabs);
        viewPager = (ViewPager) view.findViewById(R.id.fragmentPager);
        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(0, false);
        fragmentTabs.setViewPager(viewPager);

        return view;
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
            args.putString("salesCategory",salesCategory);
            args.putString("userID",userID);
            switch (fragmentPosition) {
                case 0:
                    selectFragment= new TopAdsFragment();
                    selectFragment.setArguments(args);
                    break;
                case 1:
                    selectFragment= new NewAdsFragment();
                    selectFragment.setArguments(args);
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
                    return getString(R.string.title_fragment_topads);
                case 1:
                    return getString(R.string.title_fragment_newads);
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
            Intent intent = new Intent(getActivity(),SalesAddActivity.class);
            intent.putExtra("UserName",userID);
            intent.putExtra("SalesCategory",salesCategory);
            startActivity(intent);
        }
        return false;
    }
}
