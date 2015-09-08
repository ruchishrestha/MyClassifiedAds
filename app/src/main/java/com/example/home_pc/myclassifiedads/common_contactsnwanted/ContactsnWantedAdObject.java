package com.example.home_pc.myclassifiedads.common_contactsnwanted;

import android.graphics.Bitmap;

/**
 * Created by Home-PC on 7/25/2015.
 */
public class ContactsnWantedAdObject /*implements Parcelable*/{

    String adImageURL;
    String title,description,aDdress,contactNo,mobileNo,emailId,userName,category,ad_insertdate,tableCategory,userID;
    Double latitude,longitute;
    int adid;
    Bitmap image;


    public ContactsnWantedAdObject(String userName, String title, String description, String aDdress, String contactNo, String mobileNo, String emailId, String category, Double latitude, Double longitute, String adImageURL) {
        this.userName = userName;
        this.title = title;
        this.description = description;
        this.aDdress = aDdress;
        this.contactNo = contactNo;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
        this.category = category;
        this.latitude = latitude;
        this.longitute = longitute;
        this.adImageURL = adImageURL;
    }

    public ContactsnWantedAdObject(int adid, String ad_insertdate, String userName, String title, String description, String category, String contactNo,String mobileNo, String aDdress, String emailId, Double latitude, Double longitute, String adImageURL){
        this.adid=adid;
        this.ad_insertdate=ad_insertdate;
        this.userName =userName;
        this.title=title;
        this.description=description;
        this.category=category;
        this.aDdress=aDdress;
        this.contactNo=contactNo;
        this.mobileNo=mobileNo;
        this.emailId=emailId;
        this.latitude=latitude;
        this.longitute=longitute;
        this.adImageURL=adImageURL;
    }

    public ContactsnWantedAdObject(int adid, String adImageURL, String userName, String title, String aDdress, String contactNo,String mobileNo){
        this.adid=adid;
        this.adImageURL=adImageURL;
        this.userName =userName;
        this.title=title;
        this.aDdress=aDdress;
        this.contactNo=contactNo;
        this.mobileNo=mobileNo;
    }

    public ContactsnWantedAdObject(int adid, String tableCategory){
        this.adid=adid;
        this.tableCategory=tableCategory;
    }

    public ContactsnWantedAdObject(int adid, String tableCategory, String userID){
        this.adid=adid;
        this.tableCategory=tableCategory;
        this.userID=userID;

    }

    public ContactsnWantedAdObject(int adid,String adImageURL,String title,String category,String contactNo,String mobileNo,Double latitude,Double longitute){
        this.adid=adid;
        this.adImageURL=adImageURL;
        this.title=title;
        this.category=category;
        this.contactNo=contactNo;
        this.mobileNo=mobileNo;
        this.latitude=latitude;
        this.longitute=longitute;
    }

    public ContactsnWantedAdObject(int adid,Bitmap image,String title,String category,String contactNo,String mobileNo,Double latitude,Double longitute){
        this.adid=adid;
        this.image=image;
        this.title=title;
        this.category=category;
        this.contactNo=contactNo;
        this.mobileNo=mobileNo;
        this.latitude=latitude;
        this.longitute=longitute;
    }



    public int getAdid() {
        return adid;
    }

    public void setAdid(int adid) {
        this.adid = adid;
    }
    public void setAdImage(String adImageURL){this.adImageURL=adImageURL;}
    public String getAdImage(){return this.adImageURL;}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public String getTableCategory() {
        return tableCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void settitle(String title){this.title=title;}
    public String gettitle(){return this.title;}
    public void setDescription(String description){this.description=description;}
    public String getDescription (){return this.description;}
    public void setContactNo(String contactNo){this.contactNo=contactNo;}
    public String getContactNo(){return this.contactNo;}
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public void setemail(String emailId){this.emailId=emailId;}
    public String getemail(){return this.emailId;}
    public void setaDdress(String aDdress){this.aDdress=aDdress;}
    public String getaDdress(){return this.aDdress ;}
    public void setLatitude(Double latitude){this.latitude=latitude;}
    public Double getLatitude(){return this.latitude;}
    public void setLongitute(Double longitute){this.longitute=longitute;}
    public Double getLongitute(){return this.longitute ;}



}
