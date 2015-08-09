package com.example.home_pc.myclassifiedads.contacts;

import android.graphics.Bitmap;

/**
 * Created by Home-PC on 7/25/2015.
 */
public class ContactsAdObject {

    Bitmap contactImage;
    String title,description,address,contactNo,mobileNo,email;
    Double latitude,longitute;

    public ContactsAdObject(Bitmap contactImage,String title,String description,String address,String contactNo,String mobileNo,String email,Double latitude,Double longitute){
        this.contactImage=contactImage;
        this.title=title;
        this.description=description;
        this.address=address;
        this.contactNo=contactNo;
        this.mobileNo=mobileNo;
        this.email=email;
        this.latitude=latitude;
        this.longitute=longitute;
    }

    public void setContactImage(Bitmap contactImage){this.contactImage=contactImage;}
    public Bitmap getContactImage(){return this.contactImage;}
    public void settitle(String title){this.title=title;}
    public String gettitle(){return this.title;}
    public void setDescription(String description){this.description=description;}
    public String getDescription (){return this.description;}
    public void setContactNo(String contactNo){this.contactNo=contactNo;}
    public String getContactNo(){return this.contactNo;}
    public void setemail(String email){this.email=email;}
    public String getemail(){return this.email;}
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public void setaddress(String address){this.address=address;}
    public String getaddress(){return this.address ;}
    public void setLatitude(Double latitude){this.latitude=latitude;}
    public Double getLatitude(){return this.latitude;}
    public void setLongitute(Double longitute){this.longitute=longitute;}
    public Double getLongitute(){return this.longitute ;}
}
