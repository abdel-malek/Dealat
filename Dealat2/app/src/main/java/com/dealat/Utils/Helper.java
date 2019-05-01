package com.dealat.Utils;

import android.net.Uri;

import static com.dealat.GlobalConstants.QUERY_PARA_AD_ID;
import static com.dealat.GlobalConstants.QUERY_PARA_TEMPLATE_ID;
import static com.dealat.GlobalConstants.URL_AUTHORITY;
import static com.dealat.GlobalConstants.URL_SCHEME;

public class Helper {
    public static String GenerateAdDetailsURL(String id, Integer template){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(URL_SCHEME)
                .authority(URL_AUTHORITY)
                .appendPath("index.php")
                .appendPath("home_control")
                .appendPath("load_ad_details")
                .appendQueryParameter(QUERY_PARA_AD_ID, id)
                .fragment("section-name");

        return builder.build().toString();
    }

    public static String GenerateHomeURL(){

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(URL_SCHEME)
                .authority(URL_AUTHORITY);

        return builder.build().toString();
    }

}
