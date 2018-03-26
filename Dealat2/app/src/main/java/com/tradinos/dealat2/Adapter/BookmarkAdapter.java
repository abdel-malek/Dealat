package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tradinos.dealat2.Model.Bookmark;
import com.tradinos.dealat2.R;

import java.util.List;

/**
 * Created by developer on 26.03.18.
 */

public class BookmarkAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Bookmark> bookmarks;

    public BookmarkAdapter(Context context, List<Bookmark> bookmarks) {
        this.inflater = LayoutInflater.from(context);
        this.bookmarks = bookmarks;
    }

    @Override
    public int getCount() {
        return this.bookmarks.size();
    }

    @Override
    public Bookmark getItem(int i) {
        return this.bookmarks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.inflater.inflate(R.layout.row_bookmark, null);
        }

        view.findViewById(R.id.buttonFalse).setTag(i);
        view.findViewById(R.id.buttonTrue).setTag(i);

        ((TextView)view.findViewById(R.id.title)).setText(getItem(i).getTitle());

        return view;
    }
}
