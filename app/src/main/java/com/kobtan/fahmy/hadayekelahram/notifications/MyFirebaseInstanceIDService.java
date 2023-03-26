package com.kobtan.fahmy.hadayekelahram.notifications;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.kobtan.fahmy.hadayekelahram.Constants;


/**
 * Created by L on 09/05/2017.
 * Copyright (c) 2017 Centroida. All rights reserved.
 */

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG,s);
    }

}