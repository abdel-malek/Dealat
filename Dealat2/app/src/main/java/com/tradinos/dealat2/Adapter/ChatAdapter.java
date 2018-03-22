package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Model.Chat;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

import java.util.List;

/**
 * Created by developer on 22.03.18.
 */

public class ChatAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;

    private List<Chat> chats;

    public ChatAdapter(Context context, List<Chat> chats) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.chats = chats;
    }

    @Override
    public int getCount() {
        return this.chats.size();
    }

    @Override
    public Chat getItem(int i) {
        return this.chats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.inflater.inflate(R.layout.row_chat, null);
            view.setTag(new ViewHolder(view));
        }

        initializeView(getItem(i), (ViewHolder) view.getTag());

        return view;
    }

    private void initializeView(Chat item, ViewHolder holder) {
        holder.textViewTitle.setText(item.getAdTitle());

        User user = new CurrentAndroidUser(context).Get();
        String url = null;
        if (user != null) {
            if (user.getId().equals(item.getUserId())) {
                holder.textViewName.setText(item.getSellerName());
                url = item.getSellerPic();
            } else {
                holder.textViewName.setText(item.getUserName());
                url = item.getUserPic();
            }
        }

        if (url != null) {
            ImageLoader mImageLoader = InternetManager.getInstance(context).getImageLoader();
            mImageLoader.get(MyApplication.getBaseUrlForImages() + url,
                    ImageLoader.getImageListener(holder.imageView,
                            R.drawable.ic_person_48dp, R.drawable.ic_person_48dp));
        }
    }

    class ViewHolder {
        TextView textViewTitle, textViewName;
        ImageView imageView;

        ViewHolder(View rootView) {
            textViewTitle = rootView.findViewById(R.id.title);
            textViewName = rootView.findViewById(R.id.textName);
            imageView = rootView.findViewById(R.id.imageView);
        }
    }
}
