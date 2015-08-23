package com.example.home_pc.myclassifiedads.user_login;

/**
 * Created by Home-PC on 7/29/2015.
 */
public class OrganizationUser {


    String organizationName,aDdress,registrationNo,userName,passWord,contactNo,mobileNo,emailId,webSite,organizationPicURL;
    Double latitude,longitude;

    public OrganizationUser(String userName, String passWord, String organizationName,String registrationNo, String aDdress, String contactNo, String mobileNo, String emailId, String webSite, Double latitude, Double longitude,String organizationPicURL) {
        this.organizationPicURL = organizationPicURL;
        this.organizationName = organizationName;
        this.aDdress = aDdress;
        this.registrationNo = registrationNo;
        this.userName = userName;
        this.passWord = passWord;
        this.contactNo = contactNo;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
        this.webSite = webSite;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public OrganizationUser(String organizationName, String registrationNo, String passWord, String aDdress, String contactNo, String mobileNo, String emailId, String webSite, Double latitude, Double longitude, String organizationPicURL) {
        this.organizationName = organizationName;
        this.registrationNo = registrationNo;
        this.passWord = passWord;
        this.aDdress = aDdress;
        this.contactNo = contactNo;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
        this.webSite = webSite;
        this.latitude = latitude;
        this.longitude = longitude;
        this.organizationPicURL = organizationPicURL;
    }

    public String getOrganizationPic() {
        return organizationPicURL;
    }

    public void setOrganizationPic(String organizationPicURL) {
        this.organizationPicURL = organizationPicURL;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getaDdress() {
        return aDdress;
    }

    public void setaDdress(String aDdress) {
        this.aDdress = aDdress;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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
