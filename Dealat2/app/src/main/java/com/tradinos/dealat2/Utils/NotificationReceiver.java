package com.tradinos.dealat2.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.tradinos.dealat2.Model.Chat;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.View.ChatActivity;
import com.vdurmont.emoji.EmojiParser;

import java.util.Random;

/**
 * Created by developer on 26.04.18.
 */

public class NotificationReceiver extends BroadcastReceiver {
    private final String action = "com.dealat.MSG";

    @Override
    public void onReceive(Context context, Intent intent) {

        Chat chat = (Chat) intent.getSerializableExtra("chat");
        //msg is sent normally because it will be decoded in MessageAdapter
        String msg = intent.getStringExtra("msg");
        msg = EmojiParser.parseToUnicode(msg);

        Intent chatIntent = new Intent(context, ChatActivity.class);
        chatIntent.putExtra("chat", chat);
        chatIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, new Random().nextInt(), chatIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.dealat_logo_white_background)
                .setContentTitle(chat.getAdTitle())
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }
}
