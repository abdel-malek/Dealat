package com.tradinos.dealat2.Model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 20.02.18.
 */

public class TemplatesData {

    private List<Item> educations, certificates, schedules, showPeriods, propertyStates;
    private HashMap<Integer, List<Type>> brands;
    private List<City> cities;

    public List<Item> getEducations() {
        return educations;
    }

    public void setEducations(List<Item> educations) {
        this.educations = educations;
    }

    public List<Item> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Item> certificates) {
        this.certificates = certificates;
    }

    public List<Item> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Item> schedules) {
        this.schedules = schedules;
    }

    public List<Item> getShowPeriods() {
        return showPeriods;
    }

    public void setShowPeriods(List<Item> showPeriods) {
        this.showPeriods = showPeriods;
    }

    public List<Item> getPropertyStates() {
        return propertyStates;
    }

    public void setPropertyStates(List<Item> propertyStates) {
        this.propertyStates = propertyStates;
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
