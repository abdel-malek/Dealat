package com.dealat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dealat.Controller.CurrentAndroidUser;
import com.dealat.Model.Chat;
import com.dealat.Model.User;
import com.dealat.MyApplication;
import com.dealat.R;
import com.tradinos.core.network.InternetManager;

import java.util.List;

/**
 * Created by developer on 22.03.18.
 */

public class ChatAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;

    private User user;
    private List<Chat> chats;

    public ChatAdapter(Context context, List<Chat> chats) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.chats = chats;
        user = new CurrentAndroidUser(context).Get();
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
        view = this.inflater.inflate(R.layout.row_chat, null);
        view.setTag(new ViewHolder(view));

        initializeView(getItem(i), (ViewHolder) view.getTag());

        return view;
    }

    private void initializeView(Chat item, ViewHolder holder) {
        holder.textViewTitle.setText(item.getAdTitle());

        String url = null;
        if (user != null) {
            if (user.getId().equals(item.getSellerId())) {
                holder.textViewName.setText(item.getUserName());
                url = item.getUserPic();
            } else {
                holder.textViewName.setText(item.getSellerName());
                url = item.getSellerPic();
            }
        }

        if (url != null) {
            ImageLoader mImageLoader = InternetManager.getInstance(context).getImageLoader();
            mImageLoader.get(MyApplication.getBaseUrl() + url,
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
