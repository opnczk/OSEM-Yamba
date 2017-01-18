package com.sar2016.olivier.alexandra.yamba;

import java.util.Date;

import winterwell.jtwitter.Twitter;

/**
 * Created by alexandra on 16/01/17.
 */

public class Tweet {
    private long id;
    private Date created_at;
    private String txt;
    private String user;

    public Tweet() {}

    public Tweet(Twitter.Status status){
        this.txt = status.getText();
        this.id = status.getId();
        this.user = status.getUser().toString();
        this.setCreatedAt(status.getCreatedAt());
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return this.created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public String getTxt() {
        return this.txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
