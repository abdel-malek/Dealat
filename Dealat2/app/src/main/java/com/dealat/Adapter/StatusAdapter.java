package com.dealat.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealat.Model.Ad;
import com.dealat.R;

/**
 * Created by developer on 09.05.18.
 */

public class StatusAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;

    public StatusAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.row_status, null);

        TextView textView = view.findViewById(R.id.textView);
        ImageView imageView = view.findViewById(R.id.imageView);

        Drawable drawable;
        String statusString;

        switch (i) {
            case 0:
                statusString = context.getString(R.string.all);
                drawable = null;
                break;

            case Ad.PENDING:
                statusString = context.getString(R.string.statusPending);
                drawable = ContextCompat.getDrawable(context, R.drawable.bending);
                break;

            case Ad.ACCEPTED:
                statusString = context.getString(R.string.statusAccepted);
                drawable = ContextCompat.getDrawable(context, R.drawable.check);
                break;

            case Ad.EXPIRED:
                statusString = context.getString(R.string.statusExpired);
                drawable = ContextCompat.getDrawable(context, R.drawable.expired_copy);
                break;

            case Ad.HIDDEN:
                statusString = context.getString(R.string.statusHidden);
                drawable = ContextCompat.getDrawable(context, R.drawable.hidden);
                break;

            case Ad.REJECTED:
                statusString = context.getString(R.string.statusRejected);
                drawable = ContextCompat.getDrawable(context, R.drawable.exclamation_mark_copy);
                break;

            default:
                drawable = null;
                statusString = "";
        }

        textView.setText(statusString);
        imageView.setImageDrawable(drawable);

        return view;
    }
}
