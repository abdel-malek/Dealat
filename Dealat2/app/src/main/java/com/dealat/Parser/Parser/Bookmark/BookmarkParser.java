package com.dealat.Parser.Parser.Bookmark;

import com.tradinos.core.network.TradinosParser;
import com.dealat.Model.Bookmark;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.Iterator;

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
        bookmark.setCreatedAt(jsonObject.getString("created_at"));

        // Parsing query // every filter field
        JSONObject query = new JSONObject(jsonObject.getString("query"));

        for (Iterator iterator = query.keys(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            if (validData(query.getString(key))) // when search is saved through Web App all fields are saved,
                // fields which aren't included in search are saved with empty values
                bookmark.putField(key, query.getString(key));
        }

        if (validData(jsonObject.getString("results_num")))
            bookmark.setResultNum(jsonObject.getInt("results_num"));

        return bookmark;
    }

    private boolean validData(String data) {
        return !data.equals("null") && !data.equals("");
    }
}
