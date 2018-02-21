package com.tradinos.dealat2.Model;

/**
 * Created by developer on 20.02.18.
 */

public class Location extends Item {
    private String cityName, cityId;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getFullName(){
        return this.cityName + " - "+ this.name;
    }
}
