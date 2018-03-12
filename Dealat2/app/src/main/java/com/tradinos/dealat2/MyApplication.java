package com.tradinos.dealat2;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tradinos.dealat2.Model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 18.02.18.
 */

public class MyApplication extends Application {

    static List<Category> allCategories;

    static SharedPreferences sharedPreferences;


    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sharedPreferences = getSharedPreferences("dealat", Context.MODE_PRIVATE);
    }

    public static void saveUserState(int state){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("userState", state);
        editor.commit();
    }

    public static int getUserState(){
        return sharedPreferences.getInt("userState", 1);
    }

    public static List<Category> getAllCategories() {
        if (allCategories == null) {
            Gson gson = new Gson();
            int size = sharedPreferences.getInt("allCategories_size", 0);
            allCategories = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                String json = sharedPreferences.getString("category" + i, "");
                allCategories.add(gson.fromJson(json, Category.class));
            }
        }

        return allCategories;
    }

    public void setAllCategories(List<Category> allCategories) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (int i = 0; i < allCategories.size(); i++) {
            Gson gson = new Gson();
            String json = gson.toJson(allCategories.get(i));
            editor.putString("category" + i, json);
        }

        editor.putInt("allCategories_size", allCategories.size());
        editor.commit();

        MyApplication.allCategories = allCategories;
    }

    public static Category getCategoryById(String id) {
        Category category;

        List<Category> categories = getAllCategories();

        for (int i = 0; i < categories.size(); i++) {
            category = categories.get(i);
            if (category.getId().equals(id))
                return category;
        }
        return null;
    }

    public List<Category> getSubCatsById(String parentId) {

        List<Category> categories = getAllCategories();
        List<Category> result = new ArrayList<>();
        Category category;

        for (int i = 0; i < categories.size(); i++) {
            category = categories.get(i);
            if (category.getParentId().equals(parentId)){
                result.add(category);
                hasChildAtLeast(category);
            }
        }
        return result;
    }

    private boolean hasChildAtLeast(Category parentCat){
        List<Category> categories = getAllCategories();
        Category category;

        for (int i = 0; i < categories.size(); i++) {
            category = categories.get(i);
            if (category.getParentId().equals(parentCat.getId())){
                parentCat.addSubCat(category);
                return true;
            }
        }
        return false;
    }

    public int getCurrentView(){
        return sharedPreferences.getInt("view", 1);
    }

    public void setCurrentView(int i){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("view", i);
        editor.commit();
    }

    public static String getBaseUrlForImages(){
        return "http://dealat.tradinos.com/";
        //return "http://192.168.9.53/Dealat/";
    }
}
