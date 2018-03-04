package com.tradinos.dealat2.API;

/**
 * Created by farah on 4/22/16.
 */
public enum APIModel {
    users,
    ads,
    categories,
    commercialAds;
    ;

    @Override
    public String toString () {
        switch (this){
            case users :
                return "users" ;

            case ads:
                return "items_control";

            case categories:
                return "categories_control";

            case commercialAds:
                return "commercial_items_control";

            default:
                return  "" ;
        }
    }
}

