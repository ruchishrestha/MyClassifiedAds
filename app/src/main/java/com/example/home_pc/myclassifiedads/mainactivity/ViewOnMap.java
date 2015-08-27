package com.example.home_pc.myclassifiedads.mainactivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.home_pc.myclassifiedads.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ruchi on 2015-08-26.
 */
public class ViewOnMap extends FragmentActivity {
    public static final int RESULT_LATLONG=1;
    private MapView locateOnMapView;
    private GoogleMap googleMap;
    Double latitute,longitute;
    String addres;
    LatLng position;
    List<Address> geocodeMatches = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_on_map);
        locateOnMapView = (MapView) findViewById(R.id.locateOnMapView);
        locateOnMapView.onCreate(savedInstanceState);
        locateOnMapView.onResume();

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        googleMap = locateOnMapView.getMap();
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setMyLocationEnabled(true);
        latitute=getIntent().getExtras().getDouble("latitute");
        longitute=getIntent().getExtras().getDouble("longitute");
        addres=getIntent().getExtras().getString("addres");

        if(latitute==0.0 && longitute==0.0){
            try {
                geocodeMatches =
                        new Geocoder(this).getFromLocationName(
                                addres+",Nepal", 1);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (!geocodeMatches.isEmpty()) {
                latitute = geocodeMatches.get(0).getLatitude();
                longitute = geocodeMatches.get(0).getLongitude();
                position = new LatLng(latitute, longitute);
            }

        }else{
            position = new LatLng(latitute,longitute);
        }

        MarkerOptions options = new MarkerOptions();

        // Setting position for the MarkerOptions
        options.position(position);
        googleMap.addMarker(options);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,16.0f));


    }

    @Override
    public void onResume() {
        super.onResume();
        locateOnMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        locateOnMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locateOnMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        locateOnMapView.onLowMemory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_locate_on_map, menu);
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
