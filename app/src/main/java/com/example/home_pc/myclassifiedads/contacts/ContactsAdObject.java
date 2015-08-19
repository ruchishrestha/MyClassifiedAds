package com.example.home_pc.myclassifiedads.contacts;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Home-PC on 7/25/2015.
 */
public class ContactsAdObject /*implements Parcelable*/{

    String adImage;
    String title,ad_description,address,contactNo,email,username,Category,ad_insertdate,tableCategory,viewerUsername;
    Double latitude,longitute;
    int adid;


    public ContactsAdObject(int adid,String ad_insertdate,String username,String title,String ad_description,String Category,String address,String contactNo,String email,Double latitude,Double longitute,String adImage){
        this.adid=adid;
        this.ad_insertdate=ad_insertdate;
        this.username=username;
        this.title=title;
        this.ad_description=ad_description;
        this.Category=Category;
        this.address=address;
        this.contactNo=contactNo;
        this.email=email;
        this.latitude=latitude;
        this.longitute=longitute;
        this.adImage=adImage;
    }

    public ContactsAdObject(int adid,String adImage,String username,String title,String address,String contactNo){
        this.adid=adid;
        this.adImage=adImage;
        this.username=username;
        this.title=title;
        this.address=address;
        this.contactNo=contactNo;
    }

    public ContactsAdObject(int adid,String tableCategory){
        this.adid=adid;
        this.tableCategory=tableCategory;
    }

    public ContactsAdObject(int adid,String tableCategory,String viewerUsername){
        this.adid=adid;
        this.tableCategory=tableCategory;
        this.viewerUsername=viewerUsername;

    }

    public void setAdImage(String adImage){this.adImage=adImage;}
    public String getAdImage(){return this.adImage;}
    public void settitle(String title){this.title=title;}
    public String gettitle(){return this.title;}
    public void setAd_description(String ad_description){this.ad_description=ad_description;}
    public String getAd_Description (){return this.ad_description;}
    public void setContactNo(String contactNo){this.contactNo=contactNo;}
    public String getContactNo(){return this.contactNo;}
    public void setemail(String email){this.email=email;}
    public String getemail(){return this.email;}
    public void setaddress(String address){this.address=address;}
    public String getaddress(){return this.address ;}
    public void setLatitude(Double latitude){this.latitude=latitude;}
    public Double getLatitude(){return this.latitude;}
    public void setLongitute(Double longitute){this.longitute=longitute;}
    public Double getLongitute(){return this.longitute ;}



}
