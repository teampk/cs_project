package com.pkteam.measure_happiness.model;

/*
 * Created by paeng on 18/08/2018.
 */

public class User {


    private String mId;
    private String mPassword;
    private String mName;
    private String mGender;
    private String mBirth;
    private String mDepartment;
    private String mSmile;

    private String created_at;
    private String newPassword;
    private String token;



    public String getmId() {
        return mId;
    }


    public void setmId(String mId) {
        this.mId = mId;
    }
    public void setmPassword(String mPassword){
        this.mPassword = mPassword;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public void setmBirth(String mBirth) {
        this.mBirth = mBirth;
    }
    public void setmDepartment(String mDepartment){
        this.mDepartment = mDepartment;
    }
    public void setmSmile(String mSmile){
        this.mSmile = mSmile;
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
