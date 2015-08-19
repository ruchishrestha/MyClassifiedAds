package com.example.home_pc.myclassifiedads.classified_api;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.home_pc.myclassifiedads.contacts.CommentObject;
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
                arrayList.add(new ContactsAdObject(jsonObj.getInt("adid"), jsonObj.getString("photo"),jsonObj.getString("username"),jsonObj.getString("title"),jsonObj.getString("addres"),jsonObj.getString("contact")));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseContactsList", e.getMessage());
        }
        return arrayList;
    }

    @SuppressLint("LongLogTag")
    public ArrayList<ContactsAdObject> parseContactDetails(JSONObject object)
    {
        ArrayList<ContactsAdObject> arrayList=new ArrayList<ContactsAdObject>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj=null;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new ContactsAdObject(jsonObj.getInt("adid"),jsonObj.getString("dateOnly"),jsonObj.getString("username"),jsonObj.getString("title"),jsonObj.getString("ad_description"),jsonObj.getString("Category"),jsonObj.getString("contact"),jsonObj.getString("addres"),jsonObj.getString("email"),jsonObj.getDouble("latitude"),jsonObj.getDouble("longitude"),jsonObj.getString("photo")));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseContactDetails", e.getMessage());
        }
        return arrayList;
    }

    @SuppressLint("LongLogTag")
    public ArrayList<CommentObject> parseComment(JSONObject object)
    {
        ArrayList<CommentObject> arrayList=new ArrayList<CommentObject>();
        try {
            JSONArray jsonArray = object.getJSONArray("Value");
            JSONObject jsonObj = null;
            if (jsonArray.length() == 0) {
                arrayList = null;
            } else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObj = jsonArray.getJSONObject(i);
                    arrayList.add(new CommentObject(jsonObj.getString("postedDate"), jsonObj.getString("username"), jsonObj.getString("commentText")));
                }
                }
            }catch(JSONException e){
                // TODO Auto-generated catch block
                Log.d("JSONParser => parseMyComment", e.getMessage());
            }
        return arrayList;
    }

    @SuppressLint("LongLogTag")
    public Integer parseReturnedValue(JSONObject object)
    {
        Integer a=0;
        try {
           a=object.getInt("Value");
        }catch(JSONException e){
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseReturnedValue", e.getMessage());
        }
        return a;
    }


}
