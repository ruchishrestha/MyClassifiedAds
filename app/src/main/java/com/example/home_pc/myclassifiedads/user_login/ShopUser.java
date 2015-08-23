package com.example.home_pc.myclassifiedads.user_login;

/**
 * Created by Home-PC on 7/29/2015.
 */
public class ShopUser {

    String shopName,owner,aDdress,panNo,userName,passWord,contactNo,mobileNo,emailId,webSite,shopPicURL;
    Double latitude,longitude;

    public ShopUser(String userName,String passWord, String shopName, String owner, String panNo, String aDdress, String contactNo, String mobileNo, String emailId, String webSite,  Double latitude, Double longitude, String shopPicURL) {
        this.shopPicURL = shopPicURL;
        this.shopName = shopName;
        this.owner = owner;
        this.aDdress = aDdress;
        this.panNo = panNo;
        this.userName = userName;
        this.contactNo = contactNo;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
        this.webSite = webSite;
        this.passWord = passWord;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ShopUser(String shopName, String owner, String passWord, String panNo, String aDdress, String contactNo, String mobileNo, String emailId, String webSite, Double latitude, Double longitude, String shopPicURL) {
        this.shopName = shopName;
        this.owner = owner;
        this.passWord = passWord;
        this.panNo = panNo;
        this.aDdress = aDdress;
        this.contactNo = contactNo;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
        this.webSite = webSite;
        this.latitude = latitude;
        this.longitude = longitude;
        this.shopPicURL = shopPicURL;
    }

    public String getShopPicURL() {
        return shopPicURL;
    }

    public void setShopPicURL(String shopPicURL) {
        this.shopPicURL = shopPicURL;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getaDdress() {
        return aDdress;
    }

    public void setaDdress(String aDdress) {
        this.aDdress = aDdress;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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
