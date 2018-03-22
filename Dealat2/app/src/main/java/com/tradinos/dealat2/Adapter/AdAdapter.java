package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.View.AdDetailsActivity;
import com.tradinos.dealat2.View.MasterActivity;

import java.util.List;

/**
 * Created by developer on 26.02.18.
 */

public class AdAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private int resourceLayout;
    private List<Ad> ads;

    public AdAdapter(Context context, List<Ad> ads, int resourceLayout) {
        this.context = context;
        this.ads = ads;
        this.inflater = LayoutInflater.from(context);
        this.resourceLayout = resourceLayout;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (resourceLayout == R.layout.row_view2 && (i % 2 != 0))
            view = this.inflater.inflate(R.layout.row_view2_left, null);
        else
            view = this.inflater.inflate(resourceLayout, null);

        view.setTag(new ViewHolder(view));

        initializeView(getItem(i), (ViewHolder) view.getTag());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdDetailsActivity.class);

                intent.putExtra("ad", getItem(i));

                context.startActivity(intent);
            }
        });

        return view;
    }

    private void initializeView(Ad item, ViewHolder holder) {
        int defaultDrawable = ((MasterActivity) context).getTemplateDefaultImage(item.getTemplate());
        if (item.getMainImageUrl() != null) {

            ImageLoader mImageLoader = InternetManager.getInstance(context).getImageLoader();
            mImageLoader.get(MyApplication.getBaseUrlForImages() + item.getMainImageUrl(),
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

        holder.textView.setText("500 Views");
        holder.textViewDate.setText(((MasterActivity) context).formattedDate(item.getPublishDate()));

        if (item.isFavorite())
            holder.buttonFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));

        if (item.isFeatured())
            holder.imageViewFeatured.setVisibility(View.VISIBLE);
    }

    class ViewHolder {
        ImageView imageView, imageViewFeatured;
        TextView textViewPrice, textViewTitle, textView, textViewDate;
        ImageButton buttonFav;

        ViewHolder(View view) {
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
