package com.dealat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealat.Controller.CurrentAndroidUser;
import com.dealat.Fragment.ChatsFragment;
import com.dealat.Model.Chat;
import com.dealat.Model.User;
import com.dealat.MyApplication;
import com.dealat.R;
import com.dealat.View.ChatActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChatPagingAdapter extends RecyclerView.Adapter<ChatPagingAdapter.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    private Context context;
    private LayoutInflater inflater;

    private User user;
    private List<Chat> chats;
    private ChatsFragment mChatsFragment;

    public ChatPagingAdapter(Context context, ChatsFragment chatsFragment) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.chats = new ArrayList<>();
        user = new CurrentAndroidUser(context).Get();
        mChatsFragment = chatsFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM:
                return new ViewHolder(inflater.inflate(R.layout.row_chat, null));

            case LOADING:
                return new ViewHolder(inflater.inflate(R.layout.footer_progress_bar, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM:
                final Chat item = this.chats.get(position);

                holder.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ChatActivity.class);

                        intent.putExtra("chat", item);

                        mChatsFragment.startActivityForResult(intent, ChatsFragment.VIEW_CHAT);
                    }
                });

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

                if (url == null)
                    holder.imageView.setImageResource(R.drawable.ic_person_48dp);
                else {
                    Picasso.with(context)
                            .load(MyApplication.getBaseUrl() + url)
                            .into(holder.imageView);
                }

                break;

            case LOADING:
        }
    }

    @Override
    public int getItemCount() {
        return chats == null ? 0 : chats.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == chats.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addAll(List<Chat> chats) {
        if (chats != null && this.chats != null) {
            this.chats.addAll(chats);
            this.notifyDataSetChanged();
        }
    }

    public void clear() {
        if (this.chats != null)
            this.chats.clear();
    }

    public void removeChat(Chat chat) {
        if (this.chats != null) {
            this.chats.remove(chat);
            notifyDataSetChanged();
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        this.chats.add(new Chat());
        notifyItemInserted(chats.size() - 1);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = chats.size() - 1;

        if (position >= 0 && position < chats.size()) { // Avoid ArrayIndexOutOfBoundsException
            Chat item = chats.get(position);

            if (item != null) {
                chats.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewName;
        ImageView imageView;

        View container;

        ViewHolder(View rootView) {
            super(rootView);

            container = rootView;
            textViewTitle = rootView.findViewById(R.id.title);
            textViewName = rootView.findViewById(R.id.textName);
            imageView = rootView.findViewById(R.id.imageView);
        }
    }
}
