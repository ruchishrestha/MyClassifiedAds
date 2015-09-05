package com.example.home_pc.myclassifiedads.draweritemsfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.home_pc.myclassifiedads.myads.MyContactsFragment;
import com.example.home_pc.myclassifiedads.myads.MyJobsFragment;
import com.example.home_pc.myclassifiedads.myads.MyRealestateFragment;
import com.example.home_pc.myclassifiedads.myads.MySalesFragment;
import com.example.home_pc.myclassifiedads.myads.MyWantedFragment;

import java.util.Locale;

/**
 * Created by Home-PC on 7/23/2015.
 */
public class MyAdsFragment extends Fragment {
    private PagerSlidingTabStrip fragmentTabs;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private Fragment selectFragment;
    private ViewPager viewPager;
    String userID,userCategory;


    public MyAdsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab_fragments, container, false);
        setHasOptionsMenu(true);
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
            args.putString("userID",userID);
            args.putString("userCategory",userCategory);
            switch (fragmentPosition) {
                case 0:
                    selectFragment= new MyContactsFragment();
                    args.putString("tableCategory","contacts");
                    selectFragment.setArguments(args);
                    break;
                case 1:
                    selectFragment= new MyContactsFragment();
                    args.putString("tableCategory","wanted");
                    selectFragment.setArguments(args);
                    break;
                case 2:
                    selectFragment= new MySalesFragment();
                    selectFragment.setArguments(args);
                    break;
                case 3:
                    selectFragment= new MyRealestateFragment();
                    selectFragment.setArguments(args);
                    break;
                case 4:
                    selectFragment= new MyJobsFragment();
                    selectFragment.setArguments(args);
                    break;
                default:
                    break;
            }

            return selectFragment;

        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_fragment_mycontacts);
                case 1:
                    return getString(R.string.title_fragment_mywanted);
                case 2:
                    return getString(R.string.title_fragment_mysales);
                case 3:
                    return getString(R.string.title_fragment_myrealestate);
                case 4:
                    return getString(R.string.title_fragment_myjobs);
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

        return false;
    }
}
