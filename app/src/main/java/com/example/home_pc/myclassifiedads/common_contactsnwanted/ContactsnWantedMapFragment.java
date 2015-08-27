package com.example.home_pc.myclassifiedads.common_contactsnwanted;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ruchi on 2015-08-12.
 */
public class ContactsnWantedMapFragment extends Fragment {

    MapView mMapView;
    private ArrayList<ContactsnWantedAdObject> mMyMarkersArray = new ArrayList<ContactsnWantedAdObject>();
    private HashMap<Marker, ContactsnWantedAdObject> mMarkersHashMap;
    private GoogleMap googleMap;
    Marker PointHere;
    MarkerOptions marker;
    LatLng loc;
    Boolean first = true;
    String tableCategory,userID;

    public ContactsnWantedMapFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        View v = inflater.inflate(R.layout.activity_locate_on_map, container,
                false);
        setHasOptionsMenu(true);
        mMapView = (MapView) v.findViewById(R.id.locateOnMapView);
        mMapView.onCreate(savedInstanceState);
        tableCategory=getArguments().getString("tableCategory");
        userID=getArguments().getString("userID");
        mMarkersHashMap = new HashMap<Marker, ContactsnWantedAdObject>();
        first = true;
        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        googleMap = mMapView.getMap();
        googleMap.setMyLocationEnabled(true);
        new AsyncLoadContactAds().execute();
return v;
    }

    protected class AsyncLoadContactAds extends
            AsyncTask<Void, Void, ArrayList<ContactsnWantedAdObject>> {
        ArrayList<ContactsnWantedAdObject> cObject = null;

        @Override
        protected ArrayList<ContactsnWantedAdObject> doInBackground(Void... params) {

            RestAPI api = new RestAPI();
            try {
                cObject = new ArrayList<ContactsnWantedAdObject>();
                JSONObject jsonObj = api.GetContactsList(tableCategory);
                JSONParser parser = new JSONParser();
                cObject = parser.parseContactsList(jsonObj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadContactList", e.getMessage());
            }
            return cObject;
        }

    }

}
