package com.dealat.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.dealat.Controller.CurrentAndroidUser;
import com.dealat.Model.Chat;
import com.dealat.Model.User;
import com.dealat.MyApplication;
import com.dealat.R;
import com.dealat.View.ChatActivity;
import com.vdurmont.emoji.EmojiParser;

import java.util.List;
import java.util.Random;

/**
 * Created by developer on 26.04.18.
 */


// this receiver is registered in manifest
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        final String CHANNEL_ID = "Chat";
        final String CHANNEL_NAME = "Chats notifications";

        User user = new CurrentAndroidUser(context).Get();

        Chat chat = (Chat) intent.getSerializableExtra("chat");
        //msg is sent normally because it will be decoded in MessageAdapter // I mean Emoji decoding
        String msg = intent.getStringExtra("msg");
        msg = EmojiParser.parseToUnicode(msg);

        Intent chatIntent = new Intent(context, ChatActivity.class);
        chatIntent.putExtra("chat", chat);
        chatIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, new Random().nextInt(), chatIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        String notificationTitle;
        if (amISeller(user, chat))
            notificationTitle = chat.getUserName();
        else
            notificationTitle = chat.getSellerName();

        notificationTitle += " (" + chat.getAdTitle() + ")";

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.dealat_logo_white_background)
                .setContentTitle(notificationTitle)
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        // to group notification related to same chat
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(notificationTitle);

        List<String> messages = MyApplication.saveAndGetMessages(chat.getChatId(), msg);
        for (int i = 0; i < messages.size(); i++)
            inboxStyle.addLine(messages.get(i));

        notificationBuilder.setStyle(inboxStyle);

        notificationManager.notify(Integer.valueOf(chat.getChatId()), notificationBuilder.build());
    }

    private boolean amISeller(User user, Chat chat) {
        if (user != null)
            return user.getId().equals(chat.getSellerId());

        return false;
    }
}
