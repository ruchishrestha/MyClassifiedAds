package com.example.home_pc.myclassifiedads.realestates;

/**
 * Created by Home-PC on 7/27/2015.
 */
public class RealEstatesAdObject {
    String[] realEstateImages;
    int realestateID;
    String title,description,houseNo,propertyType,saleType,price,aDdress,contactNo,mobileNo,email,userName,userID,ad_insertdate,tableCategory;
    Double latitude,longitude;

    public RealEstatesAdObject (String userName,String title,String description,String houseNo,String propertyType,String saleType,String aDdress,String contactNo,String mobileNo,String email,Double latitude,Double longitude ){
        this.userName = userName;
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
        this.longitude=longitude;
    }

    public RealEstatesAdObject (int realestateID,String ad_insertdate,String username,String title,String description,String houseNo,String propertyType,String saleType,String price,String aDdress,String contactNo,String mobileNo,Double latitude,Double longitute ){
        this.realestateID=realestateID;
        this.ad_insertdate=ad_insertdate;
        this.userName =username;
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
        this.longitude =longitute;
    }

    public RealEstatesAdObject(int realestateID,String title,String price,String saleType,String aDdress,String contactNo,String username){
        this.realestateID=realestateID;
        this.title=title;
        this.price=price;
        this.saleType=saleType;
        this.aDdress=aDdress;
        this.contactNo=contactNo;
        this.userName =username;

    }

    public RealEstatesAdObject(int realestateID,String title,String saleType,String price,String contactNo,String mobileNo,Double latitude,Double longitude){
        this.realestateID=realestateID;
        this.title=title;
        this.price=price;
        this.saleType=saleType;
        this.contactNo=contactNo;
       this.mobileNo=mobileNo;
        this.latitude=latitude;
        this.longitude=longitude;

    }

    public RealEstatesAdObject(int realestateID,String tableCategory,String userID){
        this.realestateID=realestateID;
        this.tableCategory=tableCategory;
        this.userID=userID;
    }

    public int getRealestateID() {
        return realestateID;
    }

    public String getPrice() {
        return price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void settitle(String title){this.title=title;}
    public String gettitle(){return this.title;}
    public void setDescription(String description){this.description=description;}
    public String getDescription (){return this.description;}
    public void setHouseNo(String houseNo){this.houseNo=houseNo;}
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
    public void setLongitude(Double longitude){this.longitude = longitude;}
    public Double getLongitude(){return this.longitude;}
}
