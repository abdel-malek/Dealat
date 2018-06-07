package com.tradinos.dealat2.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.Model.Chat;
import com.tradinos.dealat2.Parser.Parser.Ad.AdParser;
import com.tradinos.dealat2.Parser.Parser.Chat.ChatParser;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.View.AdDetailsActivity;
import com.tradinos.dealat2.View.HomeActivity;
import com.tradinos.dealat2.View.PublicNotificationActivity;

import org.json.JSONException;

import java.util.Random;

/**
 * Created by developer on 12.03.18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //Log.d(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            // Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            try {
                sendNotification(remoteMessage.getData().get("ntf_body"),
                        remoteMessage.getData().get("ntf_text"), remoteMessage.getData().get("ntf_title"),
                        remoteMessage.getData().get("ntf_type"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    private void sendNotification(String body, String txt, String title, String type) throws JSONException {

        Intent intent;
        String channelId = "Dealat";

        switch (type) {
            case "1":
                Chat chat = new ChatParser().Parse(body);
                chat.setAdTitle(title);

                intent = new Intent("com.dealat.MSG");
                intent.putExtra("msg", txt);
                intent.putExtra("chat", chat);
                sendOrderedBroadcast(intent, null);

                //return because Notification of chats are built in NotificationReceiver
                return;

            case "2":
                Ad ad = new AdParser().Parse(body);
                intent = new Intent(this, AdDetailsActivity.class);
                intent.putExtra("ad", ad);
                channelId = "Ads";
                break;

            case "3": //Public Notification
            case "4": // Notification to certain user
                intent = new Intent(this, PublicNotificationActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("txt", txt);
                channelId = "General";
                break;

            default: // just in case
                intent = new Intent(this, HomeActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, new Random().nextInt(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.dealat_logo_white_background)
                .setContentTitle(title)
                .setContentText(txt)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int id = new Random().nextInt();
        // ids of notification of type 2 and 3 (action, public) are always negative to avoid conflict with msg notification (type 1)
        // that there Ids are same as chatId which is definitely positive
        if (id > 0)
            id = id * -1;

        notificationManager.notify(id, notificationBuilder.build());
    }
}
