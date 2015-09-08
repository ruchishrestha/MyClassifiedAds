package com.example.home_pc.myclassifiedads.sales;

/**
 * Created by Home-PC on 7/31/2015.
 */
public class SalesAdsObject {
    int salesID;
    String userName,title,description,category,brand,modelNo,price,status,contactNo,condition,usedTime,ad_postedDate,rating;
    Double rating1;

    public SalesAdsObject(String userName, String title, String description, String category, String brand, String modelNo, String price, String status, String contactNo, String condition, String usedTime, String rating) {
        this.userName = userName;
        this.title = title;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.modelNo = modelNo;
        this.price = price;
        this.status = status;
        this.contactNo = contactNo;
        this.condition = condition;
        this.usedTime = usedTime;
        this.rating=rating;
    }

    public SalesAdsObject(int salesID, String userName, String title, String brand, String modelNo, String price, String status, String condition, Double rating){
        this.salesID=salesID;
        this.userName=userName;
        this.rating1=rating;
        this.title=title;
        this.price=price;
        this.status=status;
        this.brand=brand;
        this.modelNo=modelNo;
        this.condition=condition;
    }

    public SalesAdsObject(int salesID, String userName, String title, String brand, String modelNo, String price, String status, String condition, Double rating, String ad_postedDate, String usedTime, String contactNo, String description,String category){
        this.salesID=salesID;
        this.ad_postedDate=ad_postedDate;
        this.usedTime=usedTime;
        this.contactNo=contactNo;
        this.description=description;
        this.userName=userName;
        this.rating1=rating;
        this.title=title;
        this.price=price;
        this.status=status;
        this.brand=brand;
        this.modelNo=modelNo;
        this.condition=condition;
        this.category=category;
    }

    public SalesAdsObject(int salesID, String userName, String title, String brand, String modelNo, String price, String status, String condition, Double rating, String salesCategory){
        this.salesID=salesID;
        this.userName=userName;
        this.rating1=rating;
        this.title=title;
        this.price=price;
        this.status=status;
        this.brand=brand;
        this.modelNo=modelNo;
        this.condition=condition;
        this.category=salesCategory;
    }

    public SalesAdsObject(int salesID, String title,String description,String category, String brand, String modelNo, String price, String status, String contactNo,String condition, String usedTime){
        this.salesID=salesID;
        this.description=description;
        this.category=category;
        this.title=title;
        this.price=price;
        this.status=status;
        this.brand=brand;
        this.modelNo=modelNo;
        this.condition=condition;
        this.contactNo=contactNo;
        this.usedTime=usedTime;
    }

    public int getSalesID() {
        return salesID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
