package com.tradinos.dealat2.Model;

import java.io.Serializable;

/**
 */
public class User implements Serializable {


    private String id;
    private int role;
    private String name;
    private String username;
    private String password;
    private int country;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRole(){return role;}

    public void setRole(int role){
        this.role = role;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
