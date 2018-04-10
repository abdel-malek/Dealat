package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tradinos.dealat2.Model.Bookmark;
import com.tradinos.dealat2.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        view = this.inflater.inflate(R.layout.row_bookmark, null);

        view.findViewById(R.id.buttonFalse).setTag(i);
        view.findViewById(R.id.buttonTrue).setTag(i);

        final int index = 1; // to get second view, which is for sure TextView
        HashMap<String, String> fields = getItem(i).getFields();
        LinearLayout container;
        TextView textView;
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            container = view.findViewWithTag(entry.getKey());
            if (container != null) {
                container.setVisibility(View.VISIBLE);
                textView = (TextView) container.getChildAt(index);
                if (textView != null)
                    textView.setText(entry.getValue());
            }
        }

        return view;
    }
}
