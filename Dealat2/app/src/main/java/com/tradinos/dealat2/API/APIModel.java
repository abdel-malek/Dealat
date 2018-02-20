package com.tradinos.dealat2.API;

/**
 * Created by farah on 4/22/16.
 */
public enum APIModel {
    users,
    ads,
    categories
    ;

    @Override
    public String toString () {
        switch (this){
            case users :
                return "users" ;

            case ads:
                return "ads_control";

            case categories:
                return "categories_control";

            default:
                return  "" ;
        }
    }
}

