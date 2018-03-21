package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.View.MasterActivity;

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

        if (view == null) {
            view = this.inflater.inflate(R.layout.row_my_ad, null);
            view.setTag(new ViewHolder(view));
        }

        initializeView(getItem(i), (ViewHolder) view.getTag());

        return view;
    }

    private void initializeView(Ad item, ViewHolder holder) {
        int defaultDrawable = ((MasterActivity) context).getTemplateDefaultImage(item.getTemplate());
        if (item.getMainImageUrl() != null) {

            ImageLoader mImageLoader = InternetManager.getInstance(context).getImageLoader();
            mImageLoader.get(MyApplication.getBaseUrlForImages() + item.getMainImageUrl(),
                    ImageLoader.getImageListener(holder.imageViewMain,
                            defaultDrawable, defaultDrawable));
        } else
            holder.imageViewMain.setImageDrawable(ContextCompat.getDrawable(context, defaultDrawable));

        holder.textViewTitle.setText(item.getTitle());
        holder.textViewDate.setText(((MasterActivity) context).formattedDate(item.getPublishDate()));
        holder.textViewExpires.setText(context.getString(R.string.expires)
                + " " + ((MasterActivity) context).getExpiryTime(item.getPublishDate(), item.getShowPeriod()));


        if (item.getExpiresAfter() <= 0)
            item.setStatus(Ad.EXPIRED);

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
                statusRsc = R.drawable.dealat_logo_red;
                statusString = "";
        }

        holder.textViewStatus.setText(statusString);
        holder.imageViewStatus.setImageDrawable(ContextCompat.getDrawable(context, statusRsc));
    }


    class ViewHolder {
        private ImageView imageViewStatus, imageViewMain;
        private TextView textViewStatus, textViewTitle, textViewDate, textViewExpires;

        ViewHolder(View rootView) {
            imageViewMain = rootView.findViewById(R.id.imageView);
            imageViewStatus = rootView.findViewById(R.id.imageViewCheck);
            textViewStatus = rootView.findViewById(R.id.textView);
            textViewTitle = rootView.findViewById(R.id.title);
            textViewDate = rootView.findViewById(R.id.textViewDate);
            textViewExpires = rootView.findViewById(R.id.textDate);
        }
    }
}
