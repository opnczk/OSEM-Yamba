package com.sar2016.olivier.alexandra.yamba;

import java.util.Date;

/**
 * Created by alexandra on 16/01/17.
 */

public class Tweet {
    private int id;
    private Date created_at;
    private String txt;
    private String user;

    public Tweet() {}

    public Tweet(Date created_at, String txt, String user) {
        this.created_at = created_at;
        this.txt = txt;
        this.user = user;
    }

    public Tweet(String txt, String user) {
        this.txt = txt;
        this.user = user;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
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
