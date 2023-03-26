package com.kobtan.fahmy.hadayekelahram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kobtan.fahmy.hadayekelahram.News.NewsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.Timer;

public class FavouritsActvity extends AppCompatActivity {

    private ListView list ;
    private ArrayList<String> itemname = new ArrayList<>() ;
    private ArrayList<String> itemTelephone = new ArrayList<>() ;
    private ArrayList<String> itemDetails = new ArrayList<>() ;
    private ArrayList<String> itemPostImages = new ArrayList<>() ;
    private ArrayList<String> itemLogoImages = new ArrayList<>() ;
    private ArrayList<String> itemaddress = new ArrayList<>() ;
    private ArrayList<String> itemdate = new ArrayList<>() ;
    private ArrayList<String> itemkey = new ArrayList<>() ;


    private TextView title ;
    private Typeface typeface ;
    private String newString , newStringtwo;

    private CustomListAdapterNews adapter ;
    private DatabaseReference mCustomerDatabase;

    private SharedPreferences mSharedPreferences ;

    private Set<String> colorsSet ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourits_actvity);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");
        backky();


        this.mSharedPreferences = getSharedPreferences( "mmkk" , MODE_PRIVATE);
        // Read the values from SharedPreferences
        colorsSet = mSharedPreferences.getStringSet(
                getResources().getString(R.string.sp_key_colors),
                null
        );

        title = (TextView) findViewById(R.id.titlee) ;
        title.setTypeface(typeface);
        list = (ListView) findViewById(R.id.list);




            mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("news");


            getUserInfo();


            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        final int position, long id) {

                    String selecteditem = itemkey.get(position);


                    /*Intent i = new Intent(getApplicationContext() , AdvObjectPreviewActivity.class);
                    i.putExtra("STRING_I_NEED", newStringtwo ) ;
                    i.putExtra("STRING_I_NEED3", itemkey.get(position) ) ;
                    i.putExtra("STRING_I_NEED2", itemkeyMember.get(position) ) ;
                    startActivity(i); */

                    Toast.makeText(FavouritsActvity.this, itemkey.toString(), Toast.LENGTH_LONG).show();

                }
            });


    }


    private void getUserInfo(){
        mCustomerDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    if (childSnapShot.getKey().equals("name")) {

                        if (colorsSet != null) {
                            for (String color : colorsSet) {
                                if (dataSnapshot.getKey().equals(color)) {


                        itemname.add(childSnapShot.getValue().toString());
                        itemTelephone.add(dataSnapshot.child("telephone").getValue().toString());
                        itemDetails.add(dataSnapshot.child("details").getValue().toString());
                        if (dataSnapshot.hasChild("post_img")) {
                            itemPostImages.add(dataSnapshot.child("post_img").getValue().toString());
                        } else {
                            itemPostImages.add("waiting");
                        }
                        if (dataSnapshot.hasChild("address")) {
                            itemaddress.add(dataSnapshot.child("address").getValue().toString());
                        } else {
                            itemaddress.add("address");
                        }
                        if (dataSnapshot.hasChild("date")) {
                            itemdate.add(dataSnapshot.child("date").getValue().toString());
                        } else {
                            itemdate.add("1-2-2020");
                        }

                        itemLogoImages.add(dataSnapshot.child("profile_img").getValue().toString());


                        itemkey.add(dataSnapshot.getKey().toString());

                    }
                }}}

                }

                Collections.reverse(itemname);
                Collections.reverse(itemTelephone);
                Collections.reverse(itemDetails);
                Collections.reverse(itemPostImages);
                Collections.reverse(itemLogoImages);
                Collections.reverse(itemaddress);
                Collections.reverse(itemdate);
                Collections.reverse(itemkey);


                adapter = new CustomListAdapterNews(FavouritsActvity.this, itemname , itemTelephone, itemDetails , itemPostImages , itemLogoImages , itemaddress , itemdate , itemkey );
                list.setAdapter(adapter);

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
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        //i.putExtra("STRING_I_NEED3", "buy");
        startActivity(i);
        finish();
    }

    private void backky () {
        ImageView back  = (ImageView) findViewById(R.id.backyy) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                //i.putExtra("STRING_I_NEED3", "buy");
                startActivity(i);
                finish();
            }
        });

    }
}
