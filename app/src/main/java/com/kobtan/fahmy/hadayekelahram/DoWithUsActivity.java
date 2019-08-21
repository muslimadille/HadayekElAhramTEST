package com.kobtan.fahmy.hadayekelahram;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DoWithUsActivity extends AppCompatActivity {


    private Typeface typeface ;
    private LinearLayout map , call ;
    private ImageView whatsapp , fb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_with_us);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(" ");
        backky();
        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");

        TextView textView = (TextView) findViewById(R.id.text) ;
        TextView textView_title = (TextView) findViewById(R.id.texttitle) ;
        textView.setTypeface(typeface);
        textView_title.setTypeface(typeface);

        LinearLayout map = (LinearLayout) findViewById(R.id.map) ;
        LinearLayout call = (LinearLayout) findViewById(R.id.call) ;
        fb = (ImageView) findViewById(R.id.facebook) ;
        whatsapp = (ImageView) findViewById(R.id.whatsapp) ;

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "01018588855";
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                startActivity(surf);

                Toast.makeText(DoWithUsActivity.this, "Calling", Toast.LENGTH_SHORT).show();
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

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://web.facebook.com/groups/876354812461388/?ref=share&_rdc=1&_rdr/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                Toast.makeText(DoWithUsActivity.this, "Openning Facebook Page", Toast.LENGTH_SHORT).show();
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


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_test, menu);

        return super.onCreateOptionsMenu(menu);
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
