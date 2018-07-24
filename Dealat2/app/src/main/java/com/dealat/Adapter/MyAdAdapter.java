package com.dealat.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dealat.Model.Ad;
import com.dealat.MyApplication;
import com.dealat.R;
import com.dealat.View.MasterActivity;
import com.tradinos.core.network.InternetManager;

import java.util.List;

/**
 * Created by developer on 14.03.18.
 */

public class MyAdAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<Ad> ads;

    public MyAdAdapter(Context context, List<Ad> ads) {
        this.ads = ads;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.ads.size();
    }

    @Override
    public Ad getItem(int i) {
        return this.ads.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = this.inflater.inflate(R.layout.row_my_ad, null);
        view.setTag(new ViewHolder(view));

        initializeView(getItem(i), (ViewHolder) view.getTag());

        return view;
    }

    private void initializeView(Ad item, ViewHolder holder) {
        int defaultDrawable = ((MasterActivity) context).getTemplateDefaultImage(item.getTemplate());
        if (item.getMainImageUrl() != null) {

            ImageLoader mImageLoader = InternetManager.getInstance(context).getImageLoader();
            mImageLoader.get(MyApplication.getBaseUrl() + item.getMainImageUrl(),
                    ImageLoader.getImageListener(holder.imageViewMain,
                            defaultDrawable, defaultDrawable));
        } else
            holder.imageViewMain.setImageDrawable(ContextCompat.getDrawable(context, defaultDrawable));

        holder.textViewTitle.setText(item.getTitle());

        if (item.getPublishDate() != null) {
            holder.textViewDate.setText(context.getString(R.string.published)
                    + " " + ((MasterActivity) context).formattedDate(item.getPublishDate()));

            // only published ads their expiry dates are calculated
            String text;
            if (item.getStatus() == Ad.ACCEPTED && item.getExpiresAfter() < 0) {
                text = context.getString(R.string.expired);
                item.setStatus(Ad.EXPIRED); // we need to change it to filter in MyAdsFragment
                //holder.textViewExpires.setTextColor(ContextCompat.getColor(context, R.color.red));
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
    }


    class ViewHolder {
        private ImageView imageViewStatus, imageViewMain, imageViewFeatured;
        private TextView textViewStatus, textViewTitle, textViewDate, textViewExpires;

        ViewHolder(View rootView) {
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
