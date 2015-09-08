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
    ImageView photo;
    View v;
    Bitmap contactad_Image;
    TextView title,contactNo,mobileNo,saleType,price;

    public RealEstateMapFragment(){

    }

    @Override
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
                mMyMarkersArray.add(new RealEstatesAdObject(result.get(i).getRealestateID(),result.get(i).gettitle(),result.get(i).getSaleType(),result.get(i).getPrice(),result.get(i).getContactNo(),result.get(i).getMobileNo(),result.get(i).getLatitude(),result.get(i).getLongitude()));
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

            v = View.inflate(getActivity(),R.layout.infowindow_layout_realestate,null);
            final RealEstatesAdObject myMarker = mMarkersHashMap.get(marker);
            title = (TextView)v.findViewById(R.id.ad_title);
            contactNo = (TextView)v.findViewById(R.id.contactNo);
            mobileNo=(TextView)v.findViewById(R.id.mobileNo);
            saleType=(TextView)v.findViewById(R.id.saleType);
            price=(TextView)v.findViewById(R.id.price);
            photo=(ImageView)v.findViewById(R.id.photo);
            AsyncLoadImage getimage = new AsyncLoadImage();
            try {
                photo.setImageBitmap(getimage.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, myMarker.getRealestateID()).get());
            }catch (Exception e){}
            title.setText(myMarker.gettitle());
            mobileNo.setText(myMarker.getMobileNo());
            contactNo.setText(myMarker.getContactNo());
            saleType.setText(/*myMarker.getSaleType()*/ "for sale");
            price.setText(/*"NPR."+myMarker.getPrice()*/ "23432");

            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(v.getContext(), RealEstateViewDetail.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("realestateID", myMarker.getRealestateID());
                    bundle.putString("userID", userID);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });


            return v;
        }
    }

    protected class AsyncLoadImage extends
            AsyncTask<Integer, Void, Bitmap> {
        int pos;
        Bitmap realestatePic;
        String picURL;

        @Override
        protected Bitmap doInBackground(Integer... params) {
            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObject = api.GetRealestatePictureURL(params[0]);
                JSONParser parser = new JSONParser();
                picURL= parser.parseReturnedURL(jsonObject);
                if(picURL!=null){
                    realestatePic= ImageLoaderAPI.AzureImageDownloader(picURL);
                }
                else{
                    realestatePic=null;
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadURl", e.getMessage());
            }
            return realestatePic;
        }

    }



    public int dptopx(float dp){
        // Get the screen's density scale
        final float scale = getActivity().getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return ((int) (dp * scale + 0.5f));
    }


}

