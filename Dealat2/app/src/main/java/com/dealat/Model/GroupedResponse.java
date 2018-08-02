package com.dealat.Model;

import java.util.ArrayList;
import java.util.List;

public class GroupedResponse {
    private List<Category> categories;
    private List<CommercialAd> commercialAds;
    private List<Ad> ads;

    public GroupedResponse() {
        this.commercialAds = new ArrayList<>();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<CommercialAd> getCommercialAds() {
        return commercialAds;
    }

    public void setCommercialAds(List<CommercialAd> commercialAds) {
        this.commercialAds = commercialAds;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }
}
