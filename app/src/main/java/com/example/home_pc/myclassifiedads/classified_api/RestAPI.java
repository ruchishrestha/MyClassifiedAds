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

    public JSONObject CreateIndividualProfile(String userName,String passWord,String firstName,String middleName,String lastName,String aDdress,String contactNo,String mobileNo,String emailId,String webSite,String profilePic) throws Exception {
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
        p.put("profilePic",mapObject(profilePic));
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
        o.put("interface","RestAPI");
        o.put("method", "UserAuthentication");
        p.put("userName",mapObject(userName));
        p.put("passWord",mapObject(passWord));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetUserDetail(String userName,String userCategory) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetUserDetail");
        p.put("userName",mapObject(userName));
        p.put("userCategory",mapObject(userCategory));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject PushAdstoSales(String adid,String username,String title,String ad_desc,String brand,double price,String ad_stat,String condition,String timeused,long contact) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "PushAdstoSales");
        p.put("adid",mapObject(adid));
        p.put("username",mapObject(username));
        p.put("title",mapObject(title));
        p.put("ad_desc",mapObject(ad_desc));
        p.put("brand",mapObject(brand));
        p.put("price",mapObject(price));
        p.put("ad_stat",mapObject(ad_stat));
        p.put("condition",mapObject(condition));
        p.put("timeused",mapObject(timeused));
        p.put("contact",mapObject(contact));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetSalesDetail() throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetSalesDetail");
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject DeleteSalesAd(String adid) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "DeleteSalesAd");
        p.put("adid",mapObject(adid));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject GetContactsList() throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "GetContactsList");
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

}


