package com.example.home_pc.myclassifiedads.classified_api;

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

    /*public ArrayList<ContactsAdObject> parseContactsDetail(JSONObject object)
    {
        ArrayList<ContactsAdObject> arrayList=new ArrayList<ContactsAdObject>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj=null;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new ContactsAdObject(jsonObj.getString("adid"), jsonObj.getString("username"),jsonObj.getString("title"),jsonObj.getString("ad_desc"),jsonObj.getString("brand"), jsonObj.getDouble("price"),jsonObj.getString("ad_stat"),jsonObj.getString("condition"),jsonObj.getString("timeused"),jsonObj.getLong("contact")));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseSalesDetail", e.getMessage());
        }
        return arrayList;
    }*/
}
