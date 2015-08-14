package com.example.home_pc.myclassifiedads.classified_api;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.home_pc.myclassifiedads.contacts.ContactsAdObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ruchi on 2015-08-08.
 */
public class JSONParser {
    public JSONParser()
    {
        super();
    }


    @SuppressLint("LongLogTag")
    public ArrayList<ContactsAdObject> parseContactsList(JSONObject object)
    {
        ArrayList<ContactsAdObject> arrayList=new ArrayList<ContactsAdObject>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj=null;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new ContactsAdObject(jsonObj.getInt("contactID"), jsonObj.getString("dateOnly"),jsonObj.getString("contact_photo"),jsonObj.getString("username"),jsonObj.getString("title"),jsonObj.getString("ad_description"),jsonObj.getString("contactsCategory"),jsonObj.getString("addres"),jsonObj.getString("contact"), jsonObj.getString("email"),jsonObj.getDouble("latitude"),jsonObj.getDouble("longitude")));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseContactsList", e.getMessage());
        }
        return arrayList;
    }
}
