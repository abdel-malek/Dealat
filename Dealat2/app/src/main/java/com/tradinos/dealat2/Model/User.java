package com.tradinos.dealat2.Model;

import java.io.Serializable;

/**
 */
public class User implements Serializable {

    public static final int NOT_REGISTERED = 1, PENDING = 2, REGISTERED = 3;

    private String id, name, phone;
    private String serverKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getServerKey() {
        return serverKey;
    }

    public void setServerKey(String serverKey) {
        this.serverKey = serverKey;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
