package com.tradinos.dealat2.API;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by malek on 4/22/16.
 */
public class URLBuilder {
    private static String SERVER_URL;

    private static final boolean RE_WRITING_URL = true;

    private APIModel model;
    private String action;
    private APIFormat format;
    Context context;

    public URLBuilder(APIModel model, String action) {
        this.model = model;
        this.action = action;
        this.format = APIFormat.JSON;
    }

    public URLBuilder(Context context) {
        this.context = context;
    }

    public static URLBuilder getInstance(Context context) {
        return new URLBuilder(context);
    }

    public void setServerUrl(String serverUrl) {
        SharedPreferences preferences = context.getSharedPreferences("Server", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("server_url", serverUrl);
        editor.commit();
    }

    public String getServerUrl() {
        // DON'T FORGET TO CHANGE getBaseUrl IN MyApplication
        SharedPreferences preferences = context.getSharedPreferences("Server", Context.MODE_PRIVATE);
        if (preferences == null)

            //    SERVER_URL="http://192.168.9.90/Dealat/index.php/api";
            //    SERVER_URL = "http://dealat.tradinos.com/index.php/api";
            SERVER_URL = "http://deal-at.com/index.php/api";
        else {
            //    SERVER_URL = preferences.getString("server_url", "http://192.168.9.90/Dealat/index.php/api" + "");
            //  SERVER_URL = preferences.getString("server_url", "http://dealat.tradinos.com/index.php/api" + "");
            SERVER_URL = preferences.getString("server_url", "http://deal-at.com/index.php/api" + "");
        }
        return SERVER_URL;
    }

    public URLBuilder(APIModel model, String action, APIFormat format) {
        this.model = model;
        this.action = action;
        this.format = format;
    }

    public String getURL(Context context) {
        this.context = context;
        String url;
        if (RE_WRITING_URL)
            url = getServerUrl() + "/" + model.toString() + "/" + action + "/format/" + format.toString() + "?";
        else
            url = getServerUrl() + "?method=" + model.toString() + "." + action + "&format=" + format.toString();

        return url;
    }
}