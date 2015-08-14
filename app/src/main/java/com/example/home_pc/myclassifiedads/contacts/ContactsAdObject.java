package com.example.home_pc.myclassifiedads.contacts;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Home-PC on 7/25/2015.
 */
public class ContactsAdObject implements Parcelable{

   String contactImage;
    String title,ad_description,address,contactNo,email,username,contacts_category,ad_insertdate;
    Double latitude,longitute;
    int contactID;


    public ContactsAdObject(int contactID,String ad_insertdate,String contactImage,String username,String title,String ad_description,String contacts_category,String address,String contactNo,String email,Double latitude,Double longitute){
        this.contactID=contactID;
        this.ad_insertdate=ad_insertdate;
        this.contactImage=contactImage;
        this.username=username;
        this.title=title;
        this.ad_description=ad_description;
        this.contacts_category=contacts_category;
        this.address=address;
        this.contactNo=contactNo;
        this.email=email;
        this.latitude=latitude;
        this.longitute=longitute;
    }

    public ContactsAdObject(int contactID,String contactImage,String username,String title,String address,String contactNo,Double latitude,Double longitute){
        this.contactID=contactID;
        this.contactImage=contactImage;
        this.username=username;
        this.title=title;
        this.address=address;
        this.contactNo=contactNo;
        this.latitude=latitude;
        this.longitute=longitute;
    }

    public void setContactImage(String contactImage){this.contactImage=contactImage;}
    public String getContactImage(){return this.contactImage;}
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


    public static final Parcelable.Creator<ContactsAdObject> CREATOR = new Parcelable.Creator<ContactsAdObject>() {
        public ContactsAdObject createFromParcel(Parcel in) {
            return new ContactsAdObject(in);
        }

        public ContactsAdObject[] newArray(int size) {
            return new ContactsAdObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ad_insertdate);
        dest.writeString(contactImage);
        dest.writeString(title);
        dest.writeString(ad_description);
        dest.writeString(address);
        dest.writeString(contactNo);
        dest.writeString(email);
        dest.writeInt(contactID);
        dest.writeString(username);
        dest.writeString(contacts_category);
        dest.writeDouble(latitude);
        dest.writeDouble(longitute);
    }

    public ContactsAdObject(Parcel in){
        contactImage=in.readString();
        ad_insertdate=in.readString();
        title=in.readString();
        ad_description=in.readString();
        address=in.readString();
        contactNo=in.readString();
        email=in.readString();
        contactID=in.readInt();
        username=in.readString();
        contacts_category=in.readString();
        latitude=in.readDouble();
        longitute=in.readDouble();
    }
}
