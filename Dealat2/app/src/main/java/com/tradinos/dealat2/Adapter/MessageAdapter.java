package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tradinos.dealat2.Model.Message;
import com.tradinos.dealat2.R;

import java.util.List;

/**
 * Created by developer on 22.03.18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private final int ME = 0, OTHER = 1;

    private List<Message> messages;

    private LayoutInflater inflater;
    private boolean iAmSeller;

    public MessageAdapter(Context context, List<Message> messages, boolean iAmSeller) {
        this.inflater = LayoutInflater.from(context);
        this.messages = messages;
        this.iAmSeller = iAmSeller;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == OTHER)
            return new MessageViewHolder(this.inflater.inflate(R.layout.row_chat_other, parent, false));
        else
            return new MessageViewHolder(this.inflater.inflate(R.layout.row_chat_me, parent, false));
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.textViewText.setText(message.getText());
        holder.textViewDate.setText(message.getCreatedAt());

        if (holder.imageViewCheck != null){ // it doesn't exist in row_chat_other
            if (message.isSent())
                holder.imageViewCheck.setVisibility(View.VISIBLE);
            else
                holder.imageViewCheck.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.messages.size();
    }

    public Message getItem(int i) {
        return this.messages.get(i);
    }

    @Override
    public int getItemViewType(int position) {
        boolean toSeller = messages.get(position).isToSeller();
        if ((toSeller && iAmSeller) || (!toSeller && !iAmSeller))
            return OTHER;
        else
            return ME;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView textViewText, textViewDate;
        ImageView imageViewCheck;

        public MessageViewHolder(View itemView) {
            super(itemView);

            textViewText = itemView.findViewById(R.id.textView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            imageViewCheck = itemView.findViewById(R.id.imageView);
        }
    }
}
