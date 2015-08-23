package com.example.home_pc.myclassifiedads.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.home_pc.myclassifiedads.R;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

public class LocateOnMapActivity extends FragmentActivity {

    public static final int RESULT_LATLONG=1;
    private MapView locateOnMapView;
    private GoogleMap googleMap;
    Double _latitude,_longitude;

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

        _latitude = getIntent().getDoubleExtra("Latitude",0.0);
        _longitude = getIntent().getDoubleExtra("Longitude",0.0);

        googleMap = locateOnMapView.getMap();
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setMyLocationEnabled(true);

        if(_latitude != 0.0 && _longitude != 0.0)
        {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(_latitude,_longitude),16.0f));
            googleMap.addMarker( new MarkerOptions().position(new LatLng(_latitude,_longitude)).title("Current Shop Location"));
        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                _latitude=latLng.latitude;
                _longitude=latLng.longitude;
                Intent returnBack= new Intent();
                returnBack.putExtra("Latitude",_latitude);
                returnBack.putExtra("Longitude", _longitude);
                setResult(RESULT_LATLONG,returnBack);
                onBackPressed();
            }
        });
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
