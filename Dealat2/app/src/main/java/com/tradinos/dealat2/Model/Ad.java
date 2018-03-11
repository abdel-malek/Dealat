package com.tradinos.dealat2.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 18.02.18.
 */

public class Ad implements Serializable {
    // Ad status
    public final static int PENDING = 1, ACCEPTED = 2, EXPIRED = 3,
            HIDDEN = 4, REJECTED = 5, DELETED = 6;


    private String id, locationId, categoryId, publishDate;
    private String title, description, locationName, cityName;
    private String mainImageUrl;
    private double price;
    private int template, status;
    private boolean negotiable, featured;
    private List<String> imagesPaths;

    public Ad(){
        description = "";
        imagesPaths = new ArrayList<>();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isNegotiable() {
        return negotiable;
    }

    public void setNegotiable(boolean negotiable) {
        this.negotiable = negotiable;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public List<String> getImagesPaths() {
        return imagesPaths;
    }

   public void addImagePath(String path){
       this.imagesPaths.add(path);
   }

   public String getImagePath(int i){
       return this.imagesPaths.get(i);
   }
}
