package com.tradinos.dealat2.Parser.Parser.Bookmark;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Bookmark;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 25.03.18.
 */

public class BookmarkParser implements TradinosParser<Bookmark> {
    @Override
    public Bookmark Parse(String text) throws JSONException {
        return null;
    }

    public Bookmark Parse(JSONObject jsonObject) throws JSONException {
        Bookmark bookmark = new Bookmark();

        bookmark.setId(jsonObject.getString("user_bookmark_id"));
        bookmark.setQuery(jsonObject.getString("query"));
        bookmark.setCreatedAt(jsonObject.getString("created_at"));

        if (validData(jsonObject.getString("results_num")))
            bookmark.setResultNum(jsonObject.getInt("results_num"));

        bookmark.setTitle("Bookmark "+bookmark.getId());

        return bookmark;
    }

    private boolean validData(String data) {
        return !data.equals("null");
    }
}
