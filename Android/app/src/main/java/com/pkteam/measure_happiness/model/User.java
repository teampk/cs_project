package com.pkteam.measure_happiness.model;

/*
 * Created by paeng on 18/08/2018.
 */

public class User {


    private String mEmail;
    private String mPassword;
    private String mName;
    private String mGender;
    private String mBirth;
    private String mSmile;

    private String created_at;
    private String newPassword;
    private String token;



    public void setEmail(String email) {
        this.mEmail = email;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
