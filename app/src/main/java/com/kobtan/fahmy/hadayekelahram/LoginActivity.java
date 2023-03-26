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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {


    private Toolbar toolbar ;
    private Typeface typeface ;
    private EditText telephoneNumber_editText , password_editText ;

    private ProgressDialog progressDialog ;

    private DatabaseReference mCustomerDatabase;

    private StringCurrentUserPref currentUserPref ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" ");
        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");

        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("members");

        telephoneNumber_editText = (EditText) findViewById(R.id.input_telephone) ;
        password_editText = (EditText) findViewById(R.id.input_password) ;

        telephoneNumber_editText.setTypeface(typeface);
        password_editText.setTypeface(typeface);
        Button login = (Button) findViewById(R.id.btn_login) ;
        login.setTypeface(typeface);
        TextView signUp = (TextView) findViewById(R.id.link_signup) ;
        signUp.setTypeface(typeface);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , RegisteraionActivity.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loginn();
            }
        });



    }


    public void loginn() {


        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("جاري الدخول...");
        progressDialog.show();

        final String telephone = telephoneNumber_editText.getText().toString();
        final String password = password_editText.getText().toString();


        if (telephone.equals("") || password.equals("")) {

            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, "من فضلك ادخل البيانات كاملة اولا", Toast.LENGTH_LONG).show();

        } else {
            signIn(telephone , password);
        }
    }

    private void signIn(final String telephone , final String password)
    {


        mCustomerDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    if (childSnapShot.getKey().equals("telephone")) {
                        if (dataSnapshot.child("telephone").getValue().toString().equals(telephone)) {
                            if (dataSnapshot.child("password").getValue().toString().equals(password)) {


                                String key = dataSnapshot.getKey().toString();

                                currentUserPref = new StringCurrentUserPref(LoginActivity.this) ;
                                currentUserPref.storeUser(key);

                                startActivity(new Intent(getApplicationContext(), MemberPanelAdvActivity.class));

                                progressDialog.dismiss();
                            }

                        }
                    }
                }
                if (currentUserPref.getUser() == null){
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }) ;

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(LoginActivity.this , MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        currentUserPref = new StringCurrentUserPref(this);

        if (currentUserPref.getUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MemberPanelAdvActivity.class);
            startActivity(intent);
            Toast.makeText(this, "اهلا بك", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
