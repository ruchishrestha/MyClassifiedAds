package com.example.home_pc.myclassifiedads.jobs;

import android.graphics.Bitmap;

/**
 * Created by Home-PC on 7/31/2015.
 */
public class JobAdsObject {

    String title,ad_postedDate,logoURL,ad_description,username,responsibility,skills,jobCategory,jobCategoryOthers,
            jobTime,vaccancyNo,salary,aDdress,contactNo,emailId,webSite;
    Double latitude,longitude;
    int jobID;

    public JobAdsObject(String ad_postedDate,String username,String title, String ad_description, String responsibility, String skills, String jobCategory, String jobTime, String vaccancyNo, String salary, String aDdress, String contactNo, String emailId, String webSite, Double latitude,Double longitude,String logoURL) {
        this.ad_postedDate=ad_postedDate;
        this.username=username;
        this.title = title;
        this.ad_description = ad_description;
        this.responsibility = responsibility;
        this.skills = skills;
        this.jobCategory = jobCategory;
        this.jobTime = jobTime;
        this.vaccancyNo = vaccancyNo;
        this.salary = salary;
        this.aDdress = aDdress;
        this.contactNo = contactNo;
        this.emailId = emailId;
        this.webSite = webSite;
        this.latitude=latitude;
        this.longitude=longitude;
        this.logoURL=logoURL;
    }

   public JobAdsObject(int jobID,String title,String jobCategory,String vaccancyNo,String salary,String usernme,String logoURL){
       this.jobID=jobID;
       this.title=title;
       this.jobCategory=jobCategory;
       this.vaccancyNo=vaccancyNo;
       this.salary=salary;
       this.username=usernme;
       this.logoURL=logoURL;
   }

    public String getTitle() {
        return title;
    }

    public String getVaccancyNo() {
        return vaccancyNo;
    }

    public String getAd_postedDate() {
        return ad_postedDate;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public String getAd_description() {
        return ad_description;
    }

    public String getUsername() {
        return username;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public String getSkills() {
        return skills;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public String getJobCategoryOthers() {
        return jobCategoryOthers;
    }

    public String getJobTime() {
        return jobTime;
    }

    public String getSalary() {
        return salary;
    }

    public String getaDdress() {
        return aDdress;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getWebSite() {
        return webSite;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public int getJobID() {
        return jobID;
    }
}
