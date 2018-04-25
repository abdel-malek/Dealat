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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        DateFormat dateInstance = SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH);
        DateFormat timeInstance = SimpleDateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH); //time without seconds
        // setting date and time
        try {
            holder.textViewDate.setText(dateInstance.format(format.parse(message.getCreatedAt())));
            holder.textViewTime.setText(timeInstance.format(format.parse(message.getCreatedAt())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // grouping messages which were sent on the same day together under their date
        if (position > 0) { // exclude check for first item (i-1)

            try {
                String myDate = dateInstance.format(format.parse(message.getCreatedAt()));
                String preDate = dateInstance.format(format.parse(getItem(position-1).getCreatedAt()));
                if(myDate.equals(preDate))
                    holder.textViewDate.setVisibility(View.GONE);
                else
                    holder.textViewDate.setVisibility(View.VISIBLE);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else
            holder.textViewDate.setVisibility(View.VISIBLE);

        if (holder.imageViewCheck != null) { // it doesn't exist in row_chat_other
            if (message.isSent()){
                holder.imageViewCheck.setVisibility(View.VISIBLE);
            }
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

        TextView textViewText, textViewDate, textViewTime;
        ImageView imageViewCheck;

        public MessageViewHolder(View itemView) {
            super(itemView);

            textViewText = itemView.findViewById(R.id.textView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            imageViewCheck = itemView.findViewById(R.id.imageView);
        }
    }
}
