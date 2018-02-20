package com.tradinos.dealat2.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by developer on 18.02.18.
 */

public class Category implements Serializable{
    private String id, name, parentId, imageUrl;
    private int templateId;
    private List<Category> subCategories;


    public static Category getMain(){
        Category category = new Category();

        category.setId("0");
        category.setParentId("-1");

        return category;
    }

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public void addSubCat(Category category){
        this.subCategories.add(0, category);
    }
}
