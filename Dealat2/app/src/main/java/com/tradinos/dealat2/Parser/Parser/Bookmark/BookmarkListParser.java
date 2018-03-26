package com.tradinos.dealat2.Parser.Parser.Bookmark;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Bookmark;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 25.03.18.
 */

public class BookmarkListParser implements TradinosParser<List<Bookmark>> {
    @Override
    public List<Bookmark> Parse(String text) throws JSONException {

        List<Bookmark> bookmarks = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(text);
        JSONObject jsonObject;
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            bookmarks.add(new BookmarkParser().Parse(jsonObject));
        }

        return bookmarks;
    }
}
