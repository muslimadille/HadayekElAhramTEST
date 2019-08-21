package com.kobtan.fahmy.hadayekelahram;

import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NotePayActivity extends AppCompatActivity {

    private Toolbar toolbar ;
    private Typeface typeface ;
    private Button login , register ;
    private TextView title , cost;
    private StringCurrentUserPref currentUserPref ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_pay);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(" ");

        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");


        login = (Button) findViewById(R.id.login) ;
        register = (Button) findViewById(R.id.register) ;
        title = (TextView) findViewById(R.id.titlee) ;
        cost = (TextView) findViewById(R.id.text) ;

        login.setTypeface(typeface);
        register.setTypeface(typeface);
        title.setTypeface(typeface);
        cost.setTypeface(typeface);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , LoginActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , RegisteraionActivity.class));
            }
        });


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
                startActivity(new Intent(NotePayActivity.this , MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        currentUserPref = new StringCurrentUserPref(this);

        if (currentUserPref.getUser() != null) {
            Intent intent = new Intent(NotePayActivity.this, MemberPanelAdvActivity.class);
            startActivity(intent);
            Toast.makeText(this, "اهلا بك", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}

