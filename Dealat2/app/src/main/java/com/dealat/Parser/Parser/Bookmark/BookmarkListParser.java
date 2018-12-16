package com.dealat.Parser.Parser.Bookmark;

import com.google.android.gms.common.util.JsonUtils;
import com.tradinos.core.network.TradinosParser;
import com.dealat.Model.Bookmark;

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
            try {
                bookmarks.add(new BookmarkParser().Parse(jsonObject));

            } catch (JSONException e) {
            }
        }

        return bookmarks;
    }
}
