/* JSON API for android appliation */
package com.example.home_pc.myclassifiedads.classified_api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.json.JSONArray;

public class RestAPI {
    private final String urlString = "http://classifiedapi.azurewebsites.net/Handler1.ashx";

    private static String convertStreamToUTF8String(InputStream stream) throws IOException {
	    String result = "";
	    StringBuilder sb = new StringBuilder();
	    try {
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[4096];
            int readedChars = 0;
            while (readedChars != -1) {
                readedChars = reader.read(buffer);
                if (readedChars > 0)
                   sb.append(buffer, 0, readedChars);
            }
            result = sb.toString();
		} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    private String load(String contents) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(60000);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        OutputStreamWriter w = new OutputStreamWriter(conn.getOutputStream());
        w.write(contents);
        w.flush();
        InputStream istream = conn.getInputStream();
        String result = convertStreamToUTF8String(istream);
        return result;
    }


    private Object mapObject(Object o) {
		Object finalValue = null;
		if (o.getClass() == String.class) {
			finalValue = o;
		}
		else if (Number.class.isInstance(o)) {
			finalValue = String.valueOf(o);
		} else if (Date.class.isInstance(o)) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", new Locale("en", "USA"));
			finalValue = sdf.format((Date)o);
		}
		else if (Collection.class.isInstance(o)) {
			Collection<?> col = (Collection<?>) o;
			JSONArray jarray = new JSONArray();
			for (Object item : col) {
				jarray.put(mapObject(item));
			}
			finalValue = jarray;
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			Method[] methods = o.getClass().getMethods();
			for (Method method : methods) {
				if (method.getDeclaringClass() == o.getClass()
						&& method.getModifiers() == Modifier.PUBLIC
						&& method.getName().startsWith("get")) {
					String key = method.getName().substring(3);
					try {
						Object obj = method.invoke(o, null);
						Object value = mapObject(obj);
						map.put(key, value);
						finalValue = new JSONObject(map);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
		return finalValue;
	}

    public JSONObject MyFullTextSearcher(String myQuery) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "MyFullTextSearcher");
        p.put("myQuery",mapObject(myQuery));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject CreateIndividualProfile(String userName,String passWord,String firstName,String middleName,String lastName,String aDdress,String contactNo,String mobileNo,String emailId,String webSite,String profilePictureURL) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "CreateIndividualProfile");
        p.put("userName",mapObject(userName));
        p.put("passWord",mapObject(passWord));
        p.put("firstName",mapObject(firstName));
        p.put("middleName",mapObject(middleName));
        p.put("lastName",mapObject(lastName));
        p.put("aDdress",mapObject(aDdress));
        p.put("contactNo",mapObject(contactNo));
        p.put("mobileNo",mapObject(mobileNo));
        p.put("emailId",mapObject(emailId));
        p.put("webSite",mapObject(webSite));
        p.put("profilePictureURL",mapObject(profilePictureURL));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject CreateOrganizationProfile(String userName,String passWord,String organizationName,String registrationNo,String aDdress,String contactNo,String mobileNo,String emailId,String webSite,double latitude,double longitude,String organizationPicture) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "CreateOrganizationProfile");
        p.put("userName",mapObject(userName));
        p.put("passWord",mapObject(passWord));
        p.put("organizationName",mapObject(organizationName));
        p.put("registrationNo",mapObject(registrationNo));
        p.put("aDdress",mapObject(aDdress));
        p.put("contactNo",mapObject(contactNo));
        p.put("mobileNo",mapObject(mobileNo));
        p.put("emailId",mapObject(emailId));
        p.put("webSite",mapObject(webSite));
        p.put("latitude",mapObject(latitude));
        p.put("longitude",mapObject(longitude));
        p.put("organizationPicture",mapObject(organizationPicture));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject CreateShopProfile(String userName,String passWord,String shopName,String shopOwner,String panNo,String aDdress,String contactNo,String mobileNo,String emailId,String webSite,double latitude,double longitude,String shopPictureURL) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "CreateShopProfile");
        p.put("userName",mapObject(userName));
        p.put("passWord",mapObject(passWord));
        p.put("shopName",mapObject(shopName));
        p.put("shopOwner",mapObject(shopOwner));
        p.put("panNo",mapObject(panNo));
        p.put("aDdress",mapObject(aDdress));
        p.put("contactNo",mapObject(contactNo));
        p.put("mobileNo",mapObject(mobileNo));
        p.put("emailId",mapObject(emailId));
        p.put("webSite",mapObject(webSite));
        p.put("latitude",mapObject(latitude));
        p.put("longitude",mapObject(longitude));
        p.put("shopPictureURL",mapObject(shopPictureURL));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject UserAuthentication(String userName,String passWord) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface", "RestAPI");
        o.put("method", "UserAuthentication");
        p.put("userName", mapObject(userName));
        p.put("passWord", mapObject(passWord));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetIndividualDetail(String userName) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetIndividualDetail");
        p.put("userName",mapObject(userName));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetShopDetail(String userName) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetShopDetail");
        p.put("userName",mapObject(userName));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetOrganizationDetail(String userName) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetOrganizationDetail");
        p.put("userName",mapObject(userName));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject AddContactsAds(String userName,String title,String description,String category,String aDdress,String contactNo,String mobileNo,String emailId,double latitude,double longitude,String picURL) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "AddContactsAds");
        p.put("userName",mapObject(userName));
        p.put("title",mapObject(title));
        p.put("description",mapObject(description));
        p.put("category",mapObject(category));
        p.put("aDdress",mapObject(aDdress));
        p.put("contactNo",mapObject(contactNo));
        p.put("mobileNo",mapObject(mobileNo));
        p.put("emailId",mapObject(emailId));
        p.put("latitude",mapObject(latitude));
        p.put("longitude",mapObject(longitude));
        p.put("picURL",mapObject(picURL));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject UpdateContactsAd(String adId,String pictureURL) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "UpdateContactsAd");
        p.put("adId",mapObject(adId));
        p.put("pictureURL",mapObject(pictureURL));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetContactsCategory() throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetContactsCategory");
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject AddWantedAds(String userName,String title,String description,String category,String aDdress,String contactNo,String mobileNo,String emailId,double latitude,double longitude,String picURL) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "AddWantedAds");
        p.put("userName",mapObject(userName));
        p.put("title",mapObject(title));
        p.put("description",mapObject(description));
        p.put("category",mapObject(category));
        p.put("aDdress",mapObject(aDdress));
        p.put("contactNo",mapObject(contactNo));
        p.put("mobileNo",mapObject(mobileNo));
        p.put("emailId",mapObject(emailId));
        p.put("latitude",mapObject(latitude));
        p.put("longitude",mapObject(longitude));
        p.put("picURL",mapObject(picURL));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject UpdateWantedAd(String adId,String pictureURL) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "UpdateWantedAd");
        p.put("adId",mapObject(adId));
        p.put("pictureURL",mapObject(pictureURL));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetWantedCategory() throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetWantedCategory");
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject AddSalesAds(String userName,String title,String description,String brand,String model,String price,String salesStatus,String condition,String timeUsed,String contactNo,String avgRating,String salesCategory) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "AddSalesAds");
        p.put("userName",mapObject(userName));
        p.put("title",mapObject(title));
        p.put("description",mapObject(description));
        p.put("brand",mapObject(brand));
        p.put("model",mapObject(model));
        p.put("price",mapObject(price));
        p.put("salesStatus",mapObject(salesStatus));
        p.put("condition",mapObject(condition));
        p.put("timeUsed",mapObject(timeUsed));
        p.put("contactNo",mapObject(contactNo));
        p.put("avgRating",mapObject(avgRating));
        p.put("salesCategory",mapObject(salesCategory));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject AddtoSalesGallery(String salesId,String salesCategory,ArrayList<String> pictureURL) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "AddtoSalesGallery");
        p.put("salesId",mapObject(salesId));
        p.put("salesCategory",mapObject(salesCategory));
        p.put("pictureURL",mapObject(pictureURL));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject AddRealEstateAds(String userName,String title,String description,String houseNo,String propertyType,String saleType,String price,String aDdress,String contactNo,String mobileNo,double latitude,double longitude) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "AddRealEstateAds");
        p.put("userName",mapObject(userName));
        p.put("title",mapObject(title));
        p.put("description",mapObject(description));
        p.put("houseNo",mapObject(houseNo));
        p.put("propertyType",mapObject(propertyType));
        p.put("saleType",mapObject(saleType));
        p.put("price",mapObject(price));
        p.put("aDdress",mapObject(aDdress));
        p.put("contactNo",mapObject(contactNo));
        p.put("mobileNo",mapObject(mobileNo));
        p.put("latitude",mapObject(latitude));
        p.put("longitude",mapObject(longitude));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject AddtoRealEstateGallery(String realId,ArrayList<String> pictureURL) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "AddtoRealEstateGallery");
        p.put("realId",mapObject(realId));
        p.put("pictureURL",mapObject(pictureURL));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetPropertyType() throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetPropertyType");
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject AddJobAds(String userName,String jobTitle,String jobDescription,String responsibility,String skills,String jobCategory,String jobTiming,String vacancy,String salary,String aDdress,String contactNo,String emailId,String webSite,double latitude,double longitude,String organizationLogoURL) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "AddJobAds");
        p.put("userName",mapObject(userName));
        p.put("jobTitle",mapObject(jobTitle));
        p.put("jobDescription",mapObject(jobDescription));
        p.put("responsibility",mapObject(responsibility));
        p.put("skills",mapObject(skills));
        p.put("jobCategory",mapObject(jobCategory));
        p.put("jobTiming",mapObject(jobTiming));
        p.put("vacancy",mapObject(vacancy));
        p.put("salary",mapObject(salary));
        p.put("aDdress",mapObject(aDdress));
        p.put("contactNo",mapObject(contactNo));
        p.put("emailId",mapObject(emailId));
        p.put("webSite",mapObject(webSite));
        p.put("latitude",mapObject(latitude));
        p.put("longitude",mapObject(longitude));
        p.put("organizationLogoURL",mapObject(organizationLogoURL));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject UpdateJobAd(String jobId,String pictureURL) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "UpdateJobAd");
        p.put("jobId",mapObject(jobId));
        p.put("pictureURL",mapObject(pictureURL));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetJobCategory() throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetJobCategory");
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetSalesDetail(int salesID) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetSalesDetail");
        p.put("salesID",mapObject(salesID));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject DeleteSalesAd(int adid,String category) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "DeleteSalesAd");
        p.put("adid",mapObject(adid));
        p.put("category",mapObject(category));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }
    public JSONObject GetContactsList(String category) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetContactsList");
        p.put("category",mapObject(category));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetContactDetails(int adid,String category) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetContactDetails");
        p.put("adid",mapObject(adid));
        p.put("category",mapObject(category));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetMyComment(int adid,String username,String category) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetMyComment");
        p.put("adid",mapObject(adid));
        p.put("username",mapObject(username));
        p.put("category",mapObject(category));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetAllComments(int adid,String category) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetAllComments");
        p.put("adid",mapObject(adid));
        p.put("category",mapObject(category));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject PushComments(String category,String username,int adid,String commentText) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "PushComments");
        p.put("category",mapObject(category));
        p.put("username",mapObject(username));
        p.put("adid",mapObject(adid));
        p.put("commentText",mapObject(commentText));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject PushtoWatchlist(int adid,String category,String username) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "PushtoWatchlist");
        p.put("adid",mapObject(adid));
        p.put("category",mapObject(category));
        p.put("username",mapObject(username));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetRealestateList() throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetRealestateList");
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetRealestateDetails(int realestateID) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetRealestateDetails");
        p.put("realestateID",mapObject(realestateID));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetJobsList() throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetJobsList");
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetJobsDetail(int jobID) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetJobsDetail");
        p.put("jobID",mapObject(jobID));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetRealestatePictureURL(int adid) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetRealestatePictureURL");
        p.put("adid",mapObject(adid));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetAllImages(int adid) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetAllImages");
        p.put("adid",mapObject(adid));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetSalesList(String salesCategory,String orderads) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetSalesList");
        p.put("salesCategory",mapObject(salesCategory));
        p.put("orderads",mapObject(orderads));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetSalesImages(int adid) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetSalesImages");
        p.put("adid",mapObject(adid));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetSalesPictureURL(int adid) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetSalesPictureURL");
        p.put("adid",mapObject(adid));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject PushRateValue(int salesID,String userID,String salesCategory,double myrating) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "PushRateValue");
        p.put("salesID",mapObject(salesID));
        p.put("userID",mapObject(userID));
        p.put("salesCategory",mapObject(salesCategory));
        p.put("myrating",mapObject(myrating));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetMyRating(int salesID,String userID,String salesCategory) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetMyRating");
        p.put("salesID",mapObject(salesID));
        p.put("userID",mapObject(userID));
        p.put("salesCategory",mapObject(salesCategory));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetCommentRating(int adid,String username) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetCommentRating");
        p.put("adid",mapObject(adid));
        p.put("username",mapObject(username));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetContactsForMap(String category) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetContactsForMap");
        p.put("category",mapObject(category));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetUserCategory(String username) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetUserCategory");
        p.put("username",mapObject(username));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetMyContactsList(String userID,String category) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetMyContactsList");
        p.put("userID",mapObject(userID));
        p.put("category",mapObject(category));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject DeleteContactsWantedAd(int adid,String category) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "DeleteContactsWantedAd");
        p.put("adid",mapObject(adid));
        p.put("category",mapObject(category));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject UpdateContactsAds(int adid,String title,String description,String category,String aDdress,String contactNo,String mobileNo,String emailId,double latitude,double longitude,String picURL) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "UpdateContactsAds");
        p.put("adid",mapObject(adid));
        p.put("title",mapObject(title));
        p.put("description",mapObject(description));
        p.put("category",mapObject(category));
        p.put("aDdress",mapObject(aDdress));
        p.put("contactNo",mapObject(contactNo));
        p.put("mobileNo",mapObject(mobileNo));
        p.put("emailId",mapObject(emailId));
        p.put("latitude",mapObject(latitude));
        p.put("longitude",mapObject(longitude));
        p.put("picURL",mapObject(picURL));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject UpdateWantedAds(int adid,String title,String description,String category,String aDdress,String contactNo,String mobileNo,String emailId,double latitude,double longitude,String picURL) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "UpdateWantedAds");
        p.put("adid",mapObject(adid));
        p.put("title",mapObject(title));
        p.put("description",mapObject(description));
        p.put("category",mapObject(category));
        p.put("aDdress",mapObject(aDdress));
        p.put("contactNo",mapObject(contactNo));
        p.put("mobileNo",mapObject(mobileNo));
        p.put("emailId",mapObject(emailId));
        p.put("latitude",mapObject(latitude));
        p.put("longitude",mapObject(longitude));
        p.put("picURL",mapObject(picURL));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetMySalesList(String userID) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetMySalesList");
        p.put("userID",mapObject(userID));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject UpdateSalesAds(int adid,String title,String description,String brand,String model,String price,String salesStatus,String condition,String timeUsed,String contactNo) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "UpdateSalesAds");
        p.put("adid",mapObject(adid));
        p.put("title",mapObject(title));
        p.put("description",mapObject(description));
        p.put("brand",mapObject(brand));
        p.put("model",mapObject(model));
        p.put("price",mapObject(price));
        p.put("salesStatus",mapObject(salesStatus));
        p.put("condition",mapObject(condition));
        p.put("timeUsed",mapObject(timeUsed));
        p.put("contactNo",mapObject(contactNo));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject DeleteSalesURL(int salesID) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "DeleteSalesURL");
        p.put("salesID",mapObject(salesID));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }


}


