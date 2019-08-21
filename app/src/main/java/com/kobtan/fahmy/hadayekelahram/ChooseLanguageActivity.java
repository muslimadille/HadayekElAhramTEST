package com.kobtan.fahmy.hadayekelahram;

import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChooseLanguageActivity extends AppCompatActivity {
    private Typeface typeface ;
    private Toolbar toolbar;
    private Button button , button_two ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle(" ");
        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");

        button = (Button) findViewById(R.id.one) ;
        button_two = (Button) findViewById(R.id.two) ;
        button_two.setTypeface(typeface);
        button.setTypeface(typeface);

        TextView textTitle = (TextView) findViewById(R.id.titlee) ;
        textTitle.setTypeface(typeface);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , MainActivity.class));
            }
        });

        button_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , MainActivity.class));
            }
        });





    }


    @Override
    public void onBackPressed() {
        finish();
    }

}
