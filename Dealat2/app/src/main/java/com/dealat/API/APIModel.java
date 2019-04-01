package com.dealat.API;

/**
 * Created by farah on 4/22/16.
 */
public enum APIModel {
    users,
    qrUsers,
    ads,
    categories,
    commercialAds,
    homeControl,
    data;

    @Override
    public String toString () {
        switch (this){
            case homeControl :
                return "home_control" ;

            case users :
                return "users_control" ;

            case qrUsers:
                return "QR_users_control";

            case ads:
                return "items_control";

            case categories:
                return "categories_control";

            case commercialAds:
                return "commercial_items_control";

            case data:
                return "data_control";

            default:
                return  "" ;
        }
    }
}

