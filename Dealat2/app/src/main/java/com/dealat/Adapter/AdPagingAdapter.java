package com.dealat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dealat.Model.Ad;
import com.dealat.Model.Category;
import com.dealat.MyApplication;
import com.dealat.R;
import com.dealat.View.AdDetailsActivity;
import com.dealat.View.MasterActivity;
import com.tradinos.core.network.InternetManager;

import java.util.ArrayList;
import java.util.List;

public class AdPagingAdapter extends RecyclerView.Adapter<AdPagingAdapter.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private Context context;
    private LayoutInflater inflater;
    private int resourceLayout;
    private List<Ad> ads;

    private boolean isLoadingAdded = false;


    public AdPagingAdapter(Context context, int resourceLayout) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.resourceLayout = resourceLayout;
        this.ads = new ArrayList<>();
    }

    public void setResourceLayout(int resourceLayout) {
        this.resourceLayout = resourceLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case ITEM:
                return new ViewHolder(inflater.inflate(resourceLayout, null));

            case LOADING:
                return new ViewHolder(inflater.inflate(R.layout.footer_progress_bar, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case ITEM:
                final Ad item = this.ads.get(position);

                holder.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, AdDetailsActivity.class);
                        intent.putExtra("ad", item);

                        context.startActivity(intent);
                    }
                });

                int defaultDrawable = ((MasterActivity) context).getTemplateDefaultImage(item.getTemplate());
                if (item.getMainImageUrl() != null) {

                    ImageLoader mImageLoader = InternetManager.getInstance(context).getImageLoader();
                    mImageLoader.get(MyApplication.getBaseUrl() + item.getMainImageUrl(),
                            ImageLoader.getImageListener(holder.imageView,
                                    defaultDrawable, defaultDrawable));
                } else
                    holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, defaultDrawable));

                if (item.getTemplate() == Category.JOBS)
                    holder.textViewPrice.setVisibility(View.INVISIBLE);
                else
                    holder.textViewPrice.setText(((MasterActivity) context).formattedNumber(item.getPrice()) +
                            " " + context.getString(R.string.sp));

                holder.textViewTitle.setText(item.getTitle());

                if (item.getPublishDate() != null)
                    holder.textViewDate.setText(context.getString(R.string.published)
                            + " " + ((MasterActivity) context).formattedDate(item.getPublishDate()));

                if (item.isFavorite())
                    holder.buttonFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));

                if (item.isFeatured())
                    holder.imageViewFeatured.setVisibility(View.VISIBLE);

                break;

            case LOADING:

        }
    }

    @Override
    public int getItemCount() {
        return ads == null ? 0 : ads.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (resourceLayout == R.layout.row_view2 || resourceLayout == R.layout.row_view2_left) {
            if (position % 2 == 0)
                resourceLayout = R.layout.row_view2;
            else
                resourceLayout = R.layout.row_view2_left;
        }
        return (position == ads.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addAll(List<Ad> ads) {
        if (ads != null && this.ads != null) {
            this.ads.addAll(ads);
            this.notifyDataSetChanged();
        }
    }

    public void clear() {
        if (this.ads != null)
            this.ads.clear();
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        this.ads.add(new Ad());
        notifyItemInserted(ads.size() - 1);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = ads.size() - 1;

        if (position >= 0 && position < ads.size()) { // Avoid ArrayIndexOutOfBoundsException
            Ad item = ads.get(position);

            if (item != null) {
                ads.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, imageViewFeatured;
        TextView textViewPrice, textViewTitle, textView, textViewDate;
        ImageButton buttonFav;
        View container;

        ViewHolder(View view) {
            super(view);

            container = view;
            imageView = view.findViewById(R.id.imageView);
            imageViewFeatured = view.findViewById(R.id.imageViewFeatured);
            textViewPrice = view.findViewById(R.id.textViewPrice);
            textViewTitle = view.findViewById(R.id.title);
            textView = view.findViewById(R.id.textView);
            textViewDate = view.findViewById(R.id.textViewDate);
            buttonFav = view.findViewById(R.id.buttonFav);
        }
    }
}
