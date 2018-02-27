package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.inflater.inflate(resourceLayout, null);
           // view.setTag(new ViewHolder(view));
        }

        //initializeView(getItem(i), (ViewHolder) view.getTag());

        return view;
    }

    private void initializeView(Ad item, ViewHolder holder) {
        if (item.getMainImageUrl() != null) {
            ImageLoader mImageLoader = InternetManager.getInstance(context).getImageLoader();
            mImageLoader.get(MyApplication.getBaseUrlForImages() + item.getMainImageUrl(),
                    ImageLoader.getImageListener(holder.imageView,
                            R.drawable.dealat_logo_red_background_lined, R.drawable.dealat_logo_red_background_lined));
        }


    }

    class ViewHolder {
        ImageView imageView;
        TextView textViewPrice, textViewNegotiable, textView, textViewDate;
        ImageButton buttonFav;

        ViewHolder(View view) {
            imageView = view.findViewById(R.id.imageView);
            textViewPrice = view.findViewById(R.id.textViewPrice);
            textViewNegotiable = view.findViewById(R.id.textViewNegotiable);
            textView = view.findViewById(R.id.textView);
            textViewDate = view.findViewById(R.id.textViewDate);
            buttonFav = view.findViewById(R.id.buttonFav);
        }
    }
}
