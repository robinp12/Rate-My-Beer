package com.example.ratemybeer;

import com.google.firebase.database.ServerValue;

import java.sql.Timestamp;

public class Comment {

    private String content,uid,uimg,uname, id, beername;
    private String timestamp;


    public Comment() {
    }

    public Comment(String content, String uid, String uimg, String uname, String Beername,String timestamp) {
        this.content = content;
        this.uid = uid;
        this.uimg = uimg;
        this.uname = uname;
        this.timestamp = timestamp;
        this.id = uname+'_'+ timestamp;
        this.beername = Beername;

    }
/*
    public Comment(String content, String uid, String uimg, String uname, Object timestamp) {
        this.content = content;
        this.uid = uid;
        this.uimg = uimg;
        this.uname = uname;
        this.timestamp = timestamp;
    }*/

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUimg() {
        return uimg;
    }

    public void setUimg(String uimg) {
        this.uimg = uimg;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getBeername() { return beername; }

    public void setBeername(String beername) { this.beername = beername; }
}
