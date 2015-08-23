package com.example.home_pc.myclassifiedads.user_login;

/**
 * Created by Home-PC on 7/29/2015.
 */
public class IndividualUser {

    String firstName,middleName,lastName,userName,passWord,aDdress,contactNo,mobileNo,emailId,webSite,profilePicURL;

    public IndividualUser(String userName,String passWord,String firstName,String middleName,String lastName,String aDdress,String contactNo,String mobileNo,String emailId,String webSite,String profilePicURL){

        this.userName=userName;
        this.passWord=passWord;
        this.firstName=firstName;
        this.middleName=middleName;
        this.lastName=lastName;
        this.aDdress=aDdress;
        this.contactNo=contactNo;
        this.mobileNo=mobileNo;
        this.emailId=emailId;
        this.webSite=webSite;
        this.profilePicURL=profilePicURL;

    }

    public IndividualUser(String firstName, String middleName, String lastName, String passWord, String aDdress, String contactNo, String mobileNo, String emailId, String webSite, String profilePicURL) {
        this.middleName = middleName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passWord = passWord;
        this.aDdress = aDdress;
        this.contactNo = contactNo;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
        this.webSite = webSite;
        this.profilePicURL = profilePicURL;
    }

    public String getProfilePic() {
        return profilePicURL;
    }

    public void setProfilePic(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
