package com.pkteam.measure_happiness.model;

/**
 * Created by paeng on 18/08/2018.
 */

public class Value {
    private String mEmail;
    private String created_at;
    private int mValue1;
    private int mValue2;
    private int mValue3;
    private int mValue4;
    private int mValue5;
    private int mValue6;
    private int mValue7;
    private int mValue8;
    private int mValue9;
    private int mValue10;

    public Value (String id,
                  String createdAt,
                  int v1,
                  int v2,
                  int v3,
                  int v4,
                  int v5,
                  int v6,
                  int v7,
                  int v8,
                  int v9,
                  int v10){
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







}
