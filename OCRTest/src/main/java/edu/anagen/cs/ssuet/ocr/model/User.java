package edu.anagen.cs.ssuet.ocr.model;

import io.realm.RealmObject;

/**
 * Created by hp on 10/8/2017.
 */

public class User extends RealmObject {


    private int id;
    private String fullname;
    private String email;
    private String password;
    private String mobile;


    public User() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
