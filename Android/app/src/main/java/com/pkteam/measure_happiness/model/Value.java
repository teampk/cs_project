package com.pkteam.measure_happiness.model;

/*
 * Created by paeng on 18/08/2018.
 */

import java.io.Serializable;

public class Value implements Serializable {
    private String userId;
    private String createdAt;
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
        this.createdAt = createdAt;
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

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getValue1() {
        return value1;
    }

    public String getValue2() {
        return value2;
    }

    public String getValue3() {
        return value3;
    }

    public String getValue4() {
        return value4;
    }

    public String getValue5() {
        return value5;
    }

    public String getValue6() {
        return value6;
    }

    public String getValue7() {
        return value7;
    }

    public String getValue8() {
        return value8;
    }

    public String getValue9() {
        return value9;
    }

    public String getValue10() {
        return value10;
    }
}
