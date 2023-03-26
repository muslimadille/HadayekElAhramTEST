package com.kobtan.fahmy.hadayekelahram;

import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MemberPanelAdvActivity extends AppCompatActivity {


    private Typeface typeface ;
    private Button add_adv , edit_adv , sp_adv , logout_adv  ;
    private ImageView back_img ;
    private StringCurrentUserPref currentUserPref ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_panel_adv);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        currentUserPref = new StringCurrentUserPref(this);
        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");
        backky();
        add_adv = (Button) findViewById(R.id.addAdv) ;
        add_adv.setTypeface(typeface);
        edit_adv = (Button) findViewById(R.id.editAdv) ;
        edit_adv.setTypeface(typeface);
        sp_adv = (Button) findViewById(R.id.spAdv) ;
        sp_adv.setTypeface(typeface);
        logout_adv = (Button) findViewById(R.id.logout) ;
        logout_adv.setTypeface(typeface);



        add_adv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , AddAdvActivity.class));
            }
        });

        edit_adv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , EditMyAdvActivity.class));
            }
        });



        sp_adv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , AdvWithUsActivity.class));
            }
        });

        logout_adv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentUserPref.getUser() != null) {
                    currentUserPref.clear();
                    Toast.makeText(MemberPanelAdvActivity.this, "تم تسجيل الخروج", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(getApplicationContext() , MainActivity.class));
            }
        });




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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext() , MainActivity.class));
        finish();
    }
    @Override
    public void onStart() {
        super.onStart();

        currentUserPref = new StringCurrentUserPref(this);

        if (currentUserPref.getUser() != null) {
            //Toast.makeText(this, "اهلا بك", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            Intent intent = new Intent(MemberPanelAdvActivity.this, AddAdvActivity.class);
            startActivity(intent);
            return;
        }
    }
}
