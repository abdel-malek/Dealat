package com.tradinos.dealat2.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tradinos.dealat2.Parser.Parser.StringParser;
import com.tradinos.dealat2.R;

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

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            try {
                sendNotification(new StringParser().Parse(remoteMessage.getData().get("ntf_body")),remoteMessage.getData().get("ntf_text"), remoteMessage.getData().get("title"),
                        remoteMessage.getData().get("ntf_type"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    private void sendNotification(String orderId, String txt, String title, String type) {

        Intent intent;

     /*   switch (type){
            case "action":
                intent = new Intent(this, ActionActivity.class);
                break;

            default: //just in case
                intent = new Intent(this, OrderDetailsActivity.class);
                intent.putExtra("myOrder", true); //boolean to hide Reorder button
                intent.putExtra("withLog", true);
        }*/

//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    /*    intent.putExtra("order_id", orderId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, new Random().nextInt()  , intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(txt)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(new Random().nextInt() , notificationBuilder.build());*/
    }
}
