package com.example.home_pc.myclassifiedads.classified_api;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.home_pc.myclassifiedads.contacts.CommentObject;
import com.example.home_pc.myclassifiedads.contacts.ContactsAdObject;
import com.example.home_pc.myclassifiedads.jobs.JobAdsObject;
import com.example.home_pc.myclassifiedads.realestates.RealEstatesAdObject;

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
        ArrayList<ContactsAdObject> arrayList=new ArrayList<>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj;
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
        ArrayList<ContactsAdObject> arrayList=new ArrayList<>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj;
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
        ArrayList<CommentObject> arrayList=new ArrayList<>();
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
    public Boolean parseReturnedValue(JSONObject object)
    {
        Boolean flag=false;
        try {
           flag=object.getBoolean("Value");
        }catch(JSONException e){
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseReturnedValue", e.getMessage());
        }
        return flag;
    }

    @SuppressLint("LongLogTag")
    public ArrayList<RealEstatesAdObject> parseRealestateList(JSONObject object)
    {
        ArrayList<RealEstatesAdObject> arrayList=new ArrayList<>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new RealEstatesAdObject(jsonObj.getInt("realestateID"),jsonObj.getString("title"),jsonObj.getDouble("price"),jsonObj.getString("saleType"),jsonObj.getString("addres"),jsonObj.getString("contact"),jsonObj.getString("username")));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseRealestateList", e.getMessage());
        }
        return arrayList;
    }

    @SuppressLint("LongLogTag")
    public ArrayList<RealEstatesAdObject> parseRealestateDetails(JSONObject object)
    {
        ArrayList<RealEstatesAdObject> arrayList=new ArrayList<>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new RealEstatesAdObject(jsonObj.getInt("realestateID"),jsonObj.getString("dateOnly"),jsonObj.getString("username"),jsonObj.getString("title"),jsonObj.getString("ad_description"),jsonObj.getString("houseNo"),jsonObj.getString("propertyType"),jsonObj.getString("saleType"),jsonObj.getDouble("price"),jsonObj.getString("addres"),jsonObj.getString("contact"),jsonObj.getString("mobile"),jsonObj.getDouble("latitude"),jsonObj.getDouble("longitude")));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseContactDetails", e.getMessage());
        }
        return arrayList;
    }

    @SuppressLint("LongLogTag")
    public ArrayList<JobAdsObject> parseJobsList(JSONObject object)
    {
        ArrayList<JobAdsObject> arrayList=new ArrayList<>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new JobAdsObject(jsonObj.getInt("jobID"),jsonObj.getString("title"),jsonObj.getString("jobCategory"),jsonObj.getString("vaccancyNo"),jsonObj.getString("salary"),jsonObj.getString("username"),jsonObj.getString("logoURL")));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseJobsList", e.getMessage());
        }
        return arrayList;
    }

    @SuppressLint("LongLogTag")
    public ArrayList<JobAdsObject> parseJobDetails(JSONObject object)
    {
        ArrayList<JobAdsObject> arrayList=new ArrayList<>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new JobAdsObject(jsonObj.getString("dateOnly"),jsonObj.getString("username"),
                        jsonObj.getString("title"),jsonObj.getString("ad_description"),jsonObj.getString("responsibility"),
                        jsonObj.getString("skills"),jsonObj.getString("jobCategory"),jsonObj.getString("jobTime"),
                        jsonObj.getString("vaccancyNo"),jsonObj.getString("salary"),jsonObj.getString("addres"),
                        jsonObj.getString("contact"),jsonObj.getString("email"),jsonObj.getString("website"),
                        jsonObj.getDouble("latitude"),jsonObj.getDouble("longitude"),jsonObj.getString("logoURL")));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseJobDetails", e.getMessage());
        }
        return arrayList;
    }


}
