package com.kobtan.fahmy.hadayekelahram.uberuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


import com.hbb20.CountryCodePicker;
import com.kobtan.fahmy.hadayekelahram.R;

public class CheckTelephoneUserActivity extends AppCompatActivity {

    private EditText editTextMobile;
    private CountryCodePicker ccp;
    private ImageButton back_btn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_telephone_user);
        initializeView() ;
    }
    private void initializeView() {
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        editTextMobile = findViewById(R.id.editTextMobile);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // go_sign_in_txt =  findViewById(R.id.go_sign_in_txt);


        ccp.registerCarrierNumberEditText(editTextMobile);
        ccp.setNumberAutoFormattingEnabled(false);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = editTextMobile.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length() < 10){
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }

                Intent intent = new Intent(CheckTelephoneUserActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("mobile", ccp.getFullNumberWithPlus());

                startActivity(intent);
            }
        });

       /* go_sign_in_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , LoginUserActivity.class));
            }
        });*/
    }
}