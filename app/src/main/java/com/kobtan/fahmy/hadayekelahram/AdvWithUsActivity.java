package com.kobtan.fahmy.hadayekelahram;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AdvWithUsActivity extends AppCompatActivity {

    private Toolbar toolbar ;
    private Typeface typeface ;
    private LinearLayout call_li ;
    private ImageView fb , whatsapp ;
    private LinearLayout map ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_with_us);
        //getWindow().getAttributes().windowAnimations = R.style.Fade;
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(" ");
        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");

        backky();

        TextView textView = (TextView) findViewById(R.id.text) ;
        TextView textView2 = (TextView) findViewById(R.id.text2) ;
        TextView textView3 = (TextView) findViewById(R.id.text3) ;
        TextView textViewYeah = (TextView) findViewById(R.id.textyeeh) ;

        call_li = (LinearLayout) findViewById(R.id.call) ;
        fb = (ImageView) findViewById(R.id.facebook) ;
        whatsapp = (ImageView) findViewById(R.id.whatsapp) ;
        map = (LinearLayout) findViewById(R.id.googlee) ;
        textView.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textViewYeah.setTypeface(typeface);


        call_li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "01018588855";
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                startActivity(surf);

                Toast.makeText(AdvWithUsActivity.this, "Calling", Toast.LENGTH_SHORT).show();
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://web.facebook.com/groups/876354812461388/?ref=share&_rdc=1&_rdr/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                Toast.makeText(AdvWithUsActivity.this, "Openning Facebook Page", Toast.LENGTH_SHORT).show();
            }
        });
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = "+20 1018588855"; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(getApplicationContext() , MapsActivity.class) ;
                i.putExtra("STRING_I_NEED" , Double.valueOf(01)) ;
                i.putExtra("STRING_I_NEED2" , Double.valueOf(01)) ;
                startActivity(i);
            }
        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext() , MainActivity.class));
        finish();
    }
    private void backky () {
        ImageView back  = (ImageView) findViewById(R.id.backyy) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , MainActivity.class));
            }
        });

    }

}
