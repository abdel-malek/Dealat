package com.dealat.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 18.02.18.
 */

public class Type extends Item {

    private int templateId;
    private List<Item> models;
    private String fullName, categoryId;

    public Type(){ // every type has "--" option
        this.models = new ArrayList<>();
        this.addNoModel();
    }

    public static Type getNoType(){
        Type type = new Type();

        type.setId("-1");
        type.setName("--");
        type.setFullName("--");
        type.setCategoryId("-1");

        return type;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public List<Item> getModels() {
        return models;
    }

    public void setModels(List<Item> models) {
        this.models = models;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void addNoModel(){
        if (this.models == null)
            return;
        this.models.add(0, getNoItem());
    }
}
