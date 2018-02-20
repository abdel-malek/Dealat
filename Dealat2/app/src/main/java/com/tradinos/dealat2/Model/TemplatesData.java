package com.tradinos.dealat2.Model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 20.02.18.
 */

public class TemplatesData {

    private List<Item> educations, schedules;
    private HashMap<Integer, List<Type>> types;

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

    public HashMap<Integer, List<Type>> getTypes() {
        return types;
    }

    public void setTypes(HashMap<Integer, List<Type>> types) {
        this.types = types;
    }
}
