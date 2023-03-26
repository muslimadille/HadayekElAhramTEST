package com.kobtan.fahmy.hadayekelahram.uberuser;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kobtan.fahmy.hadayekelahram.R;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class VerifyPhoneActivity extends AppCompatActivity {

    private String mVerificationId;
    private EditText editTextCode;
    private FirebaseAuth mAuth;
    //private TextView go_sign_in_txt ;
    private static String TAG = "NUM VER";
    private static String KEY_AUTH = "11414107a0b3524e5e4315a9b9f524b72e3b0b3c64e97c79c61b5464460d6574";
    private String mobile ;
    private ImageButton back_btn ;
    DatabaseReference database ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        database =FirebaseDatabase.getInstance().getReference().child("uber_users") ;

        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        Toast.makeText(this, mobile, Toast.LENGTH_SHORT).show();
        initializeView();
        //test amir
        sendVerificationCode(mobile);

    }

    //Initialize
    public void initializeView () {
        //initializing object
        back_btn = findViewById(R.id.back_btn);

        mAuth = FirebaseAuth.getInstance();
        editTextCode = findViewById(R.id.editTextCode);
        //editTextCode.setEnabled(false);
        editTextCode.setFocusable(false);
       // go_sign_in_txt =  findViewById(R.id.go_sign_in_txt);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    editTextCode.setError("Enter valid code");
                    editTextCode.requestFocus();
                    return;
                }
                verifyVerificationCode(editTextCode.getText().toString());

            }
        });
//        go_sign_in_txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext() , LoginUserActivity.class));
//            }
//
//        });

    }



    private void createUserData() {

    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();
            //Toast.makeText(VerifyPhoneActivity.this, "Success", Toast.LENGTH_LONG).show();
            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            //editTextCode.setEnabled(true);

            if (code != null) {
                editTextCode.setText(code);
                //verifying the code
                //verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
            //amir da el fix bta3 moshkelet el code ba3d el code sent
            editTextCode.setFocusableInTouchMode(true);

        }
    };
    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPhoneActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            //Toast.makeText(VerifyPhoneActivity.this, mAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(VerifyPhoneActivity.this, RegisterUserDetailsActivity.class);
                            String token  = mAuth.getCurrentUser().getUid();
                            //intent.putExtra("mobile_number" , mobile) ;
                            //intent.putExtra("token" , token) ;
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //startActivity(intent);

                            database.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChild(token)) {
                                        Toast.makeText(VerifyPhoneActivity.this, "Has Child Exist", Toast.LENGTH_SHORT).show();
                                        database.child(token).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.hasChild("type")) {
                                                    if (dataSnapshot.child("type").getValue().toString().equals("user")) {
                                                        startActivity(new Intent(getApplicationContext() , UserUberMainActivity.class));
                                                    }else {
                                                        Toast.makeText(VerifyPhoneActivity.this, "Your account is not a user profile", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(VerifyPhoneActivity.this, "Not Has Child", Toast.LENGTH_SHORT).show();
                                        createNewUser(token);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                        }
                    }
                });
    }
    private void createNewUser(String token) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final EditText edittext = new EditText(this);
        alert.setTitle("Please Enter Your Name");

        alert.setView(edittext);

        alert.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                String YouEditTextValue = edittext.getText().toString();

                if (YouEditTextValue.equals(""))
                {
                    YouEditTextValue = "User Name" ;
                }

                HashMap hashMap = new HashMap();
                hashMap.put("type" ,"user");
                hashMap.put("name" ,YouEditTextValue);
                hashMap.put("valid" ,"on");

                database.child(token).setValue(hashMap) ;
                Toast.makeText(VerifyPhoneActivity.this, "Hello " + YouEditTextValue, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext() , UserUberMainActivity.class));
            }
        });


        alert.show();
    }

}