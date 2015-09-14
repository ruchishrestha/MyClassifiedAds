package com.example.home_pc.myclassifiedads.classified_api;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.home_pc.myclassifiedads.comments.CommentObject;
import com.example.home_pc.myclassifiedads.common_contactsnwanted.ContactsnWantedAdObject;
import com.example.home_pc.myclassifiedads.draweritemsfragments.WatchlistObject;
import com.example.home_pc.myclassifiedads.jobs.JobAdsObject;
import com.example.home_pc.myclassifiedads.realestates.RealEstatesAdObject;
import com.example.home_pc.myclassifiedads.sales.SalesAdsObject;
import com.example.home_pc.myclassifiedads.user_login.IndividualUser;
import com.example.home_pc.myclassifiedads.user_login.OrganizationUser;
import com.example.home_pc.myclassifiedads.user_login.ShopUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
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
    public ArrayList<String> getMyQueryResult(JSONObject object){
        ArrayList<String> categoryList = new ArrayList<String>();

        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                categoryList.add(jsonObj.getString("MyUserName"));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => MyQueryResult", e.getMessage());
        }
        return categoryList;
    }



    @SuppressLint("LongLogTag")
    public IndividualUser getIndividualDetail(JSONObject object)
    {
        IndividualUser iUser=null;
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj=null;
            for(int i=0;i<jsonArray.length();i++) {
                jsonObj = jsonArray.getJSONObject(i);
                iUser = new IndividualUser(jsonObj.getString("firstName"),jsonObj.getString("middleName"),jsonObj.getString("lastName"),jsonObj.getString("pasword"),jsonObj.getString("addres"),jsonObj.getString("contactNo"),jsonObj.getString("mobileNo"),jsonObj.getString("emailId"),jsonObj.getString("webSite"),jsonObj.getString("profilePictureURL"));
            }
        }
        catch(Exception e){
            System.out.println("ERROROROROROROR!!" + e);
        }
        return iUser;
    }

    @SuppressLint("LongLogTag")
    public ShopUser getShopDetail(JSONObject object)
    {
        ShopUser sUser=null;
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj=null;
            for(int i=0;i<jsonArray.length();i++) {
                jsonObj = jsonArray.getJSONObject(i);
                sUser = new ShopUser(jsonObj.getString("shopName"),jsonObj.getString("shopOwner"),jsonObj.getString("pasword"),jsonObj.getString("panNo"),jsonObj.getString("addres"),jsonObj.getString("contactNo"),jsonObj.getString("mobileNo"),jsonObj.getString("emailId"),jsonObj.getString("webSite"),jsonObj.getDouble("latitude"),jsonObj.getDouble("longitude"),jsonObj.getString("shopPictureURL"));
            }
        }
        catch(Exception e){
            System.out.println("ERROROROROROROR!!");
        }
        return sUser;
    }

    @SuppressLint("LongLogTag")
    public OrganizationUser getOrganizationDetail(JSONObject object)
    {
        OrganizationUser oUser=null;
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj=null;
            for(int i=0;i<jsonArray.length();i++) {
                jsonObj = jsonArray.getJSONObject(i);
                oUser = new OrganizationUser(jsonObj.getString("organizationName"),jsonObj.getString("registrationNo"),jsonObj.getString("pasword"),jsonObj.getString("addres"),jsonObj.getString("contactNo"),jsonObj.getString("mobileNo"),jsonObj.getString("emailId"),jsonObj.getString("webSite"),jsonObj.getDouble("latitude"),jsonObj.getDouble("longitude"),jsonObj.getString("organizationPictureURL"));
            }
        }
        catch(Exception e){
            System.out.println("ERROROROROROROR!!");
        }
        return oUser;
    }

    @SuppressLint("LongLogTag")
    public String checkUserName(JSONObject object){
        String userChecker="";
        try{
            userChecker = object.getString("Value");
        }
        catch(Exception e){
            System.out.println("CHECK ERROR!! "+e);
        }
        return userChecker;
    }

    @SuppressLint("LongLogTag")
    public String[] authenticateUser(JSONObject object)
    {
        String[] userCategory={"","",""};
        try{
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj=null;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                userCategory[0] = jsonObj.getString("userCategory");
                userCategory[1] = jsonObj.getString("fullUserName");
                userCategory[2] = jsonObj.getString("pictureURL");
            }
        }
        catch (Exception e){
            System.out.println("ERROR-USER: "+e);
            userCategory[0] = "Error";
            userCategory[1] = "";
            userCategory[2] = "";
        }
        return userCategory;
    }

    public String getId(JSONObject object){
        String adID="";
        try{
            adID = object.getString("Value");
        }
        catch(Exception e){
            System.out.println("ID-ERROR: "+e);
        }
        return adID;
    }

    public String getResult(JSONObject object){
        String result="";
        try{
            result = object.getString("Value");
        }
        catch (Exception e){
            System.out.println("JobResult: " + e);
        }
        return result;
    }

    @SuppressLint("LongLogTag")
    public ArrayList<String> getList(JSONObject object){
        ArrayList<String> categoryList = new ArrayList<String>();

        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                categoryList.add(jsonObj.getString("Category"));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseContactsList", e.getMessage());
        }
        return categoryList;
    }


    @SuppressLint("LongLogTag")
    public ArrayList<ContactsnWantedAdObject> parseContactsList(JSONObject object)
    {
        ArrayList<ContactsnWantedAdObject> arrayList=new ArrayList<>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new ContactsnWantedAdObject(jsonObj.getInt("adid"), jsonObj.getString("photoURL"),jsonObj.getString("username"),jsonObj.getString("title"),jsonObj.getString("addres"),jsonObj.getString("contact"),jsonObj.getString("mobile")));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseContactsList", e.getMessage());
        }
        return arrayList;
    }

    @SuppressLint("LongLogTag")
    public ContactsnWantedAdObject parseContactDetails(JSONObject object)
    {
        ContactsnWantedAdObject arrayList=null;
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                arrayList=(new ContactsnWantedAdObject(jsonObj.getInt("adid"),jsonObj.getString("dateOnly"),jsonObj.getString("username"),jsonObj.getString("title"),jsonObj.getString("ad_description"),jsonObj.getString("Category"),jsonObj.getString("contact"),jsonObj.getString("mobile"),jsonObj.getString("addres"),jsonObj.getString("email"),jsonObj.getDouble("latitude"),jsonObj.getDouble("longitude"),jsonObj.getString("photoURL")));
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
                arrayList.add(new RealEstatesAdObject(jsonObj.getInt("realestateID"),jsonObj.getString("title"),jsonObj.getString("price"),jsonObj.getString("saleType"),jsonObj.getString("addres"),jsonObj.getString("contact"),jsonObj.getString("username")));
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
                arrayList.add(new RealEstatesAdObject(jsonObj.getInt("realestateID"),jsonObj.getString("dateOnly"),jsonObj.getString("username"),jsonObj.getString("title"),jsonObj.getString("ad_description"),jsonObj.getString("houseNo"),jsonObj.getString("propertyType"),jsonObj.getString("saleType"),jsonObj.getString("price"),jsonObj.getString("addres"),jsonObj.getString("contact"),jsonObj.getString("mobile"),jsonObj.getDouble("latitude"),jsonObj.getDouble("longitude")));
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

    @SuppressLint("LongLogTag")
    public String parseReturnedURL(JSONObject object){
        String realestatePic=null;
        try {
            realestatePic = object.getString("Value");
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseURL", e.getMessage());
        }
        return realestatePic;
    }

    public ArrayList<String> parseReturnedURLs(JSONObject object){
       ArrayList<String> realestatePic=null;

        try {
            realestatePic=new ArrayList<>();
            {
                JSONArray jsonArray = object.getJSONArray("Value");
                if(jsonArray.length()==0){
                    realestatePic=null;
                }else{
                    for (int i = 0; i < jsonArray.length(); i++) {
                        realestatePic.add(jsonArray.get(i).toString());
                    }
                }
            }

        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseURLs", e.getMessage());
        }
        return realestatePic;
    }

    @SuppressLint("LongLogTag")
    public ArrayList<SalesAdsObject> parseSalesList(JSONObject object) {
        ArrayList<SalesAdsObject> arrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = object.getJSONArray("Value");
            JSONObject jsonObj;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObj = jsonArray.getJSONObject(i);
                arrayList.add(new SalesAdsObject(jsonObj.getInt("salesID"),jsonObj.getString("username"),jsonObj.getString("title"),jsonObj.getString("brand"),
                        jsonObj.getString("model"),jsonObj.getString("price"),jsonObj.getString("salesStatus"),jsonObj.getString("condition"),jsonObj.getDouble("averageRating")));

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseSalesList", e.getMessage());
        }
        return arrayList;
    }

    @SuppressLint("LongLogTag")
    public Double parseMyRating(JSONObject object){
        Double averageRate=0.0;
        try {
            averageRate = object.getDouble("Value");
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseMyRating", e.getMessage());
        }
        return averageRate;
    }

    @SuppressLint("LongLogTag")
    public ArrayList<SalesAdsObject> parseSalesDetails(JSONObject object) {
        ArrayList<SalesAdsObject> arrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = object.getJSONArray("Value");
            JSONObject jsonObj;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObj = jsonArray.getJSONObject(i);
                arrayList.add(new SalesAdsObject(jsonObj.getInt("salesID"),jsonObj.getString("username"),jsonObj.getString("title"),jsonObj.getString("brand"),
                        jsonObj.getString("model"),jsonObj.getString("price"),jsonObj.getString("salesStatus"),jsonObj.getString("condition"),jsonObj.getDouble("averageRating"),jsonObj.getString("dateOnly"),jsonObj.getString("timeused"),
                        jsonObj.getString("contact"),jsonObj.getString("ad_description"),jsonObj.getString("salesCategory")));

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseSalesDetails", e.getMessage());
        }
        return arrayList;
    }

    @SuppressLint("LongLogTag")
    public ArrayList<ContactsnWantedAdObject> parseContactsForMap(JSONObject object)
    {
        ArrayList<ContactsnWantedAdObject> arrayList=new ArrayList<>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new ContactsnWantedAdObject(jsonObj.getInt("adid"), jsonObj.getString("photoURL"),jsonObj.getString("title"),jsonObj.getString("Category"),jsonObj.getString("contact"),jsonObj.getString("mobile"),jsonObj.getDouble("latitude"), jsonObj.getDouble("longitude")));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseContactsForMap", e.getMessage());
        }
        return arrayList;
    }

    @SuppressLint("LongLogTag")
    public String parseUserCategory(JSONObject object){
        String userCategory=null;
        try {
            userCategory = object.getString("Value");
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseUserCategory", e.getMessage());
        }
        return userCategory;
    }

    @SuppressLint("LongLogTag")
    public ArrayList<SalesAdsObject> parseMySalesList(JSONObject object) {
        ArrayList<SalesAdsObject> arrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = object.getJSONArray("Value");
            JSONObject jsonObj;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObj = jsonArray.getJSONObject(i);
                arrayList.add(new SalesAdsObject(jsonObj.getInt("salesID"),jsonObj.getString("username"),jsonObj.getString("title"),jsonObj.getString("brand"),
                        jsonObj.getString("model"),jsonObj.getString("price"),jsonObj.getString("salesStatus"),jsonObj.getString("condition"),jsonObj.getDouble("averageRating"),jsonObj.getString("salesCategory")));

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseMySalesList", e.getMessage());
        }
        return arrayList;
    }

    @SuppressLint("LongLogTag")
    public ArrayList<RealEstatesAdObject> parseRealestateForMap(JSONObject object)
    {
        ArrayList<RealEstatesAdObject> arrayList=new ArrayList<>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new RealEstatesAdObject(jsonObj.getInt("realestateID"),jsonObj.getString("title"),jsonObj.getString("saleType"),
                        jsonObj.getString("price"),jsonObj.getString("contact"),jsonObj.getString("mobile"),jsonObj.getDouble("latitude"), jsonObj.getDouble("longitude")));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseRealEstateForMap", e.getMessage());
        }
        return arrayList;
    }

    @SuppressLint("LongLogTag")
    public ArrayList<WatchlistObject> parseWatchlist(JSONObject object)
    {
        ArrayList<WatchlistObject> arrayList=new ArrayList<>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new WatchlistObject(jsonObj.getInt("adid"),jsonObj.getString("category"),jsonObj.getString("title")));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseRealEstateForMap", e.getMessage());
        }
        return arrayList;
    }
}
