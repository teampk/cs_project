package com.pkteam.measure_happiness.model;

/*
 * Created by paeng on 18/08/2018.
 */

public class Value {
    private String userId;
    private String created_at;
    private String value1;
    private String value2;
    private String value3;
    private String value4;
    private String value5;
    private String value6;
    private String value7;
    private String value8;
    private String value9;
    private String value10;

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
        this.userId = id;
        this.created_at = createdAt;
        this.value1 = v1;
        this.value2 = v2;
        this.value3 = v3;
        this.value4 = v4;
        this.value5 = v5;
        this.value6 = v6;
        this.value7 = v7;
        this.value8 = v8;
        this.value9 = v9;
        this.value10 = v10;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUserId() {
        return userId;
    }

    public String getCreated_at() {
        return created_at;
    }
}
