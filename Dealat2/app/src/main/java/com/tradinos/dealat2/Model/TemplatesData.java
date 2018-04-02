package com.tradinos.dealat2.Model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 20.02.18.
 */

public class TemplatesData {

    private List<Item> educations, schedules;
    private HashMap<Integer, List<Type>> brands;
    //private List<Location> locations;
    private List<City> cities;

    public List<Item> getEducations() {
        return educations;
    }

    public void setEducations(List<Item> educations) {
        this.educations = educations;
    }

    public List<Item> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Item> schedules) {
        this.schedules = schedules;
    }

    public HashMap<Integer, List<Type>> getBrands() {
        return brands;
    }

    public void setBrands(HashMap<Integer, List<Type>> brands) {
        this.brands = brands;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
