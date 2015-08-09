package com.example.home_pc.myclassifiedads.wanteds;

/**
 * Created by Home-PC on 7/26/2015.
 */
public class WantedAdObject {

    String title,description,aDdress,contactNo,mobileNo,email;
    Double latitude,longitute;

    public WantedAdObject(String title,String description,String aDdress,String contactNo,String mobileNo,String email,Double latitude,Double longitute){
        this.title=title;
        this.description=description;
        this.aDdress=aDdress;
        this.contactNo=contactNo;
        this.mobileNo=mobileNo;
        this.email=email;
        this.latitude=latitude;
        this.longitute=longitute;
    }

    public void settitle(String title){this.title=title;}
    public String gettitle(){return this.title;}
    public void setDescription(String description){this.description=description;}
    public String getDescription (){return this.description;}
    public String getaDdress() {
        return aDdress;
    }
    public void setaDdress(String aDdress) {
        this.aDdress = aDdress;
    }
    public String getContactNo() {
        return contactNo;
    }
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
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
