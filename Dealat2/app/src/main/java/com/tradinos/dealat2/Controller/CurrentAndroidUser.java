package com.tradinos.dealat2.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.tradinos.dealat2.Model.User;


public class CurrentAndroidUser implements CurrentUser {

    Context context;

    public CurrentAndroidUser(Context context) {
        this.context = context;
    }

    public static CurrentAndroidUser getInstance(Context context) {
        return new CurrentAndroidUser(context);
    }

    @Override
    public void Save(User user) {
        SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        if (user.getId() != null) {
            editor.putString("id", user.getId());
            editor.putString("name", user.getName());
            editor.putString("phone", user.getPhone());
            editor.putString("serverKey", user.getServerKey());
            editor.putString("cityId", user.getCityId());
            editor.putString("personal_image", user.getImageUrl());
            editor.putBoolean("visible_phone", user.isVisiblePhone());
        }

        editor.commit();
    }

    @Override
    public Boolean IsLogged() {
        SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        return !(preferences == null || preferences.getString("id", "").equals(""));
    }

    @Override
    public User Get() {
        SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        if (preferences == null || preferences.getString("id", "").equals(""))
            return null;
        else {
            User user = new User();
            user.setId(preferences.getString("id", ""));
            user.setName(preferences.getString("name", ""));
            user.setPhone(preferences.getString("phone", ""));
            user.setServerKey(preferences.getString("serverKey", ""));
            user.setCityId(preferences.getString("cityId", ""));
            user.setImageUrl(preferences.getString("personal_image", ""));
            user.setVisiblePhone(preferences.getBoolean("visible_phone", false));

            return user;
        }
    }

    public void clearUser() {
        SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
