/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.model;

/**
 *
 * @author FRIENDLY
 */
public class User {
    private int userid;
    private String username,userpass,usertype;
    
    public User(int userid, String username, String userpass, String usertype) {
        this.userid = userid;
        this.username = username;
        this.userpass = userpass;
        this.usertype = usertype;
    }

    public User() {
      
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    @Override
    public String toString() {
        return "User{" + "userid=" + userid + ", username=" + username + ", userpass=" + userpass + ", usertype=" + usertype + '}';
    }
    
}
