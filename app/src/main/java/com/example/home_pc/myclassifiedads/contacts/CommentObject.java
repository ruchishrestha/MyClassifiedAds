package com.example.home_pc.myclassifiedads.contacts;

/**
 * Created by Ruchi on 2015-08-12.
 */
public class CommentObject {
    String tableCategory,username,commentText,commentDate;
    int adid;

    public CommentObject(String commentDate,String username,String commentText){
        this.commentDate=commentDate;
        this.username=username;
        this.commentText=commentText;
    }

    public CommentObject(int adid,String username,String tableCategory){
        this.adid=adid;
        this.tableCategory=tableCategory;
        this.username=username;
    }

    public CommentObject(String tableCategory,String username,int adid,String commentText){
        this.tableCategory=tableCategory;
        this.username=username;
        this.adid=adid;
        this.commentText=commentText;
    }

    public CommentObject(int adid,String tableCategory){
        this.adid=adid;
        this.tableCategory=tableCategory;
    }

}
