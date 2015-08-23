package com.example.home_pc.myclassifiedads.draweritemsfragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.JSONParser;
import com.example.home_pc.myclassifiedads.classified_api.RestAPI;
import com.example.home_pc.myclassifiedads.user_login.IndividualUser;

import org.json.JSONObject;

/**
 * Created by Home-PC on 7/23/2015.
 */
public class MyIndividualAccount extends Fragment {

    ImageView profilePicture;
    EditText firstName,middleName,lastName,userName,passWord,aDdress,contactNo,mobileNo,emailId,webSite;
    String userNam;

    public MyIndividualAccount() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.account_individual, container,
                false);

        userNam = getArguments().getString("UserName");

        new AsyncGetDetail(view).execute();

        return view;
    }

    protected class AsyncGetDetail extends AsyncTask<Void,Void,IndividualUser>{

        View view;
        IndividualUser individualUser;

        public AsyncGetDetail(View view) {
            this.view = view;
        }

        @Override
        protected IndividualUser doInBackground(Void... params) {

            RestAPI api = new RestAPI();

            try{
                JSONObject jsonObject=api.GetIndividualDetail(userNam);
                JSONParser parser = new JSONParser();
                individualUser = parser.getIndividualDetail(jsonObject);
            }catch (Exception e){
                System.out.println("AsyncError: "+e);
            }

            return individualUser;
        }

        @Override
        protected void onPostExecute(IndividualUser result) {

            profilePicture = (ImageView) view.findViewById(R.id.profilePicture);
            firstName = (EditText) view.findViewById(R.id.firstName);
            middleName = (EditText) view.findViewById(R.id.middleName);
            lastName = (EditText) view.findViewById(R.id.lastName);
            userName = (EditText) view.findViewById(R.id.userName);
            passWord = (EditText) view.findViewById(R.id.passWord);
            aDdress = (EditText) view.findViewById(R.id.address);
            contactNo = (EditText) view.findViewById(R.id.contactNo);
            mobileNo = (EditText) view.findViewById(R.id.mobileNo);
            emailId = (EditText) view.findViewById(R.id.emailId);
            webSite = (EditText) view.findViewById(R.id.webSite);

            passWord.setEnabled(false);

            firstName.setText(result.getFirstName());
            middleName.setText(result.getMiddleName());
            lastName.setText(result.getLastName());
            userName.setText(userNam);
            passWord.setText(result.getPassWord());
            aDdress.setText(result.getaDdress());
            contactNo.setText(result.getContactNo());
            mobileNo.setText(result.getMobileNo());
            emailId.setText(result.getEmailId());
            webSite.setText(result.getWebSite());
        }
    }

}
