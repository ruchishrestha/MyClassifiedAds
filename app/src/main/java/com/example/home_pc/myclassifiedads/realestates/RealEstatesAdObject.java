package com.example.home_pc.myclassifiedads.realestates;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Home-PC on 7/27/2015.
 */
public class RealEstatesAdObject {
    ArrayList<Bitmap> realEstateImages;
    String title,description,houseNo,propertyType,saleType,aDdress,contactNo,mobileNo,email;
    Double latitude,longitute;

    public RealEstatesAdObject (ArrayList<Bitmap> realEstateImages,String title,String description,String houseNo,String propertyType,String saleType,String aDdress,String contactNo,String mobileNo,String email,Double latitude,Double longitute ){
        this.realEstateImages=realEstateImages;
        this.title=title;
        this.description=description;
        this.houseNo=houseNo;
        this.propertyType=propertyType;
        this.saleType=saleType;
        this.aDdress=aDdress;
        this.contactNo=contactNo;
        this.mobileNo=mobileNo;
        this.email=email;
        this.latitude=latitude;
        this.longitute=longitute;
    }

    public void setRealEstateImages(ArrayList<Bitmap> realEstateImages){this.realEstateImages=realEstateImages;}
    public ArrayList<Bitmap> getRealEstateImages(){return this.realEstateImages;}
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
