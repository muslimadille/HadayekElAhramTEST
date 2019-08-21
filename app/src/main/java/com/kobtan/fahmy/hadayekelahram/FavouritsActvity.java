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

import java.util.ArrayList;
import java.util.Set;
import java.util.Timer;

public class FavouritsActvity extends AppCompatActivity {

    private ListView list ;
    private ArrayList<String> itemname = new ArrayList<>() ;
    private ArrayList<String> itemprice = new ArrayList<>() ;
    private ArrayList<String> itemtel = new ArrayList<>();
    private ArrayList<String> itemdate = new ArrayList<>();
    private ArrayList<String> itemimg = new ArrayList<>();
    private ArrayList<String> itemgate = new ArrayList<>();

    private ArrayList<String> itemkey = new ArrayList<>();
    private ArrayList<String> itemkeyMember = new ArrayList<>();

    private TextView title ;
    private Typeface typeface ;
    private String newString , newStringtwo;

    private CustomListAdapterBuy adapter ;
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




            mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("buy");


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
                    if (childSnapShot.hasChildren()) {
                    for (DataSnapshot subSnapShot : childSnapShot.getChildren()) {
                        if (subSnapShot.getKey().equals("name")) {

                            if (colorsSet != null) {
                                for (String color : colorsSet) {
                                    if (childSnapShot.getKey().equals(color)) {

                                        itemname.add(subSnapShot.getValue().toString());
                                        itemtel.add(childSnapShot.child("telephone").getValue().toString());
                                        itemprice.add(childSnapShot.child("price").getValue().toString());
                                        itemdate.add(childSnapShot.child("date").getValue().toString());
                                        itemgate.add(childSnapShot.child("gate").getValue().toString());
                                        itemkeyMember.add(childSnapShot.child("memberID").getValue().toString());


                                        if (childSnapShot.hasChild("img_0")) {
                                            itemimg.add(childSnapShot.child("img_0").getValue().toString());
                                        } else {
                                            itemimg.add("waiting");
                                        }
                                        itemkey.add(childSnapShot.getKey().toString());

                                    }
                                }



                            }
                        }
                    }
                    }
                    //Log.i("Ameeer" , childSnapShot.toString() ) ;

                }
                adapter = new CustomListAdapterBuy(FavouritsActvity.this, itemname , itemprice, itemimg , itemtel , itemdate, itemgate, itemkey);
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
        i.putExtra("STRING_I_NEED3", "buy");
        startActivity(i);
        finish();
    }

    private void backky () {
        ImageView back  = (ImageView) findViewById(R.id.backyy) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("STRING_I_NEED3", "buy");
                startActivity(i);
                finish();
            }
        });

    }
}
