package com.kobtan.fahmy.hadayekelahram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisteraionActivity extends AppCompatActivity {


    private Toolbar toolbar ;
    private Typeface typeface ;
    private Button login ;
    private EditText name_edit , email_edit , telephone_edit , password_edit ;

    private DatabaseReference current_user_db;
    private StringCurrentUserPref currentUserPref ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeraion);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" ");
        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");


        login = (Button) findViewById(R.id.btn_login) ;
        login.setTypeface(typeface);
        TextView signUp = (TextView) findViewById(R.id.link_signup) ;
        signUp.setTypeface(typeface);
        name_edit = (EditText) findViewById(R.id.input_name)  ;
        email_edit = (EditText) findViewById(R.id.input_email)  ;
        telephone_edit = (EditText) findViewById(R.id.input_phone)  ;
        password_edit = (EditText) findViewById(R.id.input_password)  ;
        name_edit.setTypeface(typeface);
        telephone_edit.setTypeface(typeface);
        email_edit.setTypeface(typeface);
        password_edit.setTypeface(typeface);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , LoginActivity.class));
            }
        });





        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(RegisteraionActivity.this,
                        R.style.Theme_AppCompat_DayNight_Dialog);

                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("جاري التسجيل...");
                progressDialog.show();

                final String email = email_edit.getText().toString();
                final String password = password_edit.getText().toString();
                final String name = name_edit.getText().toString();
                final String telephone = telephone_edit.getText().toString();


                if (email.equals("") || password.equals("") || name.equals("") || telephone.equals("")) {

                    progressDialog.dismiss();
                    Toast.makeText(RegisteraionActivity.this, "من فضلك ادخل البيانات كاملة اولا", Toast.LENGTH_LONG).show();

                } else {

                    updateUI();
                    startActivity(new Intent(getApplicationContext(), MemberPanelAdvActivity.class));
                    Toast.makeText(RegisteraionActivity.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }
        });



    }

    private void updateUI() {

        FirebaseDatabase.getInstance().getReference().child("members").push() ;
        String key = FirebaseDatabase.getInstance().getReference().child("members").push().getKey();
        current_user_db = FirebaseDatabase.getInstance().getReference().child("members").child(key);

        HashMap map = new HashMap();
        map.put("name", name_edit.getText().toString());
        map.put("telephone", telephone_edit.getText().toString() );
        map.put("password", password_edit.getText().toString());
        map.put("email", email_edit.getText().toString());
        map.put("fees", "free");

        current_user_db.updateChildren(map);

        currentUserPref = new StringCurrentUserPref(RegisteraionActivity.this) ;
        currentUserPref.storeUser(key);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_test, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext() , LoginActivity.class));
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(RegisteraionActivity.this , LoginActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        currentUserPref = new StringCurrentUserPref(this);

        if (currentUserPref.getUser() != null) {
            Intent intent = new Intent(RegisteraionActivity.this, MemberPanelAdvActivity.class);
            startActivity(intent);
            Toast.makeText(this, "اهلا بك", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
