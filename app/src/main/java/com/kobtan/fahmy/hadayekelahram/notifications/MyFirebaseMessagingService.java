package com.kobtan.fahmy.hadayekelahram.notifications;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.format.DateFormat;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kobtan.fahmy.hadayekelahram.News.NewsActivity;
import com.kobtan.fahmy.hadayekelahram.R;
import com.kobtan.fahmy.hadayekelahram.StartActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


/**
 * Created by L on 09/05/2017.
 * Copyright (c) 2017 Centroida. All rights reserved.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String NOTIFICATION_ID_EXTRA = "notificationId";
    private static final String IMAGE_URL_EXTRA = "imageUrl";
    private static final String ADMIN_CHANNEL_ID ="admin_channel";
    private NotificationManager notificationManager;
    private String title , content , date ;
    private HashMap map ;
    private DatabaseReference current_user_db ;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        title = remoteMessage.getData().get("title") ;
        content = remoteMessage.getData().get("message") ;
        date = getdate() ;

        FirebaseDatabase.getInstance().getReference().child("notifications").push() ;
        String key = FirebaseDatabase.getInstance().getReference().child("notifications").push().getKey();
        current_user_db = FirebaseDatabase.getInstance().getReference().child("notifications").child(key);

        map = new HashMap();
        map.put("title", title);
        map.put("message", content);
        map.put("date", date);


        current_user_db.setValue(map);

        Intent notificationIntent = new Intent(this, NewsActivity.class);
        if(StartActivity.isAppRunning){
            //Some action
        }else{
            //Show notification as usual
        }

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0 /* Request code */, notificationIntent,
                PendingIntent.FLAG_ONE_SHOT);

        //You should use an actual ID instead
        int notificationId = new Random().nextInt(60000);


        Bitmap bitmap = getBitmapfromUrl(remoteMessage.getData().get("image-url"));

        Intent likeIntent = new Intent(this,LikeService.class);
        likeIntent.putExtra(NOTIFICATION_ID_EXTRA,notificationId);
        likeIntent.putExtra(IMAGE_URL_EXTRA,remoteMessage.getData().get("image-url"));
        PendingIntent likePendingIntent = PendingIntent.getService(this,
                notificationId+1,likeIntent,PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels();
        }




        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                        .setLargeIcon(bitmap)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(remoteMessage.getData().get("title"))
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .setSummaryText(remoteMessage.getData().get("message"))
                                .bigPicture(bitmap))/*Notification with Image*/
                        .setContentText(remoteMessage.getData().get("message"))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .addAction(R.drawable.ic_favorite_true,
                                getString(R.string.notification_add_to_cart_button),likePendingIntent)
                        .setContentIntent(pendingIntent);

        notificationManager.notify(notificationId, notificationBuilder.build());




    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(){
        CharSequence adminChannelName = getString(R.string.notifications_admin_channel_name);
        String adminChannelDescription = getString(R.string.notifications_admin_channel_description);

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
    private String getdate () {
        String date = (DateFormat.format(" التاريخ "+" dd-MM-yyyy", new java.util.Date()).toString());
        return date;
    }
}
