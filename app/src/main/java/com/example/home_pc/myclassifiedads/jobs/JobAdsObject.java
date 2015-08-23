package com.example.home_pc.myclassifiedads.jobs;

/**
 * Created by Home-PC on 7/31/2015.
 */
public class JobAdsObject {

    String title,ad_postedDate,logoURL, description,userName,responsibility,skills,jobCategory,
            jobTime,vaccancyNo,salary,aDdress,contactNo,emailId,webSite,tableCategory,userID;
    Double latitude,longitude;
    int jobID;

    public JobAdsObject(String userName,String logoURL, String title, String description, String responsibility, String skills, String jobCategory, String jobTime, String vacancies, String salary, String aDdress, String contactNo, String emailId, String webSite, Double latitude,Double longitude) {
        this.userName = userName;
        this.logoURL = logoURL;
        this.title = title;
        this.description = description;
        this.responsibility = responsibility;
        this.skills = skills;
        this.jobCategory = jobCategory;
        this.jobTime = jobTime;
        this.vaccancyNo = vacancies;
        this.salary = salary;
        this.aDdress = aDdress;
        this.contactNo = contactNo;
        this.emailId = emailId;
        this.webSite = webSite;
        this.latitude=latitude;
        this.longitude=longitude;
    }


    public JobAdsObject(String ad_postedDate,String userName,String title, String description, String responsibility, String skills, String jobCategory, String jobTime, String vaccancyNo, String salary, String aDdress, String contactNo, String emailId, String webSite, Double latitude,Double longitude,String logoURL) {
        this.ad_postedDate=ad_postedDate;
        this.userName=userName;
        this.title = title;
        this.description = description;
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

   public JobAdsObject(int jobID,String title,String jobCategory,String vaccancyNo,String salary,String userName,String logoURL){
       this.jobID=jobID;
       this.title=title;
       this.jobCategory=jobCategory;
       this.vaccancyNo=vaccancyNo;
       this.salary=salary;
       this.userName=userName;
       this.logoURL=logoURL;
   }

    public JobAdsObject(int jobID,String tableCategory,String userID){
        this.jobID=jobID;
        this.tableCategory=tableCategory;
        this.userID=userID;
    }

    public String getUserName() {
        return userName;
    }

    public String gettitle() {
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

    public String getDescription() {
        return description;
    }

    public String getUsername() {
        return userName;
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
