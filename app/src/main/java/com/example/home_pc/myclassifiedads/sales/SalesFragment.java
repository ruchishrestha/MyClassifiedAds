package com.example.home_pc.myclassifiedads.sales;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;



import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.sales.SalesAddActivity;

import java.util.Locale;

/**
 * Created by Home-PC on 7/23/2015.
 */
public class SalesFragment extends Fragment {
    public static final String TAG = SalesFragment.class.getSimpleName();
  //  SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    android.support.v4.app.Fragment fragment;

    public static SalesFragment newInstance() {
        return new SalesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public SalesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tabbed_fragment, container,
                false);
        setHasOptionsMenu(true);
       /* mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
*/
        return view;
    }

   /* public class SectionsPagerAdapter extends FragmentPagerAdapter{
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {

            Bundle args = new Bundle();
            switch (position) {
                case 0:
                    fragment = new SalesAllAds();
                    args.putInt(SalesAllAds.ARG_SECTION_NUMBER, position + 1);
                    break;
                case 1:
                    fragment = new SalesTopAds();
                    args.putInt(SalesTopAds.ARG_SECTION_NUMBER, position + 1);
                    break;
                case 2:
                    fragment = new SalesNewAds();
                    args.putInt(SalesNewAds.ARG_SECTION_NUMBER, position + 1);
                    break;
                default:
                    break;
            }
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }

    }
*/
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        inflater.inflate(R.menu.menu_add_ads, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.addads){
            Intent intent = new Intent(getActivity(),SalesAddActivity.class);
            startActivity(intent);
        }
        return false;
    }
}
