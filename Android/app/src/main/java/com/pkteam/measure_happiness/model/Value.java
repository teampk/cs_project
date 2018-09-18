package com.pkteam.measure_happiness.model;

/**
 * Created by paeng on 18/08/2018.
 */

public class Value {
    private String mEmail;
    private String created_at;
    private String mValue1;
    private String mValue2;
    private String mValue3;
    private String mValue4;
    private String mValue5;
    private String mValue6;
    private String mValue7;
    private String mValue8;
    private String mValue9;
    private String mValue10;

    public Value (String id,
                  String createdAt,
                  String v1,
                  String v2,
                  String v3,
                  String v4,
                  String v5,
                  String v6,
                  String v7,
                  String v8,
                  String v9,
                  String v10){
        this.mEmail = id;
        this.created_at = createdAt;
        this.mValue1 = v1;
        this.mValue2 = v2;
        this.mValue3 = v3;
        this.mValue4 = v4;
        this.mValue5 = v5;
        this.mValue6 = v6;
        this.mValue7 = v7;
        this.mValue8 = v8;
        this.mValue9 = v9;
        this.mValue10 = v10;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
