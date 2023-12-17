package com.muqadisaejaz.smdproject;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle the incoming FCM message here
        Log.d("FCMMessageReceived", "From: " + remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {
            Log.d("FCMMessageReceived", "Notification Title: " + remoteMessage.getNotification().getTitle());
            Log.d("FCMMessageReceived", "Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // You can access other message data using remoteMessage.getData()
        // For example: remoteMessage.getData().get("key");
    }

    // Add other methods if needed, such as onNewToken for handling token refresh
}
