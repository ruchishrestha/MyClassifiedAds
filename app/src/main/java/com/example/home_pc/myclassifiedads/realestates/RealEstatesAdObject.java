package com.example.home_pc.myclassifiedads.realestates;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Home-PC on 7/27/2015.
 */
public class RealEstatesAdObject {
    String[] realEstateImages;int realestateID;
    String title,description,houseNo,propertyType,saleType,aDdress,contactNo,mobileNo,email,username,userID,ad_insertdate,tableCategory;
    Double latitude,longitute,price;

    public RealEstatesAdObject (int realestateID,String ad_insertdate,String username,String title,String description,String houseNo,String propertyType,String saleType,Double price,String aDdress,String contactNo,String mobileNo,Double latitude,Double longitute ){
        this.realestateID=realestateID;
        this.ad_insertdate=ad_insertdate;
        this.username=username;
        this.title=title;
        this.description=description;
        this.houseNo=houseNo;
        this.price=price;
        this.propertyType=propertyType;
        this.saleType=saleType;
        this.aDdress=aDdress;
        this.contactNo=contactNo;
        this.mobileNo=mobileNo;
        this.latitude=latitude;
        this.longitute=longitute;
    }

    public RealEstatesAdObject(int realestateID,String title,Double price,String saleType,String aDdress,String contactNo,String username){
        this.realestateID=realestateID;
        this.title=title;
        this.price=price;
        this.saleType=saleType;
        this.aDdress=aDdress;
        this.contactNo=contactNo;
        this.username=username;

    }

    public RealEstatesAdObject(int realestateID,String tableCategory,String userID){
        this.realestateID=realestateID;
        this.tableCategory=tableCategory;
        this.userID=userID;
    }


    public void settitle(String title){this.title=title;}
    public String gettitle(){return this.title;}
    public void setDescription(String description){this.description=description;}
    public String getDescription (){return this.description;}
    public void setHouseNo(String HouseNo){this.houseNo=houseNo;}
    public String getHouseNo (){return this.houseNo;}
    public void setPropertyType(String propertyType){this.propertyType=propertyType;}
    public String getPropertyType (){return this.propertyType;}
    public String getSaleType() {
        return saleType;
    }
    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }
    public void setADdress(String aDdress){this.aDdress=aDdress;}
    public String getADdress(){return this.aDdress ;}
    public void setContactNo(String contactNo){this.contactNo=contactNo;}
    public String getContactNo(){return this.contactNo;}
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public void setemail(String email){this.email=email;}
    public String getemail(){return this.email;}
    public void setLatitude(Double latitude){this.latitude=latitude;}
    public Double getLatitude(){return this.latitude;}
    public void setLongitute(Double longitute){this.longitute=longitute;}
    public Double getLongitute(){return this.longitute ;}
}
