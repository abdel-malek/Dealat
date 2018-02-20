package com.tradinos.dealat2.Model;

import com.tradinos.dealat2.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 18.02.18.
 */

public class Category implements Serializable {

    // templates
    public static final int VEHICLES = 1, PROPERTIES = 2, MOBILES = 3, ELECTRONICS = 4,
            FASHION = 5, KIDS = 6, SPORTS = 7, JOBS = 8, INDUSTRIES = 9, SERVICES = 10;

    private String id, name, parentId, imageUrl;
    private int templateId;
    private List<Category> subCategories;

    public Category() {
        this.subCategories = new ArrayList<>();
    }

    public static Category getMain() {
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

    public String getFullName() {
        if (parentId.equals("0"))
            return this.name;
        return MyApplication.getCategoryById(this.parentId).getFullName() + " - " + this.name;
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

    public void addSubCat(Category category) {
        this.subCategories.add(0, category);
    }

    public boolean hasSubCats() {
        if (this.subCategories.size() > 0)
            return true;
        return false;
    }
}
