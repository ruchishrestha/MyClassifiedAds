package com.example.home_pc.myclassifiedads.contacts;

/**
 * Created by Ruchi on 2015-08-12.
 */
public class CommentObject {
   public String tableCategory,username,commentText,commentDate,userID;
   public int adid;

    public CommentObject(String commentDate,String username,String commentText){
        this.commentDate=commentDate;
        this.username=username;
        this.commentText=commentText;
    }

    public CommentObject(int adid,String userID,String tableCategory){
        this.adid=adid;
        this.tableCategory=tableCategory;
        this.userID=userID;
    }

    public CommentObject(String tableCategory,String userID,int adid,String commentText){
        this.tableCategory=tableCategory;
        this.userID=userID;
        this.adid=adid;
        this.commentText=commentText;
    }

    public CommentObject(int adid,String tableCategory){
        this.adid=adid;
        this.tableCategory=tableCategory;
    }

}
