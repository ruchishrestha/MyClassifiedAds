package com.example.home_pc.myclassifiedads.wanteds;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.wanteds.WantedAddActivity;

/**
 * Created by Home-PC on 7/23/2015.
 */
public class WantedFragment extends Fragment {


    public WantedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.wanted_item, container,
                false);
        setHasOptionsMenu(true);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        inflater.inflate(R.menu.menu_add_ads, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.addads){
            Intent intent = new Intent(getActivity(), WantedAddActivity.class);
            startActivity(intent);
        }
        return false;
    }
}
