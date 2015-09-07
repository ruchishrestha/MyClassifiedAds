package com.example.home_pc.myclassifiedads.realestates;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.ImageLoaderAPI;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ruchi on 2015-08-20.
 */
public class RealEstateMapFragment extends Fragment {
    MapView mMapView;
    private ArrayList<RealEstatesAdObject> mMyMarkersArray = new ArrayList<RealEstatesAdObject>();
    private HashMap<Marker, RealEstatesAdObject> mMarkersHashMap;
    private GoogleMap googleMap;
    Marker PointHere;
    MarkerOptions marker;
    Boolean first = true;
    String tableCategory,userID;
    ImageView adImage;
    View v;
    Bitmap contactad_Image;
    TextView title,contactNo,mobileNo,category;

    public RealEstateMapFragment(){

    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        View v = inflater.inflate(R.layout.activity_locate_on_map, container,
                false);
        //setHasOptionsMenu(true);
        mMapView = (MapView) v.findViewById(R.id.locateOnMapView);
        mMapView.onCreate(savedInstanceState);
        userID=getArguments().getString("userID");
        tableCategory=getArguments().getString("tableCategory");
        mMarkersHashMap = new HashMap<Marker, RealEstatesAdObject>();
        first = true;
        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        googleMap = mMapView.getMap();
        googleMap.setMyLocationEnabled(true);
        new AsyncLoadContactAds().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return v;
    }

    protected class AsyncLoadContactAds extends
            AsyncTask<Void, Void, ArrayList<RealEstatesAdObject>> {
        ArrayList<RealEstatesAdObject> realEstatesAdObjects = null;
        @Override
        protected ArrayList<RealEstatesAdObject> doInBackground(Void... params) {

            RestAPI api = new RestAPI();
            try {
                realEstatesAdObjects = new ArrayList<RealEstatesAdObject>();
                JSONObject jsonObj = api.GetRealestateForMap();
                JSONParser parser = new JSONParser();
                realEstatesAdObjects = parser.parseRealestateForMap(jsonObj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadContactForMap", e.getMessage());
            }
            return realEstatesAdObjects;
        }



        @Override
        protected void onPostExecute(ArrayList<RealEstatesAdObject> result) {

            for (int i = 0; i < result.size(); i++) {
                mMyMarkersArray.add(new RealEstatesAdObject(result.get(i).getAdid(),result.get(i).getAdImage(), result.get(i).gettitle(),result.get(i).getCategory(),result.get(i).getContactNo(),result.get(i).getMobileNo(),result.get(i).getLatitude(),result.get(i).getLongitute()));
            }
            plotMarkers(mMyMarkersArray);
        }

    }

    private void plotMarkers(ArrayList<RealEstatesAdObject> markers)
    {
        if(markers.size() > 0)
        {
            for (RealEstatesAdObject myMarker : markers)
            {

                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getLatitude(), myMarker.getLongitude()));
                Marker currentMarker = googleMap.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);
                googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
            }
        }
    }

    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
    {

        public MarkerInfoWindowAdapter()
        {
        }

        @Override
        public View getInfoWindow(Marker marker)
        {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker)
        {

            v = View.inflate(getActivity(),R.layout.infowindow_layout_contacts,null);
            final RealEstatesAdObject myMarker = mMarkersHashMap.get(marker);
            title = (TextView)v.findViewById(R.id.ad_title);
            contactNo = (TextView)v.findViewById(R.id.contactNo);
            mobileNo=(TextView)v.findViewById(R.id.mobileNo);
            category=(TextView)v.findViewById(R.id.category);
            adImage=(ImageView)v.findViewById(R.id.contact_photo);
            if(myMarker.getAdImage()!=null){
                //  contactad_Image=Bitmap.createScaledBitmap(myMarker.image, dptopx(100), dptopx(100), true);
                //  Toast.makeText(getActivity(),myMarker.getAdImage(),Toast.LENGTH_LONG).show();
                new AsyncLoadImage(marker).execute(myMarker.getAdImage());
                adImage.setImageBitmap(contactad_Image);
            }
            title.setText(myMarker.gettitle());
            category.setText(myMarker.getCategory());
            contactNo.setText(myMarker.getContactNo());
            mobileNo.setText(myMarker.getMobileNo());

            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(v.getContext(), RealEstateViewDetail.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("adid",myMarker.getAdid());
                    bundle.putString("userID", userID);
                    bundle.putString("tableCategory", tableCategory);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });


            return v;
        }
    }

    protected class AsyncLoadImage extends
            AsyncTask<String, Void, Bitmap> {
        ProgressDialog progressDialog;
        Marker markerShowingInfoWindow;

        public AsyncLoadImage(Marker marker){
            this.markerShowingInfoWindow=marker;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                contactad_Image = ImageLoaderAPI.AzureImageDownloader(params[0]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadImage", e.getMessage());
            }
            return contactad_Image;
        }

        @Override protected void onPreExecute(){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap result){
            contactad_Image=Bitmap.createScaledBitmap(result, dptopx(100), dptopx(100), true);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            if (markerShowingInfoWindow != null && markerShowingInfoWindow.isInfoWindowShown()) {
                markerShowingInfoWindow.hideInfoWindow();
                markerShowingInfoWindow.showInfoWindow();
            }
        }

    }

    public int dptopx(float dp){
        // Get the screen's density scale
        final float scale = getActivity().getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return ((int) (dp * scale + 0.5f));
    }
*/


}

