package com.kobtan.fahmy.hadayekelahram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class SignToSaratnActivity extends AppCompatActivity {


    private Toolbar toolbar ;
    private Typeface typeface ;
    private EditText telephoneNumber_editText , name_editText ;
    private TextView textDate ;
    private DatabaseReference current_user_db ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_to_saratn);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" ");
        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");

        telephoneNumber_editText = (EditText) findViewById(R.id.input_telephone) ;
        name_editText = (EditText) findViewById(R.id.input_name) ;
        textDate = (TextView) findViewById(R.id.textDate) ;

        telephoneNumber_editText.setTypeface(typeface);
        name_editText.setTypeface(typeface);

        Button login = (Button) findViewById(R.id.btn_login) ;
        login.setTypeface(typeface);


        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> items = new ArrayList<String>();

                items.add("يوم الاحد");
                items.add("يوم الاثنين");
                items.add("يوم الثلاثاء");
                items.add("يوم الاربع");
                items.add("يوم الخميس");
                items.add("يوم الجمعة");
                items.add("يوم السبت");


                final String[] list = getStringArray(items);

                AlertDialog sMatrial = new AlertDialog.Builder(SignToSaratnActivity.this)
                        .setTitle("اختر اليوم")
                        .setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                textDate.setText(list[which]);
                            }
                        })
                        .show();


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(SignToSaratnActivity.this,
                        R.style.Theme_AppCompat_DayNight_Dialog);

                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("جاري التسجيل...");
                progressDialog.show();


                final String name = name_editText.getText().toString();
                final String telephone = telephoneNumber_editText.getText().toString();


                if (name.equals("") || telephone.equals("")) {

                    progressDialog.dismiss();
                    Toast.makeText(SignToSaratnActivity.this, "من فضلك ادخل البيانات كاملة اولا", Toast.LENGTH_LONG).show();

                } else {
                    updateUI();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(SignToSaratnActivity.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }
        });





    }


    private void updateUI() {

        FirebaseDatabase.getInstance().getReference().child("patients").push() ;
        String key = FirebaseDatabase.getInstance().getReference().child("patients").push().getKey();
        current_user_db = FirebaseDatabase.getInstance().getReference().child("patients").child(key);

        HashMap map = new HashMap();
        map.put("name", name_editText.getText().toString());
        map.put("telephone", telephoneNumber_editText.getText().toString() );
        map.put("date", textDate.getText().toString());
        map.put("status", "no");

        current_user_db.updateChildren(map);


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
                startActivity(new Intent(SignToSaratnActivity.this , MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static String[] getStringArray(ArrayList<String> arr)
    {

        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
    }


}
