package com.example.home_pc.myclassifiedads.draweritemsfragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.ImageLoaderAPI;
import com.example.home_pc.myclassifiedads.gcm_service.QuickstartPreferences;
import com.example.home_pc.myclassifiedads.gcm_service.RegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GcmPubSub;

import java.io.IOException;

/**
 * Created by Home-PC on 7/23/2015.
 */
public class SettingsFragment extends Fragment {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "Settings";
    Button registerDevice;
    CheckBox contacts,wanted,sales,realEstate,jobs;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    boolean sentToken;
    SharedPreferences sharedPreferences;

    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.account_settings, container,
                false);
        sharedPreferences =getActivity().getSharedPreferences("UserPreference",0);
        sentToken = sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);

        registerDevice = (Button) view.findViewById(R.id.registerDevice);
        contacts = (CheckBox) view.findViewById(R.id.contactsBox);
        wanted = (CheckBox) view.findViewById(R.id.wantedBox);
        sales = (CheckBox) view.findViewById(R.id.salesBox);
        realEstate = (CheckBox) view.findViewById(R.id.realEstateBox);
        jobs = (CheckBox) view.findViewById(R.id.jobsBox);
        mRegistrationProgressBar = (ProgressBar) view.findViewById(R.id.registrationProgressBar);
        mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
        contacts.setChecked(sharedPreferences.getBoolean("SubscribeContacts", false));
        wanted.setChecked(sharedPreferences.getBoolean("SubscribeWanted",false));
        sales.setChecked(sharedPreferences.getBoolean("SubscribeSales",false));
        realEstate.setChecked(sharedPreferences.getBoolean("SubscribeRealEstate",false));
        jobs.setChecked(sharedPreferences.getBoolean("SubscribeJobs",false));

        registerDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegistrationProgressBar.setVisibility(ProgressBar.VISIBLE);
                sharedPreferences = getActivity().getSharedPreferences("UserPreference",0);
                sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER,false).apply();
                if (checkPlayServices()) {
                    // Start IntentService to register this application with GCM.
                    Intent intent = new Intent(getActivity(), RegistrationIntentService.class);

                    getActivity().startService(intent);
                }
            }
        });


        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscriber(contacts.isChecked(),contacts.getText().toString());
                sharedPreferences = getActivity().getSharedPreferences("UserPreference",0);
                sharedPreferences.edit().putBoolean("SubscribeContacts",contacts.isChecked()).apply();
            }
        });
        wanted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscriber(wanted.isChecked(),wanted.getText().toString());
                sharedPreferences = getActivity().getSharedPreferences("UserPreference",0);
                sharedPreferences.edit().putBoolean("SubscribeWanted",wanted.isChecked()).apply();
            }
        });
        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscriber(sales.isChecked(),sales.getText().toString());
                sharedPreferences = getActivity().getSharedPreferences("UserPreference",0);
                sharedPreferences.edit().putBoolean("SubscribeSales",sales.isChecked()).apply();
            }
        });
        realEstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscriber(realEstate.isChecked(),realEstate.getText().toString());
                sharedPreferences = getActivity().getSharedPreferences("UserPreference",0);
                sharedPreferences.edit().putBoolean("SubscribeRealEstate",realEstate.isChecked()).apply();
            }
        });
        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscriber(jobs.isChecked(),jobs.getText().toString());
                sharedPreferences = getActivity().getSharedPreferences("UserPreference",0);
                sharedPreferences.edit().putBoolean("SubscribeJobs",jobs.isChecked()).apply();
            }
        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                sharedPreferences =getActivity().getSharedPreferences("UserPreference",0);
                sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Toast.makeText(getActivity(),"Device Registered",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),getString(R.string.token_error_message),Toast.LENGTH_SHORT).show();
                }
            }
        };

        return view;
    }

    public void subscriber(boolean ticked,String topic){
        sharedPreferences = getActivity().getSharedPreferences("UserPreference",0);
        if (sentToken) {
            sharedPreferences.edit().putBoolean(QuickstartPreferences.TOGGLE,ticked).apply();
            sharedPreferences.edit().putString(QuickstartPreferences.MYTOPIC, topic).apply();
            if (checkPlayServices()) {
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(getActivity(), RegistrationIntentService.class);
                getActivity().startService(intent);
            }
        }
        else {
            Toast.makeText(getActivity(), "Register Device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(getActivity(), resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

}
