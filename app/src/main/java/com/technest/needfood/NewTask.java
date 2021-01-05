package com.technest.needfood;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.technest.needfood.admin.DashboardAdminActivity;

@SuppressLint("StaticFieldLeak")
public class NewTask extends AsyncTask<Void, Integer, String> {

    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    Context context;
    String title;
    String message;
    public NewTask(Context context, String title, String message) {
        this.context = context;
        this.title = title;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);

        Intent intent = new Intent(context, DashboardAdminActivity.class);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
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
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, notifBuilder.build());
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

//        Bundle bundle = new Bundle();
//        bundle.putString("title", title);
//        bundle.putString("message", message);
//
//        Intent intent = new Intent(context, DashboardAdminActivity.class);
//        intent.putExtras(bundle);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setSmallIcon(R.drawable.ic_kesiniku_foreground) // icon
//                .setAutoCancel(true) // menghapus notif ketika user melakukan tap pada notif
//                .setLights(200,200,200) // light button
//                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI) // set sound
//                .setOnlyAlertOnce(true) // set alert sound notif
//                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setContentIntent(pendingIntent); // action notif ketika di tap
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(1, notifBuilder.build());
    }

    @Override
    protected String doInBackground(Void... voids) {
        synchronized (this){
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putString("message", message);

            Intent intent = new Intent(context, DashboardAdminActivity.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
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
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1, notifBuilder.build());
        }
        return "done";
    }



}
