package com.dealat.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealat.Model.Bookmark;
import com.dealat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookmarkPagingAdapter extends RecyclerView.Adapter<BookmarkPagingAdapter.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private LayoutInflater inflater;
    private List<Bookmark> bookmarks;

    private boolean isLoadingAdded = false;


    public BookmarkPagingAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        bookmarks = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM:
                return new ViewHolder(inflater.inflate(R.layout.row_bookmark, null));

            case LOADING:
                return new ViewHolder(inflater.inflate(R.layout.footer_progress_bar, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (getItemViewType(position)) {

            case ITEM:

                holder.buttonDelete.setTag(position);
                holder.buttonView.setTag(position);

                 // hide everything
                for (int i=0; i< holder.container.getChildCount(); i++)
                    holder.container.getChildAt(i).setVisibility(View.GONE);


                final int index = 1; // to get second view, which is for sure TextView
                HashMap<String, String> fields = bookmarks.get(position).getFields();
                LinearLayout container;
                TextView textView;
                for (Map.Entry<String, String> entry : fields.entrySet()) {
                    container = holder.container.findViewWithTag(entry.getKey());
                    if (container != null) {
                        container.setVisibility(View.VISIBLE);
                        textView = (TextView) container.getChildAt(index);
                        if (textView != null)
                            textView.setText(entry.getValue());
                    }
                }

                holder.container.findViewById(R.id.container2).setVisibility(View.VISIBLE);
                break;

            case LOADING:

        }
    }

    public Bookmark getItem(int position) {
        return this.bookmarks.get(position);
    }

    @Override
    public int getItemCount() {
        return bookmarks == null ? 0 : bookmarks.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == bookmarks.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void removeItem(int position){
        this.bookmarks.remove(position);
        this.notifyDataSetChanged();
    }

    public void addAll(List<Bookmark> bookmarks) {
        if (bookmarks != null && this.bookmarks != null)
            this.bookmarks.addAll(bookmarks);

        notifyDataSetChanged();
    }

    public void clear() {
        if (this.bookmarks != null)
            this.bookmarks.clear();
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        this.bookmarks.add(new Bookmark());
        notifyItemInserted(bookmarks.size() - 1);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = bookmarks.size() - 1;

        if (position >= 0 && position < bookmarks.size()) { // Avoid ArrayIndexOutOfBoundsException
            Bookmark item = bookmarks.get(position);

            if (item != null) {
                bookmarks.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        Button buttonView, buttonDelete;
        LinearLayout container;

        public ViewHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            buttonView = itemView.findViewById(R.id.buttonTrue);
            buttonDelete = itemView.findViewById(R.id.buttonFalse);
        }
    }
}
