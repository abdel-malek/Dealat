package com.dealat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealat.Model.Ad;
import com.dealat.MyApplication;
import com.dealat.R;
import com.dealat.View.AdDetailsActivity;
import com.dealat.View.MasterActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdPagingAdapter extends RecyclerView.Adapter<MyAdPagingAdapter.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private Context context;
    private LayoutInflater inflater;
    private List<Ad> ads;

    private boolean isLoadingAdded = false;

    public MyAdPagingAdapter(Context context) {
        this.ads = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM:
                return new ViewHolder(inflater.inflate(R.layout.row_my_ad, null));

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
                    public void onClick(View v) {

                        Intent intent = new Intent(context, AdDetailsActivity.class);
                        intent.putExtra("ad", item);
                        context.startActivity(intent);
                    }
                });

                int defaultDrawable = ((MasterActivity) context).getTemplateDefaultImage(item.getTemplate());
                if (item.getMainImageUrl() != null) {

                    Picasso.with(context)
                            .load(MyApplication.getBaseUrl() + item.getMainImageUrl())
                            .into(holder.imageViewMain);
                } else
                    holder.imageViewMain.setImageDrawable(ContextCompat.getDrawable(context, defaultDrawable));

                holder.textViewTitle.setText(item.getTitle());

                if (item.getPublishDate() == null) {
                    holder.textViewDate.setText("");
                    holder.textViewExpires.setText("");
                } else {
                    holder.textViewDate.setText(context.getString(R.string.published)
                            + " " + ((MasterActivity) context).formattedDate(item.getPublishDate()));

                    // only published ads their expiry dates are calculated
                    String text;
                    if (item.getStatus() == Ad.ACCEPTED && item.getExpiresAfter() < 0) {
                        text = context.getString(R.string.expired);
                        item.setStatus(Ad.EXPIRED); // we need to change it to filter in MyAdsFragment
                    } else
                        text = context.getString(R.string.expires);

                    holder.textViewExpires.setText(text + " " + ((MasterActivity) context).formattedDate(item.getExpiryDate()));
                }

                int statusRsc;
                String statusString;

                switch (item.getStatus()) {
                    case Ad.PENDING:
                        statusRsc = R.drawable.bending;
                        statusString = context.getString(R.string.statusPending);
                        break;

                    case Ad.ACCEPTED:
                        statusRsc = R.drawable.check;
                        statusString = context.getString(R.string.statusAccepted);
                        break;

                    case Ad.EXPIRED:
                        statusRsc = R.drawable.expired_copy;
                        statusString = context.getString(R.string.statusExpired);
                        break;

                    case Ad.HIDDEN:
                        statusRsc = R.drawable.hidden;
                        statusString = context.getString(R.string.statusHidden);
                        break;

                    case Ad.REJECTED:
                        statusRsc = R.drawable.exclamation_mark_copy;
                        statusString = context.getString(R.string.statusRejected);
                        break;

                    default:
                        statusRsc = R.drawable.dealat_logo_white_background;
                        statusString = "";
                }

                holder.textViewStatus.setText(statusString);
                holder.imageViewStatus.setImageDrawable(ContextCompat.getDrawable(context, statusRsc));

                if (item.isFeatured())
                    holder.imageViewFeatured.setVisibility(View.VISIBLE);
                else
                    holder.imageViewFeatured.setVisibility(View.INVISIBLE);

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
        return (position == ads.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addAll(List<Ad> ads) {
        if (ads != null && this.ads != null)
            this.ads.addAll(ads);

        notifyDataSetChanged();
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
        private ImageView imageViewStatus, imageViewMain, imageViewFeatured;
        private TextView textViewStatus, textViewTitle, textViewDate, textViewExpires;

        private View container;

        ViewHolder(View rootView) {
            super(rootView);

            container = rootView;
            imageViewMain = rootView.findViewById(R.id.imageView);
            imageViewStatus = rootView.findViewById(R.id.imageViewCheck);
            textViewStatus = rootView.findViewById(R.id.textView);
            textViewTitle = rootView.findViewById(R.id.title);
            textViewDate = rootView.findViewById(R.id.textViewDate);
            textViewExpires = rootView.findViewById(R.id.textDate);
            imageViewFeatured = rootView.findViewById(R.id.imageViewFeatured);
        }
    }
}
