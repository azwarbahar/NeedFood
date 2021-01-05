package com.technest.needfood;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.technest.needfood.admin.DashboardAdminActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ServiceNotifikasiPesananBaru extends FirebaseMessagingService {

    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";

    @Override
    public void onNewToken(@NonNull String s) {
        Log.d("TAG", "Refreshed token: " + s);
    }

    @SuppressLint("WrongThread")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

//        Map<String, String> data = remoteMessage.getData();
//        String dataPayload = data.get("data");
//
//        if (remoteMessage.getData().size() > 0) {
//            Log.e("TAG", "Message data payload: " + remoteMessage.getData());
//
//            try {
//                JSONObject jsonParse = new JSONObject(dataPayload);
//                showNotif(jsonParse.getString("title"), jsonParse.getString("message"));
////                new NewTask(this, jsonParse.getString("title"), jsonParse.getString("message")).execute();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (remoteMessage.getNotification() != null) {
//            Log.e("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
//            showNotif(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
////            new NewTask(this,remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody()).execute();
//        }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showNotif(String title, String message){

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);

        Intent intent = new Intent(this, DashboardAdminActivity.class);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_kesiniku_foreground) // icon
                .setAutoCancel(true) // menghapus notif ketika user melakukan tap pada notif
                .setLights(200,200,200) // light button
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI) // set sound
                .setOnlyAlertOnce(true) // set alert sound notif
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent); // action notif ketika di tap
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, notifBuilder.build());
    }

}