package com.example.home_pc.myclassifiedads.mainactivity;

/**
 * Created by Home-PC on 6/30/2015.
 */
public class DrawerItem {
    String itemName;
    int imgResID;
    String title;
    int info;

    public DrawerItem(String title) {
        this(null,0,0);
        this.title = title;
    }
    public DrawerItem(String itemName, int imgResID,int info) {
        super();
        this.itemName = itemName;
        this.imgResID = imgResID;
        this.info=info;
    }

    public String getTitle() {
        return title;
    }
    public void settitle(String title) {
        this.title = title;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public int getImgResID() {
        return imgResID;
    }
    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }
    public int getinfo(){return info;}
    public void setinfo(int info){this.info=info;}

}
