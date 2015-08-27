package com.example.home_pc.myclassifiedads.common_contactsnwanted;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.media.Image;
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
    Boolean first = true;
    String tableCategory,userID;
   ImageView adImage;
    View v;
    Bitmap contactad_Image;
    TextView title,contactNo,mobileNo,category;

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
        userID=getArguments().getString("userID");
        tableCategory=getArguments().getString("tableCategory");
        Toast.makeText(getActivity(),tableCategory,Toast.LENGTH_LONG).show();
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
               JSONObject jsonObj = api.GetContactsForMap(tableCategory);
                JSONParser parser = new JSONParser();
               cObject = parser.parseContactsForMap(jsonObj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadContactForMap", e.getMessage());
            }
            return cObject;
        }

        @Override
        protected void onPostExecute(ArrayList<ContactsnWantedAdObject> result) {

            for (int i = 0; i < result.size(); i++) {
                mMyMarkersArray.add(new ContactsnWantedAdObject(result.get(i).getAdid(),result.get(i).getAdImage(), result.get(i).gettitle(),result.get(i).getCategory(),result.get(i).getContactNo(),result.get(i).getMobileNo(),result.get(i).getLatitude(),result.get(i).getLongitute()));
            }
            plotMarkers(mMyMarkersArray);
        }

    }

    private void plotMarkers(ArrayList<ContactsnWantedAdObject> markers)
    {
        if(markers.size() > 0)
        {
            for (ContactsnWantedAdObject myMarker : markers)
            {

                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getLatitude(), myMarker.getLongitute()));
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
            final ContactsnWantedAdObject myMarker = mMarkersHashMap.get(marker);
            title = (TextView)v.findViewById(R.id.ad_title);
            contactNo = (TextView)v.findViewById(R.id.contactNo);
            mobileNo=(TextView)v.findViewById(R.id.mobileNo);
            category=(TextView)v.findViewById(R.id.category);
            adImage=(ImageView)v.findViewById(R.id.contact_photo);
            if(!myMarker.getAdImage().equals("-")){
                Toast.makeText(getActivity(),myMarker.getAdImage(),Toast.LENGTH_LONG).show();
                new AsyncLoadImage(myMarker).execute(myMarker.getAdImage());
                adImage.setImageBitmap(contactad_Image);
            }
            title.setText(myMarker.gettitle());
            category.setText(myMarker.getCategory());
            contactNo.setText(myMarker.getContactNo());
            mobileNo.setText(myMarker.getMobileNo());


            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                   Intent intent = new Intent(v.getContext(), ContactsnWantedViewDetail.class);
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
        ContactsnWantedAdObject myMarker;

        public AsyncLoadImage(ContactsnWantedAdObject myMarker){
            this.myMarker=myMarker;
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


        }

    }

    public int dptopx(float dp){
        // Get the screen's density scale
        final float scale = getActivity().getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return ((int) (dp * scale + 0.5f));
    }



}
