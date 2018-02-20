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
            if (category.getParentId().equals(parentId))
                result.add(category);
        }
        return result;
    }
}
