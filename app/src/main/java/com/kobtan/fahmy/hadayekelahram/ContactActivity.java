package com.kobtan.fahmy.hadayekelahram;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {

    private Toolbar toolbar ;
    private Typeface typeface ;
    private Button call_btn , msg_btn , website_btn , saveBtn ;
    private ImageView fb_img , maps_img;
    private EditText nameEditText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" ");
        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");


        call_btn = (Button) findViewById(R.id.reports) ;
        //call_btn.animate().translationXBy(-200).setDuration(2500) ;
        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "01119874001";
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                startActivity(surf);

                Toast.makeText(ContactActivity.this, "Calling", Toast.LENGTH_SHORT).show();
            }
        });

        msg_btn = (Button) findViewById(R.id.bills) ;
        //msg_btn.animate().translationXBy(-200).setDuration(2500) ;
        msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:01119874001");
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "The SMS text");
                startActivity(it);

                Toast.makeText(ContactActivity.this, "Messaging now !", Toast.LENGTH_SHORT).show();
            }
        });

       // website_btn = (Button) findViewById(R.id.timetable) ;
       // website_btn.animate().translationXBy(-200).setDuration(2500) ;
       /* website_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                Toast.makeText(ContactActivity.this, "Openning Site", Toast.LENGTH_SHORT).show();
            }
        });*/



        fb_img = (ImageView) findViewById(R.id.facebook) ;
        fb_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                Toast.makeText(ContactActivity.this, "Openning Facebook Page", Toast.LENGTH_SHORT).show();
            }
        });

        maps_img = (ImageView) findViewById(R.id.google) ;
        maps_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(ContactActivity.this , MapsActivity.class));
            }
        });
/*
        mail_img = (ImageView) findViewById(R.id.mail_img) ;
        mail_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","Amiir01140201222@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "To ");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Enter Message ..");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        feedback_img = (ImageView) findViewById(R.id.feedback_img) ;
        feedback_img.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                displayDialog() ;
            }
        }); */


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext() , MainActivity.class));
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(ContactActivity.this , MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayDialog()
    {
        final AlertDialog d = new AlertDialog.Builder(this)
                .setTitle("Give Your Feedback")
                .setIcon(R.drawable.ic_send_white_24dp)
                .setView(R.layout.dialogefeedbacklayout)
                .create();
        d.show();


        saveBtn= (Button) d.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameEditText= (EditText) d.findViewById(R.id.nameEditText);
                Toast.makeText(ContactActivity.this, "شكرا لك", Toast.LENGTH_SHORT).show();
                d.hide();
            }
        });

    }
}