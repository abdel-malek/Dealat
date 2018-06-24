package com.dealat.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 02.04.18.
 */

public class City extends Item {
    private List<Item> locations;

    public static City getNoCity(){
        City city = new City();

        city.setId("-1");
        city.setName("--");

        return city;
    }

    public City(){
        this.locations = new ArrayList<>();
        this.addNoLocation();
    }

    public List<Item> getLocations() {
        return locations;
    }

    public void setLocations(List<Item> locations) {
        this.locations = locations;
    }

    public void addNoLocation(){
        if (locations == null)
            locations = new ArrayList<>();

        locations.add(0, getNoItem());
    }
}
