package com.tradinos.dealat2.Model;

/**
 * Created by developer on 20.02.18.
 */

public class Location extends Item {
    private String cityName, cityId;

    public Location(){
    }

    public static Location getNoLocation(String name){
        Location location = new Location();

        location.setId("-1");
        location.setCityName("");
        location.setName(name);
        return location;
    }

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
        if (this.isNothing())
            return name;
        return this.cityName + " - "+ this.name;
    }
}
