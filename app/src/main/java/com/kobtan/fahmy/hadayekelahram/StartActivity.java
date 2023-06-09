package com.kobtan.fahmy.hadayekelahram;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;

public class StartActivity extends AppCompatActivity {


   // RotateLoading rotateLoading ;
    TextView textName ;
    private Typeface typeface ;
    private EditText nameEditText_Admin;
    private Button saveBtn_Admin ;
    public static boolean isAppRunning;
    private AdView mAdView;
    private String newString ;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        typeface = Typeface.createFromAsset(getAssets(), "font.ttf");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
               // Toast.makeText(StartActivity.this, "Loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
               // Toast.makeText(StartActivity.this, String.valueOf(errorCode), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
               // Toast.makeText(StartActivity.this, "opend", Toast.LENGTH_SHORT).show();
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
               // Toast.makeText(StartActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
               // Toast.makeText(StartActivity.this, "left app", Toast.LENGTH_SHORT).show();
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
               // Toast.makeText(StartActivity.this, "closed", Toast.LENGTH_SHORT).show();
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        //  rotateLoading = (RotateLoading) findViewById(R.id.rotateloading);

        // rotateLoading.start();

        //displayDialogAdmin();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // do something
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                // If you just use this that is not a valid context. Use ActivityName.this
                startActivity(intent); }
        }, 2000);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "1";
        String channel2 = "2";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId,
                    "Channel 1", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("This is BNT");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationChannel notificationChannel2 = new NotificationChannel(channel2,
                    "Channel 2", NotificationManager.IMPORTANCE_MIN);

            notificationChannel.setDescription("This is bTV");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel2);


        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAppRunning = false;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayDialogAdmin()
    {
        final AlertDialog d = new AlertDialog.Builder(this)
                .setTitle("Admin Panel")
                .setIcon(R.drawable.logoteb)
                .setView(R.layout.dialogepasswordlayout)
                .create();
        d.show();


        saveBtn_Admin= (Button) d.findViewById(R.id.saveBtn);
        nameEditText_Admin= (EditText) d.findViewById(R.id.nameEditText);

        saveBtn_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEditText_Admin.getText().toString().equals("")) {
                    //Toast.makeText(StartActivity.this, "Enter Password First", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference admin = FirebaseDatabase.getInstance().getReference().child("admin");
                    admin.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String password = dataSnapshot.getValue().toString();
                            if (nameEditText_Admin.getText().toString().equals(password)) {
                               // Toast.makeText(StartActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            } else {
                               // Toast.makeText(StartActivity.this, "Password incorrect", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    d.hide();
                }
            }
        });


    }

}
