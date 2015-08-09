package com.example.home_pc.myclassifiedads.jobs;

import android.graphics.Bitmap;

/**
 * Created by Home-PC on 7/31/2015.
 */
public class JobAdsObject {

    Bitmap organizationLogo;
    String title,description,responsibility,skills,jobCategory,jobCategoryOthers,jobTiming,vacancies,salary,aDdress,contactNo,emailId,webSite;
    Double latitude,longitude;

    public JobAdsObject(Bitmap organizationLogo, String title, String description, String responsibility, String skills, String jobCategory, String jobCategoryOthers, String jobTiming, String vacancies, String salary, String aDdress, String contactNo, String emailId, String webSite, Double latitude,Double longitude) {
        this.organizationLogo = organizationLogo;
        this.title = title;
        this.description = description;
        this.responsibility = responsibility;
        this.skills = skills;
        this.jobCategory = jobCategory;
        this.jobCategoryOthers = jobCategoryOthers;
        this.jobTiming = jobTiming;
        this.vacancies = vacancies;
        this.salary = salary;
        this.aDdress = aDdress;
        this.contactNo = contactNo;
        this.emailId = emailId;
        this.webSite = webSite;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public Bitmap getOrganizationLogo() {
        return organizationLogo;
    }

    public void setOrganizationLogo(Bitmap organizationLogo) {
        this.organizationLogo = organizationLogo;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getJobCategoryOthers() {
        return jobCategoryOthers;
    }

    public void setJobCategoryOthers(String jobCategoryOthers) {
        this.jobCategoryOthers = jobCategoryOthers;
    }

    public String getJobTiming() {
        return jobTiming;
    }

    public void setJobTiming(String jobTiming) {
        this.jobTiming = jobTiming;
    }

    public String getVacancies() {
        return vacancies;
    }

    public void setVacancies(String vacancies) {
        this.vacancies = vacancies;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
