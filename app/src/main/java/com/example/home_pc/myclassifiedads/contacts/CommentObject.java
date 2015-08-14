package com.example.home_pc.myclassifiedads.contacts;

/**
 * Created by Ruchi on 2015-08-12.
 */
public class CommentObject {
    String category,username,commentText;
    int adid;
    public CommentObject(String category,String username,int adid,String commentText){
        this.category=category;
        this.username=username;
        this.adid=adid;
        this.commentText=commentText;
    }
}
