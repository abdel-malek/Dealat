package com.dealat.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by developer on 18.02.18.
 */

public class Ad implements Serializable {
    // Ad status
    public final static int PENDING = 1, ACCEPTED = 2, EXPIRED = 3,
            HIDDEN = 4, REJECTED = 5, DELETED = 6;

    private String id, cityId, locationId, categoryId, publishDate, expiryDate, creationDate;
    private String title, description, locationName, cityName;
    private String sellerId, sellerName, sellerPhone, whatsAppNumber;
    private String rejectNote;
    private String mainImageUrl, mainVideoUrl;
    private double price;
    private int template, status, showPeriod, expiresAfter, days; //days is the number of days of showPeriod
    // but here showPeriod stands for the id of it as we intent to make a management for it
    private boolean negotiable, featured, favorite, visiblePhone;
    private List<String> imagesPaths;

    public Ad(){
        description = "";
        imagesPaths = new ArrayList<>();
    }


    public String getId() {
        return id;
    }

    public String getFormattedId(){
        return String.format(Locale.ENGLISH,"%04d", Integer.valueOf(this.id));
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getWhatsAppNumber() {
        return whatsAppNumber;
    }

    public void setWhatsAppNumber(String whatsAppNumber) {
        this.whatsAppNumber = whatsAppNumber;
    }

    public String getRejectNote() {
        return rejectNote;
    }

    public void setRejectNote(String rejectNote) {
        this.rejectNote = rejectNote;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getMainVideoUrl() {
        return mainVideoUrl;
    }

    public void setMainVideoUrl(String mainVideoUrl) {
        this.mainVideoUrl = mainVideoUrl;
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

    public int getShowPeriod() {
        return showPeriod;
    }

    public void setShowPeriod(int showPeriod) {
        this.showPeriod = showPeriod;
    }

    public int getExpiresAfter() {
        return expiresAfter;
    }

    public void setExpiresAfter(int expiresAfter) {
        this.expiresAfter = expiresAfter;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isVisiblePhone() {
        return visiblePhone;
    }

    public void setVisiblePhone(boolean visiblePhone) {
        this.visiblePhone = visiblePhone;
    }

    public List<String> getImagesPaths() {
        return imagesPaths;
    }

    public void setImagesPaths(List<String> imagesPaths) {
        this.imagesPaths = imagesPaths;
    }

    public void addImagePath(String path){
       this.imagesPaths.add(path);
   }

   public String getImagePath(int i){
       return this.imagesPaths.get(i);
   }

   public boolean isRejected(){
       return this.status == REJECTED;
   }
}
