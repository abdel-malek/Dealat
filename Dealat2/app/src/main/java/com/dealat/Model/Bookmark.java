package com.dealat.Model;

import java.util.HashMap;

/**
 * Created by developer on 25.03.18.
 */

public class Bookmark { // saved searches
    private String id, createdAt;
    private int resultNum;

    private HashMap<String, String> fields;

    public Bookmark(){
        this.fields = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String, String> getFields() {
        return fields;
    }

    public void putField(String key, String value) {
        this.fields.put(key, value);
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getResultNum() {
        return resultNum;
    }

    public void setResultNum(int resultNum) {
        this.resultNum = resultNum;
    }
}
