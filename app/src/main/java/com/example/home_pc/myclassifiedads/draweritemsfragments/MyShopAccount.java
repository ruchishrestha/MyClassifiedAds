package com.example.home_pc.myclassifiedads.draweritemsfragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.mainactivity.LocateOnMapActivity;
import com.example.home_pc.myclassifiedads.user_login.ShopUser;

import org.json.JSONObject;

/**
 * Created by Home-PC on 8/19/2015.
 */
public class MyShopAccount extends Fragment {

    private final int REQUEST_LATLONG=2;
    ImageView shopPicture;
    EditText shopName,owner,panNo,userName,passWord,aDdress,contactNo,mobileNo,emailId,webSite;
    TextView locateOnMap;
    Double _latitude,_longitude;
    String userNam;

    public MyShopAccount() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.account_shop, container,
                false);

        userNam = getArguments().getString("userID");

        locateOnMap = (TextView) view.findViewById(R.id.locateOnMap);

        locateOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locateOnMap= new Intent(getActivity(), LocateOnMapActivity.class);
                locateOnMap.putExtra("Latitude",_latitude);
                locateOnMap.putExtra("Longitude",_longitude);
                startActivityForResult(locateOnMap,REQUEST_LATLONG);
            }
        });

        new AsyncGetDetail(view).execute();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("RESULT");
        if (requestCode==REQUEST_LATLONG){
            if(resultCode== LocateOnMapActivity.RESULT_LATLONG){
                _latitude=data.getDoubleExtra("Latitude",_latitude);
                _longitude=data.getDoubleExtra("Longitude",_longitude);
                Toast.makeText(getActivity(),""+_latitude+" "+_longitude,Toast.LENGTH_LONG).show();
            }
        }
    }



    protected class AsyncGetDetail extends AsyncTask<Void,Void,ShopUser> {

        View view;
        ShopUser shopUser;

        public AsyncGetDetail(View view) {
            this.view = view;
        }

        @Override
        protected ShopUser doInBackground(Void... params) {

            RestAPI api = new RestAPI();

            try{
                JSONObject jsonObject=api.GetShopDetail(userNam);
                JSONParser parser = new JSONParser();
                shopUser = parser.getShopDetail(jsonObject);
            }catch (Exception e){
                System.out.println("AsyncError: "+e);
            }

            return shopUser;
        }

        @Override
        protected void onPostExecute(ShopUser result) {
            shopPicture = (ImageView) view.findViewById(R.id.shopPic);
            shopName = (EditText) view.findViewById(R.id.shopName);
            owner = (EditText) view.findViewById(R.id.ownerName);
            panNo = (EditText) view.findViewById(R.id.panNo);
            userName = (EditText) view.findViewById(R.id.userName);
            passWord = (EditText) view.findViewById(R.id.passWord);
            aDdress = (EditText) view.findViewById(R.id.address);
            contactNo = (EditText) view.findViewById(R.id.contactNo);
            mobileNo = (EditText) view.findViewById(R.id.mobileNo);
            emailId = (EditText) view.findViewById(R.id.emailId);
            webSite = (EditText) view.findViewById(R.id.webSite);

            passWord.setEnabled(false);

            shopName.setText(result.getShopName());
            owner.setText(result.getOwner());
            userName.setText(userNam);
            passWord.setText(result.getPassWord());
            panNo.setText(result.getPanNo());
            aDdress.setText(result.getaDdress());
            contactNo.setText(result.getContactNo());
            mobileNo.setText(result.getMobileNo());
            emailId.setText(result.getEmailId());
            webSite.setText(result.getWebSite());
            _latitude = result.getLatitude();
            _longitude = result.getLongitude();
        }
    }
}
